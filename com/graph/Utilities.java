package com.graph;

import java.awt.Point;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Vector;

import javax.swing.JOptionPane;

import com.graph.Main;
import com.graph.Utilities;
import com.graph.components.Arc;
import com.graph.components.Cost;
import com.graph.components.Node;
import com.graph.graphics.Panel;

public class Utilities {

	/*-------Cost helper methods------*/

	public static void addCost(int x, int y, int cost) {
		Main.costuri.add(new Cost(x, y, cost));
	}

	public static int getCost(int x, int y) {
		for (Cost c : Main.costuri) {
			if ((c.getX() == x && c.getY() == y) || (c.getX() == y && c.getY() == x))
				return c.getCost();
		}
		return 0;
	}

	/*---General helper methods----*/
	
	/**** checks if there is arc between x and y *****/
	static public boolean existsArc(int x, int y) {
		if (Main.current == 1)
			for (Arc arc : Main.listaArce) {
				int[] nodes = Utilities.arcNodes(arc);
				if ((nodes[0] == x && nodes[1] == y))
					return true;
			}
		else
			for (Arc arc : Main.listaArce) {
				int[] nodes = Utilities.arcNodes(arc);
				if ((nodes[0] == x && nodes[1] == y) || (nodes[0] == y && nodes[1] == x))
					return true;
			}
		return false;
	}

	static public Arc getArc(int x, int y) {
		for (Arc arc : Main.listaArce) {
			int[] nodes = Utilities.arcNodes(arc);
			if ((nodes[0] == x && nodes[1] == y) || (nodes[0] == y && nodes[1] == x))
				return arc;
		}
		return null;
	}

	public static Vector<String> arcsToString() {
		Vector<String> data = new Vector<String>();

		if (Main.current == 0)
			for (Arc a : Main.listaArce) {
				int[] arcs = Utilities.arcNodes(a);
				if (Main.enableCost) {
					data.addElement("Arc from node " + arcs[0] + " to node " + arcs[1] + " has cost "
							+ Utilities.getCost(arcs[0], arcs[1]));
				} else
					data.addElement("Arc from node " + arcs[0] + " to node " + arcs[1]);
			}
		else
			for (Arc a : Main.listaArce) {
				int[] arcs = Utilities.arcNodes(a);
				data.addElement("Arc from node " + arcs[0] + " to node " + arcs[1]);
			}
		return data;
	}

	public static boolean insideNode(Point p, Node node) {
		int r = (int) Math.pow(node.getSize() / 2, 2);
		int d = (int) (Math.pow(p.x - node.getCoordX(), 2) + Math.pow(p.y - node.getCoordY(), 2));
		if (d <= r) {
			return true;
		}
		return false;
	}

	public static boolean isArcGood(Arc arc) {
		boolean ok1 = false;
		boolean ok2 = false;
		boolean ok3 = true;
		for (Node n : Main.listaNoduri) {
			if (Utilities.insideNode(arc.getStart(), n))
				ok1 = true;
			if (Utilities.insideNode(arc.getEnd(), n))
				ok2 = true;

		}
		for (Arc a : Main.listaArce) {

			if (arc.existsAlready(a)) {
				ok3 = false;
			}
		}

		if (ok1 && ok2 && ok3)
			return true;
		return false;

	}

	public static Point nodeCoord(Point p) {
		for (Node n : Main.listaNoduri) {
			if (Utilities.insideNode(p, n)) {
				return new Point(n.getCoordX(), n.getCoordY());
			}
		}
		return null;
	}

	public static boolean collidesOther(Node node) {
		for (Node n : Main.listaNoduri) {
			if (node.collides(n))
				return true;
		}
		return false;
	}

	public static void reset() {
		Main.listaArce = new Vector<Arc>();
		Main.listaNoduri = new Vector<Node>();
		Main.costuri = new Vector<Cost>();
		Main.enableCost = false;
		Main.nodeNr = 1;
		Panel.mainPane.getRightComponent().repaint();

	}

	public static int[] arcNodes(Arc a) {
		int[] result = new int[2];
		for (Node n : Main.listaNoduri) {
			if (insideNode(a.getStart(), n)) {
				result[0] = n.getNumber();
			}
			if (insideNode(a.getEnd(), n)) {
				result[1] = n.getNumber();
			}
		}
		return result;
	}

	public static String roadMsg(Vector<Integer> v) {
		String msg = new String();
		msg += "Road is :";
		for (int index = 0; index < v.size() - 1; index++) {
			msg += v.get(index) + " -> ";
		}
		msg += v.lastElement() + " ; ";
		return msg;
	}

	public static void exportFile(String filename) {
		BufferedWriter writer = null;
		try {

			writer = new BufferedWriter(new FileWriter(filename));
			if (Main.current == 0) {
				if (Main.enableCost == true) {
					writer.write("0 1");
					writer.newLine();
					for (Arc a : Main.listaArce) {
						int[] nodes = Utilities.arcNodes(a);
						writer.write(a.getStart().x + " " + a.getStart().y + " " + a.getEnd().x + " " + a.getEnd().y
								+ " " + nodes[0] + " " + nodes[1] + " " + Utilities.getCost(nodes[0], nodes[1]));
						writer.newLine();
					}
				} else {
					writer.write("0 0");
					writer.newLine();
					for (Arc a : Main.listaArce) {
						int[] nodes = Utilities.arcNodes(a);
						writer.write(a.getStart().x + " " + a.getStart().y + " " + a.getEnd().x + " " + a.getEnd().y
								+ " " + nodes[0] + " " + nodes[1]);
						writer.newLine();

					}
				}
			} else {
				writer.write("1 0");
				writer.newLine();
				for (Arc a : Main.listaArce) {
					int[] nodes = Utilities.arcNodes(a);
					writer.write(a.getStart().x + " " + a.getStart().y + " " + a.getEnd().x + " " + a.getEnd().y + " "
							+ nodes[0] + " " + nodes[1]);
					writer.newLine();
				}
			}

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				// Close the writer regardless of what happens...
				writer.close();
			} catch (Exception e) {
			}
		}
	}

	public static void importFile(String filename) {

		BufferedReader br = null;
		try {
			String line;
			br = new BufferedReader(new FileReader(filename));
			line = br.readLine();
			if (line.contains("0 0")) {
				Main.current = 0;
				while ((line = br.readLine()) != null) {

					String[] split = line.split(" ");
					Point a = new Point(Integer.parseInt(split[0]), Integer.parseInt(split[1]));
					Point b = new Point(Integer.parseInt(split[2]), Integer.parseInt(split[3]));
					Main.listaArce.add(new Arc(a, b));

					Node n1 = new Node(a.x, a.y, Integer.parseInt(split[4]), Main.nodeSize);
					Node n2 = new Node(b.x, b.y, Integer.parseInt(split[5]), Main.nodeSize);

					if (Main.nodeNr < Integer.parseInt(split[4]))
						Main.nodeNr = Integer.parseInt(split[4]);
					if (Main.nodeNr < Integer.parseInt(split[5]))
						Main.nodeNr = Integer.parseInt(split[5]);
					if (!collidesOther(n1))
						Main.listaNoduri.add(n1);
					if (!collidesOther(n2))
						Main.listaNoduri.add(n2);
				}
			} else if (line.contains("0 1")) {
				Main.current = 0;
				Main.enableCost = true;
				while ((line = br.readLine()) != null) {

					String[] split = line.split(" ");
					Point a = new Point(Integer.parseInt(split[0]), Integer.parseInt(split[1]));
					Point b = new Point(Integer.parseInt(split[2]), Integer.parseInt(split[3]));
					Main.listaArce.add(new Arc(a, b));

					Cost c = new Cost(Integer.parseInt(split[4]), Integer.parseInt(split[5]),
							Integer.parseInt(split[6]));
					Main.costuri.add(c);

					Node n1 = new Node(a.x, a.y, Integer.parseInt(split[4]), Main.nodeSize);
					Node n2 = new Node(b.x, b.y, Integer.parseInt(split[5]), Main.nodeSize);

					if (Main.nodeNr < Integer.parseInt(split[4]))
						Main.nodeNr = Integer.parseInt(split[4]);
					if (Main.nodeNr < Integer.parseInt(split[5]))
						Main.nodeNr = Integer.parseInt(split[5]);
					if (!collidesOther(n1))
						Main.listaNoduri.add(n1);
					if (!collidesOther(n2))
						Main.listaNoduri.add(n2);
				}
			} else {
				Main.current = 1;
				while ((line = br.readLine()) != null) {

					String[] split = line.split(" ");
					Point a = new Point(Integer.parseInt(split[0]), Integer.parseInt(split[1]));
					Point b = new Point(Integer.parseInt(split[2]), Integer.parseInt(split[3]));
					Main.listaArce.add(new Arc(a, b));

					Node n1 = new Node(a.x, a.y, Integer.parseInt(split[4]), Main.nodeSize);
					Node n2 = new Node(b.x, b.y, Integer.parseInt(split[5]), Main.nodeSize);

					if (Main.nodeNr < Integer.parseInt(split[4]))
						Main.nodeNr = Integer.parseInt(split[4]);
					if (Main.nodeNr < Integer.parseInt(split[5]))
						Main.nodeNr = Integer.parseInt(split[5]);
					if (!collidesOther(n1))
						Main.listaNoduri.add(n1);
					if (!collidesOther(n2))
						Main.listaNoduri.add(n2);
				}
			}
			Main.nodeNr++;

		} catch (IOException e) {
			// e.printStackTrace();
			JOptionPane.showMessageDialog(null, "File not found or error opening it.");
		} finally {
			try {
				if (br != null)
					br.close();
			} catch (IOException ex) {
				// ex.printStackTrace();
				JOptionPane.showMessageDialog(null, "File not found or error opening it.");
			}
		}

	}

}