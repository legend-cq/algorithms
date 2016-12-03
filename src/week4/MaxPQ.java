package week4;

import java.util.NoSuchElementException;

public class MaxPQ <Key extends Comparable<Key>> {
	private Key[] keys = null;
	private int size = 0;
	
	public MaxPQ() {
		keys = (Key[]) new Comparable[2];
	}
	
	private void swim(int k) {
		while(k > 1 && !less(keys[k],keys[k/2])) {
			exch(keys,k,k/2);
			k=k/2;
		}
	}
	
	private void sink(int k) {
		while(k <= size/2) {
			int max = 2*k;
			if(max+1 <= size && less(keys[max],keys[max+1])) {
				max++;
			}
			if(less(keys[k],keys[max])) {
				exch(keys,k,max);
				k=max;
			} else {
				break;
			}
		}
	}
	
	public void insert(Key key) {
		if(keys.length - 1 == size) {
			keys = resize(keys,2*size + 1);
		}
		keys[++size] = key;
		swim(size);
	}
	
	public Key deleteMax() {
		if(size == 0) {
			throw new NoSuchElementException();
		}
		Key max = keys[1];
		exch(keys,1,size);
		keys[size--] = null;
		sink(1);
		if((keys.length-1)/4 == size) {
			keys = resize(keys,(keys.length-1)/2);
		}
		return max;
	}
	
	public Key max() {
		if(size == 0) {
			throw new NoSuchElementException();
		}
		return keys[1];
	}
	
	private void exch(Key[] keys,int i, int j) {
		Key temp = keys[i];
		keys[i] = keys[j];
		keys[j] = temp;
	}
	
	private boolean less(Key a, Key b) {
		return a.compareTo(b) < 0;
		
	}
	
	private Key[] resize(Key[] keys,int size) {
		Key[] newKeys = (Key[]) new Comparable[size];
		System.arraycopy(keys, 0, newKeys, 0, (keys.length < size)?keys.length:size);
		return newKeys;
	}
	
	public static void main(String[] args) {
		MaxPQ<Integer> pq = new MaxPQ<Integer>();
		pq.insert(100);
		pq.insert(99);
		pq.insert(101);
		System.out.println(pq.deleteMax());
		pq.insert(105);
		pq.insert(106);
		pq.insert(97);
		pq.insert(90);
		System.out.println(pq.deleteMax());
		pq.insert(80);
		pq.insert(105);
		System.out.println(pq.deleteMax());
		pq.insert(103);
		System.out.println(pq.deleteMax());
		System.out.println(pq.deleteMax());
		System.out.println(pq.deleteMax());
		System.out.println(pq.deleteMax());
		System.out.println(pq.deleteMax());
		System.out.println(pq.deleteMax());
		System.out.println(pq.deleteMax());
	}
}
