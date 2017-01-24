package com.graph.algorithms;

import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.Scanner;
import java.util.Vector;

import com.graph.Main;
import com.graph.Utilities;
import com.graph.components.Arc;

public class BoruvkaMT {
	private Bag<Edges> mst = new Bag<Edges>(); // Edges in MST
	private double weight; // weight of MST

	public BoruvkaMT(EdgesWeightedGraph G) {
		UF uf = new UF(G.V());
		// repeat at most log V times or until we have V-1 Edges
		for (int t = 1; t < G.V() && mst.size() < G.V() - 1; t = t + t) {
			// foreach tree in forest, find closest Edges
			// if Edges weights are equal, ties are broken in favor of first
			// Edges
			// in G.Edges()
			Edges[] closest = new Edges[G.V()];
			for (Edges e : G.Edges()) {
				int v = e.either(), w = e.other(v);
				int i = uf.find(v), j = uf.find(w);
				if (i == j)
					continue; // same tree
				if (closest[i] == null || less(e, closest[i]))
					closest[i] = e;
				if (closest[j] == null || less(e, closest[j]))
					closest[j] = e;
			}
			// add newly discovered Edges to MST
			for (int i = 0; i < G.V(); i++) {
				Edges e = closest[i];
				if (e != null) {
					int v = e.either(), w = e.other(v);
					// don't add the same Edges twice
					if (!uf.connected(v, w)) {
						mst.add(e);
						weight += e.weight();
						uf.union(v, w);
					}
				}
			}
		}
		// check optimality conditions
		assert check(G);
	}

	public Iterable<Edges> Edges() {
		return mst;
	}

	public double weight() {
		return weight;
	}

	// is the weight of Edges e strictly less than that of Edges f?
	private static boolean less(Edges e, Edges f) {
		return e.weight() < f.weight();
	}

	// check optimality conditions (takes time proportional to E V lg* V)
	private boolean check(EdgesWeightedGraph G) {
		// check weight
		double totalWeight = 0.0;
		for (Edges e : Edges()) {
			totalWeight += e.weight();
		}
		double EPSILON = 1E-12;
		if (Math.abs(totalWeight - weight()) > EPSILON) {
			System.err.printf("Weight of Edges does not equal weight(): %f vs. %f\n", totalWeight, weight());
			return false;
		}
		// check that it is acyclic
		UF uf = new UF(G.V());
		for (Edges e : Edges()) {
			int v = e.either(), w = e.other(v);
			if (uf.connected(v, w)) {
				System.err.println("Not a forest");
				return false;
			}
			uf.union(v, w);
		}
		// check that it is a spanning forest
		for (Edges e : G.Edges()) {
			int v = e.either(), w = e.other(v);
			if (!uf.connected(v, w)) {
				System.err.println("Not a spanning forest");
				return false;
			}
		}
		// check that it is a minimal spanning forest (cut optimality
		// conditions)
		for (Edges e : Edges()) {
			// all Edges in MST except e
			uf = new UF(G.V());
			for (Edges f : mst) {
				int x = f.either(), y = f.other(x);
				if (f != e)
					uf.union(x, y);
			}
			// check that e is min weight Edges in crossing cut
			for (Edges f : G.Edges()) {
				int x = f.either(), y = f.other(x);
				if (!uf.connected(x, y)) {
					if (f.weight() < e.weight()) {
						System.err.println("Edges " + f + " violates cut optimality conditions");
						return false;
					}
				}
			}
		}
		return true;
	}

	public static Vector<Arc> run() {
		EdgesWeightedGraph G = new EdgesWeightedGraph();
		BoruvkaMT mst = new BoruvkaMT(G);
		//System.out.println("MST: ");
		Vector<Arc> arcs = new Vector<Arc>() ;// mst.Arcs();
		
		for (Edges e : mst.Edges()) {
			//System.out.println(e);

		}
		System.out.println(arcs);
		return arcs;
		//System.out.printf("Total weight of MST: %.5f\n", mst.weight());
	}
}

class Bag<Item> implements Iterable<Item> {
	private int N; // number of elements in bag
	private Node<Item> first; // beginning of bag

	// helper linked list class
	private static class Node<Item> {
		private Item item;
		private Node<Item> next;
	}

	public Bag() {
		first = null;
		N = 0;
	}

	public boolean isEmpty() {
		return first == null;
	}

	public int size() {
		return N;
	}

	public void add(Item item) {
		Node<Item> oldfirst = first;
		first = new Node<Item>();
		first.item = item;
		first.next = oldfirst;
		N++;
	}

	public Iterator<Item> iterator() {
		return new ListIterator<Item>(first);
	}

	// an iterator, doesn't implement remove() since it's optional
	@SuppressWarnings("hiding")
	private class ListIterator<Item> implements Iterator<Item> {
		private Node<Item> current;

		public ListIterator(Node<Item> first) {
			current = first;
		}

		public boolean hasNext() {
			return current != null;
		}

		public void remove() {
			throw new UnsupportedOperationException();
		}

		public Item next() {
			if (!hasNext())
				throw new NoSuchElementException();
			Item item = current.item;
			current = current.next;
			return item;
		}
	}
}

class EdgesWeightedGraph {
	private final int V;
	private final int E;
	private Bag<Edges>[] adj;

	@SuppressWarnings("unchecked")
	public EdgesWeightedGraph() {
		int V = Main.nodeNr ;
		if (V < 0) {
			throw new IllegalArgumentException("Number of vertices must be nonnegative");
		}
		this.V = V;
		adj = (Bag<Edges>[]) new Bag[V];
		for (int v = 0; v < V; v++) {
			adj[v] = new Bag<Edges>();
		}
		E = Main.listaArce.capacity();
		if (E < 0) {
			throw new IllegalArgumentException("Number of Edges must be nonnegative");
		}
		for (Arc a : Main.listaArce) {
			int[] noduri = Utilities.arcNodes(a);
			int v = noduri[0];
			int w = noduri[1];
			double weight = Utilities.getCost(noduri[0], noduri[1]);
			System.out.println(weight);
			Edges e = new Edges(v, w, weight);
			addEdges(e);
		}
	}

	public int V() {
		return V;
	}

	public int E() {
		return E;
	}

	public void addEdges(Edges e) {
		int v = e.either();
		int w = e.other(v);
		if (v < 0 || v >= V)
			throw new IndexOutOfBoundsException("vertex " + v + " is not between 0 and " + (V - 1));
		if (w < 0 || w >= V)
			throw new IndexOutOfBoundsException("vertex " + w + " is not between 0 and " + (V - 1));
		adj[v].add(e);
		adj[w].add(e);
	}

	public Iterable<Edges> adj(int v) {
		if (v < 0 || v >= V)
			throw new IndexOutOfBoundsException("vertex " + v + " is not between 0 and " + (V - 1));
		return adj[v];
	}

	public Iterable<Edges> Edges() {
		Bag<Edges> list = new Bag<Edges>();
		for (int v = 0; v < V; v++) {
			int selfLoops = 0;
			for (Edges e : adj(v)) {
				if (e.other(v) > v) {
					list.add(e);
				}
				// only add one copy of each self loop (self loops will be
				// consecutive)
				else if (e.other(v) == v) {
					if (selfLoops % 2 == 0)
						list.add(e);
					selfLoops++;
				}
			}
		}
		return list;
	}
	
	public Vector<Arc> Arcs(){
		Vector<Arc> arcs = new Vector<Arc>();
		for (int v = 0; v < V; v++) {
			int selfLoops = 0;
			for (Edges e : adj(v)) {
				if (e.other(v) > v) {
					arcs.add(Utilities.getArc(v, e.other(v)));
				}
				// only add one copy of each self loop (self loops will be
				// consecutive)
				else if (e.other(v) == v) {
					if (selfLoops % 2 == 0)
						arcs.add(Utilities.getArc(v, e.other(v)));
					selfLoops++;
				}
			}
		}
		return arcs;
	}

	public String toString() {
		String NEWLINE = System.getProperty("line.separator");
		StringBuilder s = new StringBuilder();
		s.append(V + " " + E + NEWLINE);
		for (int v = 0; v < V; v++) {
			s.append(v + ": ");
			for (Edges e : adj[v]) {
				s.append(e + "  ");
			}
			s.append(NEWLINE);
		}
		return s.toString();
	}
}

class Edges implements Comparable<Edges> {
	private final int v;
	private final int w;
	private final double weight;

	public Edges(int v, int w, double weight) {
		if (v < 0)
			throw new IndexOutOfBoundsException("Vertex name must be a nonnegative integer");
		if (w < 0)
			throw new IndexOutOfBoundsException("Vertex name must be a nonnegative integer");
		if (Double.isNaN(weight))
			throw new IllegalArgumentException("Weight is NaN");
		this.v = v;
		this.w = w;
		this.weight = weight;
	}

	
	public int returnV(){
		return this.v;
	}
	public int returnW(){
		return this.w;
	}
	public double weight() {
		return weight;
	}

	public int either() {
		return v;
	}

	public int other(int vertex) {
		if (vertex == v)
			return w;
		else if (vertex == w)
			return v;
		else
			throw new IllegalArgumentException("Illegal endpoint");
	}

	public int compareTo(Edges that) {
		if (this.weight() < that.weight())
			return -1;
		else if (this.weight() > that.weight())
			return +1;
		else
			return 0;
	}

	public String toString() {
		return String.format("%d-%d %.5f", v, w, weight);
	}
}

class UF {
	private int[] id; // id[i] = parent of i
	private byte[] rank; // rank[i] = rank of subtree rooted at i (cannot be
							// more than 31)
	private int count; // number of components

	public UF(int N) {
		if (N < 0)
			throw new IllegalArgumentException();
		count = N;
		id = new int[N];
		rank = new byte[N];
		for (int i = 0; i < N; i++) {
			id[i] = i;
			rank[i] = 0;
		}
	}

	public int find(int p) {
		if (p < 0 || p >= id.length)
			throw new IndexOutOfBoundsException();
		while (p != id[p]) {
			id[p] = id[id[p]]; // path compression by halving
			p = id[p];
		}
		return p;
	}

	public int count() {
		return count;
	}

	public boolean connected(int p, int q) {
		return find(p) == find(q);
	}

	public void union(int p, int q) {
		int i = find(p);
		int j = find(q);
		if (i == j)
			return;
		// make root of smaller rank point to root of larger rank
		if (rank[i] < rank[j])
			id[i] = j;
		else if (rank[i] > rank[j])
			id[j] = i;
		else {
			id[j] = i;
			rank[i]++;
		}
		count--;
	}
}