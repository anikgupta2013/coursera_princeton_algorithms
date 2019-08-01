import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.MinPQ;
import edu.princeton.cs.algs4.Stack;
import edu.princeton.cs.algs4.StdOut;

public class Solver {
	private Stack<Board> solution = new Stack<Board>(); // Stack that stores solution
	private boolean solvable = true; // is this puzzle solvable
	private int moves = 0; // number of moves taken to solve the puzzle
    public Solver(Board initial)  // find a solution to the initial board (using the A* algorithm)
    {
    	if (initial == null) { // make sure initial is not null
    		throw new java.lang.IllegalArgumentException();
    	}
    	MinPQ<SearchNode> pq = new MinPQ<SearchNode>(); // MinPQ for the board
    	MinPQ<SearchNode> pqt = new MinPQ<SearchNode>(); // MinPQ for twin board to check if original board is even possible
    	pq.insert(new SearchNode(initial)); // insert initial board and its twin in respective MinPQ's
    	pqt.insert(new SearchNode(initial.twin()));
    	
    	SearchNode removed = pq.delMin(); // remove min in both
    	SearchNode removedt = pqt.delMin();
    	
    	while(!removed.getBoard().isGoal() && !removedt.getBoard().isGoal()) { // while neither puzzle is solved
    		moves++;
	    	for (Board neighbor : removed.getBoard().neighbors()) { // find all neighbors of the removed board
	    		if (removed.getPred() == null || !neighbor.equals(removed.getPred().getBoard())) { // make sure neighbor is not the preceding board
	    			pq.insert(new SearchNode(neighbor, removed));
	    		}
	    	}
	    	for (Board neighbor : removedt.getBoard().neighbors()) { // find all neighbors of the removed twin board
	    		if (removedt.getPred() == null || !neighbor.equals(removedt.getPred().getBoard())) { // make sure neighbor is not the preceding twin board
	    			pqt.insert(new SearchNode(neighbor, removedt));
	    		}
	    	}
	    	removed = pq.delMin();
	    	removedt = pqt.delMin();
    	}
    	moves = -1;
    	if (removedt.getBoard().isGoal()) { // if the twin board is solved first, meaning original board is not solvable
    		solvable = false; 
    		solution = null;
    	} 
    	else{
    		while (removed != null) {  // if board is solvable, form stack of solution path
	    		solution.push(removed.getBoard());
	    		removed = removed.getPred();
	    		moves++;
    		}
    	}    	
    }
    public boolean isSolvable() {  return solvable; } // is the initial board solvable?
    public int moves() { return moves; } // min number of moves to solve initial board; -1 if unsolvable    
    public Iterable<Board> solution() { return solution; } // sequence of boards in a shortest solution; null if unsolvable
    
    public static void main(String[] args) // solve a slider puzzle (given below)
    {
	    // create initial board from file
	    In in = new In(args[0]);
	    int n = in.readInt();
	    int[][] blocks = new int[n][n];
	    for (int i = 0; i < n; i++)
	        for (int j = 0; j < n; j++) {
	            blocks[i][j] = in.readInt();
	        }
	    Board initial = new Board(blocks);

	    // solve the puzzle
	    Solver solver = new Solver(initial);

	    // print solution to standard output
	    if (!solver.isSolvable())
	        StdOut.println("No solution possible");
	    else {
	        StdOut.println("Minimum number of moves = " + solver.moves());
	        for (Board board : solver.solution())
	            StdOut.println(board);
	    }
    }
    private class SearchNode implements Comparable<SearchNode> {
    	private Board b; // current board
    	private int moves = 0; // moves taken
    	private SearchNode pred; // previous SearchNode
    	private int manhattan; // manhattan value of this board
    	private int priority; // hamming priority of this board
    	public SearchNode(Board b) {
    		this.b = b;
    		this.pred = null;
    		manhattan = b.manhattan();
    		priority = b.hamming();
    	}
    	public SearchNode(Board b, SearchNode pred) {
    		this.b = b;
    		this.moves = pred.moves + 1;
    		this.pred = pred;
    		manhattan = b.manhattan();
    		priority = b.hamming();
    	}
    	public String toString() { return b.toString(); }    	
    	public Board getBoard() { return b; }
    	public int getMoves() { return moves; }
    	public SearchNode getPred() { return pred; }
    	public int getManhattan() { return manhattan; }
    	public int getPriority() { return priority; }

		@Override
		public int compareTo(SearchNode o) { return (this.manhattan - o.getManhattan()) + (this.getMoves() - o.getMoves()); }
    }
}