package net.avh4.util.imagecomparison;

import org.junit.Before;
import org.junit.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.containsString;

public class ApprovalImageNotFoundExceptionTest {

    private ApprovalImageNotFoundException subject;

    @Before
    public void setup() throws Exception {
        subject = new ApprovalImageNotFoundException("missing file.png");
    }

    @Test
    public void getMessage_shouldIncludeMessage() throws Exception {
        assertThat(subject.getMessage(),
                containsString("missing file.png"));
    }
}
