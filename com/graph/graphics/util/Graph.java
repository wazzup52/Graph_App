package com.graph.graphics.util;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.util.Vector;

import javax.swing.BorderFactory;
import javax.swing.JPanel;

import com.graph.Main;
import com.graph.Utilities;
import com.graph.components.Arc;
import com.graph.components.Node;

public class Graph extends JPanel {

	public static Vector<Integer> predecesor;
	private static final long serialVersionUID = 1L;

	public Graph(Vector<Integer> p) {
		setBorder(BorderFactory.createLineBorder(Color.black));
		repaint();
		predecesor = p;

	}

	protected void paintComponent(Graphics g) {
		super.paintComponent(g);// apelez metoda paintComponent din clasa de
								// baza

		for (Arc a : Main.listaArce) {
			int[] nodes = Utilities.arcNodes(a);
			if (predecesor.elementAt(nodes[1]).equals(nodes[0])) {
				a.drawArrow((Graphics2D) g, a.getStart().x, a.getStart().y, a.getEnd().x, a.getEnd().y);
			}
		}
		// deseneaza lista de noduri
		for (Node nod : Main.listaNoduri) {
			nod.drawNode(g, Main.nodeSize);
		}
	}

}
