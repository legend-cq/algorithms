package week9;

import java.util.Arrays;
import java.util.Random;

import edu.princeton.cs.algs4.Stopwatch;

public class RadixSortMSD {
	public static final int R = 256;
	public static final int MASK = R - 1;
	public static final int BITS_PER_BYTE = 8;
	public static final int WIDTH = 32 / BITS_PER_BYTE;
	
	public static void sort(String[] arr) {
		String[] aux = new String[arr.length];
		sort(arr, 0, arr.length - 1, 0, aux);
	}
	
	private static void sort(String[] arr, int start, int end, int d, String[] aux) {
		if (start >= end) {
			return;
		}
		int[] count = new int[R + 2];
		
		for (int i = start; i <= end; i++) {
			count[charAt(arr[i], d) + 2]++;
		}
		for (int i = 0; i <= R; i++) {
			count[i + 1] += count[i];
		}
		for (int i = start; i <= end; i++) {
			int c = charAt(arr[i], d) + 1;
			aux[count[c]++] =  arr[i];
		}
		for (int i = start; i <= end; i++) {
			arr[i] = aux[i - start];
		}
		for (int i = 0; i < R; i++) {
			sort(arr, count[i] + start, count[i + 1] - 1 + start, d + 1, aux);
		}
	}
	
	public static void sort(int[] arr) {
		int[] aux = new int[arr.length];
		sort(arr, 0, arr.length - 1, 0, aux);
	}

	private static void sort(int[] arr, int start, int end, int d, int[] aux) {
		if (start >= end) {
			return;
		}
		if (d >= WIDTH) {
			return;
		}
		int[] count = new int[R + 1];
		for (int i = start; i <= end; i++) {
			int c = (arr[i] >> (WIDTH - 1 - d) * BITS_PER_BYTE) & MASK;
			count[c + 1]++;
		}
		for (int i = 0; i < R; i++) {
			count[i + 1] += count[i];
		}
		for (int i = start; i <= end; i++) {
			int c = (arr[i] >> (WIDTH - 1 - d) * BITS_PER_BYTE) & MASK;
			aux[count[c]++] = arr[i];
		}
		for (int i = start; i <= end; i++) {
			arr[i] = aux[i - start];
		}
		sort(arr, start + 0, start + count[0] - 1, d + 1, aux);
		for (int i = 0; i < R - 1; i++) {
			sort(arr, start + count[i], start + count[i + 1] - 1, d + 1, aux);
		}
	}

	private static int charAt(String str, int d) {
		if (d < str.length()) {
			return str.charAt(d);
		} else {
			return -1;
		}
	}
	
	public static void main(String[] args) {
//		String[] arr = {"steven","jason","ada","lunar","nicole","adam"};
//		RadixSortMSD.sort(arr);
//		for (String string : arr) {
//			System.out.println(string);
//		}
		Random random = new Random();
		int size = 100000000;
		int[] arr = new int[size];
		int[] arr1 = new int[size];
		int[] arr2 = new int[size];
		for (int i = 0; i < size; i++) {
			arr[i] = random.nextInt(10000000);
			arr1[i] = arr[i];
			arr2[i] = arr[i];
		}
		Stopwatch timer1 = new Stopwatch();
		RadixSortMSD.sort(arr);
		double time1 = timer1.elapsedTime();
		System.out.println(time1);
		Stopwatch timer2 = new Stopwatch();
		RadixSortLSD.sort(arr1);
		double time2 = timer2.elapsedTime();
		System.out.println(time2);
		Stopwatch timer3 = new Stopwatch();
		Arrays.sort(arr2);
		double time3 = timer3.elapsedTime();
		System.out.println(time3);
	}
}
