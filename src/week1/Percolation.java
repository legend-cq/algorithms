package week1;

import edu.princeton.cs.algs4.WeightedQuickUnionUF;

public class Percolation {
	private boolean[] isOpenArr;
	private WeightedQuickUnionUF uf = null;
	private WeightedQuickUnionUF noBottomUF = null;
	private int N = 0;
	
	// create n-by-n grid, with all sites blocked 
	public Percolation(int n) {
		if(n <= 0) {
			throw new IllegalArgumentException();
		} 
		isOpenArr = new boolean[n*n+2];
		uf = new  WeightedQuickUnionUF(n*n+2);
		noBottomUF = new  WeightedQuickUnionUF(n*n+1);
		N = n;
	}
	// open site (row i, column j) if it is not open already
	public void open(int i, int j) {
		validation(i,j);
		isOpenArr[convertIndex(i,j)] = true;
		if(j != 1 && isOpenArr[convertIndex(i,j)-1]) {
			uf.union(convertIndex(i,j), convertIndex(i,j)-1);
			noBottomUF.union(convertIndex(i,j), convertIndex(i,j)-1);
		}
		if(j != N && isOpenArr[convertIndex(i,j)+1]) {
			uf.union(convertIndex(i,j), convertIndex(i,j)+1);
			noBottomUF.union(convertIndex(i,j), convertIndex(i,j)+1);
		}
		if(i != 1 && isOpenArr[convertIndex(i,j)-N]) {
			uf.union(convertIndex(i,j), convertIndex(i,j)-N);
			noBottomUF.union(convertIndex(i,j), convertIndex(i,j)-N);
		}
		if(i != N && isOpenArr[convertIndex(i,j)+N]) {
			uf.union(convertIndex(i,j), convertIndex(i,j)+N);
			noBottomUF.union(convertIndex(i,j), convertIndex(i,j)+N);
		}
		if(i == 1) {
			uf.union(0,convertIndex(i,j));
			noBottomUF.union(0,convertIndex(i,j));
		}
		if(i == N) {
			uf.union(N*N+1, convertIndex(i,j));
		}
	}
	// is site (row i, column j) open?
	public boolean isOpen(int i, int j) {
		validation(i,j);
		return isOpenArr[convertIndex(i,j)];
	}
	// is site (row i, column j) full?
	public boolean isFull(int i, int j) {
		validation(i,j);
		return noBottomUF.connected(0, convertIndex(i,j));
	}
	// does the system percolate?
	public boolean percolates() {
		return uf.connected(0, N * N + 1); 
	}            
	// test client (optional)
	public static void main(String[] args) {
		Percolation p = new Percolation(5);
		p.open(1, 1);
		p.open(1, 2);
		p.open(2, 2);
		System.out.println(p.percolates());
		System.out.println(p.isFull(2, 2));
		p.open(3, 2);
		p.open(3, 3);
		p.open(4, 3);
		p.open(5, 3);
		System.out.println(p.percolates());
	} 
	
	private int convertIndex(int i, int j) {
		return (i-1) * N + j;
	}
	
	private void validation(int i, int j) {
		if(i < 1 || i > N || j < 1 || j > N) {
			throw new IndexOutOfBoundsException();
		}
	}
}