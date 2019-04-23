package org.padler.gradle.minify.minifier;

import com.yahoo.platform.yui.compressor.CssCompressor;

import java.io.*;

public class CssMinifier extends Minifier {

    @Override
    protected void minify(File srcFile, File dstFile) {
        try {
            min(srcFile, dstFile);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void min(File srcFile, File dstFile) throws IOException {
        InputStreamReader iosR = new InputStreamReader(new FileInputStream(srcFile));
        OutputStreamWriter iosW = new OutputStreamWriter(new FileOutputStream(dstFile));
        CssCompressor compressor = new CssCompressor(iosR);
        compressor.compress(iosW, -1);
        iosW.flush();
        iosR.close();
        iosW.close();
    }
}
