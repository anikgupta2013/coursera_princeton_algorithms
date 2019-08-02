/* Works for Unweighted Undirected Graphs */
public class GraphProcessing {
	/**
	 * 
	 * @param G the graph
	 * @param v the vertex
	 * @return the number of adjacent vertices to v
	 */
	public static int degree(AdjacencyListGraph G, int v){
		int degree = 0;
		for (int w : G.adj(v)) degree++;
		return degree;
	}
	
	/**
	 * 
	 * @param G the graph
	 * @return the most number of adjacent vertices any vertex in G has
	 */
	public static int maxDegree(AdjacencyListGraph G){
		int max = 0;
		for (int v = 0; v < G.V(); v++)
			if (degree(G, v) > max)
				max = degree(G, v);
		return max;
	}
	
	/**
	 * 
	 * @param G the graph
	 * @return the average number of edges the vertices in G have
	 */
	public static double averageDegree(AdjacencyListGraph G)
	{ return 2.0 * G.E() / G.V(); }
	
	/**
	 * 
	 * @param G the graph
	 * @return the number of self-loops that exist in G
	 */
	public static int numberOfSelfLoops(AdjacencyListGraph G){
		int count = 0;
		for (int v = 0; v < G.V(); v++)
			for (int w : G.adj(v))
				if (v == w) count++;
		return count/2; // each edge counted twice
	}
	
	/**
	 * Graph Processing Challenge #1 - 
	 * @param G the graph
	 * @return is the graph bipartite
	 */
	public static boolean isBipartite(AdjacencyListGraph G){
		return true;
	}
}
