# Gradle Minify Plugin!

[![Build Status](https://github.com/gradle-webtools/gradle-minify-plugin/actions/workflows/master.yml/badge.svg)](https://github.com/gradle-webtools/gradle-minify-plugin/actions/workflows/master.yml)
[![Quality Gate Status](https://sonarcloud.io/api/project_badges/measure?project=gradle-minify-plugin&metric=alert_status)](https://sonarcloud.io/dashboard?id=gradle-minify-plugin)

[![Bugs](https://sonarcloud.io/api/project_badges/measure?project=gradle-minify-plugin&metric=bugs)](https://sonarcloud.io/dashboard?id=gradle-minify-plugin)
[![Code Smells](https://sonarcloud.io/api/project_badges/measure?project=gradle-minify-plugin&metric=code_smells)](https://sonarcloud.io/dashboard?id=gradle-minify-plugin)
[![Vulnerabilities](https://sonarcloud.io/api/project_badges/measure?project=gradle-minify-plugin&metric=vulnerabilities)](https://sonarcloud.io/dashboard?id=gradle-minify-plugin)
[![Coverage](https://sonarcloud.io/api/project_badges/measure?project=gradle-minify-plugin&metric=coverage)](https://sonarcloud.io/dashboard?id=gradle-minify-plugin)

A simple gradle plugin to minify CSS and JavaScript files.

Uses [Google Closure Compiler](https://github.com/google/closure-compiler) and [custom
](https://github.com/616slayer616/closure-stylesheets) [Google Closure Stylesheets
](https://github.com/google/closure-stylesheets) for minification.

## Getting started

### Add Plugin

```kottin
plugins {
  id("org.gradlewebtools.minify") version "<version>"
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

If you need more than one minification task's you should add them manually. Therefore, this plugin provides the task
types `JsMinifyTask` and `CssMinifyTask`.

#### Creating additional JsMinifyTask

```kotlin
tasks.create<JsMinifyTask>("additionalJsMinify") { //this: JsMinifyTask
    srcDir = project.file("js")
    dstDir = project.file("build/js")
}
```

```groovy
task additionalJsMinify(type: org.gradlewebtools.minify.JsMinifyTask) {
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

```groovy
task cssMinify(type: org.gradlewebtools.minify.CssMinifyTask) {
    srcDir = project.file("css")
    dstDir = project.file("build/css")
}
```

#### Options

Note: Enum properties must be specified by their fully qualified classname.

##### JsMinifyTask

```kotlin
tasks.create<JsMinifyTask>("additionalJsMinify") { //this: JsMinifyTask
    srcDir = project.file("js")
    dstDir = project.file("build/js")
    options {
        ignoreMinFiles = false
        compilationLevel = com.google.javascript.jscomp.CompilationLevel.SIMPLE_OPTIMIZATIONS
        env = com.google.javascript.jscomp.CompilerOptions.Environment.BROWSER
        languageIn = null
        languageOut = null
        warningLevel = com.google.javascript.jscomp.WarningLevel.QUIET
        extraAnnotationNames = listOf()
        strictModeInput = false
        debug = false
        exportLocalPropertyDefinitions = false
        formatting = listOf()
        generateExports = false
        renamePrefixNamespace = null
        renameVariablePrefix = null
        moduleResolution = com.google.javascript.jscomp.deps.ModuleLoader.ResolutionMode.BROWSER
        processCommonJsModules = false
        packageJsonEntryNames = listOf()
        angularPass = false
        forceInjectLibrary = listOf()
        polymerVersion = null
        rewritePolyfills = false
        charset = kotlin.textCharsets.UTF_8
        checksOnly = false
        browserFeaturesetYear = null
        createSourceMaps = false
        originalFileNames = false
        copyOriginalFile = false
    }
}
```

```groovy
task additionalJsMinify(type: org.gradlewebtools.minify.JsMinifyTask) {
    srcDir = project.file("js")
    dstDir = project.file("build/js")
    options.ignoreMinFiles = false
    options.compilationLevel = com.google.javascript.jscomp.CompilationLevel.SIMPLE_OPTIMIZATIONS
    env = com.google.javascript.jscomp.CompilerOptions.Environment.BROWSER
    options.languageIn = null
    options.languageOut = null
    warningLevel = com.google.javascript.jscomp.WarningLevel.QUIET
    options.extraAnnotationNames = listOf()
    options.strictModeInput = false
    options.debug = false
    options.exportLocalPropertyDefinitions = false
    options.formatting = listOf()
    options.generateExports = false
    options.renamePrefixNamespace = null
    options.renameVariablePrefix = null
    moduleResolution = com.google.javascript.jscomp.deps.ModuleLoader.ResolutionMode.BROWSER
    options.processCommonJsModules = false
    options.packageJsonEntryNames = listOf()
    options.angularPass = false
    options.dartPass = false
    options.forceInjectLibrary = listOf()
    options.polymerVersion = null
    options.rewritePolyfills = false
    charset = kotlin.textCharsets.UTF_8
    options.checksOnly = false
    options.browserFeaturesetYear = null
    options.createSourceMaps = false
    options.originalFileNames = false
    options.copyOriginalFile = false
}
```

| option  | effect                     | values                              | default                             |
|---------|----------------------------|-------------------------------------|-------------------------------------|
| srcDir  | Sets source directory      | File?                               | null                                |
| dstDir  | Sets destination directory | File?                               | null                                |
| options | Sets JS minifier options   | [js options](#jsminifytask-options) | [js options](#jsminifytask-options) |

##### JsMinifyTask options

| option                         |     | effect                                                                                                                     | values                                                          | default                                                               |
|--------------------------------|:----|----------------------------------------------------------------------------------------------------------------------------|-----------------------------------------------------------------|-----------------------------------------------------------------------|
| ignoreMinFiles                 |     | ignore files named ".min."                                                                                                 | true, false                                                     | false                                                                 |
| compilationLevel               |     | Specifies the compilation level to use                                                                                     | com.google.javascript.jscomp.CompilationLevel                   | com.google.javascript.jscomp.CompilationLevel.SIMPLE_OPTIMIZATIONS    |
| env                            |     | Determines the set of builtin externs to load                                                                              | com.google.javascript.jscomp.CompilerOptions.Environment        | com.google.javascript.jscomp.CompilerOptions.Environment.BROWSER      |
| languageIn                     |     | Sets the language spec to which input sources should conform                                                               | com.google.javascript.jscomp.CompilerOptions.LanguageMode       |                                                                       |
| languageOut                    |     | Sets the language spec to which output should conform                                                                      | com.google.javascript.jscomp.CompilerOptions.LanguageMode       |                                                                       |
| warningLevel                   |     | Specifies the warning level to use                                                                                         | com.google.javascript.jscomp.WarningLevel                       | com.google.javascript.jscomp.WarningLevel.QUIET                       |
| extraAnnotationName            |     | A whitelist of tag names in JSDoc                                                                                          | list of strings                                                 | null                                                                  |
| strictModeInput                |     | Assume input sources are to run in strict mode.                                                                            | true, false                                                     | false                                                                 |
| debug                          |     | Enable debugging options                                                                                                   | true, false                                                     | false                                                                 |
| exportLocalPropertyDefinitions |     | Generates export code for local properties marked with @export                                                             | true, false                                                     | false                                                                 |
| formatting                     |     | Specifies which formatting options, if any, should be applied to the output JS                                             | com.google.javascript.jscomp.CommandLineRunner.FormattingOption | null                                                                  |
| generateExports                |     | Generates export code for those marked with @export                                                                        | true, false                                                     | false                                                                 |
| renamePrefixNamespace          |     | Specifies the name of an object that will be used to store all non-extern globals                                          | string                                                          | null                                                                  |
| renameVariablePrefix           |     | Specifies a prefix that will be prepended to all variables                                                                 | string                                                          | null                                                                  |
| moduleResolution               |     | Specifies how the compiler locates modules                                                                                 | com.google.javascript.jscomp.deps.ModuleLoader.ResolutionMode   | com.google.javascript.jscomp.deps.ModuleLoader.ResolutionMode.BROWSER |
| processCommonJsModules         |     | Process CommonJS modules to a concatenable form                                                                            | true, false                                                     | false                                                                 |
| packageJsonEntryNames          |     | Ordered list of entries to look for in package.json files when processing modules with the NODE module resolution strategy | list of strings                                                 | ["browser", "module", "main"]                                         |
| angularPass                    |     | Generate \$inject properties for AngularJS for functions annotated with @ngInject                                          | true, false                                                     | false                                                                 |
| dartPass                       |     | Rewrite Dart Dev Compiler output to be compiler-friendly                                                                   | true, false                                                     | false                                                                 |
| forceInjectLibrary             |     | Force injection of named runtime libraries. The format is <name> where <name> is the name of a runtime library             | base, es6_runtime, runtime_type_check                           | empty list                                                            |
| polymerVersion                 |     | Which version of Polymer is being used                                                                                     | 1, 2                                                            | null                                                                  |
| rewritePolyfills               |     | Rewrite ES6 library calls to use polyfills provided by the compiler's runtime                                              | true, false                                                     | false                                                                 |
| charset                        |     | Input and output charset for all files                                                                                     | java.nio.charset.Charset                                        | By default, we accept UTF-8 as input and output US_ASCII              |
| checksOnly                     |     | Don't generate output. Run checks, but no optimization passes                                                              | true, false                                                     | false                                                                 |
| browserFeaturesetYear          |     | Browser feature set year                                                                                                   | 2012, 2019, 2020                                                | 0                                                                     |
| emitUseStrict                  |     | Whether 'use strict' should be added to the file                                                                           | true, false                                                     | true                                                                  |

See [Google Closure Compiler](https://github.com/google/closure-compiler/wiki/Flags-and-Options) for more information

##### CssMinifyTask

```kotlin
tasks.create<CssMinifyTask>("additionalCssMinify") { //this: CssMinifyTask
    srcDir = project.file("css")
    dstDir = project.file("build/css")
    options {
        ignoreMinFiles = false
        inputOrientation = com.google.common.css.JobDescription.InputOrientation.LTR
        outputOrientation = com.google.common.css.JobDescription.OutputOrientation.LTR
        outputFormat = com.google.common.css.JobDescription.OutputFormat.COMPRESSED
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
        outputRenamingMapFormat = com.google.common.css.OutputRenamingMapFormat.JSON
        compileConstants = mapOf()
        options.sourceMapLevel = com.google.common.css.JobDescription.SourceMapDetailLevel.DEFAULT
        createSourceMaps = false
        originalFileNames = false
        copyOriginalFile = false
    }
}
```

```groovy
task cssMinify(type: org.gradlewebtools.minify.CssMinifyTask) {
    srcDir = project.file("css")
    dstDir = project.file("build/css")
    options.ignoreMinFiles = false
    options.inputOrientation = InputOrientation.LTR
    options.outputOrientation = OutputOrientation.LTR
    outputFormat = com.google.common.css.JobDescription.OutputFormat.COMPRESSED
    options.copyrightNotice = null
    options.trueConditionNames = listOf()
    options.allowDefPropagation = true
    options.allowUnrecognizedFunctions = true
    options.allowedNonStandardFunctions = listOf()
    options.allowedUnrecognizedProperties = listOf()
    options.allowUnrecognizedProperties = true
    options.vendor = null
    options.allowKeyframes = true
    options.allowWebkitKeyframes = true
    options.processDependencies = true
    options.excludedClassesFromRenaming = listOf()
    options.simplifyCss = true
    options.eliminateDeadStyles = false
    options.cssRenamingPrefix = ""
    options.preserveComments = false
    outputRenamingMapFormat = com.google.common.css.OutputRenamingMapFormat.JSON
    options.compileConstants = mapOf()
    options.sourceMapLevel = com.google.common.css.JobDescription.SourceMapDetailLevel.DEFAULT
    options.createSourceMaps = false
    options.originalFileNames = false
    options.copyOriginalFile = false
}
```

| option  | effect                     | values                                | default                               |
|---------|----------------------------|---------------------------------------|---------------------------------------|
| srcDir  | Sets source directory      | `File?`                               | `null`                                |
| dstDir  | Sets destination directory | `File?`                               | `null`                                |
| options | Sets CSS minifier options  | [css options](#cssminifytask-options) | [css options](#cssminifytask-options) |

##### CSSMinifyTask options

| option                        | effect                        | values                                                                   | default                                                                          |
|-------------------------------|-------------------------------|--------------------------------------------------------------------------|----------------------------------------------------------------------------------|
| ignoreMinFiles                | ignore files named ".min."    | true, false                                                              | false                                                                            |
| inputOrientation              | inputOrientation              | com.google.common.css.JobDescription.InputOrientation                    | com.google.common.css.JobDescription.OutputOrientation.LTR                       |
| outputOrientation             | outputOrientation             | com.google.common.css.JobDescription.OutputOrientation                   | com.google.common.css.JobDescription.OutputOrientation.LTR                       |
| outputFormat                  | outputFormat                  | com.google.common.css.JobDescription.OutputFormat                        | com.google.common.css.JobDescription.OutputFormat.COMPRESSED                     |
| copyrightNotice               | copyrightNotice               | String                                                                   | null                                                                             |
| trueConditionNames            | trueConditionNames            | list of strings                                                          | empty list                                                                       |
| allowDefPropagation           | allowDefPropagation           | true, false                                                              | true                                                                             |
| allowUnrecognizedFunctions    | allowUnrecognizedFunctions    | true, false                                                              | true                                                                             |
| allowedNonStandardFunctions   | allowedNonStandardFunctions   | list of strings                                                          | empty list                                                                       |
| allowedUnrecognizedProperties | allowedUnrecognizedProperties | list of strings                                                          | empty list                                                                       |
| allowUnrecognizedProperties   | allowUnrecognizedProperties   | true, false                                                              | true                                                                             |
| vendor                        | vendor                        | com.google.common.css.Vendor                                             | null                                                                             |
| allowKeyframes                | allowKeyframes                | true, false                                                              | true                                                                             |
| allowWebkitKeyframes          | allowWebkitKeyframes          | true, false                                                              | true                                                                             |
| processDependencies           | processDependencies           | true, false                                                              | true                                                                             |
| excludedClassesFromRenaming   | excludedClassesFromRenaming   | list of strings                                                          | empty list                                                                       |
| simplifyCss                   | simplifyCss                   | true, false                                                              | true                                                                             |
| eliminateDeadStyles           | eliminateDeadStyles           | true, false                                                              | false                                                                            |
| cssRenamingPrefix             | CSS renaming prefix           | String                                                                   | empty string                                                                     |
| preserveComments              | preserveComments              | true, false                                                              | false                                                                            |
| outputRenamingMapFormat       | outputRenamingMapFormat       | com.google.common.cssOutputRenamingMapFormat                             | com.google.common.cssOutputRenamingMapFormat.JSON                                |
| compileConstants              | compileConstants              | map                                                                      | empty map                                                                        |
| sourceMapLevel                | sourceMapLevel                | com.google.common.css.JobDescription.JobDescription.SourceMapDetailLevel | com.google.common.css.JobDescription.JobDescription.SourceMapDetailLevel.DEFAULT |
