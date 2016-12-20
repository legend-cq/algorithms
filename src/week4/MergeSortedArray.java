package week4;

import edu.princeton.cs.algs4.MinPQ;

public class MergeSortedArray {
	private static class Element implements Comparable<Element>{
		private int arrayId;
		private int index;
		private int value;
		public Element(int arrayId,int index,int value) {
			this.arrayId = arrayId;
			this.index = index;
			this.value = value;
		}
		@Override
		public int compareTo(Element o) {
			return ((Integer)this.value).compareTo(o.value);
		}
	}
	public static void merge(int[] arr1,int[] arr2,int[] arr3) {
		MinPQ<Element> pq = new MinPQ<Element>();
		pq.insert(new Element(1,0,arr1[0]));
		pq.insert(new Element(2,0,arr2[0]));
		pq.insert(new Element(3,0,arr3[0]));
		while(!pq.isEmpty()) {
			Element e = pq.delMin();
			if(e.arrayId == 1 && e.index < arr1.length - 1) {
				pq.insert(new Element(1,e.index+1,arr1[e.index+1]));
			} else if(e.arrayId == 2 && e.index < arr2.length - 1) {
				pq.insert(new Element(2,e.index+1,arr2[e.index+1]));
			} if(e.arrayId == 3 && e.index < arr3.length - 1) {
				pq.insert(new Element(3,e.index+1,arr3[e.index+1]));
			}
			System.out.println(e.value);
		}
	}
	
	public static void main(String[] args) {
		int[] arr1 = {1,4,7,10,13};
		int[] arr2 = {2,5,8,11,14,17};
		int[] arr3 = {3,6,9,12};
		MergeSortedArray.merge(arr1,arr2,arr3);
	}
}
