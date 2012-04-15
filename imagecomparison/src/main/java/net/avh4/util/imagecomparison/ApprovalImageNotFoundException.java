package net.avh4.util.imagecomparison;

import junit.framework.AssertionFailedError;

public class ApprovalImageNotFoundException extends AssertionFailedError {
    public ApprovalImageNotFoundException(String outputFilename) {
        super(outputFilename);
    }
}
