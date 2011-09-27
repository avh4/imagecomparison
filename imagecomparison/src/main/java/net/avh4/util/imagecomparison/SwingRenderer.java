package net.avh4.util.imagecomparison;

import java.awt.Component;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.Window;
import java.awt.image.BufferedImage;

import javax.swing.JFrame;

class SwingRenderer implements Renderer {

	@Override
	public BufferedImage getImage(final Object item) {
		if (item instanceof JFrame) {
			return drawComponent(((JFrame) item).getContentPane());
		} else if (item instanceof Component) {
			return drawComponent((Component) item);
		} else {
			return null;
		}
	}

	static BufferedImage drawComponent(final Component c) {
		validateComponent(c);
		final BufferedImage image = new BufferedImage(c.getWidth(),
				c.getHeight(), BufferedImage.TYPE_INT_ARGB);
		final Graphics2D g = image.createGraphics();
		g.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
				RenderingHints.VALUE_ANTIALIAS_OFF);
		g.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING,
				RenderingHints.VALUE_TEXT_ANTIALIAS_OFF);
		c.paint(g);
		g.dispose();
		return image;
	}

	private static void validateComponent(final Component c) {
		if (!c.isValid()) {
			if (c instanceof Window) {
				final Window c1 = (Window) c;
				c1.pack();
			} else {
				c.setSize(c.getPreferredSize());
			}
		}
	}

}
