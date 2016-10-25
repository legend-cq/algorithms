package week1;

import edu.princeton.cs.algs4.Stopwatch;

public class BitonicSearch {

	public static void main(String[] args) {
		int[] a = new int[100000000];
		int j = 0;
		for(int i = 1; i <= 100000000;i=i+2) {
			a[j++] = i;
		}
		for(int i = 100000000;i>=2;i=i-2) {
			a[j++] = i;
		}
		Stopwatch sw = new Stopwatch();
		System.out.println(indexOf(a,1));
		System.out.println(sw.elapsedTime());
	}
	
	public static int indexOf(int[] a,int key) {
		int low = 0;
		int high = a.length -1;
		return indexOf(a,key,low,high);
	}
	
	public static int indexOf(int[] a,int key,int low,int high) {
		if(low > high) {
			return -1;
		}
		int middle = low + (high - low)/2;
		if(a[middle] == key) {
			return middle;
		}
		if(a[low] < a[middle] && a[middle] < a[high]) {
			if(key < a[middle]) {
				return indexOf(a,key,low,middle-1);
			} else {
				return indexOf(a,key,middle+1,high);
			}
		} else if(a[low] > a[middle] && a[middle] > a[high]) {
			if(key < a[middle]) {
				return indexOf(a,key,middle+1,high);
			} else {
				return indexOf(a,key,low,middle-1);
			}
		} else {
			int index = -1;
			index = indexOf(a,key,low,middle-1);
			if(index != -1) {
				return index;
			}
			return indexOf(a,key,middle+1,high);
		}
	}
	
	public static int indexOf2(int[] a,int key,int low,int high) {
		if(low > high) {
			return -1;
		}
		int middle = low + (high - low)/2;
		if(a[middle] == key) {
			return middle;
		}
		if(key > a[middle]) {
			if(a[middle] < a[middle+1]) {
				return indexOf2(a,key,middle+1,high);
			} else {
				return indexOf2(a,key,low,middle-1);
			}
		} else {
			int index = -1;
			index = indexOf(a,key,low,middle-1);
			if(index != -1) {
				return index;
			}
			return indexOf(a,key,middle+1,high);
		}
	}
}
