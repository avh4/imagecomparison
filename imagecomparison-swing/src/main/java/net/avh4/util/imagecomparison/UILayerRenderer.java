package net.avh4.util.imagecomparison;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;

/**
 * We do not want a compile-time dependency on uilayer-core or uilayer-swing,
 * since both of those packages depend on imagecomparison. In the code below,
 * the simplified code is commented out, and is replaced by the reflection
 * version of the same thing.
 */
public class UILayerRenderer implements Renderer {

    @Override
    public BufferedImage getImage(final Object obj) {
        try {
            final Class<?> sceneCreator = Class
                    .forName("net.avh4.framework.uilayer.SceneCreator");
            final Class<?> sceneElement = Class
                    .forName("net.avh4.framework.uilayer.scene.SceneElement");
            final Class<?> swingSceneRenderer = Class
                    .forName("net.avh4.framework.uilayer.scene.SwingSceneRenderer");

            // if (obj instanceof SceneCreator) {
            if (sceneCreator.isAssignableFrom(obj.getClass())) {
                // final Component comp = new SwingSceneRenderer((SceneCreator)
                // obj);
                final Constructor<?> ctor = swingSceneRenderer
                        .getConstructor(sceneCreator);
                final Component comp = (Component) ctor.newInstance(obj);

                return SwingRenderer.drawComponent(comp);
                // } else if (obj instanceof SceneElement) {
            } else if (sceneElement.isAssignableFrom(obj.getClass())) {
                // final Component comp = new SwingSceneRenderer((SceneElement)
                // obj);
                final Constructor<?> ctor = swingSceneRenderer
                        .getConstructor(sceneElement);
                final Component comp = (Component) ctor.newInstance(obj);

                return SwingRenderer.drawComponent(comp);
            }

        } catch (final ClassNotFoundException e) {
            e.printStackTrace();
        } catch (final SecurityException e) {
            e.printStackTrace();
        } catch (final NoSuchMethodException e) {
            e.printStackTrace();
        } catch (final IllegalArgumentException e) {
            e.printStackTrace();
        } catch (final InstantiationException e) {
            e.printStackTrace();
        } catch (final IllegalAccessException e) {
            e.printStackTrace();
        } catch (final InvocationTargetException e) {
            e.printStackTrace();
        }
        return null;
    }
}
