package week2;

import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdRandom;

public class Subset {

	public static void main(String[] args) {
		//approach 1
		/*int k = Integer.parseInt(args[0]);
		RandomizedQueue<String> queue = new RandomizedQueue<>();
		while(!StdIn.isEmpty()) {
			queue.enqueue(StdIn.readString());
		}
		for (int i = 0;i < k;i++) {
			System.out.println(queue.dequeue());
		}*/
		//approach 2
		int k = Integer.parseInt(args[0]);
		int count = 0;
		String[] strArr = new String[k];
		while(!StdIn.isEmpty()) {
			count++;
			String str = StdIn.readString();
			if(count <= k) {
				strArr[count-1] = str;
			} else {
				int random = StdRandom.uniform(count);
				if(random < k) {
					strArr[random] = str;
				}
			}
		}
		for (String string : strArr) {
			System.out.println(string);
		}
	}

}
