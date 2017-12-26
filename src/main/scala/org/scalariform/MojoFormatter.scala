package org.scalariform

import java.io.{File, FileFilter, FilenameFilter}

import org.apache.maven.plugin.logging.Log
import org.codehaus.plexus.util.{FileUtils, ReaderFactory}

import scala.io.Source
import scalariform.formatter.ScalaFormatter
import scalariform.formatter.preferences._
import scalariform.parser.ScalaParserException

/**
  * Goal which formats scala source files.
  */
object MojoFormatter {

  private val scalaFilter = new FilenameFilter {
    def accept(dir: File, name: String): Boolean = name.endsWith(".scala")
  }

  private val dirFilter = new FileFilter() {
    def accept(dir: File): Boolean = dir.isDirectory
  }

  private def findScalaFilesByFile(path: File, list: List[File]): List[File] = {
    val runningList = path.listFiles(scalaFilter).toList ::: list
    path.listFiles(dirFilter).foldLeft(runningList) { (sum, dir) =>
      findScalaFilesByFile(dir, sum)
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
      DanglingCloseParenthesis -> IntentPreference.parseValue(danglingCloseParenthesis).right.get,
      DoubleIndentConstructorArguments -> doubleIndentConstructorArguments,
      DoubleIndentMethodDeclaration -> doubleIndentMethodDeclaration,
      FirstArgumentOnNewline -> IntentPreference.parseValue(firstArgumentOnNewline).right.get,
      FirstParameterOnNewline -> IntentPreference.parseValue(firstParameterOnNewline).right.get,
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
    log.debug(files.mkString("Scalariform files to format:\n", "\n", ""))

    val encoding = ReaderFactory.FILE_ENCODING

    var count = 0

    //TODO foreach with side effect
    files.foreach { file =>
      try {
        val original = Source.fromFile(file).mkString
        val formatted = ScalaFormatter.format(original, preferences)
        if (original != formatted) {
          FileUtils.fileWrite(file, encoding, formatted)
          count += 1
        }
      } catch {
        case ex: ScalaParserException =>
          log.error("Error parsing Scala " + file + ": " + ex.getMessage)
        case ex: Exception =>
          log.error("Error formatting " + file + ": " + ex)
      }
    }
    log.info("Modified " + count + " of " + files.size + " .scala files")
  }

}
