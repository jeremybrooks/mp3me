package net.jeremybrooks.mp3me;

import java.text.Normalizer;

public class FilenameSanitizer {
    /**
     * Transliterate accented characters, leave numbers, letters, dot, and underscore;
     * replace everything else with an underscore.
     *
     * @param inputName the string to sanitize.
     */
    public static String sanitizeFilename(String inputName) {
        if (inputName == null) {
            return null;
        }
        String newName = Normalizer.normalize(inputName, Normalizer.Form.NFKD)
                .replaceAll("\\p{M}", "");
        return newName.replaceAll("[^a-zA-Z0-9-_.]", "_");
    }
}
