package net.avh4.util.imagecomparison.features;

import net.avh4.util.imagecomparison.ImageDiff;
import net.avh4.util.sandbox.Sandbox;

import java.awt.event.MouseListener;

public class Agent {
    private final Sandbox sandbox;
    private ImageDiff ui;

    public Agent(Sandbox sandbox) {
        this.sandbox = sandbox;
    }

    public void clickViewingArea() {
        final MouseListener[] mouseListeners = ui.getMouseListeners();
        for (final MouseListener mouseListener : mouseListeners) {
            mouseListener.mouseClicked(null);
        }
    }

    public void launchImagediff(String fileA, String fileB) {
        ui = ImageDiff.launch(sandbox.getRoot(), fileA, fileB);
    }

    public void launchImagediff() {
        ui = ImageDiff.launch(sandbox.getRoot());
    }

    public ImageDiff getUi() {
        return ui;
    }

    public void approve() {
        ui.getApproveButton().doClick();
    }
}
