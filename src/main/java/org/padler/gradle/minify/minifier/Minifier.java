package org.padler.gradle.minify.minifier;

import java.io.File;
import java.io.IOException;
import java.io.UncheckedIOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public abstract class Minifier {

    public void minify(String srcDir, String dstDir) {
        try (Stream<Path> filesStream = Files.walk(Paths.get(srcDir), 1).filter(f -> !f.toString().equals(srcDir))) {
            List<Path> files = filesStream.collect(Collectors.toList());
            for (Path f : files) {
                if (f.toFile().isFile()) {
                    Path dst = Paths.get(dstDir);
                    File dstFile = new File(dst.toString(), f.getFileName().toString());
                    minify(f.toFile(), dstFile);
                } else if (f.toFile().isDirectory()) {
                    String newDstDir = dstDir + "/" + f.getFileName().toString();
                    new File(newDstDir).mkdirs();
                    minify(f.toString(), newDstDir);
                }
            }
        } catch (IOException e) {
            throw new UncheckedIOException(e);
        }
    }

    protected void minify(File srcFile, File dstFile) {
        try {
            minifyFile(srcFile, dstFile);
        } catch (IOException e) {
            throw new UncheckedIOException(e);
        }
    }

    protected abstract void minifyFile(File srcFile, File dstFile) throws IOException;

}
