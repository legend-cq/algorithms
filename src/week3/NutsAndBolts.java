package week3;

import edu.princeton.cs.algs4.StdRandom;

public class NutsAndBolts {

	public static void main(String[] args) {
		int[] nuts = {1,2,3,4,5,6,7,8,9,10,11,12,13,14,15,16,17,18,19,20};
		int[] bolts = {1,2,3,4,5,16,7,8,9,14,11,12,13,10,15,6,17,18,19,20};
		for(int a = 1; a <=50;a++) {
			match(nuts,bolts);
			System.out.print("Nuts:");
			for (int i : nuts) {
				System.out.print(i + " ");
			}
			System.out.println();
			System.out.print("Bolts:");
			for (int i : bolts) {
				System.out.print(i + " ");
			}
			System.out.println();
		}
	}
	
	public static void match(int[] nuts,int[] bolts) {
		StdRandom.shuffle(nuts);
		StdRandom.shuffle(bolts);
		match(nuts,bolts,0,nuts.length - 1);
	}
	
	public static void match(int[] nuts,int[] bolts, int low, int high) {
		if(low>=high) {
			return;
		}
		int position = partition(nuts,bolts,low,high);
		match(nuts,bolts,low,position-1);
		match(nuts,bolts,position+1,high);
	}

	private static int partition(int[] nuts, int[] bolts, int low, int high) {
		int base = nuts[low];
		int lt = low;
		int gt = high;
		int eq = -1;
		while(true) {
			while(bolts[lt] <= base) {
				if(bolts[lt] == base) {
					eq = lt;
				}
				if(lt == high) {
					break;
				}
				lt++;
			}
			while(bolts[gt] >= base) {
				if(bolts[gt] == base) {
					eq = gt;
				}
				if(gt == low) {
					break;
				}
				gt--;
			}
			if(lt < gt) {
				swap(bolts,lt++,gt--);
			} else {
				break;
			}
		}
		if(eq <= gt) {
			swap(bolts,eq,gt);
			eq = gt;
		}
		if(eq >= lt) {
			swap(bolts,eq,lt);
			eq = lt;
		}
		base = bolts[eq];
		lt = low;
		gt = high;
		eq = -1;
		while(true) {
			while(nuts[lt] <= base) {
				if(nuts[lt] == base) {
					eq = lt;
				}
				if(lt == high) {
					break;
				}
				lt++;
			}
			while(nuts[gt] >= base) {
				if(nuts[gt] == base) {
					eq = gt;
				}
				if(gt == low) {
					break;
				}
				gt--;
			}
			if(lt < gt) {
				swap(nuts,lt++,gt--);
			} else {
				break;
			}
		}
		if(eq <= gt) {
			swap(nuts,eq,gt);
			eq = gt;
		}
		if(eq >= lt) {
			swap(nuts,eq,lt);
			eq = lt;
		}
		return eq;
	}

	private static void swap(int[] arr, int lt, int gt) {
		int temp = arr[lt];
		arr[lt] = arr[gt];
		arr[gt] = temp;
	}

}
