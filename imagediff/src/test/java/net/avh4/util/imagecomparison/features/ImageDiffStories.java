package net.avh4.util.imagecomparison.features;

import java.util.Arrays;
import java.util.List;

import net.avh4.util.imagecomparison.steps.Steps;

import org.jbehave.core.configuration.Configuration;
import org.jbehave.core.configuration.MostUsefulConfiguration;
import org.jbehave.core.io.CodeLocations;
import org.jbehave.core.io.LoadFromClasspath;
import org.jbehave.core.io.StoryFinder;
import org.jbehave.core.junit.JUnitStories;
import org.jbehave.core.reporters.Format;
import org.jbehave.core.reporters.StoryReporterBuilder;
import org.jbehave.core.steps.CandidateSteps;
import org.jbehave.core.steps.InstanceStepsFactory;

public class ImageDiffStories extends JUnitStories {

	@Override
	protected List<String> storyPaths() {
		final String codeLocation = CodeLocations.codeLocationFromClass(
				this.getClass()).getFile();
		return new StoryFinder().findClassNames(codeLocation,
				Arrays.asList("**/*.story"), Arrays.asList(""));
	}

	@Override
	public Configuration configuration() {
		return new MostUsefulConfiguration().useStoryLoader(
				new LoadFromClasspath(this.getClass()))
				.useStoryReporterBuilder(
						new StoryReporterBuilder().withDefaultFormats()
								.withFormats(Format.CONSOLE, Format.TXT));
	}

	@Override
	public List<CandidateSteps> candidateSteps() {
		return new InstanceStepsFactory(configuration(), new Steps())
				.createCandidateSteps();
	}
}
