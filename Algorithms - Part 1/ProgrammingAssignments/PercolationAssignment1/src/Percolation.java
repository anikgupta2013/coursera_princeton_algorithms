import edu.princeton.cs.algs4.WeightedQuickUnionUF;

public class Percolation {
	private int n; // size
	private int numOpen; // number of open spots
	private boolean[] isOpen; // is spot open
	private boolean[] connectedToTop; // is spot connected to top
	private boolean[] connectedToBottom; // is spot connected to bottom
	private boolean percolates; // does grid percolate
	private WeightedQuickUnionUF uf; // Union Find to store connects
	
	public Percolation(int n) {    // create n-by-n grid, with all sites blocked
		if (n <= 0) throw new java.lang.IllegalArgumentException();
		this.n = n;
		
		uf = new WeightedQuickUnionUF(n*n); // Union Find to store connects

		/* Initialize all to false */
		numOpen = 0;
		isOpen = new boolean[n*n];
		connectedToTop = new boolean[n*n];
		connectedToBottom = new boolean[n*n];
		for (int i = 0; i < n * n; i++) {
			isOpen[i] = false;
			connectedToTop[i] = false;
			connectedToBottom[i] = false;
		}
		percolates = false;
	}
	
	public void open(int row, int col) {   // open site (row, col) if it is not open already
		/* Make sure argument is proper */
		testArg(row, col);
		
		int i = xyTo1D(row, col); // convert from 2D to 1D points from (1, 1) to (n, n) range to (0 to n*n-1)
		
		if (!isOpen[i]) {
			isOpen[i] = true; // set the point to open if not already
			numOpen++;
			
			boolean top = false, bottom = false; // initialize that point is not connected to top or bottom
			
			if (row > 1 && isOpen[i - n]) { // check top neighbor
				// if any element connected (represented by uf.find()) with the neighbor or with the current element is connected to top, top = true;
				// if any element connected (represented by uf.find()) with the neighbor or with the current element is connected to bottom, bottom = true;
				if (connectedToTop[uf.find(i - n)] || connectedToTop[uf.find(i)] ) top = true;
				if (connectedToBottom[uf.find(i - n)] || connectedToBottom[uf.find(i)] ) bottom = true;
				uf.union(i - n, i); // connect neighbor with point
			}
			if (col > 1 && isOpen[i-1]) { // check left neighbor
				// if any element connected (represented by uf.find()) with the neighbor or with the current element is connected to top, top = true;
				// if any element connected (represented by uf.find()) with the neighbor or with the current element is connected to bottom, bottom = true;
				if (connectedToTop[uf.find(i-1)] || connectedToTop[uf.find(i)] ) top = true;
				if (connectedToBottom[uf.find(i-1)] || connectedToBottom[uf.find(i)] ) bottom = true;
				uf.union(i-1, i); // connect neighbor with point
			}
			if (row < n && isOpen[i+n]) { // check bottom neighbor
				// if any element connected (represented by uf.find()) with the neighbor or with the current element is connected to top, top = true;
				// if any element connected (represented by uf.find()) with the neighbor or with the current element is connected to bottom, bottom = true;
				if (connectedToTop[uf.find(i+n)] || connectedToTop[uf.find(i)] ) top = true;
				if (connectedToBottom[uf.find(i+n)] || connectedToBottom[uf.find(i)] ) bottom = true;
				uf.union(i+n, i); // connect neighbor with point
			}
			if (col < n && isOpen[i+1]) { // check right neighbor
				// if any element connected (represented by uf.find()) with the neighbor or with the current element is connected to top, top = true;
				// if any element connected (represented by uf.find()) with the neighbor or with the current element is connected to bottom, bottom = true;
				if (connectedToTop[uf.find(i+1)] || connectedToTop[uf.find(i)] ) top = true;
				if (connectedToBottom[uf.find(i+1)] || connectedToBottom[uf.find(i)] ) bottom = true;
				uf.union(i+1, i); // connect neighbor with point
			}
			
			if (row == 1) top = true; // if point is in top row, connected to top is true
			if (row == n) bottom = true; // if point is in bottom row, connected to bottom is true
			
			connectedToBottom[uf.find(i)] = bottom; // set bottom and top for all connected points using uf.find()
			connectedToTop[uf.find(i)] = top;
			
			if (top && bottom) percolates = true; // if point is connected through top and bottom, system percolates
		}
	}

	public boolean isOpen(int row, int col) {	// is site (row, col) open?
		testArg(row, col);
		return isOpen[xyTo1D(row, col)];
	}
	public boolean isFull(int row, int col) {	// is site (row, col) full? - is it connected to the top?
		testArg(row, col);
		return connectedToTop[uf.find(xyTo1D(row, col))]; // is any point connected to (row, col) connected to the top
	}
	public int numberOfOpenSites() { return numOpen; } // number of open sites
	public boolean percolates() { return percolates; } // does the system percolate?
	private int xyTo1D(int row, int col) { return (row-1)*n - 1 + col; } // converts from 2D to 1D => (1, 1) to (n, n) ==> (0) to (n*n - 1)
	private void testArg(int row, int col) { if (row > n || col > n || row <  1 || col < 1) throw new java.lang.IllegalArgumentException(); } // make sure point is in range
	
	public static void main(String[] args) {   // test client (optional)
//				File input = new File("percolation/input50.txt");
//				
//				try {
//					
//					Scanner in = new Scanner(input);
//					
//					Percolation p = null;
//					if(in.hasNext()){
//						int i =in.nextInt(); 
//						p = new Percolation(i);
//					}else{
//						p = new Percolation(0);
//					}
//					while(in.hasNextInt()){
//						int a = in.nextInt();
//						int b = in.nextInt();
//						p.open(a, b);
//					}
//					System.out.println(p.percolates());
//				} catch (FileNotFoundException e) {
//					e.printStackTrace();
//				}
	}
}




// Initial version with 2 UF variables to prevent backwash

/*public class Percolation {
	private int n;
	private int numOpen = 0;
	private boolean[][] grid;
	//private boolean percolates = false;
	private WeightedQuickUnionUF uf;
	private WeightedQuickUnionUF uf2;
	public Percolation(int n){                // create n-by-n grid, with all sites blocked
		if (n <= 0){
			throw new java.lang.IllegalArgumentException();
		}
		this.n = n;
		uf = new WeightedQuickUnionUF(n*n+2);
		uf2 = new WeightedQuickUnionUF(n*n+2);

		grid = new boolean[n+1][n+1];
		for(int i = 1; i <= n; i++){
			for(int j = 0; j <= n; j++){
				grid[i][j] = false;
			}
		}
	}
	public void open(int row, int col){   // open site (row, col) if it is not open already
		if(row > n || col > n || row <  1 || col < 1){
			throw new java.lang.IllegalArgumentException();
		}
		if(!grid[row][col]){
			grid[row][col] = true;
			numOpen++;

			if(row - 1 > 0){
				if(isOpen(row-1, col)){
					uf.union(xyTo1D(row-1, col), xyTo1D(row, col));
					uf2.union(xyTo1D(row-1, col), xyTo1D(row, col));
				}
			}
			if(col - 1 > 0){
				if(isOpen(row, col-1)){
					uf.union(xyTo1D(row, col-1), xyTo1D(row, col));
					uf2.union(xyTo1D(row, col-1), xyTo1D(row, col));
				}
			}
			if(row + 1 <= n){
				if(isOpen(row+1, col)){
					uf.union(xyTo1D(row+1, col), xyTo1D(row, col));
					uf2.union(xyTo1D(row+1, col), xyTo1D(row, col));
				}
			}
			if(col + 1 <= n){
				if(isOpen(row, col+1)){
					uf.union(xyTo1D(row, col+1), xyTo1D(row, col));
					uf2.union(xyTo1D(row, col+1), xyTo1D(row, col));
				}
			}
			if(row == 1){
				uf.union(xyTo1D(row, col), 0);
				uf2.union(xyTo1D(row, col), 0);
			}
			if(row == n){
				uf.union(xyTo1D(row, col), n*n+1);
				//percolates = true;
			}
		}
	}

	public boolean isOpen(int row, int col){	// is site (row, col) open?
		if(row > n || col > n || row <  1 || col < 1){
			throw new java.lang.IllegalArgumentException();
		}
		return grid[row][col];
	}
	public boolean isFull(int row, int col){	// is site (row, col) full?
		if(row > n || col > n || row <  1 || col < 1){
			throw new java.lang.IllegalArgumentException();
		}
		return uf2.connected(0, xyTo1D(row, col));
	}
	
	public int numberOfOpenSites(){       // number of open sites
		return numOpen;
	}
	public boolean percolates(){              // does the system percolate?
		//return percolates;
		return uf.connected(n*n+1, 0);
		//return percolates;
	}
	private int xyTo1D(int row, int col){
		return (row-1)*n + col;
	}

	public static void main(String[] args){   // test client (optional)
//				File input = new File("percolation/input50.txt");
//				
//				try {
//					
//					Scanner in = new Scanner(input);
//					
//					Percolation p = null;
//					if(in.hasNext()){
//						int i =in.nextInt(); 
//						p = new Percolation(i);
//					}else{
//						p = new Percolation(0);
//					}
//					while(in.hasNextInt()){
//						int a = in.nextInt();
//						int b = in.nextInt();
//						p.open(a, b);
//					}
//					System.out.println(p.percolates());
//				} catch (FileNotFoundException e) {
//					// TODO Auto-generated catch block
//					e.printStackTrace();
//				}
	}
}*/