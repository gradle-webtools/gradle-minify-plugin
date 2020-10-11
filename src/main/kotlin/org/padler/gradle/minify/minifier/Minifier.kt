package org.padler.gradle.minify.minifier;

import org.gradle.api.GradleException;
import org.padler.gradle.minify.minifier.options.MinifierOptions;
import org.padler.gradle.minify.minifier.result.Error;
import org.padler.gradle.minify.minifier.result.Report;
import org.padler.gradle.minify.minifier.result.Warning;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.IOException;
import java.io.UncheckedIOException;
import java.nio.file.*;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public abstract class Minifier {

    private static final Logger LOGGER = LoggerFactory.getLogger(Minifier.class);

    protected final Report report = new Report();

    public void minify(String srcDir, String dstDir) {
        minifyInternal(srcDir, dstDir);
        if (LOGGER.isErrorEnabled()) {
            LOGGER.error(createReport());
        }
        if (!report.getErrors().isEmpty())
            throw new GradleException(report.getErrors() + " Errors in " + getMinifierName());
    }

    protected void minifyInternal(String srcDir, String dstDir) {
        try (Stream<Path> filesStream = Files.list(Paths.get(srcDir)).filter(f -> !f.toString().equals(srcDir))) {
            List<Path> files = filesStream.collect(Collectors.toList());
            for (Path f : files) {
                if (f.toFile().isFile()) {
                    Path dst = Paths.get(dstDir);
                    String fileName = f.getFileName().toString();
                    File copy = new File(dst.toString(), fileName);
                    if (Boolean.FALSE.equals(getMinifierOptions().getOriginalFileNames())) {
                        fileName = rename(fileName);
                    }
                    File dstFile = new File(dst.toString(), fileName);
                    dstFile.getParentFile().mkdirs();
                    if (fileTypeMatches(f)) {
                        Files.copy(f, copy.toPath(), StandardCopyOption.REPLACE_EXISTING);
                        minify(f.toFile(), dstFile);
                    }
                } else if (f.toFile().isDirectory()) {
                    String newDstDir = dstDir + "/" + f.getFileName().toString();
                    minifyInternal(f.toString(), newDstDir);
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

    protected String createReport() {
        StringBuilder reportStr = new StringBuilder();
        for (Error error : report.getErrors()) {
            reportStr.append("Error: ");
            reportStr.append(error);
            reportStr.append("\n");
        }
        for (Warning warning : report.getWarnings()) {
            reportStr.append("Warning: ");
            reportStr.append(warning);
            reportStr.append("\n");
        }
        reportStr.append(getMinifierName())
                .append(": ")
                .append(report.getErrors().size())
                .append(" error(s), ")
                .append(report.getWarnings().size())
                .append(" warning(s)\n");
        return reportStr.toString();
    }

    protected void writeToFile(File dstFile, String string) {
        OpenOption create = StandardOpenOption.CREATE;
        OpenOption write = StandardOpenOption.WRITE;
        OpenOption truncateExisting = StandardOpenOption.TRUNCATE_EXISTING;
        try {
            Files.write(dstFile.toPath(), string.getBytes(), create, write, truncateExisting);
        } catch (IOException e) {
            throw new UncheckedIOException(e);
        }
    }

    protected String getExtension(String filename) {
        return Optional.ofNullable(filename)
                .filter(f -> f.contains("."))
                .map(f -> f.substring(filename.lastIndexOf('.') + 1))
                .orElse("");
    }

    protected abstract boolean fileTypeMatches(Path f);

    public abstract MinifierOptions getMinifierOptions();

    protected abstract String getMinifierName();

    protected abstract void minifyFile(File srcFile, File dstFile) throws IOException;

    protected abstract String rename(String oldName);
}
