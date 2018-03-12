package week10;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class KMP {
	private int[] computePrefix(String pattern) {
		int[] prefix = new int[pattern.length()];
		prefix[0] = 0;
		for (int i = 1; i < pattern.length(); i++) {
			int len = prefix[i - 1];
			while (len > 0 && pattern.charAt(len) != pattern.charAt(i)) {
				len = prefix[len - 1];
			}
			if (pattern.charAt(len) == pattern.charAt(i)) {
				len++;
			}
			prefix[i] = len;
		}
		return prefix;
	}
	
	private int[] computePrefix1(String pattern) {
		int[] prefix = new int[pattern.length()];
		prefix[0] = 0;
		for (int i = 1, len = 0; i < pattern.length(); i++) {
			while (len > 0 && pattern.charAt(len) != pattern.charAt(i)) {
				len = prefix[len - 1];
			}
			if (pattern.charAt(len) == pattern.charAt(i)) {
				len++;
			}
			prefix[i] = len;
		}
		return prefix;
	}
	
	public List<Integer> search(String text, String pattern) {
		List<Integer> result = new ArrayList<>();
		int[] prefix = computePrefix(pattern);
		for (int i = 0, j = 0; i < text.length();) {
			if (text.charAt(i) == pattern.charAt(j)) {
				i++;
				j++;
			} else if (j == 0) {
				i++;
			} else {
				j = prefix[j - 1];
			}
			if (j == pattern.length()) {
				result.add(i - j);
				j = prefix[j - 1];
			}
		}
		return result;
	}
	
	public static void main(String[] args) {
		KMP kmp = new KMP();
		List<Integer> list = kmp.search("aaababaabaababaab", "aabab");
		for (Integer integer : list) {
			System.out.println(integer);
		}
	}
}
