package net.avh4.util.imagecomparison;

import org.hamcrest.Matcher;
import org.junit.Test;
import org.junit.experimental.runners.Enclosed;
import org.junit.runner.RunWith;

import java.awt.image.BufferedImage;

import static java.awt.image.BufferedImage.TYPE_INT_RGB;
import static net.avh4.util.MatcherMatcher.doesNotMatch;
import static org.hamcrest.MatcherAssert.assertThat;

@RunWith(Enclosed.class)
public class ImageComparisonMatchersEnclosedTest {

    public static class Inner {
        @Test
        public void isApproved_whenApprovalImageDoesNotExist() throws Exception {
            final Matcher<?> matcher = ImageComparisonMatchers.isApproved();
            final BufferedImage image = new BufferedImage(1, 1, TYPE_INT_RGB);
            assertThat(matcher, doesNotMatch(image)
                    .withDescription("approval image ImageComparisonMatchersEnclosedTest.Inner.isApproved_whenApprovalImageDoesNotExist.png doesn't exist"));
        }
    }
}
