package com.graph.algorithms;

import java.util.Vector;

import com.graph.Main;
import com.graph.Utilities;
import com.graph.components.Arc;
import com.graph.components.Cost;

public class GenericMT {

	public static int selectN(Vector<Integer>[] N, int k) {
		for (int i = k; i < N.length; i++) {
			if (!N[i].isEmpty())
				return i;
		}
		return 0;
	}

	public static int[] selectArcWithMinCost(Vector<Integer> Ni) {
		int y = 0, yb = 0;

		int min = (int) Double.POSITIVE_INFINITY;

		for (int i = 0; i < Ni.size(); i++) {

			int x = Ni.elementAt(i);

			for (int xb = 1; xb < Main.nodeNr; xb++)

				if (Utilities.existsArc(x, xb) && !Ni.contains(xb)) {
					int cost = Utilities.getCost(x, xb);
					if (cost < min) {
						min = cost;
						y = x;
						yb = xb;
					}
				}
		}
		int[] nodes = new int[2];
		nodes[0] = y;
		nodes[1] = yb;
		return nodes;
	}

	public static int findJ(int yb, Vector<Integer>[] N) {
		for (int index = 1; index < N.length; index++) {
			if (N[index].contains(yb))
				return index;
		}
		return 0;
	}

	public static Vector<Integer> unite(Vector<Integer> v1, Vector<Integer> v2) {
		for (int i = 0; i < v2.size(); i++) {
			v1.addElement(v2.elementAt(i));
		}
		return v1;
	}

	public static Vector<Arc> uniteArc(Vector<Arc> v1, Vector<Arc> v2) {
		if (v2 != null)
			for (Arc arc : v2) {
				v1.addElement(arc);
			}
		return v1;
	}

	public static void init(Vector<Integer>[] v) {
		for (int i = 0; i < Main.nodeNr; i++) {
			v[i] = new Vector<Integer>();
		}
	}

	public static void init2(Vector<Arc>[] v) {
		for (int i = 0; i < Main.nodeNr; i++) {
			v[i] = new Vector<Arc>();
		}
	}

	public static Vector<Arc> algorithm() {

		Vector<Integer>[] N = new Vector[Main.nodeNr];
		Vector<Arc>[] Ap = new Vector[Main.nodeNr];
		Vector<Arc> App = new Vector<Arc>();

		init(N);
		init2(Ap);
		for (int index = 1; index < Main.nodeNr; index++) {
			N[index].add(index);
			Ap[index].clear();

		}
		int i = 0;
		int z = 0;
		for (int index = 1; index < Main.nodeNr; index++) {

			i = selectN(N, index - 1);

			int[] arc = selectArcWithMinCost(N[i]);
			int j = findJ(arc[1], N);
			if (j != 0) {

				N[i] = unite(N[i], N[j]);
				N[j].clear();

				Ap[i] = uniteArc(Ap[i], Ap[j]);
				Ap[i].add(Utilities.getArc(arc[0], arc[1]));
				Ap[j].clear();
				z = i;
			}

		}
		App = Ap[z];
		return App;
	}

}
