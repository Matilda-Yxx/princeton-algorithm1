import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdOut;

public class Permutation {
    public static void main(String[] args) {
        // read input arguments
        int n = Integer.parseInt(args[0]);

        RandomizedQueue<String> queue = new RandomizedQueue<String>();
        while (!StdIn.isEmpty()) {
            queue.enqueue(StdIn.readString());
        }
        // print results randomly
        int i = 0;
        for (String a : queue) {
            if (i == n){break;}
            StdOut.println(a);
            i++;
        }
    }
}
