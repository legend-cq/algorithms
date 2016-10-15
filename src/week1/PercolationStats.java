package week1;

import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.StdStats;

public class PercolationStats {
   private double[] fraction;
   public PercolationStats(int n, int trials) {
	   if(n <= 0 || trials <= 0) {
		   throw new IllegalArgumentException();
	   }
	   fraction = new double[trials];
	   for(int i=1;i<=trials;i++) {
		   int count = 0;
		   Percolation p = new Percolation(n);
		   while(!p.percolates()) {
			   int row = StdRandom.uniform(n) + 1;
			   int column = StdRandom.uniform(n) + 1;
			   if(!p.isOpen(row, column)) {
				   p.open(row, column);
				   count++;
			   }
		   }
		   fraction[i-1] = ((double)count)/(n*n);
	   }
   }
   public double mean() {
	   return StdStats.mean(fraction);
   }
   public double stddev() {
	   return StdStats.stddev(fraction);
   }
   public double confidenceLo() {
	   return mean() - 1.96 * stddev() / Math.sqrt(fraction.length);
   }
   public double confidenceHi() {
	   return mean() + 1.96 * stddev() / Math.sqrt(fraction.length);
   }

   public static void main(String[] args) {
	   PercolationStats ps = new PercolationStats(Integer.parseInt(args[0]),Integer.parseInt(args[1]));
	   System.out.println("mean                    = " + ps.mean());
	   System.out.println("stddev                  = " + ps.stddev());
	   System.out.println("95% confidence interval = " + ps.confidenceLo()+", " + ps.confidenceHi());
   }

}
