package net.avh4.util.imagecomparison.steps;

import static org.hamcrest.MatcherAssert.assertThat;

import java.awt.event.MouseListener;

import net.avh4.util.imagecomparison.ImageDiff;
import net.avh4.util.imagecomparison.Matchers;
import net.avh4.util.sandbox.Sandbox;

import org.apache.commons.codec.digest.DigestUtils;
import org.jbehave.core.annotations.Given;
import org.jbehave.core.annotations.Then;
import org.jbehave.core.annotations.When;

public class Steps {
	private ImageDiff ui;

	@Given("two slightly different image files \"expected.png\" and \"actual.png\"")
	public void givenTwoSlightlyDifferentImageFilesexpectedpngAndactualpng() {
	}

	@When("I launch \"imagediff $fileA $fileB\"")
	public void whenILaunchimagediffExpectedpngActualpng(final String fileA,
			final String fileB) {
		final Sandbox sandbox = new Sandbox();
		sandbox.useResource(fileA);
		sandbox.useResource(fileB);
		ui = ImageDiff.launch(sandbox.getRoot(), fileA, fileB);
	}

	@When("I click the display")
	public void whenIClickTheDisplay() {
		final MouseListener[] mouseListeners = ui.getMouseListeners();
		for (final MouseListener mouseListener : mouseListeners) {
			mouseListener.mouseClicked(null);
		}
	}

	@Then("I should see $what")
	public void thenIShouldSeeADisplayOfActualpngWithTheDifferencesFromExpectedpngHighlighted(
			final String what) throws Exception {
		final String hash = DigestUtils.shaHex(what);
		assertThat(ui, Matchers.looksLike("/" + hash + ".png"));
	}

}
