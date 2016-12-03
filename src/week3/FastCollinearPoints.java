package week3;

import java.util.ArrayList;
import java.util.Arrays;

import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdDraw;
import edu.princeton.cs.algs4.StdOut;

public class FastCollinearPoints {
	private int nSegment = 0;
	private ArrayList<LineSegment> segments = new ArrayList<LineSegment>();
	public FastCollinearPoints(Point[] points) {
		Point[] iPoints = new Point[points.length];
		System.arraycopy(points, 0, iPoints, 0, points.length);
		Arrays.sort(iPoints);
		for(int i = 0; i <= iPoints.length - 2;i++) {
			if(iPoints[i].compareTo(iPoints[i+1]) == 0) {
				throw new IllegalArgumentException();
			}
		}
		for(int i = 0; i <= iPoints.length-4;i++) {
			Point[] otherPoints = new Point[iPoints.length-1];
			for(int j = 0;j <= iPoints.length - 2;j++) {
				if(j < i) {
					otherPoints[j] = iPoints[j];
				} else if(j >= i) {
					otherPoints[j] = iPoints[j+1];
				}
			}
			Arrays.sort(otherPoints,iPoints[i].slopeOrder());
			int equalCount = 0;
			double slope = iPoints[i].slopeTo(otherPoints[0]);
			for(int k = 1;k < otherPoints.length;k++) {
				if(slope == iPoints[i].slopeTo(otherPoints[k])) {
					equalCount++;
				} else {
					if(equalCount >= 2) {
						if(iPoints[i].compareTo(otherPoints[k-equalCount-1]) < 0) {
							nSegment++;
							segments.add(new LineSegment(iPoints[i],otherPoints[k-1]));
						}
					}
					slope = iPoints[i].slopeTo(otherPoints[k]);
					equalCount = 0;
				}
			}
			if(equalCount >= 2) {
				if(iPoints[i].compareTo(otherPoints[otherPoints.length-equalCount-1]) < 0) {
					nSegment++;
					segments.add(new LineSegment(iPoints[i],otherPoints[otherPoints.length-1]));
				}
			}
		}
	}
	public	int numberOfSegments() {
		return nSegment;
	}
	public	LineSegment[] segments() {
		return segments.toArray(new LineSegment[segments.size()]);
	}
	
	public static void main(String[] args) {

	    // read the n points from a file
	    In in = new In(args[0]);
	    int n = in.readInt();
	    Point[] points = new Point[n];
	    for (int i = 0; i < n; i++) {
	        int x = in.readInt();
	        int y = in.readInt();
	        points[i] = new Point(x, y);
	    }

	    // draw the points
	    StdDraw.enableDoubleBuffering();
	    StdDraw.setXscale(0, 32768);
	    StdDraw.setYscale(0, 32768);
	    for (Point p : points) {
	        p.draw();
	    }
	    StdDraw.show();

	    // print and draw the line segments
	    FastCollinearPoints collinear = new FastCollinearPoints(points);
	    for (LineSegment segment : collinear.segments()) {
	        StdOut.println(segment);
	        segment.draw();
	    }
	    StdDraw.show();
	}
}
