
public interface PathsInterface {
	
	/**
	 *  is there a path from s to v?
	 * @param v destination vertex from s (source passed in constructor)
	 * @return is there a path from s to v?
	 */
	boolean hasPathTo(int v);
	
	/**
	 * path from s to v; null if no such path
	 * @param v destination vertex from s (source passed in constructor)
	 * @return path from s to v; null if no such path
	 */
	Iterable<Integer> pathTo(int v);
}
