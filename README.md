[![Maven Central](https://maven-badges.herokuapp.com/maven-central/com.github.tashoyan/scalariform-maven-plugin/badge.svg)](https://maven-badges.herokuapp.com/maven-central/com.github.tashoyan/scalariform-maven-plugin)

# Scalariform Maven plugin

Maven plugin for [Scalariform](https://github.com/scala-ide/scalariform), a Scala code formatter.

### Usage

Add a section like below in your POM-file:

    <plugins>
      <plugin>
        <groupId>com.github.tashoyan</groupId>
        <artifactId>scalariform-maven-plugin</artifactId>
        <version>0.2.1</version>
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

The plugin has just one goal: `format`, by default it is bound to `process-sources` lifecycle phase.

### Settings

| Name | Default value | Description |
| ---- | ------------- | ----------- |
| `sourceDirectory` | `src/main/scala` | Directory with Scala sources |
| `testSourceDirectory` | `src/test/scala` | Directory with Scala test sources |

For Scalariform settings, see [Scalariform web page](https://github.com/scala-ide/scalariform#preferences).

### License

[MIT License](http://www.opensource.org/licenses/mit-license.php)

### Credits

Forked from [Matt Russell's repo](https://github.com/mdr/scalariform-maven-plugin) and updated to support Scalariform 0.2.x as well as Maven 3 API.

Originally contributed to Scalariform by [Adam Crain](https://github.com/jadamcrain).
