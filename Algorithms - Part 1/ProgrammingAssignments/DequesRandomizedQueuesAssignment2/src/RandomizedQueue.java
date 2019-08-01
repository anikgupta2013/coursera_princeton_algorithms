import java.util.Iterator;

import edu.princeton.cs.algs4.StdRandom;

public class RandomizedQueue<Item> implements Iterable<Item> {
	private Item[] q; // values in random queue
	private int n; // size
	public RandomizedQueue() {                           // construct an empty randomized queue
		q = (Item[]) new Object[2];
		n = 0;
	}


	public boolean isEmpty() { return n == 0; }        // is the queue empty?
	public int size() { return n; }      // return the number of items on the queue
	public void enqueue(Item item) {          // add the item
		if (item == null) { throw new java.lang.IllegalArgumentException(); } // item added is null
		if (n == q.length) { resize(q.length*2); } // if not enough space, double the array
		q[n++] = item; // add item in array at next position, increase size
	}
	public Item dequeue() {                   // remove and return a random item
		if (isEmpty()) { throw new java.util.NoSuchElementException(); } // nothing to dequeue
		int index = StdRandom.uniform(n); // find random index to return from
		Item val = q[index]; // get the value at that random index
		q[index] = q[n-1]; // put last value in array at this random index
		q[--n] = null; // set last value in array to null, decrease size
		if (n > 0 && n == q.length/4) resize(q.length/2); // if space taken in small, resize to 1/2
		return val; // return new value
	}
	public Item sample() {                     // return (but do not remove) a random item
		if (isEmpty()) { throw new java.util.NoSuchElementException(); }
		int index = StdRandom.uniform(n);
		return q[index];
	}
	private void resize (int newSize) {
		Item[] temp = (Item[]) new Object[newSize];
		for (int i = 0; i < n; i++) {
			temp[i] = q[i];
		}
		q = temp;
	}
	public Iterator<Item> iterator() {         // return an independent iterator over items in random order
		return new ListIterator<Item>(q, n);
	}
	private class ListIterator<Item> implements Iterator<Item> {
		private Item[] q;
		private int n;
		public ListIterator(Item[] q, int n) {
			this.q = q.clone(); // store the array and its size
			this.n = n;
		}
		public void remove()      { throw new UnsupportedOperationException();  }
		@Override
		public boolean hasNext() {
			return n != 0; // size is 0 means no more elements
		}

		@Override
		public Item next() {
			if (n == 0) throw new java.util.NoSuchElementException();
			int index = StdRandom.uniform(this.n); // remove element without resizing and return it
			Item val = q[index];
			q[index] = q[this.n-1];
			q[--this.n] = null;
			return val;
		}

	}
	public static void main(String[] args) {   // unit testing (optional)

	}
}