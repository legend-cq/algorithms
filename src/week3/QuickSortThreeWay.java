package week3;

import edu.princeton.cs.algs4.StdRandom;

public class QuickSortThreeWay {
	
	public static Comparable[] sort(Comparable[] arr) {
		return sort(arr,0,arr.length - 1);
	}
	
	private static Comparable[] sort(Comparable[] arr, int low, int high) {
		if(low >= high) {
			return arr;
		}
		int i = low,j = low;
		int k = high+1;
		while(true) {
			while((j + 1) <= high && arr[++j].compareTo(arr[i]) <= 0) {
				if(arr[j].compareTo(arr[i]) != 0) {
					swap(arr,i,j);
					i++;
				}
			}
			while(arr[--k].compareTo(arr[i]) > 0) {
				
			}
			if(j < k) {
				swap(arr,j,k);
				if(arr[j].compareTo(arr[i]) < 0) {
					swap(arr,i,j);
					i++;
				}
			} else {
				break;
			}
		}
		sort(arr,low,i-1);
		sort(arr,k+1,high);
		return arr;
	}

	private static void swap(Comparable[] arr, int j, int k) {
		Comparable temp = arr[j];
		arr[j] = arr[k];
		arr[k] = temp;
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
		QuickSortThreeWay.sort(array);
		for (Integer integer : array) {
			System.out.print(integer);
			System.out.print(" ");
		}
	}

}
