import java.util.ArrayList;
import java.util.Arrays;

public class FastCollinearPoints {
	private Point[] points;
	private LineSegment[] segments;
	private int numSegments = 0;

	public FastCollinearPoints(Point[] pts){
		// finds all line segments containing 4 or more points
		if(pts == null) throw new java.lang.IllegalArgumentException();
		points = new Point[pts.length];
		points = pts.clone(); // ensure immutability
		for(int i = 0; i < pts.length; i++){ // make sure no duplicate points and no null points
			Point p = pts[i];
			if(p == null) throw new java.lang.IllegalArgumentException();
			for(int j = 0; j < i; j++){
				if(points[j].compareTo(p) == 0) throw new java.lang.IllegalArgumentException();
			}
		}
		ArrayList<LineSegment> segs = new ArrayList<LineSegment>(); // ArrayList of segments
		Point[] ps = new Point[points.length];	// List of points that would be sorted
		ps = points.clone();
		for (int i = 0; i < points.length; i++){
			Point p = points[i]; // Take the point
			Arrays.sort(ps); // sort based on point compareTo
			Arrays.sort(ps, p.slopeOrder()); // stable sort based on the slopes relative to point p
			
			/* Initialization */
			double slope = Double.MAX_VALUE;
			int count = 0;
			boolean skip = false;
			
			// Look through all points except the first one (which is p because its slope to itself is -inf
			for (int j = 1; j < ps.length; j++){
				double s = p.slopeTo(ps[j]); // store current slope in s
				if (j == 1) slope = s; // if first point, slope is s
					if (s != slope){ // if we have found the end of a set of points with the same slope
						if(!skip && count > 2) { // make sure that p is the minimum endpoint (!skip) and has enough points
							numSegments++; // new segment
							segs.add(new LineSegment(p, ps[j-1]));
						}
						/* reinitialize */
						count = 0;
						slope = s;
						skip = false;
					}
					else if (j == ps.length-1 && !skip && count+1 > 2 && p.compareTo(ps[j]) <= 0){ 
						// if the last set of points with the same slope ends with the end of the array
						numSegments++; // add the segment
						segs.add(new LineSegment(p, ps[j]));
					}
				if (p.compareTo(ps[j]) > 0) { skip = true; }// skip remaining points with same slope if p is not the minimum point in segment
				count++; // count of points in the segment++
			}
		}
		segments = new LineSegment[numSegments];
		segments = segs.toArray(segments);
	}
	public int numberOfSegments(){
		// the number of line segments
		return numSegments;
	}
	public LineSegment[] segments(){
		// the line segments
		LineSegment[] s = new LineSegment[segments.length];
		s = segments.clone();
		return s;
	}
}