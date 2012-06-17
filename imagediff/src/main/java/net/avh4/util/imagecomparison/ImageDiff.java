package net.avh4.util.imagecomparison;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.File;
import java.io.IOException;

public class ImageDiff extends JPanel implements MouseListener {

	private static final long serialVersionUID = 1L;
	private final ImageDiffView view;

	public ImageDiff(final String file1, final String file2) throws IOException {
        setBackground(new Color(232, 232, 232));
		final FlowLayout layout = (FlowLayout) getLayout();
		layout.setHgap(0);
		layout.setVgap(0);
		addMouseListener(this);
		view = new ImageDiffView(file1, file2);
		add(view);
	}

	public static void main(final String[] args) {
		final JFrame window = new JFrame();
		final ImageDiff ui = launch(args[0], args[1]);
		window.add(ui);
		window.pack();
		window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		window.setLocationRelativeTo(null);
		window.setVisible(true);
	}

	public static ImageDiff launch(final String file1, final String file2) {
		return launch(new File("."), file1, file2);
	}

	public static ImageDiff launch(final File root, final String file1,
			final String file2) {
		final String filename1 = new File(root, file1).getPath();
		final String filename2 = new File(root, file2).getPath();
		try {
			return new ImageDiff(filename1, filename2);
		} catch (final IOException e) {
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public void mouseClicked(final MouseEvent e) {
		view.setShowHighlight(false);
	}

	@Override
	public void mousePressed(final MouseEvent e) {
	}

	@Override
	public void mouseReleased(final MouseEvent e) {
	}

	@Override
	public void mouseEntered(final MouseEvent e) {
	}

	@Override
	public void mouseExited(final MouseEvent e) {
	}
}
