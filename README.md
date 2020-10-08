# Gradle Minify Plugin!
[![Build Status](https://travis-ci.com/616slayer616/gradle-minify-plugin.svg?branch=master)](https://travis-ci.com/616slayer616/gradle-minify-plugin)
[![Quality Gate Status](https://sonarcloud.io/api/project_badges/measure?project=616slayer616_gradle-minify-plugin&metric=alert_status)](https://sonarcloud.io/dashboard?id=616slayer616_gradle-minify-plugin)

[![Bugs](https://sonarcloud.io/api/project_badges/measure?project=616slayer616_gradle-minify-plugin&metric=bugs)](https://sonarcloud.io/dashboard?id=616slayer616_gradle-minify-plugin)
[![Code Smells](https://sonarcloud.io/api/project_badges/measure?project=616slayer616_gradle-minify-plugin&metric=code_smells)](https://sonarcloud.io/dashboard?id=616slayer616_gradle-minify-plugin)
[![Vulnerabilities](https://sonarcloud.io/api/project_badges/measure?project=616slayer616_gradle-minify-plugin&metric=vulnerabilities)](https://sonarcloud.io/dashboard?id=616slayer616_gradle-minify-plugin)
[![Coverage](https://sonarcloud.io/api/project_badges/measure?project=616slayer616_gradle-minify-plugin&metric=coverage)](https://sonarcloud.io/dashboard?id=616slayer616_gradle-minify-plugin)

A simple gradle plugin to minify CSS and JavaScript files.

Uses [Google Closure Compiler](https://github.com/google/closure-compiler) and [Google Closure Stylesheets
](https://github.com/google/closure-stylesheets) for minification.

# Getting started

### Gradle
```groovy
plugins {
  id "org.padler.gradle.minify" version "1.7.0"
}
```

#### Legacy plugin application
```groovy
buildscript {
  repositories {
    maven {
      url "https://plugins.gradle.org/m2/"
    }
  }
  dependencies {
    classpath "gradle.plugin.org.padler.gradle.minify:gradle-minify-plugin:1.7.0"
  }
}

apply plugin: "org.padler.gradle.minify"
```

### Configuration
````groovy
minification {
    cssDstDir = "$buildDir/dist/css"
    cssSrcDir = "${rootDir}/src/main/resources/static/css"
    jsDstDir = "$buildDir/dist/js"
    jsSrcDir = "${rootDir}/src/main/resources/static/js"

    createCssSourceMaps = true
    createJsSourceMaps = true

    css {
        cssRenamingPrefix = ""
    }

    js {
        compilationLevel = "SIMPLE"
    }   
}
````

## Options
| option                      | effect                                                     | values                           | default                      |
| --------------------------- | :--------------------------------------------------------: | -------------------------------: | ---------------------------: |
| createCssSourceMaps         | Creates CSS source maps                                    | true, false                      | false                        |
| createJsSourceMaps          | Creates JS source maps                                     | true, false                      | false                        |
| originalFileNames           | Preserves filenames instead of renaming to .min.           | true, false                      | false                        |
| css                         | Sets CSS minifier options                                  | [css options](#css-options)      | [css options](#css-options)  |
| js                          | Sets JS minifier options                                   | [js options](#js-options)        | [js options](#js-options)    |

### CSS options
| option                        | effect                                                     | values                              | default                 |
| ---------------------------   | :--------------------------------------------------------: | -------------------------------:    | ----------------------: |
| inputOrientation              | inputOrientation                                           | JobDescription.InputOrientation     | LTR                     |
| outputOrientation             | outputOrientation                                          | JobDescription.OutputOrientation    | LTR                     |
| outputFormat                  | outputFormat                                               | JobDescription.OutputFormat         | COMPRESSED              |
| copyrightNotice               | copyrightNotice                                            | String                              | null                    |
| trueConditionNames            | trueConditionNames                                         | list of strings                     | empty list              |
| allowDefPropagation           | allowDefPropagation                                        | true, false                         | true                    |
| allowUnrecognizedFunctions    | allowUnrecognizedFunctions                                 | true, false                         | true                    |
| allowedNonStandardFunctions   | allowedNonStandardFunctions                                | list of strings                     | empty list              |
| allowedUnrecognizedProperties | allowedUnrecognizedProperties                              | list of strings                     | empty list              |
| allowUnrecognizedProperties   | allowUnrecognizedProperties                                | true, false                         | true                    |
| vendor                        | vendor                                                     | Vendor                              | null                    |
| allowKeyframes                | allowKeyframes                                             | true, false                         | true                    |
| allowWebkitKeyframes          | allowWebkitKeyframes                                       | true, false                         | true                    |
| processDependencies           | processDependencies                                        | true, false                         | true                    |
| excludedClassesFromRenaming   | excludedClassesFromRenaming                                | list of strings                     | empty list              |
| simplifyCss                   | simplifyCss                                                | true, false                         | true                    |
| eliminateDeadStyles           | eliminateDeadStyles                                        | true, false                         | false                   |
| cssRenamingPrefix             | CSS renaming prefix                                        | String                              | empty string            |
| preserveComments              | preserveComments                                           | true, false                         | false                   |
| outputRenamingMapFormat       | outputRenamingMapFormat                                    | OutputRenamingMapFormat             | JSON                    |
| compileConstants              | compileConstants                                           | map                                 | empty map               |
| sourceMapLevel                | sourceMapLevel                                             | JobDescription.SourceMapDetailLevel | DEFAULT                 |

### JS options
See [Google Closure Compiler](https://github.com/google/closure-compiler/wiki/Flags-and-Options) for more information
| option                         | effect                                                                                                                     | values                                                                                                                                                                                      | default                                                  |
| ---------------------------    | :--------------------------------------------------------:                                                                 | -------------------------------:                                                                                                                                                            | ----------------------:                                  |
| compilationLevel               | Specifies the compilation level to use                                                                                     | BUNDLE, WHITESPACE_ONLY, SIMPLE, ADVANCED                                                                                                                                                   | SIMPLE                                                   |
| env                            | Determines the set of builtin externs to load                                                                              | BROWSER, CUSTOM                                                                                                                                                                             | BROWSER                                                  |
| languageIn                     | Sets the language spec to which input sources should conform                                                               | CMASCRIPT3, ECMASCRIPT5, ECMASCRIPT5_STRICT, ECMASCRIPT6_TYPED (experimental), ECMASCRIPT_2015, ECMASCRIPT_2016, ECMASCRIPT_2017, ECMASCRIPT_2018, ECMASCRIPT_2019, STABLE, ECMASCRIPT_NEXT |                                                          |
| languageOut                    | Sets the language spec to which output should conform                                                                      | CMASCRIPT3, ECMASCRIPT5, ECMASCRIPT5_STRICT, ECMASCRIPT6_TYPED (experimental), ECMASCRIPT_2015, ECMASCRIPT_2016, ECMASCRIPT_2017, ECMASCRIPT_2018, ECMASCRIPT_2019, STABLE, ECMASCRIPT_NEXT |                                                          |
| warningLevel                   | Specifies the warning level to use                                                                                         | QUIET, DEFAULT, VERBOSE                                                                                                                                                                     | QUIET                                                    |
| extraAnnotationName            | A whitelist of tag names in JSDoc                                                                                          | list of strings                                                                                                                                                                             | null                                                     |
| strictModeInput                | Assume input sources are to run in strict mode.                                                                            | true, false                                                                                                                                                                                 | false                                                    |
| debug                          | Enable debugging options                                                                                                   | true, false                                                                                                                                                                                 | false                                                    |
| exportLocalPropertyDefinitions | Generates export code for local properties marked with @export                                                             | true, false                                                                                                                                                                                 | false                                                    |
| formatting                     | Specifies which formatting options, if any, should be applied to the output JS                                             | PRETTY_PRINT, PRINT_INPUT_DELIMITER, SINGLE_QUOTES                                                                                                                                          | null                                                     |
| generateExports                | Generates export code for those marked with @export                                                                        | true, false                                                                                                                                                                                 | false                                                    |
| renamePrefixNamespace          | Specifies the name of an object that will be used to store all non-extern globals                                          | string                                                                                                                                                                                      | null                                                     |
| renameVariablePrefix           | Specifies a prefix that will be prepended to all variables                                                                 | string                                                                                                                                                                                      | null                                                     |
| moduleResolution               | Specifies how the compiler locates modules                                                                                 | BROWSER, BROWSER_WITH_TRANSFORMED_PREFIXES , NODE , WEBPACK                                                                                                                                 | BROWSER                                                  |
| processCommonJsModules         | Process CommonJS modules to a concatenable form                                                                            | true, false                                                                                                                                                                                 | false                                                    |
| packageJsonEntryNames          | Ordered list of entries to look for in package.json files when processing modules with the NODE module resolution strategy | list of strings                                                                                                                                                                             | ["browser", "module", "main"]                            |
| angularPass                    | Generate $inject properties for AngularJS for functions annotated with @ngInject                                           | true, false                                                                                                                                                                                 | false                                                    |
| dartPass                       | Rewrite Dart Dev Compiler output to be compiler-friendly                                                                   | true, false                                                                                                                                                                                 | false                                                    |
| forceInjectLibrary             | Force injection of named runtime libraries. The format is <name> where <name> is the name of a runtime library             | base, es6_runtime, runtime_type_check                                                                                                                                                       | empty list                                               |
| polymerVersion                 | Which version of Polymer is being used                                                                                     | 1, 2                                                                                                                                                                                        | null                                                     |
| rewritePolyfills               | Rewrite ES6 library calls to use polyfills provided by the compiler's runtime                                              | true, false                                                                                                                                                                                 | false                                                    |
| charset                        | Input and output charset for all files                                                                                     | charset                                                                                                                                                                                     | By default, we accept UTF-8 as input and output US_ASCII |
| checksOnly                     | Don't generate output. Run checks, but no optimization passes                                                              | true, false                                                                                                                                                                                 | false                                                    |
| browserFeaturesetYear          | Browser feature set year                                                                                                   | 2012, 2019, 2020                                                                                                                                                                            | 0                                                        |

## See Also
The [Gradle CSS Plugin](https://github.com/eriwen/gradle-css-plugin)!

The [Gradle JS Plugin](https://github.com/eriwen/gradle-js-plugin)!
