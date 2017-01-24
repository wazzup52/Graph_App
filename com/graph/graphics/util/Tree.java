package com.graph.graphics.util;

import java.awt.Color;
import java.awt.Graphics;
import java.util.Vector;

import javax.swing.BorderFactory;
import javax.swing.JPanel;

import com.graph.Main;
import com.graph.components.Arc;
import com.graph.components.Node;

public class Tree extends JPanel {

	public static Vector<Arc> arcs;
	private static final long serialVersionUID = 1L;

	public Tree(Vector<Arc> arc) {
		setBorder(BorderFactory.createLineBorder(Color.black));
		repaint();
		arcs = arc;

	}

	protected void paintComponent(Graphics g) {
		super.paintComponent(g);// apelez metoda paintComponent din clasa de
								// baza

		for (Arc a : arcs) {
			a.drawArc(g);
		}
		// deseneaza lista de noduri
		for (Node nod : Main.listaNoduri) {
			nod.drawNode(g, Main.nodeSize);
		}
	}

}
