package week4;

import java.util.Iterator;

import edu.princeton.cs.algs4.Queue;
import week4.BST.Node;

public class BST<K extends Comparable<K>,V> implements Iterable<BST<K, V>.Node<K, V>>{
	private Node<K,V> root;
	private int size;
	public class Node<K extends Comparable<K>,V> {
		private K key;
		private V value;
		private Node<K,V> left;
		private Node<K,V> right;
		private int count;
		public Node(K key,V value,int count) {
			this.key = key;
			this.value = value;
			this.count = count;
		}
	}
	
	public V put(K key,V value) {
		if(key == null) {
			throw new IllegalArgumentException();
		}
		if(root == null) {
			root = new Node<K,V>(key,value,1);
			size++;
			return null;
		}
		Node<K,V> curr = root;
		V oldValue = null;
		while(true) {
			if(curr.key.compareTo(key) > 0) {
				curr.count++;
				if(curr.left != null) {
					curr = curr.left;
				} else {
					curr.left = new Node<K,V>(key,value,1);
					size++;
					return null;
				}
			} else if(curr.key.compareTo(key) < 0) {
				curr.count++;
				if(curr.right != null) {
					curr = curr.right;
				} else {
					curr.right = new Node<K,V>(key,value,1);
					size++;
					return null;
				}
			} else {
				oldValue = curr.value;
				curr.value = value;
				return oldValue;
			}
		}
	}
	
	public V get(K key) {
		Node<K,V> curr = root;
		while(curr != null) {
			if(curr.key.compareTo(key) > 0) {
				curr = curr.left;
			} else if(curr.key.compareTo(key) < 0) {
				curr = curr.right;
			} else {
				return curr.value;
			}
		}
		return null;
	}
	
	public V delete(K key) {
		Node<K,V> curr = root;
		Node<K,V> prev = null;
		while(curr != null) {
			if(curr.key.compareTo(key) > 0) {
				prev = curr;
				curr = curr.left;
			} else if(curr.key.compareTo(key) < 0) {
				prev = curr;
				curr = curr.right;
			} else {
				if(curr.left != null) {
					if(prev.left == curr) {
						prev.left = curr.left;
					} else {
						prev.right = curr.left;
					}
					Node<K,V> newNode = null;
					if(curr.left.right != null) {
						newNode = curr.left.right;
					}
					curr.left.right = curr.right;
					if(newNode != null) {
						put(newNode.key,newNode.value);
					}
				} else if(curr.right != null) {
					if(prev.left == curr) {
						prev.left = curr.right;
					} else {
						prev.right = curr.right;
					}
				} else {
					if(prev.left == curr) {
						prev.left = null;
					} else {
						prev.right = null;
					}
				}
				return curr.value;
			}
		}
		return null;
	}
	
	public boolean isEmpty() {
		return size == 0;
	}
	
	public int size() {
		return size;
	}
	
	public boolean containsKey(K key) {
		return get(key) != null;
	}
	
	@Override
	public Iterator<BST<K, V>.Node<K, V>> iterator() {
		Queue<BST<K, V>.Node<K, V>> queue = new Queue<BST<K, V>.Node<K, V>>();
		inorder(queue,root);
		return queue.iterator();
	}

	private void inorder(Queue<BST<K, V>.Node<K, V>> queue, BST<K, V>.Node<K, V> node) {
		if(node == null) return;
		inorder(queue,node.left);
		queue.enqueue(node);
		inorder(queue,node.right);
	}
	
	public int rank(K key) {
		int rank = 0;
		Node<K,V> curr = root;
		while(curr != null) {
			if(key.compareTo(curr.key) > 0) {
				rank+=count(curr.left) + 1;
				curr = curr.right;
			} else if(key.compareTo(curr.key) < 0) {
				curr = curr.left;
			} else {
				break;
			}
		}
		return rank;
	}
	
	public K select(int rank) {
		rank++;
		Node<K,V> curr = root;
		while(curr != null) {
			if(rank > count(curr.left) + 1) {
				rank -= (count(curr.left) + 1);
				curr = curr.right;
			} else if(rank < count(curr.left) + 1) {
				curr = curr.left;
			} else {
				return curr.key;
			}
		}
		return null;
	}
	
	public K floor(K key) {
		Node<K,V> curr = root;
		K floor = null;
		while(curr != null) {
			if(key.compareTo(curr.key) > 0) {
				floor = curr.key;
				curr = curr.right;
			} else if(key.compareTo(curr.key) < 0) {
				curr = curr.left;
			} else {
				floor = curr.key;
				break;
			}
		}
		return floor;
	}
	
	public K ceiling(K key) {
		Node<K,V> curr = root;
		K ceiling = null;
		while(curr != null) {
			if(key.compareTo(curr.key) > 0) {
				curr = curr.right;
			} else if(key.compareTo(curr.key) < 0) {
				ceiling = curr.key;
				curr = curr.left;
			} else {
				ceiling = curr.key;
				break;
			}
		}
		return ceiling;
	}
	
	public int count(Node<K,V> node) {
		if(node == null) return 0;
		return node.count;
	}
	
	public int height() {
		return height(root);
	}
	
	private int heightR(BST<K, V>.Node<K, V> node) {
		if(node == null) {
			return -1;
		}
		return Math.max(height(node.left), height(node.right)) + 1;
	}
	
	private int height(BST<K, V>.Node<K, V> node) {
		if(node == null) {
			return -1;
		}
		int height = -1;
		Queue<BST<K, V>.Node<K, V>> queue = new Queue<BST<K, V>.Node<K, V>>();
		queue.enqueue(node);
		while(queue.size() > 0) {
			System.out.println();
			height++;
			int nodeCount = queue.size();
			while(nodeCount > 0) {
				Node<K,V> node2 = queue.dequeue();
				System.out.print(node2.key + " ");
				nodeCount--;
				if(node2.left != null) {
					queue.enqueue(node2.left);
				}
				if(node2.right != null) {
					queue.enqueue(node2.right);
				}
			}
		}
		return height;
	}
	
	public int rangeCount(K low, K high) {
		if(this.containsKey(high)) {
			return rank(high) - rank(low) + 1;
		} else {
			return rank(high) - rank(low);
		}
	}
	
	public Iterable<K> range(K low, K high) {
		Queue<K> queue = new Queue<K>();
		range(low, high, root,queue);
		return queue;
	}

	private void range(K low, K high, BST<K, V>.Node<K, V> node, Queue<K> queue) {
		if(node == null) {
			return;
		} else {
			if(low.compareTo(node.key) <=0 && node.key.compareTo(high) <= 0) {
				queue.enqueue(node.key);
				range(low,high,node.left,queue);
				range(low,high,node.right,queue);
			} else if(low.compareTo(node.key) > 0) {
				range(low,high,node.right,queue);
			} else if(node.key.compareTo(high) >0 ) {
				range(low,high,node.left,queue);
			}
		}
	}

	public static void main(String[] args) {
		BST<Double,String> bst = new BST<Double,String>();
		bst.put(8.0, "h");
		bst.put(6.0, "f");
		bst.put(10.0, "j");
		bst.put(3.0, "c");
		bst.put(1.0, "a");
		bst.put(2.0, "b");
		bst.put(12.0, "l");
		bst.put(13.0, "m");
		bst.put(4.0, "d");
		bst.put(5.0, "e");
		bst.put(7.0, "g");
		bst.put(14.0, "n");
		bst.put(15.0, "o");
		bst.put(16.0, "p");
		bst.put(8.0, "hh");
		bst.put(9.0, "i");
		bst.put(11.0, "k");
		
//		System.out.println(bst.get(1));
//		System.out.println(bst.get(3));
//		System.out.println(bst.get(5));
//		System.out.println(bst.delete(7));
//		System.out.println(bst.get(7));
//		System.out.println(bst.get(2));
//		System.out.println(bst.get(4));
//		System.out.println(bst.get(6));
//		System.out.println(bst.get(8));
//		System.out.println(bst.get(10));
//		System.out.println(bst.get(12));
//		System.out.println(bst.get(14));
//		System.out.println(bst.get(16));
//		System.out.println(bst.get(15));
//		for(int i =0; i<bst.size; i++) {
//			System.out.println(bst.select(i));
//		}
//		for (BST<Double, String>.Node<Double, String> node : bst) {
//			System.out.print(node.key + " ");
//			System.out.print(node.value + " ");
//			System.out.print(node.count + " ");
//			System.out.println();
//		}
		System.out.println(bst.rangeCount(0.0, 7.0));
		Iterable<Double> range = bst.range(0.0,7.0);
		for (Double double1 : range) {
			System.out.print(double1 + " ");
		}
	}
}
