package week3;

import edu.princeton.cs.algs4.StdRandom;

public class QuickSort {
	
	public static void sort(Comparable[] array) {
		sort(array,0,array.length - 1);
	}
	
	private static void sort(Comparable[] array, int low, int high) {
		if(low >= high) {
			return;
		}
//		if(high-low == 1) {
//			if(array[low].compareTo(array[high]) > 0) {
//				swap(array,low,high);
//			}
//			return;
//		}
		int i = low + 1;
		int j = high;
		while(true) {
			while(i <=high && array[i].compareTo(array[low]) < 0) {
				i++;
			}
			while(array[j].compareTo(array[low]) > 0) {
				j--;
			}
			if(i < j) {
				swap(array,i++,j--);
			} else {
				break;
			}
		}
		swap(array,low,j);
		sort(array,low,j-1);
		sort(array,j+1,high);
	}

	private static void swap(Comparable[] array, int low, int high) {
		Comparable temp = array[low];
		array[low] = array[high];
		array[high] = temp;
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
		QuickSort.sort(array);
		for (Integer integer : array) {
			System.out.print(integer);
			System.out.print(" ");
		}
	}

}
