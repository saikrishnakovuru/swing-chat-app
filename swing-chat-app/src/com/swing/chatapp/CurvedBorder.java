package com.swing.chatapp;

import java.awt.Color;
import java.awt.Component;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Insets;
import java.awt.RenderingHints;
import java.awt.geom.RoundRectangle2D;

import javax.swing.border.AbstractBorder;

public class CurvedBorder extends AbstractBorder {

	private static final long serialVersionUID = 1L;
	private int radius;

	public CurvedBorder(int radius) {
		this.radius = radius;
	}

	@Override
	public void paintBorder(Component c, Graphics g, int x, int y, int width, int height) {
		Graphics2D g2 = (Graphics2D) g.create();
		g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		g2.setColor(Color.WHITE);
		g2.draw(new RoundRectangle2D.Double(x, y, width - 1, height - 1, radius, radius));
		g2.dispose();
	}

	@Override
	public Insets getBorderInsets(Component c) {
		return new Insets(this.radius, this.radius, this.radius, this.radius);
	}

	@Override
	public Insets getBorderInsets(Component c, Insets insets) {
		insets.left = insets.right = insets.bottom = insets.top = this.radius;
		return insets;
	}
}
