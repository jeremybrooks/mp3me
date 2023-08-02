package net.jeremybrooks.mp3me;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

class FilenameSanitizerTest {

    @Test
    public void testSanitize() {
        assertEquals("abcd1234567890", FilenameSanitizer.sanitizeFilename("abcd1234567890"));
        assertEquals("abcdefghijklmnopqurstvwxyz.ABCDEFGHIJKLMNOPQRSTUVWXYZ",
                FilenameSanitizer.sanitizeFilename("abcdefghijklmnopqurstvwxyz.ABCDEFGHIJKLMNOPQRSTUVWXYZ"));
        assertEquals("aeiou", FilenameSanitizer.sanitizeFilename("åéïóü"));
        assertEquals("abcd_1234", FilenameSanitizer.sanitizeFilename("abcd 1234"));
        assertEquals("abcd_1234", FilenameSanitizer.sanitizeFilename("abcd#1234"));
        assertEquals("Sigur_Ros", FilenameSanitizer.sanitizeFilename("Sigur Rós"));
    }

    @Test
    public void testNullInput() {
        assertNull(FilenameSanitizer.sanitizeFilename(null));
    }

}