import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.StdStats;

public class PercolationStats {
	private int n;
	private int trials;
	private double vals[];
	private double mean;
	private double stddev;
	public PercolationStats(int n, int trials){    // perform trials independent experiments on an n-by-n grid
		if (n <= 0 || trials <= 0){
			throw new java.lang.IllegalArgumentException();
		}
		this.n = n;
		this.trials = trials;
		vals = new double[trials];
		
		for(int i = 0; i < trials; i++){
			Percolation per = new Percolation(n);
			while(!per.percolates()){
				per.open(StdRandom.uniform(1, n+1), StdRandom.uniform(1, n+1));
			}
			vals[i] = (per.numberOfOpenSites()+0.0)/(n*n);
		}
		mean = StdStats.mean(vals);
		stddev = StdStats.stddev(vals);
	}
	public double mean(){                          // sample mean of percolation threshold
		return mean;
	}
	public double stddev(){                        // sample standard deviation of percolation threshold
		return stddev;
	}
	public double confidenceLo(){                  // low  endpoint of 95% confidence interval
		return mean - (1.96*stddev)/Math.sqrt(trials);
	}
	public double confidenceHi(){                  // high endpoint of 95% confidence interval
		return mean + (1.96*stddev)/Math.sqrt(trials);
	}

	public static void main(String[] args){        // test client (described below)
		PercolationStats perStats = new PercolationStats(StdIn.readInt(), StdIn.readInt());
		StdOut.printf("%-24s= %f\n", "mean", perStats.mean());
		StdOut.printf("%-24s= %f\n", "stddev", perStats.stddev());
		StdOut.printf("%-24s= [%f, %f]\n", "95% confidence interval", perStats.confidenceLo(), perStats.confidenceHi());
	}
}