# Scalariform Maven plugin
Maven plugin for [Scalariform](https://github.com/scala-ide/scalariform), a Scala code formatter.

### Usage

Add a section like below in your POM-file:

    <plugins>
      <plugin>
        <groupId>com.github.tashoyan</groupId>
        <artifactId>scalariform-maven-plugin</artifactId>
        <version>0.2.0</version>
        <configuration>
          <firstArgumentOnNewline>Force</firstArgumentOnNewline>
          <firstParameterOnNewline>Force</firstParameterOnNewline>
          <allowParamGroupsOnNewlines>true</allowParamGroupsOnNewlines>
          <danglingCloseParenthesis>Force</danglingCloseParenthesis>
          <doubleIndentConstructorArguments>true</doubleIndentConstructorArguments>
          <doubleIndentMethodDeclaration>true</doubleIndentMethodDeclaration>
          <newlineAtEndOfFile>true</newlineAtEndOfFile>
          <placeScaladocAsterisksBeneathSecondAsterisk>true</placeScaladocAsterisksBeneathSecondAsterisk>
          <singleCasePatternOnNewline>false</singleCasePatternOnNewline>
          <spacesAroundMultiImports>false</spacesAroundMultiImports>
        </configuration>
      </plugin>
      ...
    </plugins>

For settings documentation, see [Scalariform web page](https://github.com/scala-ide/scalariform)

The plugin has just one goal: `format`, by default it is bound to `process-sources` lifecycle phase.

### License

[MIT License](http://www.opensource.org/licenses/mit-license.php)

Forked from [Matt Russell's repo](https://github.com/mdr/scalariform-maven-plugin) and updated to support Scalariform 0.2.x as well as Maven 3 API.

Originally contributed to Scalariform by Adam Crain (https://github.com/jadamcrain).
