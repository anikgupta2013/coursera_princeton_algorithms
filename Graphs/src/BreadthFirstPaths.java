import java.util.LinkedList;
import java.util.Queue;
import java.util.Stack;

/**
 *  The {@code BreadthFirstPaths} class represents a data type for finding
 *  shortest paths (number of edges) from a source vertex <em>s</em>
 *  (or a set of source vertices)
 *  to every other vertex in an undirected graph.
 *  <p>
 *  This implementation uses breadth-first search.
 *  The constructor takes time proportional to <em>V</em> + <em>E</em>,
 *  where <em>V</em> is the number of vertices and <em>E</em> is the number of edges.
 *  Each call to {@link #distTo(int)} and {@link #hasPathTo(int)} takes constant time;
 *  each call to {@link #pathTo(int)} takes time proportional to the length
 *  of the path.
 *  It uses extra space (not including the graph) proportional to <em>V</em>.
 *  <p>
 *  For additional documentation,
 *  see <a href="https://algs4.cs.princeton.edu/41graph">Section 4.1</a>   
 *  of <i>Algorithms, 4th Edition</i> by Robert Sedgewick and Kevin Wayne.
 *
 *  @author Anik Gupta
*/
public class BreadthFirstPaths implements PathsInterface {
    private static final int INFINITY = Integer.MAX_VALUE;
	private boolean[] marked; // marked[v] will be true if v connected to s
	private int[] edgeTo; // edgeTo[v] = previous vertex on path from s to v (not necessarily shortest)
	private int[] distTo; // distTo[v] = minimum number of steps from s to v (-1 if not possible)
	
	
	/**
     * Computes the shortest path between the source vertex {@code s}
     * and every other vertex in the graph {@code G}.
     * @param G the graph
     * @param s the source vertex
     * @throws IllegalArgumentException unless {@code 0 <= s < V}
     */
	public BreadthFirstPaths(GraphInterface G, int s){
		/* Initialize data structures */
		marked = new boolean[G.V()];
		edgeTo = new int[G.V()];
		distTo = new int[G.V()];
		validateVertex(s);
		/* Do BFS - using queue - finds shortest path from s to all other vertices in time proportional to E + V */
		bfs(G, s);
	}
	

    /**
     * Computes the shortest path between any one of the source vertices in {@code sources}
     * and every other vertex in graph {@code G}.
     * @param G the graph
     * @param sources the source vertices
     * @throws IllegalArgumentException unless {@code 0 <= s < V} for each vertex
     *         {@code s} in {@code sources}
     */
    public BreadthFirstPaths(GraphInterface G, Iterable<Integer> sources) {
        marked = new boolean[G.V()];
        distTo = new int[G.V()];
        edgeTo = new int[G.V()];
        for (int v = 0; v < G.V(); v++)
            distTo[v] = INFINITY;
        validateVertices(sources);
        bfs(G, sources);
    }
	
    // breadth-first search from a single source
	private void bfs(GraphInterface G, int s) {
		Queue<Integer> q = new LinkedList<>(); // Queue
		for (int v = 0; v < G.V(); v++) distTo[v] = INFINITY;
		/* Initialize with source */
		q.add(s);
		marked[s] = true;
		distTo[s] = 0;
		
		/* Begin using Queue to iterate */
		while (!q.isEmpty()){ // there are points left to iterate
			int v = q.remove(); // remove the next point
			for (int w : G.adj(v)) { // look at each adjacent vertex
				if (!marked[w]) { // if not marked
					q.add(w); // add to queue
					marked[w] = true; // mark it
					edgeTo[w] = v; // set previous vertex to v
					distTo[w] = distTo[v] + 1; // set distance to distance to previous vertex + 1
				}
			}
		}
	}
	
	// breadth-first search from multiple sources
    private void bfs(GraphInterface G, Iterable<Integer> sources) {
        Queue<Integer> q = new LinkedList<Integer>();
        for (int s : sources) {
            marked[s] = true;
            distTo[s] = 0;
            q.add(s);
        }
        while (!q.isEmpty()) {
            int v = q.remove();
            for (int w : G.adj(v)) {
                if (!marked[w]) {
                    edgeTo[w] = v;
                    distTo[w] = distTo[v] + 1;
                    marked[w] = true;
                    q.add(w);
                }
            }
        }
    }
	
	@Override
	/**
     * Is there a path between the source vertex {@code s} (or sources) and vertex {@code v}?
     * @param v the vertex
     * @return {@code true} if there is a path, and {@code false} otherwise
     * @throws IllegalArgumentException unless {@code 0 <= v < V}
     */
    public boolean hasPathTo(int v) {
        validateVertex(v);
        return marked[v];
    }

	@Override
	/**
     * Returns a shortest path between the source vertex {@code s} (or sources)
     * and {@code v}, or {@code null} if no such path.
     * @param  v the vertex
     * @return the sequence of vertices on a shortest path, as an Iterable
     * @throws IllegalArgumentException unless {@code 0 <= v < V}
     */	public Iterable<Integer> pathTo(int v) {
		if (!hasPathTo(v)) return null;
		Stack<Integer> path = new Stack<Integer>(); // since path is given in reverse order
		int x;
		for (x = v; distTo[x] != 0; x = edgeTo[x]) path.push(x);
		path.push(x);
		return path;
	}
	
	/**
     * Returns the number of edges in a shortest path between the source vertex {@code s}
     * (or sources) and vertex {@code v}?
     * @param v the vertex
     * @return the number of edges in a shortest path
     * @throws IllegalArgumentException unless {@code 0 <= v < V}
     */
    public int distTo(int v) {
        validateVertex(v);
        return distTo[v];
    }

	// throw an IllegalArgumentException unless {@code 0 <= v < V}
    private void validateVertex(int v) {
        int V = marked.length;
        if (v < 0 || v >= V)
            throw new IllegalArgumentException("vertex " + v + " is not between 0 and " + (V-1));
    }

    // throw an IllegalArgumentException unless {@code 0 <= v < V}
    private void validateVertices(Iterable<Integer> vertices) {
        if (vertices == null) {
            throw new IllegalArgumentException("argument is null");
        }
        int V = marked.length;
        for (int v : vertices) {
            if (v < 0 || v >= V) {
                throw new IllegalArgumentException("vertex " + v + " is not between 0 and " + (V-1));
            }
        }
    }
}
