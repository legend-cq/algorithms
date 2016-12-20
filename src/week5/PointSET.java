package week5;

import java.util.TreeSet;

import edu.princeton.cs.algs4.Point2D;
import edu.princeton.cs.algs4.Queue;
import edu.princeton.cs.algs4.RectHV;

public class PointSET {
	private TreeSet<Point2D> treeSet;
	
	public PointSET() {
		treeSet = new TreeSet<Point2D>(); 
	} 
	public boolean isEmpty() {
		return treeSet.isEmpty();
	}
	public int size() {
		return treeSet.size();
	}
	 // add the point to the set (if it is not already in the set)
	public void insert(Point2D p) {
		if(!this.contains(p)) {
			treeSet.add(p);
		}
	}
	 // does the set contain point p?
	public boolean contains(Point2D p) {
		return treeSet.contains(p);
	}            
	public void draw() {
		for (Point2D point2d : treeSet) {
			point2d.draw();
		}
	}                        
	public Iterable<Point2D> range(RectHV rect) {
		Queue<Point2D> queue = new Queue<Point2D>();
		for (Point2D point2d : treeSet) {
			if(rect.contains(point2d)) {
				queue.enqueue(point2d);
			}
		}
		return queue;
	}
	// a nearest neighbor in the set to point p; null if the set is empty
	public Point2D nearest(Point2D p) {
		if(this.isEmpty()) {
			return null;
		}
		Double minDistaince = null;
		Point2D min = null;
		for (Point2D point2d : treeSet) {
			double distance = point2d.distanceSquaredTo(p);
			if(minDistaince == null || distance < minDistaince) {
				minDistaince = distance;
				min = point2d;
			}
		}
		return min;
	} 
	public static void main(String[] args) {
		
	}                  
}
