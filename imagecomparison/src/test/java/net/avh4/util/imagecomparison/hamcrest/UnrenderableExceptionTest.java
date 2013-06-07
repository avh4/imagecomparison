package net.avh4.util.imagecomparison.hamcrest;

import net.avh4.util.imagerender.Renderer;
import net.avh4.util.imagerender.UnrenderableException;
import org.junit.Before;
import org.junit.Test;

import java.awt.image.BufferedImage;
import java.util.Arrays;
import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.containsString;

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
                "net.avh4.util.imagecomparison.hamcrest.UnrenderableExceptionTest$Renderer1"));
        assertThat(subject.getMessage(), containsString(
                "net.avh4.util.imagecomparison.hamcrest.UnrenderableExceptionTest$Renderer2"));
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
