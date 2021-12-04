import java.util.Iterator;
import edu.princeton.cs.algs4.StdOut;
/**
 * Performance requirements:
 * Your deque implementation must support each deque operation (including construction) in constant worst-case time. 
 * A deque containing n items must use at most 48n + 192 bytes of memory.
 *  Additionally, your iterator implementation must support each operation (including construction) in constant worst-case time.
 */

 /**
  * Memory usage of my class:
  * - object overhead per node = 16 bytes
  * - inner class extra overhead = 8 bytes
  * - reference to item, next, previous = 8 x 3 = 24 bytes
  * - totalling 48 bytes per node
  */

public class Deque<Item> implements Iterable<Item> {

    // node
    private Node first;
    private Node last;
    private Integer size;

    private class Node {
        Item item;
        Node next;
        Node previous;
    }

    // construct an empty deque
    public Deque() {
        first = null;
        last = null;
        size = 0;
    }

    // is the deque empty?
    public boolean isEmpty() {
        return size == 0;
    }

    // return the number of items on the deque
    public int size() {
        return size;
    }

    // add the item to the front
    public void addFirst(Item item) {
        if (item == null) throw new IllegalArgumentException("Item must not be null");
        
        if (isEmpty()) {
            first = new Node();
            first.previous = null;
            first.next = null;
            first.item = item;
            last = first;
        } else {
            Node oldFirst = first;
            first = new Node();
            first.previous = null;
            first.next = oldFirst;
            first.item = item;
            oldFirst.previous = first;
        }
        size++;
    }

    // add the item to the back
    public void addLast(Item item) {
        if (item == null) throw new IllegalArgumentException("Item must not be null");
        if (isEmpty()) {
            last = new Node();
            last.previous = null;
            last.next = null;
            last.item = item;
            first = last;
        } else {
            Node oldLast = last;
            last = new Node();
            last.previous = oldLast;
            last.next = null;
            last.item = item;
            oldLast.next = last;
        }
        size++;
    }

    // remove and return the item from the front
    public Item removeFirst() {
        if (isEmpty()) throw new java.util.NoSuchElementException("Cannot removeFirst, deque is empty!");
        Item item = first.item;
        first.item = null;
        first = first.next;
        if (first != null) {first.previous = null;}
        size--;
        return item;
    }

    // remove and return the item from the back
    public Item removeLast() {
        if (isEmpty()) throw new java.util.NoSuchElementException("Cannot removeFirst, deque is empty!");
        Item item = last.item;
        last.item = null;
        last = last.previous;
        if (last != null) {last.next = null;}
        size--;
        return item;
    }

    // return an iterator over items in order from front to back
    public Iterator<Item> iterator() {
        return new DequeIterator();
    }

    private class DequeIterator implements Iterator<Item> {
        private Node current = first;

        public boolean hasNext() { return current != null; }
        public void remove() { throw new UnsupportedOperationException("Cannot remove"); }
        public Item next() {
            if (current == null) {throw new java.util.NoSuchElementException("No more items from the current deque.");}
            Item item = current.item;
            current = current.next;
            return item;
        }
    }

    // unit testing (required)
    public static void main(String[] args) {
        // Deque<Integer> deque = new Deque<Integer>();
        // deque.addFirst(1);
        // deque.isEmpty();        
        // deque.addFirst(3);
        // for (Integer i : deque) {StdOut.println(i);}
        // StdOut.println(deque.removeFirst());   
        // StdOut.println(deque.removeFirst());

        // myDeque.addLast("coco1");
        // StdOut.println("*******************");
        // StdOut.println(myDeque.size());
        // for (String s: myDeque) {StdOut.println(s);}


        // myDeque.addLast("coco2");
        // StdOut.println("*******************");
        // StdOut.println(myDeque.size());
        // for (String s: myDeque) {StdOut.println(s);}

        // myDeque.addFirst("kiwi1");
        // StdOut.println("*******************");
        // StdOut.println(myDeque.size());
        // for (String s: myDeque) {StdOut.println(s);}

        // myDeque.addFirst("kiwi2");
        // StdOut.println("*******************");
        // StdOut.println(myDeque.size());
        // for (String s: myDeque) {StdOut.println(s);}

        // myDeque.addFirst("kiwi3");
        // StdOut.println("*******************");
        // StdOut.println(myDeque.size());
        // for (String s: myDeque) {StdOut.println(s);}


        // String removedFirst = myDeque.removeFirst();
        // StdOut.println("*******************");
        // StdOut.println("removed " + removedFirst);
        // StdOut.println(myDeque.size());
        // StdOut.println("Is empty? " +  myDeque.isEmpty());
        // for (String s: myDeque) {StdOut.println(s);}

        // String removedLast = myDeque.removeLast();
        // StdOut.println("*******************");
        // StdOut.println("removed " + removedLast);
        // StdOut.println(myDeque.size());
        // StdOut.println("Is empty? " + myDeque.isEmpty());
        // for (String s: myDeque) {StdOut.println(s);}

        // removedLast = myDeque.removeLast();
        // StdOut.println("*******************");
        // StdOut.println("removed " + removedLast);
        // StdOut.println(myDeque.size());
        // StdOut.println("Is empty? " + myDeque.isEmpty());
        // for (String s: myDeque) {StdOut.println(s);}
    }
}