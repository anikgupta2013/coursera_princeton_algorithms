import java.util.LinkedList;

public class Board {
	private final int[][] board;
	private int n = 0;
	private int hamming = 0;
	private int manhattan = 0;
	
	public Board(int[][] blocks) {
		// construct a board from an n-by-n array of blocks
		// (where blocks[i][j] = block in row i, column j)
		board = copy(blocks);
		n = blocks.length;
		
		//Calculate hamming and manhattan
		hamming = 0;
		manhattan = 0;
		for (int i = 0; i < n; i++) {
			for (int j = 0; j < n; j++) {
				if (i != n-1 || j != n-1) { // hamming
					if (board[i][j] != (i*n + j + 1)) { // if block is out of place
						hamming++;
					}
				}
				if (board[i][j] != 0) { // manhattan
					int col = (board[i][j] - 1) % n;
					int row = (board[i][j] - 1) / n;
					manhattan += Math.abs(i-row) + Math.abs(j-col); // distance between current position and expected position
				}
			}
		}
	}

	public int dimension() { return n; } // board dimension n
	public int hamming() { return hamming; } // number of blocks out of place
	public int manhattan() { return manhattan; } // sum of Manhattan distances between blocks and goal
	public boolean isGoal() { return hamming == 0; } // is number of elements out of place (hamming) equal to 0?
	public Board twin() { // a board that is obtained by exchanging any pair of blocks
		int[][] twin = copy(board);		
		for (int i = 0; i < n; i++) {
			for (int j = 0; j < n; j++) {
				if (board[i][j] != 0) { // find first non zero element in matrix
					for (int l = i; l < n; l++) {
						for (int k = j + 1; k < n; k++) {
							if (board[l][k] != 0) { // find second non zero element in matrix
								exch(twin, i, j, l, k); // swap both of those elements
								return new Board(twin);
							}
						}
					}
				}
			}
		}
		return new Board(twin);
	}
	public boolean equals(Object y) { return (y == null) ? false : y.toString().equals(this.toString()); } // does this board equal y?
	
	public Iterable<Board> neighbors() { // all neighboring boards
		LinkedList<Board> neighbors = new LinkedList<Board>();
		int[][] neighbor = copy(board);
		int i = 0, j = 0;
		boolean done = false;
	
		for (; i < n; i++) {
			for (j = 0; j < n; j++) {
				if(board[i][j] == 0){ // Find location of space at (i, j)
					done = true;
					break;
				}
			}
			if(done){break;}
		}
		if (j > 0) {
			exch(neighbor, i, j, i, j - 1);
			neighbors.add(new Board(neighbor)); // swap with left if possible
			exch(neighbor, i, j, i, j - 1);//neighbor = copy(board);
		}
		if (i > 0) {
			exch(neighbor, i, j, i - 1, j); // swap with top if possible
			neighbors.add(new Board(neighbor));
			exch(neighbor, i, j, i - 1, j);//neighbor = copy(board);
		}
		if (j < n - 1) {
			exch(neighbor, i, j, i, j + 1); // swap with right if possible
			neighbors.add(new Board(neighbor));
			exch(neighbor, i, j, i, j + 1);//neighbor = copy(board);
		}
		if (i < n - 1){
			exch(neighbor, i, j, i + 1, j); // swap with bottom if possible
			neighbors.add(new Board(neighbor));
			exch(neighbor, i, j, i + 1, j);//neighbor = copy(board);
		}
		return neighbors;
	}
	public String toString() { // string representation of this board (in the output format specified below)
		StringBuilder s = new StringBuilder();
	    s.append(n + "\n");
	    for (int i = 0; i < n; i++) {
	        for (int j = 0; j < n; j++) {
	            s.append(String.format("%2d ", board[i][j]));
	        }
	        s.append("\n");
	    }
	    return s.toString();
	}

	private static void exch(int[][] m, int r1, int c1, int r2, int c2){ // exchange values in m at (r1, c1) and (r2, c2)
		int temp = m[r1][c1];
		m[r1][c1] = m[r2][c2];
		m[r2][c2] = temp;
	}
	
	private static int[][] copy(int[][] m){ // return matrix m in another matrix to keep immutability
		int[][] result = new int[m.length][m[m.length-1].length];
		for (int i = 0; i < m.length; i++) {
			for (int j = 0; j < m[m.length-1].length; j++) {
				result[i][j] = m[i][j];
			}
		}
		return result;
	}
	public static void main(String[] args) {}
}
