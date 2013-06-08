package net.avh4.util.imagecomparison;

import org.junit.Before;
import org.junit.Test;

import java.awt.image.BufferedImage;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

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
        simpleImageArgbDuplicate = new BufferedImage(2, 1,
                BufferedImage.TYPE_INT_ARGB);
        differentImageArgb = new BufferedImage(2, 1,
                BufferedImage.TYPE_INT_ARGB);
        differentSizeImage = new BufferedImage(1, 2,
                BufferedImage.TYPE_INT_ARGB);
        simpleImageRgb = new BufferedImage(2, 1, BufferedImage.TYPE_INT_RGB);
        simpleImageRgbDuplicate = new BufferedImage(2, 1,
                BufferedImage.TYPE_INT_RGB);

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
    public void compare_identicalImages_shouldReturnSuccess() throws Exception {
        assertThat(ImageComparison.compare(simpleImageArgb, simpleImageArgbDuplicate),
                is((ImageComparisonResult) new ImageComparisonSuccess(simpleImageArgb)));
    }

    @Test
    public void compare_withNoReferenceImage_shouldReturnNoReferenceImageFailure() throws Exception {
        assertThat(ImageComparison.compare(simpleImageArgb, null),
                is((ImageComparisonResult) new NoReferenceImageResult(simpleImageArgb)));
    }

    @Test
    public void compare_imagesWithDifferingPixels_shouldReturnPixelMismatch() throws Exception {
        assertThat(ImageComparison.compare(differentImageArgb, simpleImageArgb),
                is((ImageComparisonResult) new PixelMismatchResult(differentImageArgb, 1, 0, 0xffeeeeee, 0xff222222)));
    }

    @Test
    public void compare_imagesWithDifferentDimensions_shouldReturnSizeMismatch() throws Exception {
        assertThat(ImageComparison.compare(differentSizeImage, simpleImageArgb),
                is((ImageComparisonResult) new SizeMismatchResult(differentSizeImage, 1, 2, 2, 1)));
    }

    @Test
    public void compare_identicalRgbImages_shouldReturnSuccess() throws Exception {
        assertThat(ImageComparison.compare(simpleImageRgb, simpleImageRgbDuplicate),
                is((ImageComparisonResult) new ImageComparisonSuccess(simpleImageRgb)));
    }

    @Test
    public void compare_rgbImageThatMatchesArgbImage_shouldReturnSuccess() throws Exception {
        assertThat(ImageComparison.compare(simpleImageArgb, simpleImageRgb),
                is((ImageComparisonResult) new ImageComparisonSuccess(simpleImageArgb)));
    }
}
