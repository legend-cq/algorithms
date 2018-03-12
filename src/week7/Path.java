package week7;

import edu.princeton.cs.algs4.Queue;
import edu.princeton.cs.algs4.Stack;

public class Path {
	private Graph graph;
	private int source;
	private boolean[] isMarked;
	private int[] pathTo;
	public Path(Graph graph, int source) {
		this.graph = graph;
		this.source = source;
		isMarked = new boolean[graph.vertexSize()];
		pathTo = new int[graph.vertexSize()];
		dfs1(source);
	}
	public boolean isReachable(int v) {
		return isMarked[v];
	}
	public Iterable<Integer> pathTo(int v) {
		Stack<Integer> path = new Stack<Integer>();
		if(!isReachable(v)) {
			return path;
		}
		for(int p = v;p!=source;p=pathTo[p]) {
			path.push(p);
		}
		path.push(source);
		return path;
	}
	public void dfs(int v) {
		isMarked[v] = true;
		for (Integer w :  graph.adj(v)) {
			if(!isMarked[w]) {
				pathTo[w] = v;
				System.out.println("visiting source start:" + w);
				dfs(w);
				System.out.println("visiting source end:" + w);
			}
		}
	}
	
	public void dfs1(int v) {
		Stack<Integer> stack = new Stack<Integer>();
		stack.push(v);
		while(!stack.isEmpty()) {
			int w = stack.peek();
			System.out.println("visiting source start:" + w);
			boolean isFinished = true;
			for(int x:graph.adj(w)) {
				if(!isMarked[x]) {
					pathTo[x] = w;
					stack.push(x);
					isMarked[x] = true;
					isFinished = false;
				}
			}
			if(isFinished) {
				stack.pop();
				System.out.println("visiting source end:" + w);
			}
		}
	}
	
	public void bfs(int v) {
		Queue<Integer> queue = new Queue<Integer>();
		queue.enqueue(v);
		isMarked[v] = true;
		while (!queue.isEmpty()) {
			int w = queue.dequeue();
			for(int x : graph.adj(w)) {
				if(!isMarked[x]) {
					pathTo[x] = w;
					queue.enqueue(x);
					isMarked[x] = true;
				}
			}
		}
	}
	
	public static void main(String[] args) {
		Graph graph = new Graph();
		for(int i = 0; i < 7;i++) {
			graph.addVertex();
		}
		graph.addEdge(0, 1);
		graph.addEdge(1, 2);
		graph.addEdge(2, 3);
		graph.addEdge(2, 4);
		graph.addEdge(3, 2);
		graph.addEdge(4, 1);
		graph.addEdge(4, 5);
		graph.addEdge(3, 6);
//		graph.addEdge(6, 5);
		Path path = new Path(graph,0);
		Iterable<Integer> pathTo = path.pathTo(6);
		for (Integer integer : pathTo) {
			System.out.println(integer);
		}
	}
}
