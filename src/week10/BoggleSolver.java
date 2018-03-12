package week10;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdOut;

public class BoggleSolver {
	private final Trie dictionary = new Trie();
	private int[][] offsets = {{-1, -1}, {-1, 0}, {-1, 1}, {0, -1}, {0, 1}, {1, -1}, {1, 0}, {1, 1}};
	private char[] charBoard;
	private int rowSize;
	private int columnSize;
	private Map<Integer, List<Integer>> neighbor;
	
	private class Trie {
		private final Node root = new Node();
		class Node {
			private String prefix;
			private Node[] children = new Node[26];
			private boolean isWord;
		}
		
		public void add(String str) {
			Node cur = root;
			for (int i = 0; i < str.length(); i++) {
				char c = str.charAt(i);
				Node node = cur.children[c - 'A'];
				if (node == null) {
					node = new Node();
					cur.children[c - 'A'] = node;
				}
				cur = node;
			}
			cur.isWord = true;
			cur.prefix = str;
		}
		
		public boolean hasKeyWithPrefix(Node node, char c) {
			return node.children[c - 'A'] != null;
		}
		
		public boolean contains(String str) {
			Node cur = root;
			for (int i = 0; i < str.length(); i++) {
				char c = str.charAt(i);
				Node node = cur.children[c - 'A'];
				if (node == null) {
					return false;
				}
				cur = node;
			}
			return cur.isWord;
		}
	}
	
	// Initializes the data structure using the given array of strings as the dictionary.
    // (You can assume each word in the dictionary contains only the uppercase letters A through Z.)
    public BoggleSolver(String[] dictionary) {
    	for (String string : dictionary) {
    		this.dictionary.add(string);
		}
    }

    // Returns the set of all valid words in the given Boggle board, as an Iterable.
    public Iterable<String> getAllValidWords(BoggleBoard board) {
    	rowSize = board.rows();
    	columnSize = board.cols();
    	charBoard = new char[rowSize * columnSize];
    	neighbor = new HashMap<>();
    	for (int i = 0; i < rowSize; i++) {
    		for (int j = 0; j < columnSize; j++) {
    			charBoard[i * columnSize + j] = board.getLetter(i, j);
    			List<Integer> neighborList = new ArrayList<>();
    			neighbor.put(i * columnSize + j, neighborList);
    			for (int[] offset : offsets) {
    				int row = i + offset[0];
    				int column = j + offset[1];
    				if (row >= 0 && row < rowSize && column >= 0 && column < columnSize) {
    					neighborList.add(row * columnSize + column);
    				}
    			}
    		}
    	}
    	Set<String> result = new HashSet<>();
    	boolean[] isVisited = new boolean[rowSize * columnSize];
    	for (int i = 0; i < rowSize; i++) {
    		for (int j = 0; j < columnSize; j++) {
    			dfs(i * columnSize + j, isVisited, result, dictionary.root);
    		}
    	}
    	return result;
    } 

    private void dfs(int pos, boolean[] isVisited, Set<String> result, Trie.Node node) {
    	if (isVisited[pos]) {
    		return;
    	}
    	char letter = charBoard[pos];
    	if (!dictionary.hasKeyWithPrefix(node, letter)) {
    		return;
    	}
    	node = node.children[letter - 'A'];
    	if (letter == 'Q') {
    		if (!dictionary.hasKeyWithPrefix(node, 'U')) {
        		return;
        	}
    		node = node.children['U' - 'A'];
    	}
    	if (node.isWord && node.prefix.length() > 2) {
    		result.add(node.prefix);
    	}
    	isVisited[pos] = true;
		for (int neighborPos: neighbor.get(pos)) {
			dfs(neighborPos, isVisited, result, node);
		}
    	isVisited[pos] = false;
	}

	// Returns the score of the given word if it is in the dictionary, zero otherwise.
    // (You can assume the word contains only the uppercase letters A through Z.)
    public int scoreOf(String word) {
    	if (word == null || !dictionary.contains(word)) {
    		return 0;
    	} else {
    		int len = word.length();
    		if (len <= 2) {
    			return 0;
    		} else if (len <= 4) {
    			return 1;
    		} else if (len == 5) {
    			return 2;
    		} else if (len == 6) {
    			return 3;
    		} else if (len == 7) {
    			return 5;
    		} else {
    			return 11;
    		}
    	}
    }
    
    public static void main(String[] args) {
        In in = new In(args[0]);
        String[] dictionary = in.readAllStrings();
        BoggleSolver solver = new BoggleSolver(dictionary);
        BoggleBoard board = new BoggleBoard(args[1]);
        int score = 0;
        for (String word : solver.getAllValidWords(board)) {
            StdOut.println(word);
            score += solver.scoreOf(word);
        }
        StdOut.println("Score = " + score);
    }
}
