package com.graph;

import java.util.Vector;

import javax.swing.JFrame;

import com.graph.components.Arc;
import com.graph.components.Cost;
import com.graph.components.Node;
import com.graph.graphics.Panel;

public class Main {

	public static Vector<Node> listaNoduri = new Vector<Node>();
	public static Vector<Arc> listaArce = new Vector<Arc>();
	public static Vector<Cost> costuri = new Vector<Cost>();

	public static boolean enableCost = false;

	public static int current = 0; // 1 = directed , 0 = undirected
	public static int nodeSize = 30;
	public static int nodeNr = 1;

	private static JFrame frame;

	private static void createAndShowGUI() {

		// Create and set up the window.
		frame = new JFrame("Something");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setResizable(false);
		Panel panel = new Panel();
		frame.setSize(panel.width1 + panel.width2, panel.height);
		frame.setLocationRelativeTo(null);
		frame.getContentPane().add(panel.getPane());
		// Display the window.
		frame.pack();
		frame.setVisible(true);
	}

	public static void main(String[] args) {
		javax.swing.SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				createAndShowGUI();
			}
		});
	}

}
