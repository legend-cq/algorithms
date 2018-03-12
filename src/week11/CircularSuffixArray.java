package week11;

import java.util.Arrays;
import java.util.Comparator;

public class CircularSuffixArray {
	private final Integer[] index;
	
	// circular suffix array of s
	public CircularSuffixArray(String s) {
		if (s == null) throw new IllegalArgumentException();
		index = new Integer[s.length()];
		for (int i = 0; i < s.length(); i++) {
			index[i] = i;
		}
		Arrays.sort(index, new Comparator<Integer>() {
			public int compare(Integer i1, Integer i2) {
				for (int i = 0; i < s.length(); i++) {
					char c1 = s.charAt(i1);
					char c2 = s.charAt(i2);
					if (c1 != c2) {
						return c1 - c2;
					}
					i1++;
					if (i1 == s.length()) {
						i1 = 0;
					}
					i2++;
					if (i2 == s.length()) {
						i2 = 0;
					}
				}
				return 0;
			}
		});
	}
	
	// length of s
	public int length() {
		return index.length;
	}
	
	// returns index of ith sorted suffix
	public int index(int i) {
		if (i < 0 || i >= length()) throw new IllegalArgumentException();
		return index[i];
	}
	
	// unit testing (required)
	public static void main(String[] args) {
		String str = "ABRACADABRA!";
		CircularSuffixArray suffixArr = new CircularSuffixArray(str);
		for (int i = 0; i < str.length(); i++) {
			System.out.println(suffixArr.index(i));
		}
	}
}
