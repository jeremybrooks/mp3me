package net.jeremybrooks.mp3me;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.nio.file.FileVisitResult;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.SimpleFileVisitor;
import java.util.Objects;

import static java.nio.file.FileVisitResult.CONTINUE;

public class DirKiller extends SimpleFileVisitor<Path> {
    private static final Logger logger = LogManager.getLogger();

    @Override
    public FileVisitResult postVisitDirectory(Path dir, IOException exc)throws IOException {
        Objects.requireNonNull(dir);
        if (exc != null) {
            throw exc;
        }
        if (Files.list(dir).count() == 0) {
            logger.info("Killing {}", dir);
            Files.delete(dir);
        }
        return FileVisitResult.CONTINUE;
    }


    // If there is some error accessing
    // the file, let the user know.
    // If you don't override this method
    // and an error occurs, an IOException
    // is thrown.
    @Override
    public FileVisitResult visitFileFailed(Path file, IOException e) {
        logger.error("File visit failed for file {}", file, e);
        return CONTINUE;
    }

}
