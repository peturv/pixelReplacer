package se.omnivore.gfx.pixelReplacer;

import static org.hamcrest.MatcherAssert.*;

import static org.hamcrest.core.IsEqual.*;
import org.junit.After;
import org.junit.Test;


import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;

public class PixelReplacerTest {



    @After
    public void deleteFile() throws URISyntaxException {
        //noinspection ResultOfMethodCallIgnored
        new File(getPath("pixelReplaced_test.png")).delete();
    }

    @Test
    public void dryRun() throws IOException, URISyntaxException {
        ArgumentParser arguments = new ArgumentParser(new String[]{getPath("test.png"), "#ff0000", "#000000"});
        PixelReplacer pr = new PixelReplacer();
        File newFile = pr.replaceAndWrite(arguments, null);

        BufferedImage expected = ImageIO.read(new File(getPath("after_pixelReplaced_test.png")));
        BufferedImage actual = ImageIO.read(newFile);
        assertThat(compareImages(expected, actual), equalTo(true));
    }

    private String getPath(String file) throws URISyntaxException {
        @SuppressWarnings("ConstantConditions")
        URI img = new URI(this.getClass().getClassLoader().getResource(file).toString());
        return img.getPath();
    }

    private Boolean compareImages(BufferedImage expected, BufferedImage actual) {
        if(expected.getHeight() != actual.getHeight()
                || expected.getWidth() != actual.getWidth()) {
            return false;
        }
        for(int y = 0; y < expected.getHeight(); y++) {
            for(int x = 0; x < expected.getWidth(); x++) {
                if(expected.getRGB(x, y) != actual.getRGB(x, y)) {
                    return false;
                }
            }
        }
        return true;
    }
}
