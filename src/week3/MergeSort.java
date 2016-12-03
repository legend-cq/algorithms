package week3;

import edu.princeton.cs.algs4.StdRandom;

public class MergeSort {
	public static int inversionCount = 0;
	public static void sort(Comparable[] array) {
		Comparable[] auxArray = new Comparable[array.length/2];
		sort(array,auxArray,0,array.length - 1);
	}
	
	public static void sort(Comparable[] array,Comparable[] auxArray,int low,int high) {
		if(low == high) {
			return;
		}
		int mid = low + (high-low)/2;
		sort(array,auxArray,low,mid);
		sort(array,auxArray,mid+1,high);
		merge1(array,auxArray,low,mid,high);
	}

	public static void merge(Comparable[] array, Comparable[] auxArray,int low, int mid, int high) {
		for(int i = low; i <= high;i++) {
			auxArray[i] = array[i];
		}
		int left = low;
		int right = mid + 1;
		int current = low;
		while(current <= high) {
			if(left > mid) {
				array[current] = auxArray[right];
				current++;
				right++;
			} else if(right > high) {
				array[current] = auxArray[left];
				current++;
				left++;
			} else if(auxArray[left].compareTo(auxArray[right]) <= 0) {
				array[current] = auxArray[left];
				left++;
				current++;
			} else {
				inversionCount+=(mid-left+1);
				array[current] = auxArray[right];
				right++;
				current++;
			}
		}
	}
	
	public static void merge1(Comparable[] array, Comparable[] auxArray,int low, int mid, int high) {
		for(int i = low; i <= mid;i++) {
			if(low >= auxArray.length) {
				auxArray[i-auxArray.length] = array[i];
			} else {
				auxArray[i] = array[i];
			}
		}
		int left = 0;
		if(low >= auxArray.length) {
			left = low - auxArray.length;
		} else {
			left = low;
		}
		int right = mid + 1;
		int current = low;
		while(current <= high) {
			if(left > mid || (low >= auxArray.length && left > mid - auxArray.length)) {
				array[current] = array[right];
				current++;
				right++;
			} else if(right > high) {
				array[current] = auxArray[left];
				current++;
				left++;
			} else if(auxArray[left].compareTo(array[right]) <= 0) {
				array[current] = auxArray[left];
				current++;
				left++;
			} else {
				int end = mid;
				if(low >= auxArray.length) {
					end = mid - auxArray.length;
				}
				inversionCount+=(end-left+1);
				array[current] = array[right];
				current++;
				right++;
			}
		}
	}
	
	public static void main(String[] args) {
		Integer[] array = new Integer[10];
		for (int i = 0;i < array.length;i++) {
			array[i] = StdRandom.uniform(10);
		}
		for (Integer integer : array) {
			System.out.print(integer);
			System.out.print(" ");
		}
		System.out.println();
		MergeSort.sort(array);
		for (Integer integer : array) {
			System.out.print(integer);
			System.out.print(" ");
		}
		System.out.println(inversionCount);
 	}
}
