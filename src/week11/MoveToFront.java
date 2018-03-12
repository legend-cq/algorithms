package week11;

import edu.princeton.cs.algs4.BinaryStdIn;
import edu.princeton.cs.algs4.BinaryStdOut;

public class MoveToFront {
	// apply move-to-front encoding, reading from standard input and writing to standard output
    public static void encode() {
    	char[] encode = new char[256];
    	for (int i = 0; i < 256; i++) {
			encode[i] = (char) i;
		}
    	while (!BinaryStdIn.isEmpty()) {
    		char c = BinaryStdIn.readChar(8);
    		int pos = -1;
        	for (int i = 0; i < encode.length; i++) {
        		if (c == encode[i]) {
        			pos = i;
        			break;
        		}
        	}
        	for (int i = pos; i > 0; i--) {
        		encode[i] = encode[i - 1];
        	}
        	encode[0] = c;
        	BinaryStdOut.write(pos, 8);
    	}
    	BinaryStdOut.close();
    }

    // apply move-to-front decoding, reading from standard input and writing to standard output
    public static void decode() {
    	char[] decode = new char[256];
    	for (int i = 0; i < 256; i++) {
			decode[i] = (char) i;
		}
    	while (!BinaryStdIn.isEmpty()) {
    		int pos = BinaryStdIn.readInt(8);
    		char c = decode[pos];
    		for (int i = pos; i > 0; i--) {
        		decode[i] = decode[i - 1];
        	}
    		decode[0] = c;
    		BinaryStdOut.write(c, 8);
    	}
    	BinaryStdOut.close();
    }

    // if args[0] is '-', apply move-to-front encoding
    // if args[0] is '+', apply move-to-front decoding
    public static void main(String[] args) {
    	if (args.length > 0) {
    		String operator = args[0];
    		if (operator.equals("-")) {
    			encode();
    		} else if (operator.equals("+")) {
    			decode();
    		} else {
    			throw new IllegalArgumentException();
    		}
    	}
    }
}
