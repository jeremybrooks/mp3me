package net.jeremybrooks;

import net.jeremybrooks.pressplay.FFProbe;
import net.jeremybrooks.pressplay.MediaMetadata;
import org.junit.Test;

/**
 * Unit test for simple App.
 */
public class AppTest 
{
    /**
     * Rigorous Test :-)
     */
    @Test
    public void shouldAnswerWithTrue() throws Exception {
        MediaMetadata mm = FFProbe.getMediaMetadata("/Users/jeremyb/Desktop/source/To Nije Šala _ What Makes Donna Twirl_ (Vinyl)/01 Commerce.m4a");
        System.out.println(mm.getArtist());
    }
}
