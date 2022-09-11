package net.jeremybrooks.mp3me;

import net.jeremybrooks.mp3me.model.ConversionJob;
import net.jeremybrooks.mp3me.model.Settings;
import net.jeremybrooks.pressplay.FFProbe;
import net.jeremybrooks.pressplay.MediaMetadata;
import org.apache.commons.io.FilenameUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.nio.file.FileVisitResult;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.SimpleFileVisitor;
import java.nio.file.StandardCopyOption;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.HashMap;
import java.util.Map;

import static java.nio.file.FileVisitResult.CONTINUE;

public class FileWalker extends SimpleFileVisitor<Path> {
    private static final Logger logger = LogManager.getLogger();
    private final Settings settings;
    public static Map<String, Integer> counter = new HashMap<>();
    private final long cutoff;
    private final ConversionJob job;

    private final int index;

    private final ConversionWorker conversionWorker;

    private Process runningProcess;

    FileWalker(ConversionJob job, int index, long cutoff, ConversionWorker conversionWorker) {
        this.job = job;
        this.index = index;
        settings = App.getSettings();
        this.cutoff = cutoff;
        this.conversionWorker = conversionWorker;
    }

    @Override
    public FileVisitResult visitFile(Path file, BasicFileAttributes attributes) {
        if (attributes.isRegularFile() &&
                !file.getFileName().toString().startsWith(".") &&
                attributes.creationTime().toMillis() < cutoff) {
            String extension = FilenameUtils.getExtension(file.getFileName().toString()).toLowerCase();
            String action = settings.getFileTypeActionMap().get(extension);
            if (action == null) {
                action = settings.getFileTypeActionMap().get("other");
            }
            switch (action) {
                case "Copy" -> {
                    job.setMessage("Copying " + file);
                    conversionWorker.jobUpdated(index);
                    MediaMetadata metadata = parseMetadata(file.toString());
                    try {
                    createDirectories(metadata);
                    Path dest = Paths.get(settings.getDestination(), getArtistDirectory(metadata),
                            getAlbumDirectory(metadata), file.getFileName().toString());
//                    Path dest = Paths.get(file.toString().replaceFirst(job.getSourcePath().toString(), settings.getDestination()));
                        Files.copy(file, dest, StandardCopyOption.REPLACE_EXISTING);
                        increment(extension);
                    } catch (Exception e) {
                        logger.error("Copy failed for file {}", file, e);
                        job.setError(true);
                        increment("copyError");
                    }
                }
                case "Convert" -> {
                    job.setMessage("Converting " + file);
                    conversionWorker.jobUpdated(index);
                    try {
                        MediaMetadata metadata = parseMetadata(file.toString());
                        createDirectories(metadata);
                        String mp3File = buildUniqueFilename(metadata);
                        logger.info("Converting {} to {}", file, mp3File);
                        ProcessBuilder processBuilder = new ProcessBuilder(
                                settings.getFfmpegBinary(),
                                "-y",
                                "-i", file.toString(),
                                "-codec:a", "libmp3lame",
                                "-b:a", settings.getBitrate(),
                                mp3File);
                        runningProcess = processBuilder.inheritIO().start();
                        int exitCode = runningProcess.waitFor();
                        if (exitCode == 0) {
                            increment(extension);
                        } else {
                            logger.error("Non-zero exit code {} while converting file {}", exitCode, file);
                            increment("convertExitError");
                        }
                    } catch (Exception e) {
                        logger.error("Error converting file {}", file, e);
                        job.setError(true);
                        increment("convertError");
                    }
                }
                case "Ignore" -> {
                    logger.info("Ignoring file {}", file);
                    increment(extension);
                }
                default -> logger.error("Unexpected switch condition for extension {}, action {}", extension, action);
            }
        }
        return CONTINUE;
    }

    void killRunningJob() {
        if (runningProcess != null) {
            logger.info("Killing running conversion job");
            runningProcess.destroyForcibly();
        }
    }

    private void increment(String extension) {
        job.setFilesConverted(job.getFilesConverted() + 1);
        conversionWorker.jobUpdated(index);
        if (!counter.containsKey(extension)) {
            counter.put(extension, 0);
        }
        counter.put(extension, counter.get(extension) + 1);
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


    /*
     * Create the destination directories for the given metadata
     * The destination directory is based on the artist name and album name.
     *
     */
    private void createDirectories(MediaMetadata metadata) throws IOException {
        Path destDir = Paths.get(settings.getDestination(), getArtistDirectory(metadata),
                getAlbumDirectory(metadata));
        if (!Files.exists(destDir)) {
            Files.createDirectories(destDir);
            logger.info("Created {}", destDir);
        }
    }

    private MediaMetadata parseMetadata(String file) {
        MediaMetadata metadata;
        try {
            metadata = FFProbe.getMediaMetadata(file);
        } catch (Exception e) {
            metadata = null;
        }
        return metadata;
    }

    private String buildUniqueFilename(MediaMetadata metadata) {
        String mp3File = String.format("%s/%s/%s/%02d-%02d-%s.mp3",
                settings.getDestination(),
                getArtistDirectory(metadata),
                getAlbumDirectory(metadata),
                getDiscNumber(metadata),
                getTrackNumber(metadata),
                getTitle(metadata));
        if (Files.exists(Paths.get(mp3File))) {
            mp3File = String.format("%s/%s/%s/%02d-%02d-%s-%d.mp3",
                    settings.getDestination(),
                    getArtistDirectory(metadata),
                    getAlbumDirectory(metadata),
                    getDiscNumber(metadata),
                    getTrackNumber(metadata),
                    getTitle(metadata),
                    System.currentTimeMillis());
        }
        return mp3File;
    }

    private String getArtistDirectory(MediaMetadata metadata) {
        String dir;
        if (metadata == null) {
            dir = "Unknown Artist";
        } else {
            if (metadata.isCompilation()) {
                dir = "Various Artists";
            } else {
                if (metadata.getAlbumArtist().isEmpty()) {
                    if (metadata.getArtist().isEmpty()) {
                        dir = "Unknown Artist";
                    } else {
                        dir = metadata.getArtist();
                    }
                } else {
                    dir = metadata.getAlbumArtist();
                }
            }
        }
        return sanitizeFilename(dir);
    }

    private String getAlbumDirectory(MediaMetadata metadata) {
        String dir;
        if (metadata == null) {
            dir = "Unknown Album";
        } else {
            dir = metadata.getAlbum();
            if (dir.isEmpty()) {
                dir = "Unknown Album";
            }
        }
        return sanitizeFilename(dir);
    }

    private String getTitle(MediaMetadata metadata) {
        String title;
        if (metadata == null) {
            title = "Unknown Title";
        } else {
            title = metadata.getTitle();
            if (title.isEmpty()) {
                title = "Unknown Title";
            }
        }
        return sanitizeFilename(title);
    }

    private int getDiscNumber(MediaMetadata metadata) {
        return metadata == null ? 0 : metadata.getDiscNumber();
    }

    private int getTrackNumber(MediaMetadata metadata) {
        return metadata == null ? 0 : metadata.getTrackNumber();
    }

    /*
     * Leave numbers, letters, dot, and underscore;
     * replace everything else with an underscore.
     */
    private String sanitizeFilename(String inputName) {
        return inputName.replaceAll("[^a-zA-Z0-9-_\\.]", "_");
    }

}
