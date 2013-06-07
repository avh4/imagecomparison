package net.avh4.util.imagecomparison;

import static org.hamcrest.CoreMatchers.*;
import static org.hamcrest.MatcherAssert.*;

import org.junit.Test;

public class ImageComparisonResultTest {
	@Test
	public void success_shouldIndicateEquality() throws Exception {
		assertThat(ImageComparisonResult.SUCCESS.isEqual(), is(true));
	}
}
