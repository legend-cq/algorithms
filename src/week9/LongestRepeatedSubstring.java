package week9;

import java.util.Arrays;

import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.Stopwatch;

public class LongestRepeatedSubstring {
	//brute force
	public static String lrs(String str) {
		int len = str.length();
		int max = 0;
		String lrs = "";
		for (int i = 0; i < len; i++) {
			for (int j = i + 1; j < len; j++) {
				int count = 0;
				for (int k = 0; k < len - j; k++) {
					if (str.charAt(i + k) == str.charAt(j + k)) {
						count++;
					} else {
						break;
					}
				}
				if (count > max) {
					max = count;
					lrs = str.substring(j, j + count);
				}
			}
		}
		return lrs;
	}
	
	//suffix array
	public static String lrs1(String str) {
		int len = str.length();
		int max = 0;
		String lrs = "";
		String[] suffixArr = new String[len];
		for (int i = 0; i < len; i++) {
			suffixArr[i] = str.substring(i);
		}
		Arrays.sort(suffixArr);
		for (int i = 0; i < len - 1; i++) {
			int count = 0;
			String s1 = suffixArr[i];
			String s2 = suffixArr[i + 1];
			for (int j = 0; j < s1.length(); j++) {
				if (s1.charAt(j) == s2.charAt(j)) {
					count++;
				} else {
					break;
				}
			}
			if (count > max) {
				max = count;
				lrs = s1.substring(0, count);
			}
		}
		return lrs;
	}
	
	//dynamic programming
	public static String lrs2(String str) {
		int len = str.length();
		int[][] count = new int[2][len];
		int pos = -1;
		int max = 0;
		int curRow = 0;
		int prevRow = 1 - curRow;
		for (int i = 0; i < len; i++) {
			for (int j = i + 1; j < len; j++) {
				if (str.charAt(i) == str.charAt(j)) {
					count[curRow][j] = count[prevRow][j - 1] + 1;
					if (count[curRow][j] > max) {
						max = count[curRow][j];
						pos = j;
					}
				} else {
					count[curRow][j] = 0;
				}
			}
			curRow = prevRow;
			prevRow = 1 - curRow;
		}
		return str.substring(pos - max + 1, pos + 1);
	}
	
	public static void main(String[] args) {
		String str = "aacaagtttacaagc";
		String str1 = "aaaaaaaaaa";
		String str2 = "abcdefg";
		In in = new In("C:\\Users\\qchen\\Desktop\\unreachable url");
		String str3 = in.readAll();
		Stopwatch timer1 = new Stopwatch();
		System.out.println(LongestRepeatedSubstring.lrs(str3));
		System.out.println(timer1.elapsedTime());
		Stopwatch timer2 = new Stopwatch();
		System.out.println(LongestRepeatedSubstring.lrs1(str3));
		System.out.println(timer2.elapsedTime());
		Stopwatch timer3 = new Stopwatch();
		System.out.println(LongestRepeatedSubstring.lrs2(str3));
		System.out.println(timer3.elapsedTime());
	}
}
