package com.graph.graphics;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;

import javax.swing.BorderFactory;
import javax.swing.JPanel;

import com.graph.Main;
import com.graph.Utilities;
import com.graph.components.Arc;
import com.graph.components.Node;
import com.graph.graphics.util.Cost;

public class PaintPane extends JPanel {

	private static final long serialVersionUID = 1L;// Don't know what this does

	private Point pointStart = null;
	private Point pointEnd = null;
	private boolean isDragging = false;

	public PaintPane() {
		setBorder(BorderFactory.createLineBorder(Color.black));
		addMouseListener(new MouseAdapter() {
			public void mousePressed(MouseEvent e) {
				pointStart = e.getPoint();
			}

			public void mouseReleased(MouseEvent e) {
				if (!isDragging) {
					if (!Utilities.collidesOther(new Node(e.getX(), e.getY(), -1, Main.nodeSize))) {
						addNode(e.getX(), e.getY(), Main.nodeSize);
					}
				} else {
					Arc arc = new Arc(pointStart, pointEnd);
					if (Main.current == 0) {
						if (Utilities.isArcGood(arc)) {
							Point p1 = Utilities.nodeCoord(pointStart);
							Point p2 = Utilities.nodeCoord(pointEnd);
							Arc a = new Arc(p1, p2);
							Arc a2 = new Arc(p2, p1);
							if (Main.enableCost) {
								int[] nodes = Utilities.arcNodes(a);
								Cost.show(nodes[0], nodes[1]);
								repaint();
							}
							Main.listaArce.add(a);
							Main.listaArce.add(a2);
						}
					} else {
						if (Utilities.isArcGood(arc)) {
							Main.listaArce.add(new Arc(Utilities.nodeCoord(pointStart), Utilities.nodeCoord(pointEnd)));
						}
					}
					repaint();

				}
				pointStart = null;
				isDragging = false;
			}
		});
		addMouseMotionListener(new MouseMotionAdapter() {
			public void mouseDragged(MouseEvent e) {
				pointEnd = e.getPoint();
				isDragging = true;
				repaint();
			}
		});
	}

	private void addNode(int x, int y, int nodeSize) {
		Node node = new Node(x, y, Main.nodeNr, nodeSize);
		Main.listaNoduri.add(node);
		Main.nodeNr++;
		repaint();
	}

	/*-------executes when repaint() is called------*/
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		for (Arc a : Main.listaArce) {
			a.drawArc(g);

		}
		if (pointStart != null) {
			g.drawLine(pointStart.x, pointStart.y, pointEnd.x, pointEnd.y);
		}
		for (Node nod : Main.listaNoduri) {
			nod.drawNode(g, Main.nodeSize);
		}
	}
}
