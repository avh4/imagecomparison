package net.avh4.util.imagecomparison;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class ImageDiffView extends JComponent {

    private static final long serialVersionUID = 1L;

    private final BufferedImage image1;
    private final BufferedImage image2;

    private boolean showHighlight = true;

    public ImageDiffView(File file1, File file2) throws IOException {
        image1 = ImageIO.read(file1);
        image2 = ImageIO.read(file2);
    }

    @Override
    public Dimension getPreferredSize() {
        return new Dimension(image1.getWidth() + image2.getWidth(), Math.max(
                image1.getHeight(), image2.getHeight()));
    }

    @Override
    protected void paintComponent(final Graphics g) {
        final int w1 = image1.getWidth();
        final int w2 = image2.getWidth();
        final int h1 = image1.getHeight();
        final int h2 = image2.getHeight();
        g.drawImage(image1, 0, 0, null);
        g.drawImage(image2, w1, 0, null);

        if (showHighlight) {
            g.setColor(Color.RED);
            for (int x = 0; x < Math.min(w1, w2); x++) {
                for (int y = 0; y < Math.min(h1, h2); y++) {
                    if (image1.getRGB(x, y) != image2.getRGB(x, y)) {
                        g.drawLine(x, y, x, y);
                    }
                }
            }
        }
    }

    public void setShowHighlight(final boolean b) {
        showHighlight = b;
        repaint();
    }
}
