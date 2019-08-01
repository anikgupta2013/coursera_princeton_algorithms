import edu.princeton.cs.algs4.Point2D;
import edu.princeton.cs.algs4.Queue;
import edu.princeton.cs.algs4.RectHV;
import edu.princeton.cs.algs4.SET;
import edu.princeton.cs.algs4.StdDraw;
public class PointSET {
	private SET<Point2D> pset = new SET<Point2D>();
	public PointSET() { }                             // construct an empty set of points 

	public boolean isEmpty() { return pset.isEmpty(); }    	// is the set empty? 

	public int size() { return pset.size(); }   // number of points in the set 

	public void insert(Point2D p) {             		// add the point to the set (if it is not already in the set)
	   if (p == null) throw new java.lang.IllegalArgumentException();
	   if (!pset.contains(p)) pset.add(p);
	}
	public boolean contains(Point2D p) {            	// does the set contain point p? 
		if (p == null) throw new java.lang.IllegalArgumentException();
		return pset.contains(p);
	}
	public void draw() {                         	// draw all points to standard draw 
		StdDraw.clear();
        StdDraw.setPenColor(StdDraw.BLACK);
        StdDraw.setPenRadius(0.01);
        for (Point2D p : pset) {
        	StdDraw.filledCircle(p.x(), p.y(), 0.01);
        }
        StdDraw.show();
	}
	public Iterable<Point2D> range(RectHV rect) {    // all points that are inside the rectangle (or on the boundary) 
		if (rect == null) throw new java.lang.IllegalArgumentException();
		if (pset.isEmpty()) return null;
		Queue<Point2D> intersect = new Queue<Point2D>();
		for (Point2D p : pset) if(rect.contains(p)) intersect.enqueue(p);
		return intersect;
	}
	public Point2D nearest(Point2D p) {             	// a nearest neighbor in the set to point p; null if the set is empty 
		if (p == null) throw new java.lang.IllegalArgumentException();
		if (pset.isEmpty()) return null;
		Point2D closest = pset.max();
		double distance = closest.distanceSquaredTo(p);
		double d = 0;
		for (Point2D p1 : pset) {
			d = p1.distanceSquaredTo(p);
			if (d < distance) {
				distance = d;
				closest = p1;
			}
		}
		return closest;
	}

	public static void main(String[] args) {           // unit testing of the methods (optional) 
		
	}
   
}