package hu.documaison.gui;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.PaintEvent;
import org.eclipse.swt.events.PaintListener;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.widgets.Canvas;

public class ImageHelper {

	public static void setResizedBackground(final Canvas c, final Image image) {
		c.setData(image);
		c.redraw();

		if (c.getListeners(SWT.Paint).length == 0) {
			c.addPaintListener(new PaintListener() {

				@Override
				public void paintControl(PaintEvent e) {
					Image image = (Image) c.getData();
					e.gc.setAntialias(SWT.ON);
					e.gc.setInterpolation(SWT.HIGH);
					e.gc.drawImage(image, 0, 0, image.getBounds().width,
							image.getBounds().height, 0, 0, e.width, e.height);
				}
			});
		}
	}

	public static byte[] getImageBytes(String path) throws IOException {
		File thumbFile = new File(path);
		if (thumbFile.exists() == false || thumbFile.canRead() == false) {
			throw new IOException("Can't read the given file (" + path + ")");
		}
		byte[] thumbBytes = new byte[(int) thumbFile.length()];
		FileInputStream fis = new FileInputStream(thumbFile);
		fis.read(thumbBytes);
		fis.close();
		return thumbBytes;
	}
}
