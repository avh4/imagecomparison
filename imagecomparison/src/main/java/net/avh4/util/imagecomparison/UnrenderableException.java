package net.avh4.util.imagecomparison;

import java.util.List;

public class UnrenderableException extends RuntimeException {
    private final Object unrenderableObject;
    private final List<Renderer> triedRenderers;

    public UnrenderableException(Object unrenderableObject, List<Renderer> triedRenderers) {
        this.unrenderableObject = unrenderableObject;
        this.triedRenderers = triedRenderers;
    }

    @Override
    public String getMessage() {
        StringBuilder sb = new StringBuilder();
        sb.append("Don't know how to make an image of ");
        sb.append(unrenderableObject);
        sb.append("\nUsing renderers:");
        for (Renderer renderer : triedRenderers) {
            sb.append("\n        * ");
            sb.append(renderer.toString());
        }
        return sb.toString();
    }
}
