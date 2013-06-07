package net.avh4.util.imagecomparison;

import static java.awt.image.BufferedImage.*;
import static net.avh4.util.MatcherMatcher.*;
import static org.hamcrest.MatcherAssert.*;

import java.awt.image.BufferedImage;
import java.io.File;

import org.hamcrest.Matcher;
import org.junit.AfterClass;
import org.junit.Test;
import org.junit.experimental.runners.Enclosed;
import org.junit.runner.RunWith;

@RunWith(Enclosed.class)
public class ImageComparisonMatchersEnclosedTest {

	@AfterClass
	public static void tearDown() {
		new File(
				"ImageComparisonMatchersEnclosedTest.Inner.whenApprovalImageDoesNotExist_isApproved_doesNotMatch.png")
				.delete();
	}

	public static class Inner {
		@Test
		public void whenApprovalImageDoesNotExist_isApproved_doesNotMatch()
				throws Exception {
			final Matcher<?> matcher = ImageComparisonMatchers.isApproved();
			final BufferedImage image = new BufferedImage(1, 1, TYPE_INT_RGB);
			assertThat(matcher,
					doesNotMatch(image).withDescription("approval image " +
							"ImageComparisonMatchersEnclosedTest.Inner.whenApprovalImageDoesNotExist_isApproved_doesNotMatch.png "
							+
							"doesn't exist -- expected to find it in net.avh4.util.imagecomparison"));
		}
	}
}
