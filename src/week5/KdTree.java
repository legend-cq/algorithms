package week5;

import edu.princeton.cs.algs4.Point2D;
import edu.princeton.cs.algs4.Queue;
import edu.princeton.cs.algs4.RectHV;
import edu.princeton.cs.algs4.StdDraw;

public class KdTree {
	private class Node {
		private Point2D point;
		private int height;
		private RectHV rect;
		private Node left;
		private Node right;
		
		public Node(Point2D point) {
			this.point = point;
		}
	}
	
	private int size = 0;
	private Node root = null;
	
	public KdTree() {
		
	} 
	public boolean isEmpty() {
		return size == 0;
	}
	public int size() {
		return size;
	}
	 // add the point to the set (if it is not already in the set)
	public void insert(Point2D p) {
		if(this.contains(p)) {
			return;
		}
		
		root = insert(root,p);
		if(root.rect == null) {
			root.height = 0;
			RectHV rect = new RectHV(0.0,0.0,1.0,1.0);
			root.rect = rect;
		}
	}
	
	private Node insert(Node node, Point2D p) {
		if(node == null) {
			size++;
			return new Node(p);
		} else {
			if(node.height%2 == 0) {
				if(p.x() < node.point.x()) {
					node.left = insert(node.left,p);
					if(node.left.rect == null) {
						RectHV newRect = new RectHV(node.rect.xmin(),node.rect.ymin(),node.point.x(),node.rect.ymax());
						node.left.rect = newRect;
						node.left.height = node.height + 1;
					}
				} else {
					node.right = insert(node.right,p);
					if(node.right.rect == null) {
						RectHV newRect = new RectHV(node.point.x(),node.rect.ymin(),node.rect.xmax(),node.rect.ymax());
						node.right.rect = newRect;
						node.right.height = node.height + 1;
					}
				}
			} else {
				if(p.y() < node.point.y()) {
					node.left = insert(node.left,p);
					if(node.left.rect == null) {
						RectHV newRect = new RectHV(node.rect.xmin(),node.rect.ymin(),node.rect.xmax(),node.point.y());
						node.left.rect = newRect;
						node.left.height = node.height + 1;
					}
				} else {
					node.right = insert(node.right,p);
					if(node.right.rect == null) {
						RectHV newRect = new RectHV(node.rect.xmin(),node.point.y(),node.rect.xmax(),node.rect.ymax());
						node.right.rect = newRect;
						node.right.height = node.height + 1;
					}
				}
			}
			return node;
		}
	}
	// does the set contain point p?
	public boolean contains(Point2D p) {
		return contains(root,p);
	}            
	private boolean contains(Node node, Point2D p) {
		if(node == null) {
			return false;
		} else {
			if(node.point.equals(p)) {
				return true;
			} else {
				if(node.height%2 == 0) {
					if(p.x() < node.point.x()) {
						return contains(node.left,p);
					} else {
						return contains(node.right,p);
					}
				} else {
					if(p.y() < node.point.y()) {
						return contains(node.left,p);
					} else {
						return contains(node.right,p);
					}
				}
			}
		}
	}
	public void draw() {
		draw(root);
	}                        
	private void draw(Node node) {
		if(node == null) {
			return;
		} else {
			RectHV rect = node.rect;
			StdDraw.setPenColor(StdDraw.BLACK);
			StdDraw.setPenRadius(0.004);
			node.point.draw();
			if(node.height%2 == 0) {
				StdDraw.setPenColor(StdDraw.RED);
				StdDraw.setPenRadius();
				StdDraw.line(node.point.x(), rect.ymin(), node.point.x(), rect.ymax());
			} else {
				StdDraw.setPenColor(StdDraw.BLUE);
				StdDraw.setPenRadius();
				StdDraw.line(rect.xmin(), node.point.y(), rect.xmax(), node.point.y());
			}
			draw(node.left);
			draw(node.right);
		}
	}
	public Iterable<Point2D> range(RectHV rect) {
		Queue<Point2D> queue = new Queue<Point2D>();
		range(root,rect,queue);
		return queue;
	}
	private void range(Node node, RectHV rect, Queue<Point2D> queue) {
		if(node == null) {
			return;
		} else {
			if(rect.intersects(node.rect)) {
				if(rect.contains(node.point)) {
					queue.enqueue(node.point);
				}
				range(node.left,rect,queue);
				range(node.right,rect,queue);
			} else {
				return;
			}
		}
	}
	// a nearest neighbor in the set to point p; null if the set is empty
	public Point2D nearest(Point2D p) {
		return nearest(root,p,3.0);
	}
	private Point2D nearest(Node node, Point2D p,double distance) {
		Point2D point = null;
		if(node == null) {
			return null;
		} else {
			if(node.rect.distanceSquaredTo(p) < distance) {
				if(node.point.distanceSquaredTo(p) < distance) {
					distance = node.point.distanceSquaredTo(p);
					point = node.point;
				}
				boolean isLeft = false;
				if(node.height%2 == 0) {
					if(p.x() < node.point.x()) {
						isLeft = true;
					}
				} else {
					if(p.y() < node.point.y()) {
						isLeft = true;
					}
				}
				if(isLeft) {
					Point2D leftPoint = nearest(node.left,p,distance);
					if(leftPoint != null && leftPoint.distanceSquaredTo(p) < distance) {
						distance = leftPoint.distanceSquaredTo(p);
						point = leftPoint;
					}
					Point2D rightPoint = nearest(node.right,p,distance);
					if(rightPoint != null && rightPoint.distanceSquaredTo(p) < distance) {
						distance = rightPoint.distanceSquaredTo(p);
						point = rightPoint;
					}
				} else {
					Point2D rightPoint = nearest(node.right,p,distance);
					if(rightPoint != null && rightPoint.distanceSquaredTo(p) < distance) {
						distance = rightPoint.distanceSquaredTo(p);
						point = rightPoint;
					}
					Point2D leftPoint = nearest(node.left,p,distance);
					if(leftPoint != null && leftPoint.distanceSquaredTo(p) < distance) {
						distance = leftPoint.distanceSquaredTo(p);
						point = leftPoint;
					}
				}
				return point;
			} else {
				return null;
			}
		}
	}
	
	public static void main(String[] args) {
		KdTree tree = new KdTree();
		tree.insert(new Point2D(1.0,0.0));
		System.out.println(tree.nearest(new Point2D(0.0,1.0)));
	}
}
