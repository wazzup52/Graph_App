package com.graph.algorithms;

import java.util.Vector;

import com.graph.Main;
import com.graph.Utilities;
import com.graph.components.Arc;

public class PDF {

	private static int infinity = (int) Double.POSITIVE_INFINITY;

	static public Vector<Integer> totalAlgorithm() {
		/*---declarations-----*/

		int nodSursa = 3;

		Vector<Integer> U = new Vector<Integer>();
		Vector<Integer> V = new Vector<Integer>();
		Vector<Integer> W = new Vector<Integer>();

		Vector<Integer> predecesor = new Vector<Integer>();
		int[] t1 = new int[Main.nodeNr + 1];
		int[] t2 = new int[Main.nodeNr + 1];

		/*----initializations-----*/
		U = init();
		eliminate(U, nodSursa);
		V.add(nodSursa);
		// W
		predecesor = init2();
		for (int i = 1; i <= Main.nodeNr; i++) {
			predecesor.set(i, 0);
		}
		int t = 1;
		t1[nodSursa] = 1;
		t2[nodSursa] = infinity;
		for (int i = 0; i < U.size(); i++) {
			t1[U.elementAt(i)] = infinity;
			t2[U.elementAt(i)] = infinity;
		}

		/*----Alg PBF----*/
		while (!equal(W)) {
			while (!V.isEmpty()) {
				int x = V.lastElement();
				int y = checkIfExistArc(x, U);
				if (y != 0) {
					eliminate(U, y);
					V.add(y);
					predecesor.set(y, x);
					t += 1;
					t1[y] = t;
				} else {
					eliminate(V, x);
					W.add(x);
					t += 1;
					t2[x] = t;
				}
			}
			if (!equal(W)) {
				nodSursa = smallestElement(U);
				eliminate(U, nodSursa);
				V = new Vector<Integer>();
				V.add(nodSursa);
				t += 1;
				t1[nodSursa] = t;
			} else
				break;

		}
		return predecesor;
	}

	static public Vector<Integer> partialAlgorithm(int nodSursa) {
		/*---declarations-----*/
		Vector<Integer> U = new Vector<Integer>();
		Vector<Integer> V = new Vector<Integer>();
		Vector<Integer> W = new Vector<Integer>();

		Vector<Integer> predecesor = new Vector<Integer>();
		int[] t1 = new int[Main.nodeNr + 1];
		int[] t2 = new int[Main.nodeNr + 1];

		/*----initializations-----*/
		U = init();
		eliminate(U, nodSursa);
		V.add(nodSursa);
		// W
		predecesor = init2();
		for (int i = 1; i <= Main.nodeNr; i++) {
			predecesor.set(i, 0);
		}
		int t = 1;
		t1[nodSursa] = 1;
		t2[nodSursa] = infinity;
		for (int i = 0; i < U.size(); i++) {
			t1[U.elementAt(i)] = infinity;
			t2[U.elementAt(i)] = infinity;
		}

		/*----Alg PDF----*/
		while (!V.isEmpty()) {
			int x = V.lastElement();
			int y = checkIfExistArc(x, U);
			if (y != 0) {
				eliminate(U, y);
				V.add(y);
				predecesor.set(y, x);
				t += 1;
				t1[y] = t;
			} else {
				eliminate(V, x);
				W.add(x);
				t += 1;
				t2[x] = t;
			}
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

	/**** checks if there is arc between x and y *****/
	static private boolean existsArc(int x, int y) {
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

	/*****
	 * checks if exist arc from x to any element of U , returns first element of
	 * U which meets the requirements
	 *****/
	static private int checkIfExistArc(int x, Vector<Integer> u) {
		for (int index = 0; index < u.size(); index++) {
			int y = u.elementAt(index);
			if (existsArc(x, y))
				return y;
		}
		return 0;
	}

	/***** return smallest element of v *****/
	static private int smallestElement(Vector<Integer> v) {
		int min = infinity;
		for (int i = 0; i < v.size(); i++)
			if (v.get(i) < min)
				min = v.get(i);
		return min;
	}

}
