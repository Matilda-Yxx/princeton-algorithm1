/**
 * Performance requirements.  
 * 
 * Your randomized queue implementation must support each randomized queue operation (besides creating an iterator) in constant amortized time. 
 * That is, any intermixed sequence of m randomized queue operations (starting from an empty queue) must take at most cm steps in the worst case, for some constant c. 
 * A randomized queue containing n items must use at most 48n + 192 bytes of memory.
 * 
 *  Additionally, your iterator implementation must support operations next() and hasNext() in constant worst-case time; and construction in linear time; you may (and will need to) use a linear amount of extra memory per iterator.
 */

/**
 * Array implementation of a queue.
・ Use array q[] to store items in queue. 
・enqueue(): add new item at q[last]. 
・dequeue(): remove item from q[first]. 
・Update first and last modulo the capacity. 
・Add resizing array.
 */

 /**
  * setting an array element pointing to null helps to deal with loitering issue!
  */
/**
 * Efficient solution for resizing the arrays.
・push(): double size of array s[] when array is full.
・pop(): halve size of array s[] when array is one-quarter full.
 */

 // citation: https://algs4.cs.princeton.edu/13stacks/ResizingArrayQueue.java.html

import java.util.Iterator;
import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdRandom;
import java.util.NoSuchElementException;

public class RandomizedQueue<Item> implements Iterable<Item> {
    // initial capacity of underlying resizing array
    private static final int INIT_CAPACITY = 8;

    private Item[] q;       // queue elements
    private int n;          // number of elements on queue
    private int first, last; // first and last+1 pointers of the queue array
    // last should always point to the element following the last item
    // else last cannot point to anything when the queue is empty

    // construct an empty randomized queue
    public RandomizedQueue() {
        q = (Item[]) new Object[INIT_CAPACITY];
        first = 0;
        last = 0;
        n = 0;
    }

    // is the randomized queue empty?
    public boolean isEmpty() {
        return n == 0;
    }

    // return the number of items on the randomized queue
    public int size() {
        return n;
    }

    private void resize(int capacity) {
        // identical with API from https://algs4.cs.princeton.edu/13stacks/ResizingArrayQueue.java.html
        assert capacity >= n;
        Item[] copy = (Item[]) new Object[capacity];
        for (int i = 0; i < n; i++) {
            copy[i] = q[(first + i) % q.length];
        }
        q = copy;
        first = 0;
        last  = n;
    }

    // add the item
    public void enqueue(Item item) {
        if (item == null) throw new IllegalArgumentException("Item must not be null");
        // identical with API from https://algs4.cs.princeton.edu/13stacks/ResizingArrayQueue.java.html
        // double size of array if necessary and recopy to front of array
        if (n == q.length) resize(2*q.length);   // double size of array if necessary
        q[last++] = item;                        // add item
        if (last == q.length) last = 0;          // wrap-around
        n++;
    }

    private void shuffleArray() {
        if (isEmpty()) throw new NoSuchElementException("Queue underflow");
        // shuffle array elements when trying to deque
        if (first < last) {StdRandom.shuffle(q, first, last);}
        else {
            StdRandom.shuffle(q, first, q.length - 1);
            StdRandom.shuffle(q, 0, last-1);
        }
    }

    // remove and return a random item
    public Item dequeue() {
        if (isEmpty()) throw new NoSuchElementException("Queue underflow");
        shuffleArray();                             // rearrange array elements
        Item item = q[first];
        q[first] = null;                            // to avoid loitering
        n--;
        first++;
        if (first == q.length) first = 0;           // wrap-around
        // shrink size of array if necessary
        if (n > 0 && n == q.length/4) resize(q.length/2); 
        return item;
    }

    // return a random item (but do not remove it)
    public Item sample() {
        // uniform: Returns a random integer uniformly in [a, b).
        // StdOut.println("start");
        // StdOut.println("first = " + first);
        // StdOut.println("last = " + last);
        // for (int i = 0; i < q.length; i++) {
        //     StdOut.println(q[i]);
        // }
        // StdOut.println("end");

        if (isEmpty()) throw new NoSuchElementException("Queue underflow");
        if (first < last) {return q[StdRandom.uniform(first, last)];}
        else if (first == last) {return q[first];}
        else {return q[StdRandom.uniform(first, last + q.length) % q.length];}
    }

    // return an independent iterator over items in random order
    public Iterator<Item> iterator() {
        return new RandomizedIterator();
    }

    // an iterator, doesn't implement remove() since it's optional
    private class RandomizedIterator implements Iterator<Item> {
        // since the iterators need to be independently randomized, we need to create new copies of the current queue each time
        private int i = 0;
        public boolean hasNext()  { return i < n;                               }
        public void remove()      { throw new UnsupportedOperationException();  }
        private final Item[] copyOfItems;

        public RandomizedIterator() {
            // create a copy of the current items
            copyOfItems = (Item[]) new Object[q.length];
            for (int j = 0; j < n; j++) {
                copyOfItems[j] = q[(first + j) % q.length];
            }
            if (n > 0) {StdRandom.shuffle(copyOfItems, 0, n-1);}
        }

        public Item next() {
            if (!hasNext()) throw new NoSuchElementException();
            Item item = copyOfItems[i];
            i++;
            return item;
        }
    }
    // unit testing (required)
    public static void main(String[] args) {
        
        // RandomizedQueue<Integer> queue = new RandomizedQueue<>();
        // queue.enqueue(19);
        // queue.enqueue(519);
        // for (Integer a : queue) {
        //     StdOut.println(a);
        // }
        // StdOut.println(queue.sample());
 
        /**
         * individual tests: aim to test individual methods
         */
        // RandomizedQueue<String> queue = new RandomizedQueue<String>();
        
        // test empty queue
        // StdOut.println("Is current queue empty? " + queue.isEmpty());
        // StdOut.println("Current size of the queue is given by: " + queue.size());
        
        // StdOut.println("\n*********************************");
        // StdOut.println("******** individual tests *******");
        // StdOut.println("*********************************\n");
        
        // StdOut.println("********* test enqueue **********");
        // queue.enqueue("coco");
        // queue.enqueue("is");
        // queue.enqueue("cute");
        // StdOut.println(queue.size());
        // for (String a : queue) {
        //     StdOut.println(a);
        // }
        // StdOut.println("********* test sample  **********");
        // StdOut.println(queue.sample());
        // StdOut.println(queue.sample());
        // StdOut.println(queue.sample());

        // StdOut.println("********* test dequeue **********");
        // StdOut.println(queue.dequeue());
        // StdOut.println(queue.dequeue());
        // StdOut.println(queue.dequeue());

        // // double check queue status
        // StdOut.println("********* final status **********");
        // StdOut.println("Is current queue empty? " + queue.isEmpty());
        // StdOut.println("Current size of the queue is given by: " + queue.size());
        
        // StdOut.println("\n****************************************");
        // StdOut.println("******** test intermixed methods *******");
        // StdOut.println("****************************************\n");

        // StdOut.println("***** test1: multiple independent iterators *****");
        // int n = 5;
        // RandomizedQueue<Integer> intQueue = new RandomizedQueue<Integer>();
        // for (int i = 0; i < n; i++) {intQueue.enqueue(i);}
        // for (int a : intQueue) {
        //     for (int b : intQueue)
        //         StdOut.print(a + "-" + b + " ");
        //     StdOut.println();
        // }
        // // exception handling
        // StdOut.println("\n********************************************");
        // StdOut.println("************ exception handling ************");
        // StdOut.println("********************************************\n");
        // have to comment out below in order to avoid autograder error
        // // initialize to empty queue
        // for (int i = 0; i < n; i++) {intQueue.dequeue();}
        // assert(intQueue.size() == 0);
        
        // StdOut.println("************ exception for nulls ***********");
        // try {intQueue.enqueue(null);}
        // catch (Exception e) {StdOut.println(e);}
        
        // StdOut.println("*** exception for dequeue an empty queue ***");
        // try {intQueue.dequeue();}
        // catch (Exception e) {StdOut.println(e);}
        
        // try {intQueue.sample();}
        // catch (Exception e) {StdOut.println(e);}
        
        // StdOut.println("********** exception for iterator **********");
        // intQueue.enqueue(1);
        // Iterator<Integer> iter = intQueue.iterator();
        // try {iter.remove();}
        // catch (Exception e) {StdOut.println(e);}
    }
}