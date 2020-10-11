package org.padler.gradle.minify.minifier.options;

import com.google.common.collect.Lists;
import com.google.common.css.JobDescription;
import com.google.common.css.OutputRenamingMapFormat;
import com.google.common.css.Vendor;
import lombok.Getter;
import lombok.Setter;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Getter
@Setter
public class CSSMinifierOptions extends MinifierOptions {
    private JobDescription.InputOrientation inputOrientation = JobDescription.InputOrientation.LTR;
    private JobDescription.OutputOrientation outputOrientation = JobDescription.OutputOrientation.LTR;
    private JobDescription.OutputFormat outputFormat = JobDescription.OutputFormat.COMPRESSED;
    private String copyrightNotice = null;
    private List<String> trueConditionNames = Lists.newArrayList();
    private Boolean allowDefPropagation = true;
    private Boolean allowUnrecognizedFunctions = true;
    private List<String> allowedNonStandardFunctions = Lists.newArrayList();
    private List<String> allowedUnrecognizedProperties = Lists.newArrayList();
    private Boolean allowUnrecognizedProperties = true;
    private Vendor vendor = null;
    private Boolean allowKeyframes = true;
    private Boolean allowWebkitKeyframes = true;
    private Boolean processDependencies = true;
    private List<String> excludedClassesFromRenaming = Lists.newArrayList();
    private Boolean simplifyCss = true;
    private Boolean eliminateDeadStyles = false;
    private String cssRenamingPrefix = "";
    private Boolean preserveComments = false;
    private OutputRenamingMapFormat outputRenamingMapFormat = OutputRenamingMapFormat.JSON;
    private Map<String, Integer> compileConstants = new HashMap<>();
    private JobDescription.SourceMapDetailLevel sourceMapLevel = JobDescription.SourceMapDetailLevel.DEFAULT;
}
