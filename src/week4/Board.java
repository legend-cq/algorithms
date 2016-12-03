package week4;

import java.util.ArrayList;

public class Board {
	private int[][] blocks;
	private int dimension;
	private int hamming = -1;
	private int manhattan = -1;
	public Board(int[][] blocks) {
		dimension = blocks.length;
		this.blocks = new int[dimension][dimension];
		for(int i = 0;i < dimension;i++) {
			for(int j = 0; j < dimension;j++) {
				this.blocks[i][j] = blocks[i][j];
			}
		}
	}
	public int dimension() {
		return dimension;
	}
	public int hamming() {
		if(hamming >= 0) {
			return hamming;
		}
		hamming = 0;
		for(int i = 0;i < dimension;i++) {
			for(int j = 0; j < dimension;j++) {
				if(blocks[i][j] != 0 && blocks[i][j] != (i*dimension + j + 1)) {
					hamming++;
				}
			}
		}
		return hamming;
	}
	public int manhattan() {
		if(manhattan >= 0) {
			return manhattan;
		}
		manhattan = 0;
		for(int i = 0;i < dimension;i++) {
			for(int j = 0; j < dimension;j++) {
				if(blocks[i][j] != 0) {
					int row = (blocks[i][j]-1)/ dimension;
					int column = (blocks[i][j]-1) % dimension;
					manhattan+=Math.abs(row-i) + Math.abs(column-j);
				}
			}
		}
		return manhattan;
	}
	public boolean isGoal() {
		return hamming() == 0;
	}
	public Board twin() {
		int row1 = -1;
		int column1 = -1;
		int row2 = -1;
		int column2 = -1;
		int count = 0;
		for(int i = 0;i < dimension;i++) {
			for(int j = 0; j < dimension;j++) {
				if(blocks[i][j] != 0) {
					count++;
					if(count == 1) {
						row1 = i;
						column1 = j;
					} else if(count ==2) {
						row2 = i;
						column2 = j;
					} else {
						break;
					}
				}
			}
		}
		int temp = blocks[row1][column1];
		blocks[row1][column1] = blocks[row2][column2];
		blocks[row2][column2] = temp;
		Board board = new Board(blocks);
		blocks[row2][column2] = blocks[row1][column1];
		blocks[row1][column1] = temp;
		return board;
	}
	public boolean equals(Object y) {
		if(y == null || !(y instanceof Board)) {
			return false;
		} else {
			y = (Board)y;
			return this.toString().equals(y.toString());
		}
	}
	public Iterable<Board> neighbors() {
		ArrayList<Board> boards = new ArrayList<Board>();
		int row = -1;
		int column = -1;
		for(int i = 0; i < dimension;i++) {
			for(int j = 0; j < dimension;j++) {
				if(blocks[i][j] == 0) {
					row = i;
					column = j;
				}
			}
		}
		//exchange up
		if(row > 0) {
			int temp = blocks[row-1][column];
			blocks[row-1][column] = blocks[row][column];
			blocks[row][column] = temp;
			boards.add(new Board(blocks));
			blocks[row][column] = blocks[row-1][column];
			blocks[row-1][column] = temp;
		}
		//exchange down
		if(row != (dimension - 1)) {
			int temp = blocks[row+1][column];
			blocks[row+1][column] = blocks[row][column];
			blocks[row][column] = temp;
			boards.add(new Board(blocks));
			blocks[row][column] = blocks[row+1][column];
			blocks[row+1][column] = temp;
		}
		//exchange left
		if(column > 0) {
			int temp = blocks[row][column-1];
			blocks[row][column-1] = blocks[row][column];
			blocks[row][column] = temp;
			boards.add(new Board(blocks));
			blocks[row][column] = blocks[row][column-1];
			blocks[row][column-1] = temp;
		}
		//exchange right
		if(column != (dimension -1)) {
			int temp = blocks[row][column+1];
			blocks[row][column+1] = blocks[row][column];
			blocks[row][column] = temp;
			boards.add(new Board(blocks));
			blocks[row][column] = blocks[row][column+1];
			blocks[row][column+1] = temp;
		}
		return boards;
	}
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append(dimension);
		sb.append("\n");
//		int charLen = String.valueOf(blocks[dimension-1][dimension-1]).length();
		for(int i = 0;i < dimension;i++) {
			for(int j = 0; j < dimension;j++) {
				for(int k = 1; k <= 2-String.valueOf(blocks[i][j]).length();k++) {
					sb.append(" ");
				}
				sb.append(blocks[i][j]).append(" ");
			}
			sb.append("\n");
		}
		return sb.toString();
	}

	public static void main(String[] args) {
		
	}
}
