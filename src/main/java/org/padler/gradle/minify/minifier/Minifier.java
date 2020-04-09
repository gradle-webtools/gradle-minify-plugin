package org.padler.gradle.minify.minifier;

import org.gradle.api.GradleException;

import java.io.File;
import java.io.IOException;
import java.io.UncheckedIOException;
import java.nio.file.*;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public abstract class Minifier {

    protected MinifierOptions minifierOptions = new MinifierOptions();

    protected final Report report = new Report();

    public void minify(String srcDir, String dstDir) {
        minifyInternal(srcDir, dstDir);
        System.err.println(createReport());
        if (!report.getErrors().isEmpty())
            throw new GradleException(report.getErrors() + " Errors in " + getMinifierName());
    }

    protected void minifyInternal(String srcDir, String dstDir) {
        try (Stream<Path> filesStream = Files.list(Paths.get(srcDir)).filter(f -> !f.toString().equals(srcDir))) {
            List<Path> files = filesStream.collect(Collectors.toList());
            for (Path f : files) {
                if (f.toFile().isFile()) {
                    Path dst = Paths.get(dstDir);
                    File dstFile = new File(dst.toString(), f.getFileName().toString());
                    dstFile.getParentFile().mkdirs();
                    minify(f.toFile(), dstFile);
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

    protected abstract String getMinifierName();

    protected abstract void minifyFile(File srcFile, File dstFile) throws IOException;

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

    public MinifierOptions getMinifierOptions() {
        return minifierOptions;
    }

    public void setMinifierOptions(MinifierOptions minifierOptions) {
        this.minifierOptions = minifierOptions;
    }
}
