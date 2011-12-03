package net.avh4.util.imagecomparison;

import java.awt.Color;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.File;
import java.io.IOException;

import javax.swing.JFrame;

public class ImageDiff extends JFrame implements MouseListener {

	private static final long serialVersionUID = 1L;
	private final ImageDiffView view;

	public ImageDiff(final String file1, final String file2) throws IOException {
		setBackground(Color.WHITE);
		addMouseListener(this);
		view = new ImageDiffView(file1, file2);
		getContentPane().add(view);
		pack();
	}

	public static void main(final String[] args) {
		final ImageDiff ui = launch(args[0], args[1]);
		ui.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		ui.setLocationRelativeTo(null);
		ui.setVisible(true);
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
