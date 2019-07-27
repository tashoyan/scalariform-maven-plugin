package org.scalariform;

import org.apache.maven.plugin.AbstractMojo;
import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.plugins.annotations.LifecyclePhase;
import org.apache.maven.plugins.annotations.Mojo;
import org.apache.maven.plugins.annotations.Parameter;

import java.io.File;

/**
 * Goal which formats scala source files
 */
@Mojo(name = "format", defaultPhase = LifecyclePhase.PROCESS_SOURCES, requiresProject = false, threadSafe = true)
public class ScalariformMojo extends AbstractMojo {

    /**
     * Project's source directory as specified in the POM.
     */
    @Parameter(defaultValue = "src/main/scala", property = "sourceDir", required = true)
    private File sourceDirectory;

    /**
     * Project's test source directory as specified in the POM.
     */
    @Parameter(defaultValue = "src/test/scala", property = "testSourceDir", required = true)
    private File testSourceDirectory;

    /**
     * See <a href="https://github.com/scala-ide/scalariform#alignarguments">alignArguments</a>.
     */
    @Parameter(defaultValue = "false", property = "scalariform.alignArguments")
    private Boolean alignArguments;

    /**
     * See <a href="https://github.com/scala-ide/scalariform#alignparameters">alignParameters</a>.
     */
    @Parameter(defaultValue = "false", property = "scalariform.alignParameters")
    private Boolean alignParameters;

    /**
     * See <a href="https://github.com/scala-ide/scalariform#alignsinglelinecasestatements">alignSingleLineCaseStatements</a>.
     */
    @Parameter(defaultValue = "false", property = "scalariform.alignSingleLineCaseStatements")
    private Boolean alignSingleLineCaseStatements;

    /**
     * See <a href="https://github.com/scala-ide/scalariform#alignsinglelinecasestatementsmaxarrowindent">alignSingleLineCaseStatements.maxArrowIndent</a>.
     */
    @Parameter(defaultValue = "40", property = "scalariform.alignSingleLineCaseStatements.maxArrowIndent")
    private Integer alignSingleLineCaseStatements_maxArrowIndent;

    /**
     * See <a href="https://github.com/scala-ide/scalariform#allowparamgroupsonnewlines">allowParamGroupsOnNewlines</a>.
     */
    @Parameter(defaultValue = "false", property = "scalariform.allowParamGroupsOnNewlines")
    private Boolean allowParamGroupsOnNewlines;

    /**
     * See <a href="https://github.com/scala-ide/scalariform#compactcontrolreadability">compactControlReadability</a>.
     */
    @Parameter(defaultValue = "false", property = "scalariform.compactControlReadability")
    private Boolean compactControlReadability;

    /**
     * See <a href="https://github.com/scala-ide/scalariform#compactstringconcatenation">compactStringConcatenation</a>.
     */
    @Parameter(defaultValue = "false", property = "scalariform.compactStringConcatenation")
    private Boolean compactStringConcatenation;

    /**
     * See <a href="https://github.com/scala-ide/scalariform#danglingcloseparenthesis">danglingCloseParenthesis</a>.
     */
    @Parameter(defaultValue = "Prevent", property = "scalariform.danglingCloseParenthesis")
    private String danglingCloseParenthesis;

    /**
     * See <a href="https://github.com/scala-ide/scalariform#doubleindentconstructorarguments">doubleIndentConstructorArguments</a>.
     */
    @Parameter(defaultValue = "false", property = "scalariform.doubleIndentConstructorArguments")
    private Boolean doubleIndentConstructorArguments;

    /**
     * See <a href="https://github.com/scala-ide/scalariform#doubleindentmethoddeclaration">doubleIndentMethodDeclaration</a>.
     */
    @Parameter(defaultValue = "false", property = "scalariform.doubleIndentMethodDeclaration")
    private Boolean doubleIndentMethodDeclaration;

    /**
     * See <a href="https://github.com/scala-ide/scalariform#firstargumentonnewline">firstArgumentOnNewline</a>.
     */
    @Parameter(defaultValue = "Force", property = "scalariform.firstArgumentOnNewline")
    private String firstArgumentOnNewline;

    /**
     * See <a href="https://github.com/scala-ide/scalariform#firstparameteronnewline">firstParameterOnNewline</a>.
     */
    @Parameter(defaultValue = "Force", property = "scalariform.firstParameterOnNewline")
    private String firstParameterOnNewline;

    /**
     * See <a href="https://github.com/scala-ide/scalariform#formatxml">formatXml</a>.
     */
    @Parameter(defaultValue = "true", property = "scalariform.formatXml")
    private Boolean formatXml;

    /**
     * See <a href="https://github.com/scala-ide/scalariform#indentlocaldefs">indentLocalDefs</a>.
     */
    @Parameter(defaultValue = "false", property = "scalariform.indentLocalDefs")
    private Boolean indentLocalDefs;

    /**
     * See <a href="https://github.com/scala-ide/scalariform#indentpackageblocks">indentPackageBlocks</a>.
     */
    @Parameter(defaultValue = "true", property = "scalariform.indentPackageBlocks")
    private Boolean indentPackageBlocks;

    /**
     * See <a href="https://github.com/scala-ide/scalariform#indentspaces">indentSpaces</a>.
     */
    @Parameter(defaultValue = "2", property = "scalariform.indentSpaces")
    private Integer indentSpaces;

    /**
     * See <a href="https://github.com/scala-ide/scalariform#indentwithtabs">indentWithTabs</a>.
     */
    @Parameter(defaultValue = "false", property = "scalariform.indentWithTabs")
    private Boolean indentWithTabs;

    /**
     * See <a href="https://github.com/scala-ide/scalariform#multilinescaladoccommentsstartonfirstline">multilineScaladocCommentsStartOnFirstLine</a>.
     */
    @Parameter(defaultValue = "false", property = "scalariform.multilineScaladocCommentsStartOnFirstLine")
    private Boolean multilineScaladocCommentsStartOnFirstLine;

    /**
     * See <a href="https://github.com/scala-ide/scalariform#newlineatendoffile">newlineAtEndOfFile</a>.
     */
    @Parameter(defaultValue = "false", property = "scalariform.newlineAtEndOfFile")
    private Boolean newlineAtEndOfFile;

    /**
     * See <a href="https://github.com/scala-ide/scalariform#placescaladocasterisksbeneathsecondasterisk">placeScaladocAsterisksBeneathSecondAsterisk</a>.
     */
    @Parameter(defaultValue = "false", property = "scalariform.placeScaladocAsterisksBeneathSecondAsterisk")
    private Boolean placeScaladocAsterisksBeneathSecondAsterisk;

    /**
     * See <a href="https://github.com/scala-ide/scalariform#preservespacebeforearguments">preserveSpaceBeforeArguments</a>.
     */
    @Parameter(defaultValue = "false", property = "scalariform.preserveSpaceBeforeArguments")
    private Boolean preserveSpaceBeforeArguments;

    /**
     * See <a href="https://github.com/scala-ide/scalariform#rewritearrowsymbols">rewriteArrowSymbols</a>.
     */
    @Parameter(defaultValue = "false", property = "scalariform.rewriteArrowSymbols")
    private Boolean rewriteArrowSymbols;

    /**
     * See <a href="https://github.com/scala-ide/scalariform#singlecasepatternonnewline">singleCasePatternOnNewline</a>.
     */
    @Parameter(defaultValue = "true", property = "scalariform.singleCasePatternOnNewline")
    private Boolean singleCasePatternOnNewline;

    /**
     * See <a href="https://github.com/scala-ide/scalariform#spacebeforecolon">spaceBeforeColon</a>.
     */
    @Parameter(defaultValue = "false", property = "scalariform.spaceBeforeColon")
    private Boolean spaceBeforeColon;

    /**
     * See <a href="https://github.com/scala-ide/scalariform#spacebeforecontextcolon">spaceBeforeContextColon</a>.
     */
    @Parameter(defaultValue = "false", property = "scalariform.spaceBeforeContextColon")
    private Boolean spaceBeforeContextColon;

    /**
     * See <a href="https://github.com/scala-ide/scalariform#spaceinsidebrackets">spaceInsideBrackets</a>.
     */
    @Parameter(defaultValue = "false", property = "scalariform.spaceInsideBrackets")
    private Boolean spaceInsideBrackets;

    /**
     * See <a href="https://github.com/scala-ide/scalariform#spaceinsideparentheses">spaceInsideParentheses</a>.
     */
    @Parameter(defaultValue = "false", property = "scalariform.spaceInsideParentheses")
    private Boolean spaceInsideParentheses;

    /**
     * See <a href="https://github.com/scala-ide/scalariform#spacesaroundmultiimports">spacesAroundMultiImports</a>.
     */
    @Parameter(defaultValue = "true", property = "scalariform.spacesAroundMultiImports")
    private Boolean spacesAroundMultiImports;

    /**
     * See <a href="https://github.com/scala-ide/scalariform#spaceswithinpatternbinders">spacesWithinPatternBinders</a>.
     */
    @Parameter(defaultValue = "true", property = "scalariform.spacesWithinPatternBinders")
    private Boolean spacesWithinPatternBinders;

    /**
     * See <a href="https://github.com/scala-ide/scalariform#useunicodearrows">useUnicodeArrows</a>.
     */
    @Parameter(defaultValue = "true", property = "scalariform.useUnicodeArrows")
    private Boolean useUnicodeArrows;

    @Override
    public void execute() throws MojoExecutionException {
        MojoFormatter.format(this.getLog(),
                sourceDirectory,
                testSourceDirectory,
                alignArguments,
                alignParameters,
                alignSingleLineCaseStatements,
                alignSingleLineCaseStatements_maxArrowIndent,
                allowParamGroupsOnNewlines,
                compactControlReadability,
                compactStringConcatenation,
                danglingCloseParenthesis,
                doubleIndentConstructorArguments,
                doubleIndentMethodDeclaration,
                firstArgumentOnNewline,
                firstParameterOnNewline,
                formatXml,
                indentLocalDefs,
                indentPackageBlocks,
                indentSpaces,
                indentWithTabs,
                multilineScaladocCommentsStartOnFirstLine,
                newlineAtEndOfFile,
                placeScaladocAsterisksBeneathSecondAsterisk,
                preserveSpaceBeforeArguments,
                rewriteArrowSymbols,
                singleCasePatternOnNewline,
                spaceBeforeColon,
                spaceBeforeContextColon,
                spaceInsideBrackets,
                spaceInsideParentheses,
                spacesAroundMultiImports,
                spacesWithinPatternBinders,
                useUnicodeArrows);
    }

}
