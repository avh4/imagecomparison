package net.avh4.util.imagecomparison;

import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;
import java.util.ServiceLoader;

public class ImageRenderer {
    private static final ServiceLoader<Renderer> rendererLoader = ServiceLoader.load(Renderer.class);

    public static BufferedImage getImage(final Object item) {

        if (item == null) {
            return null;
        }

        if (item instanceof BufferedImage) {
            return (BufferedImage) item;
        }

        for (final Renderer r : rendererLoader) {
            final BufferedImage rendering = r.getImage(item);
            if (rendering != null) {
                return rendering;
            }
        }

        return null;
    }

    public static List<Renderer> getRenderers() {
        List<Renderer> renderers = new ArrayList<Renderer>();
        for (Renderer renderer : rendererLoader) {
            renderers.add(renderer);
        }
        return renderers;
    }
}
