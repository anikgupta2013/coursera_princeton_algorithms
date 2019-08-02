import java.util.Stack;

/**
 *  The {@code DepthFirstPaths} class represents a data type for finding
 *  paths from a source vertex <em>s</em> to every other vertex
 *  in an undirected graph.
 *  <p>
 *  This implementation uses depth-first search.
 *  The constructor takes time proportional to <em>V</em> + <em>E</em>,
 *  where <em>V</em> is the number of vertices and <em>E</em> is the number of edges.
 *  Each call to {@link #hasPathTo(int)} takes constant time;
 *  each call to {@link #pathTo(int)} takes time proportional to the length
 *  of the path.
 *  It uses extra space (not including the graph) proportional to <em>V</em>.
 *  <p>
 *  For additional documentation, see <a href="https://algs4.cs.princeton.edu/41graph">Section 4.1</a>   
 *  of <i>Algorithms, 4th Edition</i> by Robert Sedgewick and Kevin Wayne.
 *
 *  @author Anik Gupta
 */

public class DepthFirstPaths implements PathsInterface{
	private boolean[] marked; // marked[v] will be true if v connected to s
	private int[] edgeTo; // edgeTo[v] = previous vertex on path from s to v (not necessarily shortest)
	private int s; // source/starting vertex;

	/**
     * Computes a path between {@code s} and every other vertex in graph {@code G}.
     * @param G the graph
     * @param s the source vertex
     * @throws IllegalArgumentException unless {@code 0 <= s < V}
     */
	public DepthFirstPaths(GraphInterface G, int s){
		/* Initialize data structures */	
		this.s = s;
		marked = new boolean[G.V()];
		edgeTo = new int[G.V()];
		validateVertex(s);
		
		/* Do DFS - using stack - marks all vertices connected to s in time proportional to sum of their degrees */
		dfs(G, s);		
	}
	
	private void dfs(GraphInterface G, int v){
		marked[v] = true; // vertex v has been visited
		for (int w : G.adj(v)) // iterate through each adjacent vertex
			if (!marked[w]) { // if it hasn't been visited yet
				edgeTo[w] = v; // the last node on route to w was v
				dfs(G, w); // DFS it
			}		
	}
	
	@Override
	/**
	 *  Is there a path from source vertex {@code s} to vertex {@code v}? - done in constant time
	 * @param v destination vertex from s (source passed in constructor)
	 * @return {@code true} if there is a path from s to v, {@code false} otherwise
	 * @throws IllegalArgumentException unless {@code 0 <= v < V}
	 */
	public boolean hasPathTo(int v) { 
		validateVertex(v);
		return marked[v]; 
	}

	@Override
	/**
     * Returns a path between the source vertex {@code s} and vertex {@code v}, or
     * {@code null} if no such path.
     * @param  v the vertex
     * @return the sequence of vertices on a path between the source vertex
     *         {@code s} and vertex {@code v}, as an Iterable
     * @throws IllegalArgumentException unless {@code 0 <= v < V}
     */
	public Iterable<Integer> pathTo(int v) {
		validateVertex(v);
		if (!hasPathTo(v)) return null;
		Stack<Integer> path = new Stack<Integer>(); // since path is givern in reverse order
		for (int x = v; x != s; x = edgeTo[x]) path.push(x);
		path.push(s);
		return path;
	}
	
	// throw an IllegalArgumentException unless {@code 0 <= v < V}
    private void validateVertex(int v) {
        int V = marked.length;
        if (v < 0 || v >= V)
            throw new IllegalArgumentException("vertex " + v + " is not between 0 and " + (V-1));
    }
}
