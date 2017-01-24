package com.graph.algorithms;

import com.graph.Main;
import com.graph.Utilities;
import com.graph.components.Arc;
import java.util.Collections;
import java.util.Comparator;
import java.util.LinkedList;
import java.util.List;
import java.util.Stack;
import java.util.Vector;

public class KruskalMT {

	public static int[][] transformToMatrix() {
		int[][] adjmatrix = new int[Main.nodeNr][Main.nodeNr];
		for (int i = 0; i < Main.nodeNr; i++) {
			for (int j = 0; j < Main.nodeNr; j++)
				adjmatrix[i][j] = 0;
		}
		for (Arc a : Main.listaArce) {
			int noduri[] = Utilities.arcNodes(a);
			int cost = Utilities.getCost(noduri[0], noduri[1]);
			adjmatrix[noduri[0]][noduri[1]] = cost;

		}
		return adjmatrix;
	}
	public static Vector<Arc> transformToArc(int[][] am){
		Vector<Arc> a = new Vector<Arc>();
		for(int i = 0 ; i < am.length ; i++)
			for(int j = 0 ; j < am.length ; j++){
				if(am[i][j] != 0)
					a.add(Utilities.getArc(i, j));
			}
		return a;
	}
	
	private static List<Edge> edges;
	private static int numberOfVertices;
	public static final int MAX_VALUE = 999;
	private static int visited[];
	private static int spanning_tree[][];

	public KruskalMT(int numberOfVertices) {
		KruskalMT.numberOfVertices = numberOfVertices;
		edges = new LinkedList<Edge>();
		visited = new int[KruskalMT.numberOfVertices + 1];
		spanning_tree = new int[numberOfVertices + 1][numberOfVertices + 1];
	}

	public static Vector<Arc> algorithm(int adjacencyMatrix[][]) {
		boolean finished = false;
		for (int source = 1; source <= numberOfVertices; source++) {
			for (int destination = 1; destination <= numberOfVertices; destination++) {
				if (adjacencyMatrix[source][destination] != MAX_VALUE && source != destination) {
					Edge edge = new Edge();
					edge.sourcevertex = source;
					edge.destinationvertex = destination;
					edge.weight = adjacencyMatrix[source][destination];
					adjacencyMatrix[destination][source] = MAX_VALUE;
					edges.add(edge);
				}
			}
		}
		Collections.sort(edges, new EdgeComparator());
		CheckCycle checkCycle = new CheckCycle();
		for (Edge edge : edges) {
			spanning_tree[edge.sourcevertex][edge.destinationvertex] = edge.weight;
			spanning_tree[edge.destinationvertex][edge.sourcevertex] = edge.weight;
			if (checkCycle.checkCycle(spanning_tree, edge.sourcevertex)) {
				spanning_tree[edge.sourcevertex][edge.destinationvertex] = 0;
				spanning_tree[edge.destinationvertex][edge.sourcevertex] = 0;
				edge.weight = -1;
				continue;
			}
			visited[edge.sourcevertex] = 1;
			visited[edge.destinationvertex] = 1;
			for (int i = 0; i < visited.length; i++) {
				if (visited[i] == 0) {
					finished = false;
					break;
				} else {
					finished = true;
				}
			}
			if (finished)
				break;
		}
		/*System.out.println("The spanning tree is ");
		for (int i = 1; i <= numberOfVertices; i++)
			System.out.print("\t" + i);
		System.out.println();
		for (int source = 1; source <= numberOfVertices; source++) {
			System.out.print(source + "\t");
			for (int destination = 1; destination <= numberOfVertices; destination++) {
				System.out.print(spanning_tree[source][destination] + "\t");
			}
			System.out.println();
		}*/
		
		return transformToArc(spanning_tree);
	}

	public static Vector<Arc> run() {

		KruskalMT KruskalMT = new KruskalMT(Main.nodeNr - 1);
		return algorithm(transformToMatrix());

	}
}

class Edge {
	int sourcevertex;
	int destinationvertex;
	int weight;
}

class EdgeComparator implements Comparator<Edge> {
	@Override
	public int compare(Edge edge1, Edge edge2) {
		if (edge1.weight < edge2.weight)
			return -1;
		if (edge1.weight > edge2.weight)
			return 1;
		return 0;
	}
}

class CheckCycle {
	private Stack<Integer> stack;
	private int adjacencyMatrix[][];

	public CheckCycle() {
		stack = new Stack<Integer>();
	}

	public boolean checkCycle(int adjacency_matrix[][], int source) {
		boolean cyclepresent = false;
		int number_of_nodes = adjacency_matrix[source].length - 1;

		adjacencyMatrix = new int[number_of_nodes + 1][number_of_nodes + 1];
		for (int sourcevertex = 1; sourcevertex <= number_of_nodes; sourcevertex++) {
			for (int destinationvertex = 1; destinationvertex <= number_of_nodes; destinationvertex++) {
				adjacencyMatrix[sourcevertex][destinationvertex] = adjacency_matrix[sourcevertex][destinationvertex];
			}
		}

		int visited[] = new int[number_of_nodes + 1];
		int element = source;
		int i = source;
		visited[source] = 1;
		stack.push(source);

		while (!stack.isEmpty()) {
			element = stack.peek();
			i = element;
			while (i <= number_of_nodes) {
				if (adjacencyMatrix[element][i] >= 1 && visited[i] == 1) {
					if (stack.contains(i)) {
						cyclepresent = true;
						return cyclepresent;
					}
				}
				if (adjacencyMatrix[element][i] >= 1 && visited[i] == 0) {
					stack.push(i);
					visited[i] = 1;
					adjacencyMatrix[element][i] = 0;// mark as labelled;
					adjacencyMatrix[i][element] = 0;
					element = i;
					i = 1;
					continue;
				}
				i++;
			}
			stack.pop();
		}
		return cyclepresent;
	}

}
