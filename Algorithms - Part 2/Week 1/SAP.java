// Shortest ancestral path. An ancestral path between two vertices v and w in a digraph 
// is a directed path from v to a common ancestor x, together with a directed path from 
// w to the same ancestor x. A shortest ancestral path is an ancestral path of minimum 
// total length.

public class SAP 
{
	// All methods should throw a java.lang.IndexOutOfBoundsException if one 
	// (or more) of the input arguments is not between 0 and G.V() - 1
	//
	// Assume that the iterable arguments contain at least one integer. 
	// All methods (and the constructor) should take time at most proportional 
	// to E + V in the worst case, where E and V are the number of edges and vertices 
	// in the digraph, respectively. Your data type should use space proportional to E + V.

	// constructor takes a digraph (not necessarily a DAG)
	public SAP(Digraph G)
	{
	}

	// length of shortest ancestral path between v and w; -1 if no such path
	public int length(int v, int w)
	{
	}

	// a common ancestor of v and w that participates in a shortest ancestral path; -1 if no such path
	public int ancestor(int v, int w)
	{
	}

	// length of shortest ancestral path between any vertex in v and any vertex in w; -1 if no such path
	public int length(Iterable<Integer> v, Iterable<Integer> w)
	{
	}

	// a common ancestor that participates in shortest ancestral path; -1 if no such path
	public int ancestor(Iterable<Integer> v, Iterable<Integer> w)
	{
	}

	// for unit testing of this class (such as the one below)
	public static void main(String[] args)
	{
	}
};
