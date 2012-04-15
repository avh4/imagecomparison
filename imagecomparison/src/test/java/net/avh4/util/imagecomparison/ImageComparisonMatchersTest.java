package net.avh4.util.imagecomparison;

import org.hamcrest.Matcher;
import org.junit.Test;

import java.awt.image.BufferedImage;

import static java.awt.image.BufferedImage.TYPE_INT_RGB;
import static net.avh4.util.MatcherMatcher.doesNotMatch;
import static org.hamcrest.MatcherAssert.assertThat;

public class ImageComparisonMatchersTest {

    private Matcher<?> matcher;

    @Test
    public void isApproved_withNullActual() throws Exception {
        matcher = ImageComparisonMatchers.isApproved();
        assertThat(matcher, doesNotMatch(null)
                .withDescription("don't know how to make an image of null"));
    }

    @Test
    public void isApproved_withActualThatCantBeMadeIntoAnImage() throws Exception {
        matcher = ImageComparisonMatchers.isApproved();
        assertThat(matcher, doesNotMatch(new SomeObject())
                .withDescription("don't know how to make an image of <{{ some object }}>"));
    }

    @Test
    public void isApproved_whenApprovalImageDoesNotExist() throws Exception {
        matcher = ImageComparisonMatchers.isApproved();
        final BufferedImage image = new BufferedImage(1, 1, TYPE_INT_RGB);
        assertThat(matcher, doesNotMatch(image).withDescription("approval image ImageComparisonMatchersTest.isApproved_whenApprovalImageDoesNotExist.png doesn't exist"));
    }

    private static class SomeObject {
        @Override
        public String toString() {
            return "{{ some object }}";
        }
    }
}
