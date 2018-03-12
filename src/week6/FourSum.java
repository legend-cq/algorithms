package week6;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import edu.princeton.cs.algs4.StdRandom;

public class FourSum {
	public static List<Integer[]> fourSum(int[] values) {
		HashMap<Integer,List<Integer[]>> map = new HashMap<Integer,List<Integer[]>>();
		List<Integer[]> fourSum = new ArrayList<Integer[]>();
		for(int i = 0; i < values.length;i++) {
			for(int j = i + 1; j < values.length; j++) {
				int sum = values[i] + values[j];
				List<Integer[]> list = map.get(sum);
				if(list == null) {
					list = new ArrayList<Integer[]>();
					map.put(sum, list);
				}
				list.add(new Integer[] {i,j});
				if(list.size() > 1) {
					for(int k = 0; k < list.size() - 1; k++) {
						Integer[] firstTwo = list.get(k);
						Integer[] lastTwo = list.get(list.size()-1);
						if(!firstTwo[0].equals(lastTwo[0]) && !firstTwo[0].equals(lastTwo[1]) 
								&& !firstTwo[1].equals(lastTwo[0]) && !firstTwo[1].equals(lastTwo[1])) {
							fourSum.add(new Integer[] {firstTwo[0],firstTwo[1],lastTwo[0],lastTwo[1]});
						}
					}
				}
			}
		}
		return fourSum;
	}
	
	public static void main(String[] args) {
		int size = 10;
		int[] values = new int[size];
		for (int i = 0; i < values.length;i++) {
			values[i] = StdRandom.uniform(100);
//			values[i] = 0;
			System.out.print(values[i] + ",");
		}
		System.out.println();
		List<Integer[]> sums = FourSum.fourSum(values);
		for (Integer[] integers : sums) {
			System.out.print(integers[0] + "," + integers[1] + "," + integers[2] + "," + integers[3]);
			System.out.println();
		}
	}
	
	public boolean equals(FourSum sum) {
		return true;
	}
}
