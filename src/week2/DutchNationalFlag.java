package week2;

import edu.princeton.cs.algs4.StdRandom;

public class DutchNationalFlag {
	public int colorCount = 0;
	public int swapCount = 0;
	private class Pebble{
		private String color;

		public String getColor() {
			return color;
		}

		public void setColor(String color) {
			this.color = color;
		}

	}
	
	public void sortPebble(Pebble[] pebbles) {
		int start = -1;
		int end = pebbles.length;
		for(int i = 0; i < end;i++) {
			String color = pebbles[i].getColor();
			colorCount++;
			if(color.equalsIgnoreCase("red")) {
				if(i != start+1) {
					exchange(pebbles,i,start+1);
				}
				start++;
			} else if(color.equalsIgnoreCase("white")) {
				continue;
			} else if(color.equalsIgnoreCase("blue")) {
				if(i == end-1) {
					return;
				}
				int previous = end - 1;
				String previousColor = pebbles[previous].getColor();
				colorCount++;
				while(previousColor.equalsIgnoreCase("blue")) {
					end--;
					if(i == end-1) {
						return;
					}
					previous = end - 1;
					previousColor = pebbles[previous].getColor();
					colorCount++;
				}
				if(previousColor.equalsIgnoreCase("red")) {
					exchange(pebbles,i,end-1);
					if(start + 1 != i) {
						exchange(pebbles,start+1,i);
					}
					start++;
				} else if(previousColor.equalsIgnoreCase("white")) {
					exchange(pebbles,i,end-1);
				}
				end--;
			}
		}
	}
	
	private void exchange(Pebble[] pebbles,int i,int j) {
		swapCount++;
		Pebble p = pebbles[i];
		pebbles[i] = pebbles[j];
		pebbles[j] = p;
	}
	
	public static void main(String[] args) {
		DutchNationalFlag flag= new DutchNationalFlag();
		Pebble[] pebbles = new Pebble[20];
		String[] colorArr = {"white","blue","blue","white","blue","blue","blue","blue","red","blue","blue","blue","blue","blue","blue","blue","blue","red","blue","white"};
		for(int i = 0; i < 20;i++) {
			Pebble pebble = flag.new Pebble();
			int colorNumber = StdRandom.uniform(3);
			String color = null;
			if(colorNumber == 0) {
				color = "red";
			} else if(colorNumber == 1) {
				color = "white";
			} else {
				color = "blue";
			}
			pebble.setColor(color);
//			pebble.setColor(colorArr[i]);
			pebbles[i] = pebble;
		}
		for (Pebble pebble : pebbles) {
			System.out.print(pebble.getColor() + "  ");
		}
		System.out.println();
		flag.sortPebble(pebbles);
		for (Pebble pebble : pebbles) {
			System.out.print(pebble.getColor() + "  ");
		}
		System.out.println();
		System.out.println(flag.colorCount);
		System.out.println(flag.swapCount);
	}
}
