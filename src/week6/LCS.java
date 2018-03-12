package week6;

import java.util.Arrays;

public class LCS {
	//brute force
	public static String lcs1(String str1, String str2) {
		if(str1 == null || str2 == null) {
			return null;
		}
		int start = 0;
		int end = 0;
		for(int i = 0;i < str1.length();i++) {
			for(int j = i + 1;j <= str1.length();j++) {
				String subStr = str1.substring(i, j);
				if(str2.contains(subStr) && ((j-i)>(end-start))) {
					start = i;
					end = j;
				}
			}
		}
		return str1.substring(start,end);
	}
	//dynamic programming
	public static String lcs(String str1, String str2) {
		int maxLen = 0;
		int maxPos = -1;
		int len = 0;
		for(int i=0;i<str1.length();i++) {
			if(str2.contains(String.valueOf(str1.charAt(i)))) {
				if(str2.contains(str1.substring(i-len,i+1))) {
					len = len + 1;
				} else {
					len = 1;
				}
				if(len > maxLen) {
					maxLen = len;
					maxPos = i;
				}
			} else {
				len = 0;
			}
		}
		return str1.substring(maxPos-maxLen+1,maxPos+1);
	}
	
	//dynamic programming
	public static String lcs(char[] str1, char[] str2) {
		int pos1 = -1;
		int maxLen = 0;
		int[][] lens = new int[str1.length][str2.length];
		for(int i = 0; i < str1.length;i++) {
			for(int j = 0; j < str2.length;j++) {
				if(str1[i] == str2[j]) {
					if(i ==0 || j ==0) {
						lens[i][j] = 1;
					} else {
						lens[i][j] = lens[i-1][j-1] + 1;
					}
					if(lens[i][j] > maxLen) {
						maxLen = lens[i][j];
						pos1 = i;
					}
				} else {
					lens[i][j] = 0;
				}
			}
		}
		return new String(Arrays.copyOfRange(str1, pos1-maxLen+1, pos1+1));
	}
	
	public static void main(String[] args) {
		String a = "zxasdfcajhj";
		String b = "123d1ca9090";
		System.out.println(LCS.lcs1(a, b));
		System.out.println(LCS.lcs(a, b));
		System.out.println(LCS.lcs(a.toCharArray(), b.toCharArray()));
	}
}
