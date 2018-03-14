package week11;

public class CircularSuffixArray {
	private final int[] index;
	private final int[] aux;
	private final int length;
	private static final int R = 256;
	private final String str;
	
	// circular suffix array of s
	public CircularSuffixArray(String s) {
		if (s == null) throw new IllegalArgumentException();
		str = s;
		length = s.length();
		index = new int[length];
		for (int i = 0; i < length; i++) {
			index[i] = i;
		}
		aux = new int[length];
		MSD(0, length - 1, 0);
	}
	
	private void MSD(int low, int high, int radix) {
		if (high - low <= 15) {
			insertion(low, high, radix);
			return;
		}
		if (low >= high) return;
		if (radix == length) return;
		int[] count = new int[R + 1];
		//calculate count
		for (int i = low; i <= high; i++) {
			count[str.charAt((index[i] + radix) % length)]++;
		}
		//compute position
		for (int i = 0; i < R; i++) {
			count[i + 1] += count[i];
		}
		//set position
		for (int i = high; i >= low; i--) {
			aux[low + --count[str.charAt((index[i] + radix) % length)]] = index[i];
		}
		//copy back
		for (int i = high; i >= low; i--) {
			index[i] = aux[i];
		}
		//compare next digit for equal value
		for (int i = 0; i < R; i++) {
			MSD(low + count[i], low + count[i + 1] - 1, radix + 1);
		}
	}

	private void insertion(int low, int high, int radix) {
		for (int i = low + 1; i <= high; i++) {
			for (int j = i; j > low; j--) {
				if (less(j, j - 1, radix)) {
					swap(j, j - 1);
				} else {
					break;
				}
			}
		}
	}
	
	private boolean less(int i, int j, int radix) {
		for (int k = radix; k < length; k++) {
			if (str.charAt((index[i] + k) % length) > str.charAt((index[j] + k) % length)) {
				return false;
			} else if (str.charAt((index[i] + k) % length) < str.charAt((index[j] + k) % length)) {
				return true;
			}
		}
		return false;
	}

	private void swap(int i, int j) {
		int temp = index[i];
		index[i] = index[j];
		index[j] = temp;
	}

	// length of s
	public int length() {
		return length;
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
		for (int i = 0; i < suffixArr.length(); i++) {
			System.out.println(suffixArr.index(i));
		}
	}
}
