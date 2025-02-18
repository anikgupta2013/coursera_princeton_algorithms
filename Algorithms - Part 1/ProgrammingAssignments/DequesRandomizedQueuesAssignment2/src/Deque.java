import java.util.Iterator;

public class Deque<Item> implements Iterable<Item> {
	private Node<Item> first; // first item in dequeue
	private Node<Item> last; // last item in dequeue
	private int n; // size
	public Deque() {                           // construct an empty deque
		first = null;
		last = null;
		n = 0;
	}

	private class Node<Item> {
		private Item item;
		private Node<Item> next;
		private Node<Item> prev;
	}
	public boolean isEmpty() {                 // is the deque empty?
		return n == 0;
	}
	public int size() {                        // return the number of items on the deque
		return n;
	}
	public void addFirst(Item item) {          // add the item to the front
		if (item == null) {
			throw new java.lang.IllegalArgumentException();
		}
		if (first == null) { // set first item
			first = new Node<Item> ();
			first.item = item;
			first.next = null;
			first.prev = null;
		}
		else { // add first in beginning and fix pointers
			Node<Item> oldFirst = first;
			first = new Node<Item> ();
			first.item = item;
			first.next = oldFirst;
			oldFirst.prev = first;
			first.prev = null;
		}
		if (isEmpty()) { // first element added
			last = first;
		}
		n++;
	}
	public void addLast(Item item) {           // add the item to the end
		if (item == null) {
			throw new java.lang.IllegalArgumentException();
		}
		if (last == null) {
			last = new Node<Item> ();
			last.item = item;
			last.next = null;
			last.prev = null;
		}
		else {
			Node<Item> oldLast = last;
			last = new Node<Item> ();
			last.item = item;
			last.next = null;
			last.prev = oldLast;
			oldLast.next = last;
		}
		if (isEmpty()) {
			first = last;
		}
		n++;
	}
	public Item removeFirst() {                // remove and return the item from the front
		if (isEmpty()) {
			throw new java.util.NoSuchElementException();
		}
		Item remove = first.item;
		if (first.next == null) {
			first = null;
		}
		else {
			first = first.next;
			first.prev = null;
		}
		n--;
		if (isEmpty()) {
			last = null;
		}
		return remove;	
	}
	public Item removeLast() {                 // remove and return the item from the end
		if (isEmpty()) {
			throw new java.util.NoSuchElementException();
		}
		Item remove = last.item;
		if (last.prev == null) {
			last = null;
		}
		else {
			last = last.prev;
			last.next = null;
		}
		n--;
		if (isEmpty()) {
			first = null;
		}
		return remove;	
	}
	public Iterator<Item> iterator() {         // return an iterator over items in order from front to end
		return new ListIterator<Item> (first);
	}
	private class ListIterator<Item> implements Iterator<Item> {
		Node<Item> current;
		public ListIterator(Node<Item> first) {
			current = first;
		}
		public void remove()      { throw new UnsupportedOperationException();  }
		@Override
		public boolean hasNext() {
			return current != null;
		}

		@Override
		public Item next() {
			if (!hasNext()) throw new java.util.NoSuchElementException();
			Item item = current.item;
			current = current.next;
			return item;
		}

	}
	public static void main(String[] args) {   // unit testing (optional)

	}
}