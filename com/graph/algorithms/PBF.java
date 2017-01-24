package com.graph.algorithms;

import java.util.Vector;

import com.graph.Main;
import com.graph.Utilities;
import com.graph.components.Arc;

public class PBF {

	private static int infinity = (int) Double.POSITIVE_INFINITY;

	static public Vector<Integer> totalAlgorithm() {
		/*---declarations-----*/
		int nodSursa = 3;

		Vector<Integer> U = new Vector<Integer>();
		Vector<Integer> V = new Vector<Integer>();
		Vector<Integer> W = new Vector<Integer>();
		Vector<Integer> predecesor = new Vector<Integer>();

		int[] l = new int[Main.nodeNr + 1];

		/*----initializations-----*/
		U = init();
		eliminate(U, nodSursa);
		V.add(nodSursa);
		predecesor = init2();
		for (int i = 1; i <= Main.nodeNr; i++) {
			predecesor.set(i, 0);
		}
		l[nodSursa] = 0;
		for (int i = 0; i < U.size(); i++) {
			l[U.elementAt(i)] = infinity;
		}

		/*----Alg PBF----*/
		while (!equal(W)) {
			while (!V.isEmpty()) {
				int x = V.lastElement();
				Vector<Integer> arcs = arcFromX(x);
				for (int index = 0; index < arcs.size(); index++) {
					int y = arcs.get(index);
					if (U.contains(y)) {
						eliminate(U, y);
						V.add(y);
						predecesor.set(y, x);
						l[y] = l[x] + 1;
					}
				}
				eliminate(V, x);
				W.add(x);
			}
			if (!equal(W)) {
				nodSursa = smallestElement(U);
				eliminate(U, nodSursa);
				V = new Vector<Integer>();
				V.add(nodSursa);
			} else
				break;

		}
		return predecesor;
	}

	static public Vector<Integer> partialAlgorithm(int nodSursa) {
		/*---declarations-----*/
		// int[] noduri = new int[Main.nodeNr+1];

		Vector<Integer> U = new Vector<Integer>();
		Vector<Integer> V = new Vector<Integer>();
		Vector<Integer> W = new Vector<Integer>();

		Vector<Integer> predecesor = new Vector<Integer>();
		int[] l = new int[Main.nodeNr + 1];

		/*----initializations-----*/
		U = init();
		eliminate(U, nodSursa);
		V.add(nodSursa);
		// W
		predecesor = init2();
		for (int i = 1; i <= Main.nodeNr; i++) {
			predecesor.set(i, 0);
		}
		l[nodSursa] = 0;
		for (int i = 0; i < U.size(); i++) {
			l[U.elementAt(i)] = infinity;
		}

		/*----Alg PBF----*/
		// System.out.println("U = " + U + " ; " +"V = "+ V + " ; " +"W = "+ W);
		while (!V.isEmpty()) {
			int x = V.lastElement();
			Vector<Integer> arcs = arcFromX(x);
			for (int index = 0; index < arcs.size(); index++) {
				int y = arcs.get(index);
				if (U.contains(y)) {
					eliminate(U, y);
					V.add(y);
					predecesor.set(y, x);
					l[y] = l[x] + 1;
				}
			}
			eliminate(V, x);
			W.add(x);
			// System.out.println("U = " + U + " ; " +"V = "+ V + " ; " +"W = "+
			// W);
		}
		return predecesor;
	}

	/****** road from s to y , with specified algorithm *******/
	static public Vector<Integer> road(int s, int y, Vector<Integer> p) {
		Vector<Integer> r = new Vector<Integer>();
		r.add(y);
		while (p.get(y) != 0) {
			int x = p.get(y);
			r.add(x);
			y = x;
		}

		return r;
	}

	/***** Initialization for predecesor ****/
	static private Vector<Integer> init2() {
		Vector<Integer> v = new Vector<Integer>();
		for (int index = 0; index <= Main.nodeNr; index++) {
			v.add(index);
		}
		return v;
	}

	/**** checks if V has ever node ******/
	static private boolean equal(Vector<Integer> v) {
		for (int i = 1; i <= Main.nodeNr - 1; i++) {
			if (!v.contains(i))
				return false;
		}
		return true;
	}

	/***** initialization for U ******/
	static private Vector<Integer> init() {
		Vector<Integer> v = new Vector<Integer>();
		for (int i = 1; i <= Main.nodeNr - 1; i++)
			v.add(i);
		return v;
	}

	/***** eliminates element nod from vector v ****/
	static private void eliminate(Vector<Integer> v, int nod) {
		for (int i = 0; i < v.size(); i++)
			if (v.elementAt(i) == nod)
				v.remove(i);
	}

	/***** return smallest element of v *****/
	static private int smallestElement(Vector<Integer> v) {
		int min = infinity;
		for (int i = 0; i < v.size(); i++)
			if (v.get(i) < min)
				min = v.get(i);
		return min;
	}

	/***** returns all arcs which start at x *****/
	static private Vector<Integer> arcFromX(int x) {
		Vector<Integer> arcs = new Vector<Integer>();
		for (Arc arc : Main.listaArce) {
			int[] nodes = Utilities.arcNodes(arc);
			if (nodes[0] == x) {
				arcs.add(nodes[1]);
			}
		}
		return arcs;
	}

}
