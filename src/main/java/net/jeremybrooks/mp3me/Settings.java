package net.jeremybrooks.mp3me;

import java.util.Map;

public class Settings {
    private String destination = "";
    private boolean limit;
    private String limitDateTime = "";

    private int windowWidth = 650;
    private int windowHeight = 350;
    private int windowPositionX = 100;
    private int windowPositionY = 100;

    private String ffmpegBinary = "/usr/local/bin/ffmpeg";
    private String bitrate = "256k";
    private Map<String, String> fileTypeActionMap = Map.of(
            "aiff", "Convert",
            "flac", "Convert",
            "m4a", "Convert",
            "m4v", "Copy",
            "mp3", "Copy",
            "mp4", "Copy",
            "ogg", "Convert",
            "pdf", "Copy",
            "wav", "Convert",
            "other", "Ignore");


    public String getDestination() {
        return destination;
    }

    public void setDestination(String destination) {
        this.destination = destination;
    }

    public boolean isLimit() {
        return limit;
    }

    public void setLimit(boolean limit) {
        this.limit = limit;
    }

    public String getLimitDateTime() {
        return limitDateTime;
    }

    public void setLimitDateTime(String limitDateTime) {
        this.limitDateTime = limitDateTime;
    }

    public int getWindowWidth() {
        return windowWidth;
    }

    public void setWindowWidth(int windowWidth) {
        this.windowWidth = windowWidth;
    }

    public int getWindowHeight() {
        return windowHeight;
    }

    public void setWindowHeight(int windowHeight) {
        this.windowHeight = windowHeight;
    }

    public int getWindowPositionX() {
        return windowPositionX;
    }

    public void setWindowPositionX(int windowPositionX) {
        this.windowPositionX = windowPositionX;
    }

    public int getWindowPositionY() {
        return windowPositionY;
    }

    public void setWindowPositionY(int windowPositionY) {
        this.windowPositionY = windowPositionY;
    }

    public String getFfmpegBinary() {
        return ffmpegBinary;
    }

    public void setFfmpegBinary(String ffmpegBinary) {
        this.ffmpegBinary = ffmpegBinary;
    }

    public String getBitrate() {
        return bitrate;
    }

    public void setBitrate(String bitrate) {
        this.bitrate = bitrate;
    }

    public Map<String, String> getFileTypeActionMap() {
        return fileTypeActionMap;
    }

    public void setFileTypeActionMap(Map<String, String> fileTypeActionMap) {
        this.fileTypeActionMap = fileTypeActionMap;
    }
}
