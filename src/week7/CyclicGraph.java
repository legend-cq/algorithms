package week7;

public class CyclicGraph {
	
	public boolean isCyclic(Graph graph) {
		boolean[] marked = new boolean[graph.vertexSize()];
		for(int i = 0; i < graph.vertexSize();i++) {
			if(!marked[i]) {
				if(isCyclic(graph,i,marked,-1)) {
					 return true;
				}
			}
		}
		return false;
	}
	
	private boolean isCyclic(Graph graph, int i,boolean[] marked,int p) {
		marked[i] = true;
		for(int v:graph.adj(i)) {
			if(!marked[v]) {
				if(isCyclic(graph,v,marked,i)) {
					return true;
				}
			} else if(v != p) {
				return true;
			}
		}
		return false;
	}

	public static void main(String[] args) {
		Graph graph = new Graph();
		for(int i = 0; i < 9;i++) {
			graph.addVertex();
		}
		graph.addEdge(0, 1);
		graph.addEdge(0, 2);
		graph.addEdge(0, 3);
		graph.addEdge(2, 4);
		graph.addEdge(4, 5);
		graph.addEdge(3, 6);
		graph.addEdge(7, 8);
//		graph.addEdge(5, 1);
		CyclicGraph cg = new CyclicGraph();
		System.out.println(cg.isCyclic(graph));
	}

}
