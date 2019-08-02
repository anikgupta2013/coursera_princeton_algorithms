
public interface GraphInterface {
	
	/**
	 * adds an edge v-w
	 * @param v first vertex
	 * @param w second vertex
	 */
	void addEdge(int v, int w);

	/**
	 * @param v
	 * @return vertices adjacent to v
	 */
	Iterable<Integer> adj(int v);

	/**
	 * @return number of vertices
	 */
	int V();

	/**
	 * @return number of edges
	 */
	int E();

	/**
	 * @return string representation
	 */
	String toString();

}