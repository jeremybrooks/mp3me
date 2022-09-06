package net.jeremybrooks.mp3me;

import java.nio.file.FileVisitResult;
import java.nio.file.Path;
import java.nio.file.SimpleFileVisitor;
import java.nio.file.attribute.BasicFileAttributes;

import static java.nio.file.FileVisitResult.CONTINUE;

public class FileCounter extends SimpleFileVisitor<Path> {
    private ConversionJob job;
    private int count;
    private long cutoff;

    public FileCounter(ConversionJob job, long cutoff) {
        this.job = job;
        this.cutoff = cutoff;
    }

    @Override
    public FileVisitResult visitFile(Path file, BasicFileAttributes attributes) {
        if (attributes.isRegularFile() &&
                (!file.getFileName().toString().startsWith(".")) &&
                attributes.creationTime().toMillis() < cutoff) {

            count++;
            job.setFileCount(count);
        }
        return CONTINUE;
    }
}
