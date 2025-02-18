import edu.princeton.cs.algs4.Digraph;

public class SAP {
	private Digraph G;
	// constructor takes a digraph (not necessarily a DAG)
	public SAP(Digraph G) {
		if (G == null) throw new java.lang.IllegalArgumentException();
		this.G = new Digraph(G);
	}

	// length of shortest ancestral path between v and w; -1 if no such path
	public int length(int v, int w) {
		checkValidVertex(v);
		checkValidVertex(w);
	}

	
	// a common ancestor of v and w that participates in a shortest ancestral path; -1 if no such path
	public int ancestor(int v, int w) {
		checkValidVertex(v);
		checkValidVertex(w);
	}

	// length of shortest ancestral path between any vertex in v and any vertex in w; -1 if no such path
	public int length(Iterable<Integer> v, Iterable<Integer> w) {
		if (v == null || w == null) throw new java.lang.IllegalArgumentException();
		int minPath = Integer.MAX_VALUE;
		for (Integer i : v) {
			for (Integer k : w) {
				minPath = Math.min(minPath, length(i, k));
			}
		}
		return minPath;
	}

	// a common ancestor that participates in shortest ancestral path; -1 if no such path
	public int ancestor(Iterable<Integer> v, Iterable<Integer> w) {
		if (v == null || w == null) throw new java.lang.IllegalArgumentException();
		int minPath = Integer.MAX_VALUE;
		int ancestor = -1;
		for (Integer i : v) {
			for (Integer k : w) {
				if (i == null || k == null) throw new java.lang.IllegalArgumentException();
				if (length(i, k) < minPath) ancestor = ancestor(i, k); // may need to fix
			}
		}
		return ancestor;
	}

	// do unit testing of this class
	public static void main(String[] args) {}
	
	private void checkValidVertex(int v) {
		if (v < 0 || v >= G.V()) throw new java.lang.IllegalArgumentException();
	}
}
