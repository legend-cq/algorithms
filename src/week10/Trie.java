package week10;

import java.util.ArrayList;
import java.util.List;

public class Trie {
	class Node {
		private Object value;
		private Node[] next = new Node[26];
	}
	
	private Node root = new Node();
	
	public void put(String key, Object value) {
		root = put(root, key, value, 0);
	}

	private Node put(Node node, String key, Object value, int d) {
		if (node == null) {
			node = new Node();
		}
		if (d == key.length()) {
			node.value = value;
			return node;
		}
		int c = key.charAt(d) - 'a';
		node.next[c] = put(node.next[c], key, value, d + 1);
		return node;
	}
	
	public void delete(String key) {
		root = delete(root, key, 0);
	}
	
	private Node delete(Node node, String key, int d) {
		if (node == null) {
			return null;
		}
		if (d == key.length()) {
			node.value = null;
			boolean noRef = true;
			for (int i = 0; i < node.next.length; i++) {
				if (node.next[i] != null) {
					noRef = false;
				}
			}
			if (noRef) {
				return null;
			} else {
				return node;
			}
		}
		int c = key.charAt(d) - 'a';
		node.next[c] = delete(node.next[c], key, d + 1);
		boolean noRef = true;
		for (int i = 0; i < node.next.length; i++) {
			if (node.next[i] != null) {
				noRef = false;
			}
		}
		if (noRef) {
			return null;
		} else {
			return node;
		}
	}

	public Object get(String key) {
		Node node = get(root, key, 0);
		if (node == null) {
			return null;
		}
		return node.value;
	}

	private Node get(Node node, String key, int d) {
		if (node == null) {
			return null;
		}
		if (d == key.length()) {
			return node;
		}
		int c = key.charAt(d) - 'a';
		return get(node.next[c], key, d + 1);
	}
	//pre-order
	public List<String> keys() {
		List<String> keys = new ArrayList<String>();
		preOrder(root, keys, "");
		return keys;
	}
	
	private void preOrder(Node node, List<String> keys, String key) {
		if (node == null) {
			return;
		}
		if (node.value != null) {
			keys.add(key);
		}
		for (int i = 0; i < node.next.length; i++) {
			if (node.next[i] != null) {
				preOrder(node.next[i], keys, key + (char)(i + 'a'));
			}
		}
	}
	
	public List<String> searchByPrefix(String prefix) {
		Node node = get(root, prefix, 0);
		List<String> keys = new ArrayList<String>();
		preOrder(node, keys, prefix);
		return keys;
	}
	
	public String getLongestPrefix(String str) {
		return getLongestPrefix(root, str, 0);
	}

	private String getLongestPrefix(Node node, String str, int d) {
		if (node == null) {
			return null;
		}
		if (d == str.length()) {
			if (node.value != null) {
				return str;
			}
			return null;
		}
		int c = str.charAt(d) - 'a';
		String prefix = getLongestPrefix(node.next[c], str, d + 1);
		if (prefix != null) {
			return prefix;
		}
		if (node.value != null) {
			return str.substring(0, d);
		}
		return null;
	}
	
	public List<String> search(String pattern) {
		List<String> keys = new ArrayList<>();
		search(root, pattern, 0, keys, "");
		return keys;
	}

	private void search(Node node, String pattern, int d, List<String> keys, String key) {
		if (node == null) {
			return;
		}
		if (d == pattern.length()) {
			if (node.value != null) {
				keys.add(key);
			}
			return;
		}
		char c = pattern.charAt(d);
		if (c == '.') {
			for (int i = 0; i < node.next.length; i++) {
				search(node.next[i], pattern, d + 1, keys, key + (char)(i + 'a'));
			}
		} else {
			search(node.next[c - 'a'], pattern, d + 1, keys, key + c);
		}
		return;
	}

	public static void main(String[] args) {
		Trie trie = new Trie();
		trie.put("test", 1);
		trie.put("testa", 2);
		trie.put("testb", 3);
		trie.put("abc",4);
		System.out.println(trie.get("testa"));
		System.out.println(trie.get("abc"));
		System.out.println(trie.get("tes"));
//		List<String> keys = trie.keys();
//		for (String string : keys) {
//			System.out.println(string);
//		}
//		List<String> keys1 = trie.searchByPrefix("t");
//		for (String string : keys1) {
//			System.out.println(string);
//		}
//		trie.delete("abc");
		System.out.println(trie.getLongestPrefix("testtest"));
		System.out.println(trie.search("test.."));
	}
}
