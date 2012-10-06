/*******************************************************************************
 * Copyright (c) 2011 Laurent CARON
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     Laurent CARON (laurent.caron at gmail dot com) - Initial implementation and API
 *******************************************************************************/
package hu.documaison.gui;

import org.eclipse.swt.SWT;
import org.eclipse.swt.SWTException;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.GC;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.Widget;

/**
 * Instances of this class are controls which background's texture is a gradient
 * composite
 */
public class GradientComposite extends Composite {
	private Image oldImage;
	private Color gradientEnd;
	private Color gradientStart;
	private boolean gradientEnabled = false;
	private Color defaultBackground;

	public void setDefaultBackground(Color defaultBackground) {
		this.defaultBackground = defaultBackground;
	}

	public void setGradientEnabled(boolean gradientEnabled) {
		this.gradientEnabled = gradientEnabled;
	}

	/**
	 * Constructs a new instance of this class given its parent and a style
	 * value describing its behavior and appearance.
	 * <p>
	 * The style value is either one of the style constants defined in class
	 * <code>SWT</code> which is applicable to instances of this class, or must
	 * be built by <em>bitwise OR</em>'ing together (that is, using the
	 * <code>int</code> "|" operator) two or more of those <code>SWT</code>
	 * style constants. The class description lists the style constants that are
	 * applicable to the class. Style bits are also inherited from superclasses.
	 * </p>
	 * 
	 * @param parent
	 *            a widget which will be the parent of the new instance (cannot
	 *            be null)
	 * @param style
	 *            the style of widget to construct
	 * 
	 * @exception IllegalArgumentException
	 *                <ul>
	 *                <li>ERROR_NULL_ARGUMENT - if the parent is null</li>
	 *                </ul>
	 * @exception SWTException
	 *                <ul>
	 *                <li>ERROR_THREAD_INVALID_ACCESS - if not called from the
	 *                thread that created the parent</li>
	 *                </ul>
	 * 
	 * @see Composite#Composite(Composite, int)
	 * @see SWT#NO_BACKGROUND
	 * @see SWT#NO_FOCUS
	 * @see SWT#NO_MERGE_PAINTS
	 * @see SWT#NO_REDRAW_RESIZE
	 * @see SWT#NO_RADIO_GROUP
	 * @see SWT#EMBEDDED
	 * @see SWT#DOUBLE_BUFFERED
	 * @see Widget#getStyle
	 */
	public GradientComposite(final Composite parent, final int style) {
		super(parent, style);
		this.addListener(SWT.Resize, new Listener() {
			@Override
			public void handleEvent(final Event event) {
				GradientComposite.this.redrawComposite();
			}
		});

		this.gradientEnd = new Color(this.getDisplay(), 110, 110, 110);
		this.gradientStart = new Color(this.getDisplay(), 0, 0, 0);

	}

	/**
	 * Redraws the composite
	 */
	protected void redrawComposite() {

		final Display display = this.getDisplay();
		final Rectangle rect = this.getClientArea();
		final Image newImage = new Image(display, Math.max(1, rect.width),
				Math.max(1, rect.height));

		final GC gc = new GC(newImage);

		if (gradientEnabled) {
			gc.setForeground(this.gradientStart);
			gc.setBackground(this.gradientEnd);
			gc.fillGradientRectangle(rect.x, rect.y, rect.width, rect.height,
					true);
		} else {
			gc.setForeground(this.defaultBackground);
			gc.setBackground(this.defaultBackground);
			gc.fillRectangle(rect.x, rect.y, rect.width, rect.height);
		}

		gc.dispose();

		this.setBackgroundImage(newImage);
		if (this.oldImage != null) {
			this.oldImage.dispose();
		}
		this.oldImage = newImage;

	}

	// ------------------------------------ Getters and Setters
	/**
	 * @return the gradientEnd color
	 */
	public Color getGradientEnd() {
		return this.gradientEnd;
	}

	/**
	 * @param gradientEnd
	 *            the gradientEnd color to set
	 */
	public void setGradientEnd(final Color gradientEnd) {

		this.gradientEnd = gradientEnd;
	}

	/**
	 * @return the gradientStart color
	 */
	public Color getGradientStart() {
		return this.gradientStart;
	}

	/**
	 * @param gradientStart
	 *            the gradientStart color to set
	 */
	public void setGradientStart(final Color gradientStart) {
		// SWTGraphicUtil.dispose(this.gradientStart);
		this.gradientStart = gradientStart;
	}
}
