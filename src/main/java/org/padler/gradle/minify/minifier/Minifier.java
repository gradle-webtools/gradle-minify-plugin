package org.padler.gradle.minify.minifier;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.stream.Stream;

import static java.nio.file.LinkOption.NOFOLLOW_LINKS;

public abstract class Minifier {

    public void minify(String srcDir, String dstDir) {
        try (Stream<Path> files = Files.walk(Paths.get(srcDir))) {
            files
                    .filter(f -> !f.toString().equals(srcDir))
                    .forEach(f -> {
                        if (Files.isRegularFile(f)) {
                            Path dst = Paths.get(dstDir);
                            File dstFile = new File(dst.toString() + "/" + f.getFileName().toString());
                            minify(f.toFile(), dstFile);
                        } else if (Files.isDirectory(f, NOFOLLOW_LINKS)) {
                            minify(f.toString(), dstDir + "/" + f.getFileName().toString());
                        }
                    });
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    protected abstract void minify(File scrFile, File dstFile);

}
