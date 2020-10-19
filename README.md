# Gradle Minify Plugin!

[![Build Status](https://travis-ci.com/gradle-webtools/gradle-minify-plugin.svg?branch=master)](https://travis-ci.com/gradle-webtools/gradle-minify-plugin)
[![Quality Gate Status](https://sonarcloud.io/api/project_badges/measure?project=gradle-webtools_gradle-minify-plugin&metric=alert_status)](https://sonarcloud.io/dashboard?id=gradle-webtools_gradle-minify-plugin)

[![Bugs](https://sonarcloud.io/api/project_badges/measure?project=gradle-webtools_gradle-minify-plugin&metric=bugs)](https://sonarcloud.io/dashboard?id=gradle-webtools_gradle-minify-plugin)
[![Code Smells](https://sonarcloud.io/api/project_badges/measure?project=gradle-webtools_gradle-minify-plugin&metric=code_smells)](https://sonarcloud.io/dashboard?id=gradle-webtools_gradle-minify-plugin)
[![Vulnerabilities](https://sonarcloud.io/api/project_badges/measure?project=gradle-webtools_gradle-minify-plugin&metric=vulnerabilities)](https://sonarcloud.io/dashboard?id=gradle-webtools_gradle-minify-plugin)
[![Coverage](https://sonarcloud.io/api/project_badges/measure?project=gradle-webtools_gradle-minify-plugin&metric=coverage)](https://sonarcloud.io/dashboard?id=gradle-webtools_gradle-minify-plugin)

A simple gradle plugin to minify CSS and JavaScript files.

Uses [Google Closure Compiler](https://github.com/google/closure-compiler) and [Google Closure Stylesheets
](https://github.com/google/closure-stylesheets) for minification.

## Getting started

### Add Plugin

```kottin
plugins {
  id("org.gradle-webtools.minify") version "<version>"
}
```

### Default Task Configuration

#### Minimal Configuration

```kotlin
minification {
    js { //this: JsMinifyTask
        srcDir = project.file("js")
        dstDir = project.file("build/js")
    }
    css { //this: CssMinifyTask
        srcDir = project.file("css")
        dstDir = project.file("build/css")
    }
}
```

### Tasks

If you need more than one minification task's you should add them manually. Therefore, this plugin provides the task types `JsMinifyTask` and `CssMinifyTask`.

#### Creating additional JsMinifyTask

```kotlin
tasks.create<JsMinifyTask>("additionalJsMinify") { //this: JsMinifyTask
    srcDir = project.file("js")
    dstDir = project.file("build/js")
}
```

#### Creating additional CssMinifyTask

```kotlin
tasks.create<CssMinifyTask>("additionalCssMinify") { //this: CssMinifyTask
    srcDir = project.file("css")
    dstDir = project.file("build/css")
}
```

#### Options

##### JsMinifyTask

```kotlin
tasks.create<JsMinifyTask>("additionalJsMinify") { //this: JsMinifyTask
    srcDir = project.file("js")
    dstDir = project.file("build/js")
    options {
        compilationLevel = CompilationLevel.SIMPLE_OPTIMIZATIONS
        env = CompilerOptions.Environment.BROWSER
        languageIn = null
        languageOut = null
        warningLevel = WarningLevel.QUIET
        extraAnnotationNames = listOf()
        strictModeInput = false
        debug = false
        exportLocalPropertyDefinitions = false
        formatting = listOf()
        generateExports = false
        renamePrefixNamespace = null
        renameVariablePrefix = null
        moduleResolution = ModuleLoader.ResolutionMode.BROWSER
        processCommonJsModules = false
        packageJsonEntryNames = listOf()
        angularPass = false
        dartPass = false
        forceInjectLibrary = listOf()
        polymerVersion = null
        rewritePolyfills = false
        charset = Charsets.UTF_8
        checksOnly = false
        browserFeaturesetYear = null
        createSourceMaps = false
        originalFileNames = false
        copyOriginalFile = false
    }
}
```

| option  | effect                     | values                    | default                   |
| ------- | -------------------------- | ------------------------- | ------------------------- |
| srcDir  | Sets source directory      | File?                     | null                      |
| dstDir  | Sets destination directory | File?                     | null                      |
| options | Sets JS minifier options   | [js options](#jsminifytask-options) | [js options](#jsminifytask-options) |

##### JsMinifyTask options

| option                         | effect                                                                                                                     | values                                                                                                                                                                                      | default                                                  |
| ------------------------------ | -------------------------------------------------------------------------------------------------------------------------- | ------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------- | -------------------------------------------------------- |
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
| angularPass                    | Generate \$inject properties for AngularJS for functions annotated with @ngInject                                          | true, false                                                                                                                                                                                 | false                                                    |
| dartPass                       | Rewrite Dart Dev Compiler output to be compiler-friendly                                                                   | true, false                                                                                                                                                                                 | false                                                    |
| forceInjectLibrary             | Force injection of named runtime libraries. The format is <name> where <name> is the name of a runtime library             | base, es6_runtime, runtime_type_check                                                                                                                                                       | empty list                                               |
| polymerVersion                 | Which version of Polymer is being used                                                                                     | 1, 2                                                                                                                                                                                        | null                                                     |
| rewritePolyfills               | Rewrite ES6 library calls to use polyfills provided by the compiler's runtime                                              | true, false                                                                                                                                                                                 | false                                                    |
| charset                        | Input and output charset for all files                                                                                     | charset                                                                                                                                                                                     | By default, we accept UTF-8 as input and output US_ASCII |
| checksOnly                     | Don't generate output. Run checks, but no optimization passes                                                              | true, false                                                                                                                                                                                 | false                                                    |
| browserFeaturesetYear          | Browser feature set year                                                                                                   | 2012, 2019, 2020                                                                                                                                                                            | 0                                                        |

See [Google Closure Compiler](https://github.com/google/closure-compiler/wiki/Flags-and-Options) for more information

##### CssMinifyTask

```kotlin
tasks.create<CssMinifyTask>("additionalCssMinify") { //this: CssMinifyTask
    srcDir = project.file("css")
    dstDir = project.file("build/css")
    options {
        inputOrientation = InputOrientation.LTR
        outputOrientation = OutputOrientation.LTR
        outputFormat = OutputFormat.COMPRESSED
        copyrightNotice = null
        trueConditionNames = listOf()
        allowDefPropagation = true
        allowUnrecognizedFunctions = true
        allowedNonStandardFunctions = listOf()
        allowedUnrecognizedProperties = listOf()
        allowUnrecognizedProperties = true
        vendor = null
        allowKeyframes = true
        allowWebkitKeyframes = true
        processDependencies = true
        excludedClassesFromRenaming = listOf()
        simplifyCss = true
        eliminateDeadStyles = false
        cssRenamingPrefix = ""
        preserveComments = false
        outputRenamingMapFormat = OutputRenamingMapFormat.JSON
        compileConstants = mapOf()
        sourceMapLevel = SourceMapDetailLevel.DEFAULT
        createSourceMaps = false
        originalFileNames = false
        copyOriginalFile = false
    }
}
```

| option  | effect                     | values                      | default                     |
| ------- | -------------------------- | --------------------------- | --------------------------- |
| srcDir  | Sets source directory      | `File?`                     | `null`                      |
| dstDir  | Sets destination directory | `File?`                     | `null`                      |
| options | Sets CSS minifier options  | [css options](#cssminifytask-options) | [css options](#cssminifytask-options) |

##### CSSMinifyTask options

| option                        | effect                        | values                              | default      |
| ----------------------------- | ----------------------------- | ----------------------------------- | ------------ |
| inputOrientation              | inputOrientation              | JobDescription.InputOrientation     | LTR          |
| outputOrientation             | outputOrientation             | JobDescription.OutputOrientation    | LTR          |
| outputFormat                  | outputFormat                  | JobDescription.OutputFormat         | COMPRESSED   |
| copyrightNotice               | copyrightNotice               | String                              | null         |
| trueConditionNames            | trueConditionNames            | list of strings                     | empty list   |
| allowDefPropagation           | allowDefPropagation           | true, false                         | true         |
| allowUnrecognizedFunctions    | allowUnrecognizedFunctions    | true, false                         | true         |
| allowedNonStandardFunctions   | allowedNonStandardFunctions   | list of strings                     | empty list   |
| allowedUnrecognizedProperties | allowedUnrecognizedProperties | list of strings                     | empty list   |
| allowUnrecognizedProperties   | allowUnrecognizedProperties   | true, false                         | true         |
| vendor                        | vendor                        | Vendor                              | null         |
| allowKeyframes                | allowKeyframes                | true, false                         | true         |
| allowWebkitKeyframes          | allowWebkitKeyframes          | true, false                         | true         |
| processDependencies           | processDependencies           | true, false                         | true         |
| excludedClassesFromRenaming   | excludedClassesFromRenaming   | list of strings                     | empty list   |
| simplifyCss                   | simplifyCss                   | true, false                         | true         |
| eliminateDeadStyles           | eliminateDeadStyles           | true, false                         | false        |
| cssRenamingPrefix             | CSS renaming prefix           | String                              | empty string |
| preserveComments              | preserveComments              | true, false                         | false        |
| outputRenamingMapFormat       | outputRenamingMapFormat       | OutputRenamingMapFormat             | JSON         |
| compileConstants              | compileConstants              | map                                 | empty map    |
| sourceMapLevel                | sourceMapLevel                | JobDescription.SourceMapDetailLevel | DEFAULT      |
