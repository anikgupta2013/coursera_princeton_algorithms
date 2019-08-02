/**
 *  The {@code ConnectedComponents} class represents a data type for 
 *  determining the connected components in an undirected graph.
 *  The <em>id</em> operation determines in which connected component
 *  a given vertex lies; the <em>connected</em> operation
 *  determines whether two vertices are in the same connected component;
 *  the <em>count</em> operation determines the number of connected
 *  components; and the <em>size</em> operation determines the number
 *  of vertices in the connect component containing a given vertex.

 *  The <em>component identifier</em> of a connected component is one of the
 *  vertices in the connected component: two vertices have the same component
 *  identifier if and only if they are in the same connected component.

 *  <p>
 *  This implementation uses depth-first search.
 *  The constructor takes time proportional to <em>V</em> + <em>E</em>
 *  (in the worst case),
 *  where <em>V</em> is the number of vertices and <em>E</em> is the number of edges.
 *  Afterwards, the <em>id</em>, <em>count</em>, <em>connected</em>,
 *  and <em>size</em> operations take constant time.
 *  <p>
 *  For additional documentation, see <a href="https://algs4.cs.princeton.edu/41graph">Section 4.1</a>   
 *  of <i>Algorithms, 4th Edition</i> by Robert Sedgewick and Kevin Wayne.
 *
 *  @author Anik Gupta
 */
public class ConnectedComponents {
	private boolean marked[];	// marked[v] = has vertex v been marked?
    private int[] id;           // id[v] = id of connected component containing v
    private int[] size;         // size[id] = number of vertices in given component
    private int count;          // number of connected components
	
    /**
     * Computes the connected components of the undirected graph {@code G}.
     *
     * @param G the undirected graph
     */
	public ConnectedComponents(GraphInterface G){
		marked = new boolean[G.V()]; // Initialization
		id = new int[G.V()];
		size = new int[G.V()];
		for (int v = 0; v < G.V(); v++){ // Go through all unmarked vertices and dfs them
			if(!marked[v]){
				dfs(G, v);
				count++; // for each unmarked node after dfs, increase count of connected components
			}
		}
	}
	
	
	/**
	 * the count of components in constant time
	 * @return the count of components
	 */
	public int count() { return count; }
	
	/**
	 *  returns the id of component containing v in constant time
	 * @param v the vertex
	 * @return id of component containing v
	 */
	public int id(int v) { return id[v]; }
	
	/**
     * Returns the number of vertices in the connected component containing vertex {@code v}.
     *
     * @param  v the vertex
     * @return the number of vertices in the connected component containing vertex {@code v}
     * @throws IllegalArgumentException unless {@code 0 <= v < V}
     */
    public int size(int v) {
        validateVertex(v);
        return size[id[v]];
    }
	
	/**
	 * are v and w connected computed in constant time
	 * @param v the first vertex
	 * @param w the second vertex
	 * @return are v and w connected?
	 */
	public boolean connected(int v, int w) { return id[v] == id[w]; }
	
	private void dfs(GraphInterface G, int v){
		marked[v] = true; // vertex v has been visited
		id[v] = count; // the component id = count (starting at 0) - all vertices discovered in same call of dfs have same id
		size[count]++;
		for (int w : G.adj(v)) // iterate through each adjacent vertex
			if (!marked[w]) // if it hasn't been visited yet
				dfs(G, w); // DFS it
	}
	
	// throw an IllegalArgumentException unless {@code 0 <= v < V}
    private void validateVertex(int v) {
        int V = marked.length;
        if (v < 0 || v >= V)
            throw new IllegalArgumentException("vertex " + v + " is not between 0 and " + (V-1));
    }
}
