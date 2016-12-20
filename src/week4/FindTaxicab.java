package week4;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;

public class FindTaxicab {
	public static HashSet<Integer> findTaxicab(int n) {
		ArrayList<Integer> raw = new ArrayList<Integer>();
		HashSet<Integer> taxicabs = new HashSet<Integer>();
		for(int i=1;i<=n;i++) {
			for(int j=i+1;j<=n;j++) {
				raw.add(i*i*i + j*j*j);
			}
		}
		Collections.sort(raw);
		for(int i=0;i<raw.size()-1;i++) {
			if(raw.get(i).equals(raw.get(i+1))) {
				taxicabs.add(raw.get(i));
			}
		}
		return taxicabs;
	}
	public static void main(String[] args) {
		HashSet<Integer> taxicab = FindTaxicab.findTaxicab(20);
		for (Integer integer : taxicab) {
			System.out.println(integer);
		}
	}

}
