package net.avh4.util.imagecomparison;

import java.awt.Component;
import java.awt.image.BufferedImage;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;

/**
 * We do not want a compile-time dependency on uilayer-core or uilayer-swing,
 * since both of those packages depend on imagecomparison. In the code below,
 * the simplified code is commented out, and is replaced by the reflection
 * version of the same thing.
 */
class UILayerRenderer implements Renderer {

	@Override
	public BufferedImage getImage(final Object obj) {
		try {
			final Class<?> sceneCreator = Class
					.forName("net.avh4.framework.uilayer.SceneCreator");

			// if (obj instanceof SceneCreator) {
			if (sceneCreator.isAssignableFrom(obj.getClass())) {
				// final Component comp = new SwingSceneRenderer((SceneCreator)
				// obj);
				final Class<?> swingSceneRenderer = Class
						.forName("net.avh4.framework.uilayer.swing.scene.SwingSceneRenderer");
				final Constructor<?> ctor = swingSceneRenderer
						.getConstructor(sceneCreator);
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
