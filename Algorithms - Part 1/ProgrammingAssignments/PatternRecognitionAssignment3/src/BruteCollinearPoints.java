import java.util.ArrayList;

public class BruteCollinearPoints {
	private Point[] points;
	private LineSegment[] segments;
	private int numSegments = 0;
	
	public BruteCollinearPoints(Point[] pts){
		// finds all line segments containing 4 points
		if(pts == null) throw new java.lang.IllegalArgumentException(); // Initial checks, no null values or duplicates
		points = new Point[pts.length];
		points = pts.clone(); // ensure immutability
		for(int i = 0; i < pts.length; i++){
			Point p = pts[i];
			if(p == null) throw new java.lang.IllegalArgumentException();
			for(int j = 0; j < i; j++){
				if(points[j].compareTo(p) == 0) throw new java.lang.IllegalArgumentException();
			}
		}
		
		/* Iteration */
		ArrayList<LineSegment> segs = new ArrayList<LineSegment>();
		for(int i = 0; i < points.length; i++){
			Point iP = points[i]; // First point
			for(int j = i+1; j < points.length; j++){
				Point jP = points[j]; // Second point
				for(int k = j+1; k < points.length; k++){
					Point kP = points[k]; // Third point
					for(int l = k+1; l < points.length; l++){
						Point lP = points[l]; // Fourth point
						if(iP.slopeTo(jP) == iP.slopeTo(kP) && iP.slopeTo(jP) == iP.slopeTo(lP)){ // if all have the same slope
							numSegments++; // Add segment
							Point less, big;
							less = min(iP, min(jP, min(kP, lP)));
							big  = max(iP, max(jP, max(kP, lP)));
							segs.add(new LineSegment(less, big));
						}
					}
				}
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
		return segments.clone();
	}
	
	private Point min(Point a, Point b){
		if(a.compareTo(b) < 0) return a;
		return b;
	}
	
	private Point max(Point a, Point b){
		if(a.compareTo(b) > 0) return a;
		return b;
	}
}