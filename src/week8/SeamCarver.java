package week8;

import java.awt.Color;
import java.util.Arrays;

import edu.princeton.cs.algs4.Picture;

public class SeamCarver {
	private int[] hSeam;
	private int[] vSeam;
	private int[][] RGB;
	private double[][] energy;
	private int width;
	private int height;
	
	// create a seam carver object based on the given picture
	public SeamCarver(Picture picture) {
		if (picture == null) {
			throw new IllegalArgumentException();
		}
		this.width = picture.width();
		this.height = picture.height();
		RGB = new int[height][width];
		energy = new double[height][width];
		for (int i = 0; i < height; i++) {
			for (int j = 0; j < width; j++) {
				RGB[i][j] = picture.get(j, i).getRGB();
			}
		}
		for (int i = 0; i < height; i++) {
			for (int j = 0; j < width; j++) {
				energy[i][j] = energy(j, i);
			}
		}
	}
	// current picture
	public Picture picture() {
		Picture picture = new Picture(width, height);
		for (int i = 0; i < height; i++) {
			for (int j = 0; j < width; j++) {
				Color color = new Color(RGB[i][j]);
				picture.set(j, i, color);
			}
		}
		return picture;
	}
	// width of current picture
	public int width() {
		return this.width;
	}
	// height of current picture
	public int height() {
		return this.height;
	}
	// energy of pixel at column x and row y
	public double energy(int x, int y) {
		if (x < 0 || x >= width() || y < 0 || y >= height()) {
			throw new IllegalArgumentException();
		}
		if (x == 0 || x == width() - 1 || y == 0 || y == height() - 1) {
			return 1000;
		}

		int redX = getRed(RGB[y][x + 1]) - getRed(RGB[y][x - 1]);
		int greenX = getGreen(RGB[y][x + 1]) - getGreen(RGB[y][x - 1]);
		int blueX = getBlue(RGB[y][x + 1]) - getBlue(RGB[y][x - 1]);
		int redY = getRed(RGB[y + 1][x]) - getRed(RGB[y - 1][x]);
		int greenY = getGreen(RGB[y + 1][x]) - getGreen(RGB[y - 1][x]);
		int blueY = getBlue(RGB[y + 1][x]) - getBlue(RGB[y - 1][x]);
		return Math.sqrt(redX * redX + greenX * greenX + blueX * blueX + redY * redY + greenY * greenY + blueY * blueY);
	}
	// sequence of indices for horizontal seam
	public int[] findHorizontalSeam() {
		if (hSeam != null) {
			return Arrays.copyOf(hSeam, hSeam.length);
		}
		// create one more element for sentinel
		int[] parent = new int[width() * height() + 1];
		double[] distance = new double[width() * height() + 1];
		for (int i = 0; i < distance.length - 1; i++) {
			if (i % width() == 0) {
				distance[i] = 1000;
				parent[i] = i;
			} else {
				distance[i] = Integer.MAX_VALUE;
				parent[i] = -1;
			}
		}
		distance[distance.length - 1] = Integer.MAX_VALUE;
		parent[parent.length - 1] = -1;
		for (int col = 0; col < width(); col++) {
			for (int row = 0; row < height(); row++) {
				if (col < width() - 1) {
					relax(col, row, col + 1, row - 1, parent, distance);
					relax(col, row, col + 1, row, parent, distance);
					relax(col, row, col + 1, row + 1, parent, distance);
				} else {
					relax(col, row, 0, height(), parent, distance);
				}
			}
		}
		hSeam = new int[width()];
		int parentIndex = parent.length - 1;
		for (int i = width() - 1; i >= 0; i--) {
			hSeam[i] = parent[parentIndex] / width();
			parentIndex = parent[parentIndex];
		}
		return Arrays.copyOf(hSeam, hSeam.length);
	}
	
	// sequence of indices for vertical seam
	public int[] findVerticalSeam() {
		if (vSeam != null) {
			return Arrays.copyOf(vSeam, vSeam.length);
		}
		// create one more element for sentinel
		int[] parent = new int[width() * height() + 1];
		double[] distance = new double[width() * height() + 1];
		for (int i = 0; i < distance.length; i++) {
			if (i / width() == 0) {
				distance[i] = 1000;
				parent[i] = i;
			} else {
				distance[i] = Integer.MAX_VALUE;
				parent[i] = -1;
			}
		}
		for (int row = 0; row < height(); row++) {
			for (int col = 0; col < width(); col++) {
				if (row < height() - 1) {
					relax(col, row, col - 1, row + 1, parent, distance);
					relax(col, row, col, row + 1, parent, distance);
					relax(col, row, col + 1, row + 1, parent, distance);
				} else {
					relax(col, row, 0, height(), parent, distance);
				}
			}
		}
		vSeam = new int[height()];
		int parentIndex = parent.length - 1;
		for (int i = height() - 1; i >= 0; i--) {
			vSeam[i] = parent[parentIndex] % width();
			parentIndex = parent[parentIndex];
		}
		return Arrays.copyOf(vSeam, vSeam.length);
	}
	// remove horizontal seam from current picture
	public void removeHorizontalSeam(int[] seam) {
		if (seam == null || seam.length != width()) {
			throw new IllegalArgumentException();
		}
		if (height() <= 1) {
			throw new IllegalArgumentException();
		}
		int prevRow = -1;
		for (int col = 0; col < width(); col++) {
			int row = seam[col];
			if (row < 0 || row >= height()) {
				throw new IllegalArgumentException();
			}
			if (prevRow >= 0 && Math.abs(row - prevRow) > 1) {
				throw new IllegalArgumentException();
			}
			for (int i = row; i < height() - 1; i++) {
				RGB[i][col] = RGB[i + 1][col];
				energy[i][col] = energy[i + 1][col];
			}
			prevRow = row;
		}
		this.height--;
		for (int col = 0; col < width(); col++) {
			int row = seam[col];
			if (row - 1 >= 0) {
				energy[row - 1][col] = energy(col, row - 1);
			}
			if (row < height()) {
				energy[row][col] = energy(col, row);
			}
		}
		hSeam = null;
		vSeam = null;
	}
	// remove vertical seam from current picture
	public void removeVerticalSeam(int[] seam) {
		if (seam == null || seam.length != height()) {
			throw new IllegalArgumentException();
		}
		if (width() <= 1) {
			throw new IllegalArgumentException();
		}
		int prevCol = -1;
		for (int row = 0; row < height(); row++) {
			int col = seam[row];
			if (col < 0 || col >= width()) {
				throw new IllegalArgumentException();
			}
			if (prevCol >= 0 && Math.abs(col - prevCol) > 1) {
				throw new IllegalArgumentException();
			}
			for (int i = col; i < width() - 1; i++) {
				RGB[row][i] = RGB[row][i + 1];
				energy[row][i] = energy[row][i + 1];
			}
			prevCol = col;
		}
		this.width--;
		for (int row = 0; row < height(); row++) {
			int col = seam[row];
			if (col - 1 >= 0) {
				energy[row][col - 1] = energy(col - 1, row);
			}
			if (col < width()) {
				energy[row][col] = energy(col, row);
			}
		}
		hSeam = null;
		vSeam = null;
	}
	
	private void relax(int fromCol, int fromRow, int toCol, int toRow, int[] parent, double[] distance) {
		if (toCol < 0 || toCol >= width() || toRow < 0 || toRow > height() || (toRow == height() && toCol != 0)) {
			return;
		}
		double energyVal;
		if (toRow == height() && toCol == 0) {
			energyVal = 0;
		} else {
			energyVal = energy[toRow][toCol];
		}
		if (distance[fromRow * width() + fromCol] + energyVal < distance[toRow * width() + toCol]) {
			distance[toRow * width() + toCol] = distance[fromRow * width() + fromCol] + energyVal;
			parent[toRow * width() + toCol] = fromRow * width() + fromCol;
		}
	}
	
	private int getRed(int RGB) {
        return (RGB >> 16) & 0xFF;
    }

    private int getGreen(int RGB) {
        return (RGB >> 8) & 0xFF;
    }

    private int getBlue(int RGB) {
        return (RGB >> 0) & 0xFF;
    }
}
