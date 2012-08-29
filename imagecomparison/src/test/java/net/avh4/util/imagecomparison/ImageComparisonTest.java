package net.avh4.util.imagecomparison;

import org.junit.Before;
import org.junit.Test;

import java.awt.image.BufferedImage;

public class ImageComparisonTest {

    private BufferedImage simpleImageArgb;
    private BufferedImage simpleImageArgbDuplicate;
    private BufferedImage differentImageArgb;
    private BufferedImage differentSizeImage;
    private BufferedImage simpleImageRgb;
    private BufferedImage simpleImageRgbDuplicate;

    @Before
    public void setup() throws Exception {
        simpleImageArgb = new BufferedImage(2, 1, BufferedImage.TYPE_INT_ARGB);
        simpleImageArgbDuplicate = new BufferedImage(2, 1, BufferedImage.TYPE_INT_ARGB);
        differentImageArgb = new BufferedImage(2, 1, BufferedImage.TYPE_INT_ARGB);
        differentSizeImage = new BufferedImage(1, 2, BufferedImage.TYPE_INT_ARGB);
        simpleImageRgb = new BufferedImage(2, 1, BufferedImage.TYPE_INT_RGB);
        simpleImageRgbDuplicate = new BufferedImage(2, 1, BufferedImage.TYPE_INT_RGB);

        simpleImageArgb.setRGB(0, 0, 0xff111111);
        simpleImageArgb.setRGB(1, 0, 0xff222222);
        simpleImageArgbDuplicate.setRGB(0, 0, 0xff111111);
        simpleImageArgbDuplicate.setRGB(1, 0, 0xff222222);
        differentImageArgb.setRGB(0, 0, 0xff111111);
        differentImageArgb.setRGB(1, 0, 0xffeeeeee);
        simpleImageRgb.setRGB(0, 0, 0x111111);
        simpleImageRgb.setRGB(1, 0, 0x222222);
        simpleImageRgbDuplicate.setRGB(0, 0, 0x111111);
        simpleImageRgbDuplicate.setRGB(1, 0, 0x222222);
    }

    @Test
    public void shouldPassForIdenticalImages() throws Exception {
        ImageComparison.assertImagesMatch(simpleImageArgb, simpleImageArgbDuplicate);
        // pass
    }

    @Test(expected = ImageMismatchException.class)
    public void shouldThrowForImagesWithDifferingPixels() throws Exception {
        ImageComparison.assertImagesMatch(differentImageArgb, simpleImageArgb);
    }

    @Test(expected = ImageMismatchException.class)
    public void shouldThrowForImagesWithDifferentDimensions() throws Exception {
        ImageComparison.assertImagesMatch(differentSizeImage, simpleImageArgb);
    }

    @Test
    public void shouldPassForIdenticalRgbImages() throws Exception {
        ImageComparison.assertImagesMatch(simpleImageRgb, simpleImageRgbDuplicate);
        // pass
    }

    @Test
    public void shouldPassForRgbImageThatMatchesArgbImage() throws Exception {
        ImageComparison.assertImagesMatch(simpleImageArgb, simpleImageRgb);
        // pass
    }
}
