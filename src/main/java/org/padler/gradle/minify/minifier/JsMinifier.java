package org.padler.gradle.minify.minifier;

import com.yahoo.platform.yui.compressor.JavaScriptCompressor;

import java.io.*;

public class JsMinifier extends Minifier {

    @Override
    protected void minifyFile(File srcFile, File dstFile) throws IOException {
        InputStreamReader iosR = new InputStreamReader(new FileInputStream(srcFile));
        OutputStreamWriter iosW = new OutputStreamWriter(new FileOutputStream(dstFile));
        JavaScriptCompressor compressor = new JavaScriptCompressor(iosR, null);
        compressor.compress(iosW, -1, true, false, false, false);
        iosW.flush();
        iosR.close();
        iosW.close();
    }
}
