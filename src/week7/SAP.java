package week7;

import edu.princeton.cs.algs4.BreadthFirstDirectedPaths;
import edu.princeton.cs.algs4.Digraph;
import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdOut;

public class SAP {
	private final Digraph digraph;
	
	// constructor takes a digraph (not necessarily a DAG)
	public SAP(Digraph G) {
		this.digraph = new Digraph(G);
	}

	// length of shortest ancestral path between v and w; -1 if no such path
	public int length(int v, int w) {
		validateRange(v);
		validateRange(w);
		BreadthFirstDirectedPaths pathV = new BreadthFirstDirectedPaths(digraph, v);
		BreadthFirstDirectedPaths pathW = new BreadthFirstDirectedPaths(digraph, w);
		int minLen = Integer.MAX_VALUE;
		for (int i = 0; i < digraph.V(); i++) {
			int disV = pathV.distTo(i);
			int disW = pathW.distTo(i);
			if (disV != Integer.MAX_VALUE && disW != Integer.MAX_VALUE) {
				if (disV + disW < minLen) {
					minLen = disV + disW;
				}
			}
		}
		if (minLen == Integer.MAX_VALUE) {
			minLen = -1;
		}
		return minLen;
	}

	// a common ancestor of v and w that participates in a shortest ancestral path; -1 if no such path
	public int ancestor(int v, int w) {
		validateRange(v);
		validateRange(w);
		BreadthFirstDirectedPaths pathV = new BreadthFirstDirectedPaths(digraph, v);
		BreadthFirstDirectedPaths pathW = new BreadthFirstDirectedPaths(digraph, w);
		int minLen = Integer.MAX_VALUE;
		int ancestor = -1;
		for (int i = 0; i < digraph.V(); i++) {
			int disV = pathV.distTo(i);
			int disW = pathW.distTo(i);
			if (disV != Integer.MAX_VALUE && disW != Integer.MAX_VALUE) {
				if (disV + disW < minLen) {
					minLen = disV + disW;
					ancestor = i;
				}
			}
		}
		return ancestor;
	}

	// length of shortest ancestral path between any vertex in v and any vertex in w; -1 if no such path
	public int length(Iterable<Integer> v, Iterable<Integer> w) {
		validateRange(v);
		validateRange(w);
		BreadthFirstDirectedPaths pathV = new BreadthFirstDirectedPaths(digraph, v);
		BreadthFirstDirectedPaths pathW = new BreadthFirstDirectedPaths(digraph, w);
		int minLen = Integer.MAX_VALUE;
		for (int i = 0; i < digraph.V(); i++) {
			int disV = pathV.distTo(i);
			int disW = pathW.distTo(i);
			if (disV != Integer.MAX_VALUE && disW != Integer.MAX_VALUE) {
				if (disV + disW < minLen) {
					minLen = disV + disW;
				}
			}
		}
		if (minLen == Integer.MAX_VALUE) {
			minLen = -1;
		}
		return minLen;
	}

	// a common ancestor that participates in shortest ancestral path; -1 if no such path
	public int ancestor(Iterable<Integer> v, Iterable<Integer> w) {
		validateRange(v);
		validateRange(w);
		BreadthFirstDirectedPaths pathV = new BreadthFirstDirectedPaths(digraph, v);
		BreadthFirstDirectedPaths pathW = new BreadthFirstDirectedPaths(digraph, w);
		int minLen = Integer.MAX_VALUE;
		int ancestor = -1;
		for (int i = 0; i < digraph.V(); i++) {
			int disV = pathV.distTo(i);
			int disW = pathW.distTo(i);
			if (disV != Integer.MAX_VALUE && disW != Integer.MAX_VALUE) {
				if (disV + disW < minLen) {
					minLen = disV + disW;
					ancestor = i;
				}
			}
		}
		return ancestor;
	}
	
	private void validateRange(int v) {
		if (v < 0 || v >= digraph.V()) {
			throw new IllegalArgumentException();
		}
	}
	
	private void validateRange(Iterable<Integer> v) {
		if (v == null) {
			throw new IllegalArgumentException();
		}
		for (Integer i : v) {
			validateRange(i);
		}
	}

	// do unit testing of this class
	public static void main(String[] args) {
		In in = new In(args[0]);
	    Digraph G = new Digraph(in);
	    SAP sap = new SAP(G);
	    while (!StdIn.isEmpty()) {
	        int v = StdIn.readInt();
	        int w = StdIn.readInt();
	        int length   = sap.length(v, w);
	        int ancestor = sap.ancestor(v, w);
	        StdOut.printf("length = %d, ancestor = %d\n", length, ancestor);
	    }
	}
}
