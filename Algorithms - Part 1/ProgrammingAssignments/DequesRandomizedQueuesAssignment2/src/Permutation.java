import java.util.Iterator;
import java.util.NoSuchElementException;

import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.StdRandom;

public class Permutation {
	public static void main(String[] args) {
		int k = Integer.parseInt(args[0]); // number of integers to print
		RandomizedQueue<String> queue = new RandomizedQueue<String>();
		int i = 0; // number of ints read in
		while (!StdIn.isEmpty()) {
			try {
				String a = StdIn.readString();
				i++; 
				if (i > k) { // if more numbers than k have been read
					if (StdRandom.bernoulli(1.0*k/i)) { // keep newly read number at probability k/i
						queue.dequeue(); // randomly remove a pre-existing number from queue
						queue.enqueue(a); // add the new number
					}
				}
				else { queue.enqueue(a); } // if not enough numbers, add new number
			} catch (NoSuchElementException e) { break; } 
		}
		Iterator<String> it = queue.iterator();
		while (it.hasNext()) { StdOut.println(it.next()); } // print all numbers in queue
	}
}
