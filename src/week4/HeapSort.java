package week4;

import edu.princeton.cs.algs4.StdRandom;

public class HeapSort {
	
	public static void sort(Comparable[] arr) {
		int last = arr.length - 1;
		for(int i = 1; i < arr.length - 1;i++) {
			swim(arr,i);
		}
		exch(arr,0,last--);
		while(last >= 1) {
			sink(arr,0,last);
			exch(arr,0,last--);
		}
	}
	
	private static void sink(Comparable[] arr, int i,int last) {
		while(2*i + 1 <= last) {
			int k = 2*i + 1;
			if(k+1 <= last && arr[k+1].compareTo(arr[k]) > 0) {
				k++;
			}
			if(arr[k].compareTo(arr[i]) > 0) {
				exch(arr,i,k);
				i = k;
			} else {
				break;
			}
		}
	}

	private static void swim(Comparable[] arr, int i) {
		while(i >= 1 && arr[(i-1)/2].compareTo(arr[i]) < 0) {
			exch(arr,(i-1)/2,i);
			i=(i-1)/2;
		}
	}
	
	private static void exch(Comparable[] arr,int i, int j) {
		Comparable temp = arr[i];
		arr[i] = arr[j];
		arr[j] = temp;
	}

	public static void main(String[] args) {
		Integer[] array = new Integer[50];
		for (int i = 0;i < array.length;i++) {
			array[i] = StdRandom.uniform(50);
		}
		for (Integer integer : array) {
			System.out.print(integer);
			System.out.print(" ");
		}
		System.out.println();
		HeapSort.sort(array);
		for (Integer integer : array) {
			System.out.print(integer);
			System.out.print(" ");
		}
	}

}
