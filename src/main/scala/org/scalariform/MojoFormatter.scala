package org.scalariform

import java.io.{File, FileFilter}

import org.apache.maven.plugin.logging.Log
import org.codehaus.plexus.util.{FileUtils, ReaderFactory}

import scala.io.{Codec, Source}
import scalariform.formatter.ScalaFormatter
import scalariform.formatter.preferences._
import scalariform.parser.ScalaParserException

/**
  * Goal which formats scala source files.
  */
object MojoFormatter {

  private val scalaFilter = new FileFilter {
    def accept(file: File): Boolean =
      file.isFile &&
        file.getName.endsWith(".scala")
  }

  private val dirFilter = new FileFilter() {
    def accept(dir: File): Boolean = dir.isDirectory
  }

  private def findScalaFilesByFile(path: File, foundScalaFiles: List[File]): List[File] = {
    val scalaFiles = Option(path.listFiles(scalaFilter))
      .toList
      .flatMap(_.iterator)
    val newFoundScalaFiles = scalaFiles ::: foundScalaFiles

    val subDirs = Option(path.listFiles(dirFilter))
      .toList
      .flatMap(_.iterator)
    subDirs.foldLeft(newFoundScalaFiles) { (sum, dir) =>
      findScalaFilesByFile(dir, sum)
    }
  }

  private def fromEither[T](descriptor: PreferenceDescriptor[T], either: Either[String, T]): T = {
    either.getOrElse {
      val msg = either.left.getOrElse(s"Failed to parse value for key: ${descriptor.key}")
      throw new RuntimeException(msg)
    }
  }

  def format(log: Log,
             sourceDirectory: File,
             testSourceDirectory: File,
             alignArguments: Boolean,
             alignParameters: Boolean,
             alignSingleLineCaseStatements: Boolean,
             alignSingleLineCaseStatements_maxArrowIndent: Int,
             allowParamGroupsOnNewlines: Boolean,
             compactControlReadability: Boolean,
             compactStringConcatenation: Boolean,
             danglingCloseParenthesis: String,
             doubleIndentConstructorArguments: Boolean,
             doubleIndentMethodDeclaration: Boolean,
             firstArgumentOnNewline: String,
             firstParameterOnNewline: String,
             formatXml: Boolean,
             indentLocalDefs: Boolean,
             indentPackageBlocks: Boolean,
             indentSpaces: Int,
             indentWithTabs: Boolean,
             multilineScaladocCommentsStartOnFirstLine: Boolean,
             newlineAtEndOfFile: Boolean,
             placeScaladocAsterisksBeneathSecondAsterisk: Boolean,
             preserveSpaceBeforeArguments: Boolean,
             rewriteArrowSymbols: Boolean,
             singleCasePatternOnNewline: Boolean,
             spaceBeforeColon: Boolean,
             spaceBeforeContextColon: Boolean,
             spaceInsideBrackets: Boolean,
             spaceInsideParentheses: Boolean,
             spacesAroundMultiImports: Boolean,
             spacesWithinPatternBinders: Boolean): Unit = {
    val prefs = Map[PreferenceDescriptor[_], Any](
      AlignArguments -> alignArguments,
      AlignParameters -> alignParameters,
      AlignSingleLineCaseStatements -> alignSingleLineCaseStatements,
      AlignSingleLineCaseStatements.MaxArrowIndent -> alignSingleLineCaseStatements_maxArrowIndent,
      AllowParamGroupsOnNewlines -> allowParamGroupsOnNewlines,
      CompactControlReadability -> compactControlReadability,
      CompactStringConcatenation -> compactStringConcatenation,
      DanglingCloseParenthesis -> fromEither(DanglingCloseParenthesis, IntentPreference.parseValue(danglingCloseParenthesis)),
      DoubleIndentConstructorArguments -> doubleIndentConstructorArguments,
      DoubleIndentMethodDeclaration -> doubleIndentMethodDeclaration,
      FirstArgumentOnNewline -> fromEither(FirstArgumentOnNewline, IntentPreference.parseValue(firstArgumentOnNewline)),
      FirstParameterOnNewline -> fromEither(FirstParameterOnNewline, IntentPreference.parseValue(firstParameterOnNewline)),
      FormatXml -> formatXml,
      IndentLocalDefs -> indentLocalDefs,
      IndentPackageBlocks -> indentPackageBlocks,
      IndentSpaces -> indentSpaces,
      IndentWithTabs -> indentWithTabs,
      MultilineScaladocCommentsStartOnFirstLine -> multilineScaladocCommentsStartOnFirstLine,
      NewlineAtEndOfFile -> newlineAtEndOfFile,
      PlaceScaladocAsterisksBeneathSecondAsterisk -> placeScaladocAsterisksBeneathSecondAsterisk,
      PreserveSpaceBeforeArguments -> preserveSpaceBeforeArguments,
      RewriteArrowSymbols -> rewriteArrowSymbols,
      SingleCasePatternOnNewline -> singleCasePatternOnNewline,
      SpaceBeforeColon -> spaceBeforeColon,
      SpaceBeforeContextColon -> spaceBeforeContextColon,
      SpaceInsideBrackets -> spaceInsideBrackets,
      SpaceInsideParentheses -> spaceInsideParentheses,
      SpacesAroundMultiImports -> spacesAroundMultiImports,
      SpacesWithinPatternBinders -> spacesWithinPatternBinders
    )
    log.debug(prefs.mkString("Scalariform preferences:\n", "\n", ""))
    val preferences = new FormattingPreferences(prefs)

    val files = findScalaFilesByFile(sourceDirectory, Nil) :::
      findScalaFilesByFile(testSourceDirectory, Nil)

    log.info(s"Formatting ${files.size} files")
    log.debug("Source dir: " + sourceDirectory)
    log.debug("Test source dir: " + testSourceDirectory)
    log.debug(files.mkString("Files to format:\n", "\n", ""))

    val encoding = ReaderFactory.FILE_ENCODING
    implicit val codec: Codec = Codec(encoding)

    def formatFile(file: File): Int = {
      try {
        val original = Source.fromFile(file).mkString
        val formatted = ScalaFormatter.format(original, preferences)
        if (original != formatted) {
          FileUtils.fileWrite(file, encoding, formatted)
          1
        } else 0
      } catch {
        case ex: ScalaParserException =>
          log.error("Error parsing Scala code in " + file + ": " + ex.getMessage)
          0
        case ex: Exception =>
          log.error("Error formatting " + file + ": " + ex)
          0
      }
    }

    val modifiedFiles = files.map(formatFile).sum
    log.info("Modified " + modifiedFiles + " of " + files.size + " files")
  }

}
