package week9;

import java.util.Arrays;
import java.util.Random;

import edu.princeton.cs.algs4.Stopwatch;

public class RadixSortLSD {
	public static final int R = 256;
	public static final int BITS_PER_BYTE = 8;
	
	public static void sort(String[] arr) {
		int maxLen = 0;
		for (String string : arr) {
			maxLen = Math.max(maxLen, string.length());
		}
		String[] aux = new String[arr.length];
		for (int d = maxLen - 1; d >= 0; d--) {
			int[] count = new int[R + 2];
			for (int i = 0; i < arr.length; i++) {
				count[charAt(arr[i], d) + 2]++;
			}
			for (int i = 0; i <= R; i++) {
				count[i + 1] += count[i];
			}
			for (int i = 0; i < arr.length; i++) {
				aux[count[charAt(arr[i], d) + 1]++] = arr[i];
			}
			for (int i = 0; i < arr.length; i++) {
				arr[i] = aux[i];
			}
		}
	}
	
	public static void sort(int[] arr) {
		int width = 32 / BITS_PER_BYTE;
		int len = arr.length;
		int[] aux = new int[len];
		int mask = R - 1;
		for (int d = 0; d < width; d++) {
			int[] count = new int[R + 1];
			for (int i = 0; i < len; i++) {
				count[((arr[i] >> (d * BITS_PER_BYTE)) & mask) + 1]++;
			}
			for (int i = 0; i < R; i++) {
				count[i + 1] += count[i];
			}
			for (int i = 0; i < len; i++) {
				aux[count[arr[i] >> (d * BITS_PER_BYTE) & mask]++] = arr[i];
			}
			for (int i = 0; i < len; i++) {
				arr[i] = aux[i];
			}
		}
	}
	
	private static int charAt(String str, int index) {
		if (index < str.length()) {
			return str.charAt(index);
		} else {
			return -1;
		}
	}
	
	public static void main(String[] args) {
//		String[] arr = {"steven","jason","ada","lunar","nicole","adam"};
//		RadixSortLSD.sort(arr);
//		for (String string : arr) {
//			System.out.println(string);
//		}
		Random random = new Random();
		int size = 200000000;
		int[] arr = new int[size];
		int[] arr1 = new int[size];
		for (int i = 0; i < size; i++) {
			arr[i] = random.nextInt(1000);
			arr1[i] = arr[i];
		}
		Stopwatch timer1 = new Stopwatch();
		RadixSortLSD.sort(arr);
		double time1 = timer1.elapsedTime();
		System.out.println(time1);
		Stopwatch timer2 = new Stopwatch();
		Arrays.sort(arr1);
		double time2 = timer2.elapsedTime();
		System.out.println(time2);
	}
}
