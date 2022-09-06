package net.jeremybrooks.mp3me;

import java.nio.file.Path;

public class ConversionJob {

    private boolean error;

    public int getClockIndex() {
        return clockIndex;
    }

    public void incrementClockIndex() {
        if (this.clockIndex == 7) {
            this.clockIndex = 0;
        } else {
            this.clockIndex++;
        }
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public boolean isError() {
        return error;
    }

    public void setError(boolean error) {
        this.error = error;
    }

    public enum Status {
        PENDING,
        ACTIVE,
        DONE
    }

    private Path sourcePath;
    private int fileCount;
    private int filesConverted;
    private Status status;
    private int clockIndex;

    private String message;


    public int getFilesConverted() {
        return filesConverted;
    }

    public void setFilesConverted(int filesConverted) {
        this.filesConverted = filesConverted;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public Path getSourcePath() {
        return sourcePath;
    }

    public void setSourcePath(Path sourcePath) {
        this.sourcePath = sourcePath;
    }

    public int getFileCount() {
        return fileCount;
    }

    public void setFileCount(int fileCount) {
        this.fileCount = fileCount;
    }
}
