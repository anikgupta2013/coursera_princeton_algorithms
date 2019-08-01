import edu.princeton.cs.algs4.Point2D;
import edu.princeton.cs.algs4.Queue;
import edu.princeton.cs.algs4.RectHV;
import edu.princeton.cs.algs4.StdDraw;
public class KdTree {
	private Node root;
	private int size = 0;

	public KdTree() {}                             // construct an empty set of points 

	public boolean isEmpty() { return size == 0;}   // is the set empty? 
	public int size() { return size;}               // number of points in the set 
	
	public void insert(Point2D p) {                // add the point to the set (if it is not already in the set)
		if (p == null) throw new java.lang.IllegalArgumentException();
		root = insert(root, p, null);
	}
	private Node insert(Node n, Point2D p, Node parent) {
		if (n == null){ // empty spot available to add node
			size++;
			if (parent == null) return new Node(p, new RectHV(0.0, 0.0, 1.0, 1.0), Node.VERT); // root node added with rectangle(0, 0, 1, 1) and vertical
			else if (parent.getDirection() == Node.VERT) { // Vertical parent => right half rectangle or left half rectangle
				if (parent.getPoint().x() > p.x()) return new Node(p, new RectHV(parent.getRect().xmin(), parent.getRect().ymin(), parent.getPoint().x(), parent.getRect().ymax()), !parent.getDirection());
				else return new Node(p, new RectHV(parent.getPoint().x(), parent.getRect().ymin(), parent.getRect().xmax(), parent.getRect().ymax()), !parent.getDirection());
			}
			else { // Horizontal parent => top half rectangle or bottom half rectangle
				if (parent.getPoint().y() > p.y()) return new Node(p, new RectHV(parent.getRect().xmin(), parent.getRect().ymin(), parent.getRect().xmax(), parent.getPoint().y()), !parent.getDirection());
				else return new Node(p, new RectHV(parent.getRect().xmin(), parent.getPoint().y(), parent.getRect().xmax(), parent.getRect().ymax()), !parent.getDirection());
			}
		}	
		int com = n.compareTo(p);
		if (!n.getPoint().equals(p)) { // if point already doesn't exist
			if (com < 0) n.setLB(insert(n.getLB(), p, n)); // insert on left subtree if < 0
			else n.setRT(insert(n.getRT(), p, n)); // insert on right subtree if > 0
		}
		return n;
	}
	
	public boolean contains(Point2D p) {            	// does the set contain point p? 
		if (p == null) throw new java.lang.IllegalArgumentException();
		Node x = root;
		while (x != null) {
			int com = x.compareTo(p);
			if (x.getPoint().equals(p)) return true; // point found!
			else if (com < 0) x = x.lb; // go to left subtree if compare < 0
			else x = x.rt;				// go to right subtree if compare > 0	
		}
		return false;
	}

	public void draw() {                         	// draw all points to standard draw 
		StdDraw.clear();							// initialization
		StdDraw.setPenColor(StdDraw.RED);
		StdDraw.setPenRadius(0.01);
		Node n = root;
		drawKD(n);
		StdDraw.show();
	}
	private void drawKD(Node n) {
		if (n == null) return;
		StdDraw.setPenColor((n.getDirection() == Node.VERT) ? StdDraw.RED : StdDraw.BLUE); // Vertical = red, horizontal = blue
		if (n.getDirection() == Node.VERT) StdDraw.line(n.getPoint().x(), n.getRect().ymin(), n.getPoint().x(), n.getRect().ymax()); // draw line based on rectangle
		else StdDraw.line(n.getRect().xmin(), n.getPoint().y(), n.getRect().xmax(), n.getPoint().y());
		drawPoint(n.getPoint()); // add point
		drawKD(n.getLB()); // recurse on left subtree
		drawKD(n.getRT()); // recurse on right subtree
	}
	private void drawPoint(Point2D p) { // draw a point
		StdDraw.setPenColor(StdDraw.BLACK);
		StdDraw.setPenRadius(0.01);
		StdDraw.filledCircle(p.x(), p.y(), 0.01);
	}

	public Iterable<Point2D> range(RectHV rect) {    // all points that are inside the rectangle (or on the boundary) 
		if (rect == null) throw new java.lang.IllegalArgumentException();
		Node n = root;
		Queue<Point2D> points = new Queue<Point2D>(); // that will store all points inside the rect
		return range(rect, points, n);
	}
	private Queue<Point2D> range(RectHV rect, Queue<Point2D> points, Node n) {
		if (n == null) { return points; } // done searching
		if (rect.contains(n.getPoint())) { points.enqueue(n.getPoint()); } // point is inside rect
		if (n.getLB() != null && n.getLB().getRect().intersects(rect)) { range(rect, points, n.getLB()); } // if the rect is in the left subtree rectangle, recurse left
		if (n.getRT() != null && n.getRT().getRect().intersects(rect)) { range(rect, points, n.getRT()); } // if the rect is in the right subtree rectangle, recurse right
		return points;
	}
	public Point2D nearest(Point2D p) {             	// a nearest neighbor in the set to point p; null if the set is empty 
		if (p == null) throw new java.lang.IllegalArgumentException();
		NearestPoint np = new NearestPoint(); // the nearest point and distance
		np.pt = null; // initialization
        np.dist = Double.MAX_VALUE;
        nearest(root, np, p);
        return np.pt; // return the point
	}
	private void nearest(Node n, NearestPoint np, Point2D p) {
		if (n == null) return; // no more to recurse
		if (np.pt == null) { // initialize first point
            np.pt = n.getPoint();
            np.dist = p.distanceSquaredTo(n.getPoint());
        } else {
            double dist = p.distanceSquaredTo(n.getPoint());
            if (dist < np.dist) { // if better point is found
                np.pt = n.getPoint();
                np.dist = dist;
            }
        }
		if (n.getDirection() == Node.VERT) {
			if (n.getPoint().x() >= p.x()) {
				nearest(n.getLB(), np, p); // if node is vertical and point is located to the left, go left
				if (n.getRT() != null && n.getRT().getRect().distanceSquaredTo(p) < np.dist) { // if the nearest distance from the right rectangle is less than the min dist, go right
					nearest(n.getRT(), np, p);
				}
			}
			else {
				nearest (n.getRT(), np, p); // if node is vertical and point is located to the right, go right
				if (n.getLB() != null && n.getLB().getRect().distanceSquaredTo(p) < np.dist) { // if the nearest distance from the left rectangle is less than the min dist, go left
					nearest (n.getLB(), np, p);
				}
			}
		}
		else {
			if (n.getPoint().y() >= p.y()) { // if node is horizontal and point is located to the bottom, go bottom
				nearest (n.getLB(), np, p);
				if (n.getRT() != null && n.getRT().getRect().distanceSquaredTo(p) < np.dist) { // if the nearest distance from the top rectangle is less than the min dist, go right
					nearest (n.getRT(), np, p);
				}
			}
			else {
				nearest (n.getRT(), np, p); // if node is horizontal and point is located to the top, go top
				if (n.getLB() != null && n.getLB().getRect().distanceSquaredTo(p) < np.dist){ // if the nearest distance from the bottom rectangle is less than the min dist, go left
					nearest (n.getLB(), np, p);
				}
			}
		}
	}
	private class NearestPoint {
		private Point2D pt;
		private double dist;
	}
	
	public static void main(String[] args) { }           // unit testing of the methods (optional) 

	private static class Node implements Comparable<Point2D> {
		private Point2D p;      // the point
		private RectHV rect;    // the axis-aligned rectangle corresponding to this node
		private Node lb;        // the left/bottom subtree
		private Node rt;        // the right/top subtree
		private boolean direction; // the direction of the node
		public static boolean VERT = true; // vertical
		public static boolean HORI = false; // horizontal
		public Node() {
			p = null;
			rect = null;
			lb = null;
			rt = null;
			direction = VERT;
		}
		public Node(Point2D p) {
			this.p = p;
			rect = null;
			lb = null;
			rt = null;
			direction = VERT;
		}
		public Node(Point2D p, RectHV rect, boolean direction) {
			this.p = p;
			this.rect = rect;
			lb = null;
			rt = null;
			this.direction = direction;
		}
		public Node(Point2D p, RectHV rect, boolean direction, Node lb, Node rt) {
			this.p = p;
			this.rect = rect;
			this.lb = lb;
			this.rt = rt;
			this.direction = direction;
		}
		public Point2D getPoint() { return p; }
		public RectHV getRect() { return rect; }
		public Node getLB() { return lb; }
		public Node getRT() { return rt; }
		public boolean getDirection() { return direction; }

		public void setPoint(Point2D p) { this.p = p; }
		public void setRect(RectHV rect) { this.rect = rect; }
		public void setLB(Node lb) { this.lb = lb; }
		public void setRT(Node rt) { this.rt = rt; }
		public void setDirection(boolean direction) { this.direction = direction; }
		@Override
		public int compareTo(Point2D o) { // -1 if point is left or bottom of node given direction, 0 if same, 1 if right or top
			return (this.getDirection() == Node.HORI) ? 
					((this.getPoint().y() > o.y()) ? -1 : (this.getPoint().y() < o.y()) ? 1 : 0) : 
					((this.getPoint().x() > o.x()) ? -1 : (this.getPoint().x() < o.x()) ? 1 : 0);
		}
	}
}