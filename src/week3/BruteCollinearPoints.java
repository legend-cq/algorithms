package week3;

import java.util.ArrayList;
import java.util.Arrays;

import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdDraw;
import edu.princeton.cs.algs4.StdOut;

public class BruteCollinearPoints {
	private int nSegment = 0;
	private ArrayList<LineSegment> segments = new ArrayList<LineSegment>();
	public BruteCollinearPoints(Point[] points) {
		Point[] iPoints = new Point[points.length];
		System.arraycopy(points, 0, iPoints, 0, points.length);
		Arrays.sort(iPoints);
		for(int i = 0; i <= iPoints.length - 2;i++) {
			if(iPoints[i].compareTo(iPoints[i+1]) == 0) {
				throw new IllegalArgumentException();
			}
		}
		
		for(int i = 0; i <= iPoints.length - 4;i++) {
			for(int j = i + 1; j <= iPoints.length - 3;j++) {
				for(int k = j + 1; k <= iPoints.length - 2;k++) {
					for(int l = k +1; l <= iPoints.length - 1;l++) {
						if(iPoints[l].slopeTo(iPoints[k]) == iPoints[l].slopeTo(iPoints[j])
								&& iPoints[l].slopeTo(iPoints[j]) == iPoints[l].slopeTo(iPoints[i])) {
							nSegment++;
							segments.add(new LineSegment(iPoints[i],iPoints[l]));
						}
					}
				}
			}
		}
	}
	public int numberOfSegments() {
		return nSegment;
	}
	public LineSegment[] segments() {
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
	    BruteCollinearPoints collinear = new BruteCollinearPoints(points);
	    for (LineSegment segment : collinear.segments()) {
	        StdOut.println(segment);
	        segment.draw();
	    }
	    StdDraw.show();
	}
}