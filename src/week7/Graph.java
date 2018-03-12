package week7;

import java.util.ArrayList;

import edu.princeton.cs.algs4.Bag;

public class Graph {
	private int nEdges;
	private ArrayList<Bag<Integer>> vertices = new ArrayList<Bag<Integer>>();
	public int addVertex() {
		Bag<Integer> bag = new Bag<Integer>();
		vertices.add(bag);
		return vertices.size() - 1;
	}
	public void addEdge(int v, int w) {
		nEdges++;
		vertices.get(v).add(w);
//		vertices.get(w).add(v);
	}
	public int vertexSize() {
		return vertices.size();
	}
	public int edgeSize() {
		return nEdges;
	}
	
	public Iterable<Integer> adj(int v) {
		return vertices.get(v);
	}
}
