package week7;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import edu.princeton.cs.algs4.Digraph;
import edu.princeton.cs.algs4.DirectedCycle;
import edu.princeton.cs.algs4.In;

public class WordNet {
	private final Map<String, List<Integer>> nounMap = new HashMap<String, List<Integer>>();
	private final Map<Integer, String> subsets = new HashMap<Integer, String>();
	private final SAP sap;
	
	// constructor takes the name of the two input files
	   	public WordNet(String synsets, String hypernyms) {
	   		if (synsets == null || hypernyms == null) {
	   			throw new IllegalArgumentException();
	   		}
	   		In in = new In(synsets);
	   		while (in.hasNextLine()) {
	   			String line = in.readLine();
	   			String[] parts = line.split(",");
	   			int id = Integer.parseInt(parts[0]);
	   			subsets.put(id, parts[1]);
	   			String[] nouns = parts[1].split(" ");
	   			for (String noun : nouns) {
	   				List<Integer> ids = nounMap.get(noun);
	   				if (ids == null) {
	   					ids = new ArrayList<Integer>();
	   				}
	   				ids.add(id);
	   				nounMap.put(noun, ids);
	   			}
	   		}
	   		in.close();
	   		Digraph digraph = new Digraph(subsets.size());
	   		In in1 = new In(hypernyms);
	   		while (in1.hasNextLine()) {
	   			String line = in1.readLine();
	   			String[] ids = line.split(",");
	   			int v = Integer.parseInt(ids[0]);
	   			for (int i = 1; i < ids.length; i++) {
	   				int w = Integer.parseInt(ids[i]);
	   				digraph.addEdge(v, w);
	   			}
	   		}
	   		in1.close();
	   		// check if the graph is a DAG
	   		DirectedCycle cycle = new DirectedCycle(digraph);
	   		if (cycle.hasCycle()) {
	   			throw new IllegalArgumentException();
	   		}
	   		// check if the graph is a rooted DAG
	   		int count = 0;
	   		for (int i = 0; i < digraph.V(); i++) {
	   			if (digraph.outdegree(i) == 0) {
	   				count++;
	   				if (count > 1) {
	   					throw new IllegalArgumentException();
	   				}
	   			}
	   		}
	   		sap = new SAP(digraph);
	   	}

	   	// returns all WordNet nouns
	   	public Iterable<String> nouns() {
	   		return nounMap.keySet();
	   	}

	   	// is the word a WordNet noun?
	   	public boolean isNoun(String word) {
	   		if (word == null) {
	   			throw new IllegalArgumentException();
	   		}
	   		return nounMap.containsKey(word);
	   	}

	   	// distance between nounA and nounB (defined below)
	   	public int distance(String nounA, String nounB) {
	   		if (!isNoun(nounA) || !isNoun(nounB)) {
	   			throw new IllegalArgumentException();
	   		}
	   		List<Integer> idA = nounMap.get(nounA);
	   		List<Integer> idB = nounMap.get(nounB);
	   		return sap.length(idA, idB);
	   	}

	   	// a synset (second field of synsets.txt) that is the common ancestor of nounA and nounB
	   	// in a shortest ancestral path (defined below)
	   	public String sap(String nounA, String nounB) {
	   		if (!isNoun(nounA) || !isNoun(nounB)) {
	   			throw new IllegalArgumentException();
	   		}
	   		List<Integer> idA = nounMap.get(nounA);
	   		List<Integer> idB = nounMap.get(nounB);
	   		int ancestor = sap.ancestor(idA, idB);
	   		return subsets.get(ancestor);
	   	}

	   	// do unit testing of this class
	   	public static void main(String[] args) {
	   		WordNet wordnet = new WordNet(args[0], args[1]);
	   		wordnet.sap("a", "b");
	   	}
}
