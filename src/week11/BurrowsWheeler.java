package week11;

import edu.princeton.cs.algs4.BinaryStdIn;
import edu.princeton.cs.algs4.BinaryStdOut;

public class BurrowsWheeler {
	// apply Burrows-Wheeler transform, reading from standard input and writing to standard output
    public static void transform() {
    	String str = null;
    	while (!BinaryStdIn.isEmpty()) {
    		str = BinaryStdIn.readString();
    	}
    	CircularSuffixArray suffixArr = new CircularSuffixArray(str);
    	for (int i = 0; i < suffixArr.length(); i++) {
    		if (suffixArr.index(i) == 0) {
    			BinaryStdOut.write(i);
    			break;
    		}
    	}
    	for (int i = 0; i < suffixArr.length(); i++) {
    		int pos = suffixArr.index(i);
    		if (pos == 0) {
    			pos = suffixArr.length();
    		}
    		BinaryStdOut.write(str.charAt(pos - 1), 8);
    	}
    	BinaryStdOut.close();
    }

    // apply Burrows-Wheeler inverse transform, reading from standard input and writing to standard output
    public static void inverseTransform() {
    	int start = BinaryStdIn.readInt();
    	StringBuilder sb = new StringBuilder();
    	while (!BinaryStdIn.isEmpty()) {
    		sb.append(BinaryStdIn.readChar());
    	}
    	char[] lastCol = sb.toString().toCharArray();
    	int[] count = new int[256];
    	for (char c : lastCol) {
    		count[c]++;
    	}
    	for (int i = 1; i < count.length; i++) {
    		count[i] += count[i - 1];
    	}
    	int[] next = new int[lastCol.length];
    	char[] firstCol = new char[lastCol.length];
    	for (int i = lastCol.length - 1; i >= 0; i--) {
    		int index = --count[lastCol[i]];
    		firstCol[index] = lastCol[i];
    		next[index] = i;
    	}
    	int sum = 0;
    	while (sum < lastCol.length) {
    		BinaryStdOut.write(firstCol[start]);
    		start = next[start];
    		sum++;
    	}
    	BinaryStdOut.close();
    }

    // if args[0] is '-', apply Burrows-Wheeler transform
    // if args[0] is '+', apply Burrows-Wheeler inverse transform
    public static void main(String[] args) {
    	if (args.length > 0) {
    		String operator = args[0];
    		if (operator.equals("-")) {
    			transform();
    		} else if (operator.equals("+")) {
    			inverseTransform();
    		} else {
    			throw new IllegalArgumentException();
    		}
    	}
    }
}
