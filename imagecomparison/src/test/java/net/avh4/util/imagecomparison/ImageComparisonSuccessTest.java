package net.avh4.util.imagecomparison;

import org.junit.Before;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

public class ImageComparisonSuccessTest {

    private ImageComparisonSuccess subject;

    @Before
    public void setUp() throws Exception {
        subject = new ImageComparisonSuccess(null);
    }

    @Test
    public void shouldIndicateEquality() throws Exception {
        assertThat(subject.isEqual(), is(true));
    }
}
