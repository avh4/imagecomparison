package net.avh4.util.imagecomparison;

import static org.hamcrest.MatcherAssert.*;
import static org.hamcrest.Matchers.*;

import java.awt.image.BufferedImage;
import java.util.Arrays;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

public class UnrenderableExceptionTest {

	private UnrenderableException subject;

	@Before
	public void setup() throws Exception {
		List<Renderer> renderers = Arrays
				.asList(new Renderer1(), new Renderer2());
		subject = new UnrenderableException(new SomeObject(), renderers);
	}

	@Test
	public void getMessage_shouldIncludeMessage() throws Exception {
		assertThat(subject.getMessage(), containsString(
				"Don't know how to make an image of {{ some object }}"));
	}

	@Test
	public void getMessage_shouldIncludeListOfTriedRenderers()
			throws Exception {
		assertThat(subject.getMessage(), containsString(
				"net.avh4.util.imagecomparison.UnrenderableExceptionTest$Renderer1"));
		assertThat(subject.getMessage(), containsString(
				"net.avh4.util.imagecomparison.UnrenderableExceptionTest$Renderer2"));
	}

	private static class Renderer1 implements Renderer {
		@Override
		public BufferedImage getImage(Object obj) {
			return null;
		}
	}

	private static class Renderer2 implements Renderer {
		@Override
		public BufferedImage getImage(Object obj) {
			return null;
		}
	}

	private static class SomeObject {
		@Override
		public String toString() {
			return "{{ some object }}";
		}
	}
}
