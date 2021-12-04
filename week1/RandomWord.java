/**
 *  Do not store the words in an array or list. 
 * Instead, use Knuth’s method: when reading the ith word, 
 * select it with probability 1/i to be the champion, 
 * replacing the previous champion. 
 * After reading all of the words, print the surviving champion.
 * 
 */
import edu.princeton.cs.algs4.StdIn; // stupidlu, we have to add these paclages in order to pass the autograder...
import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.StdRandom;

public class RandomWord {
    public static void main(String[] args) {
        String cur_word = "";
        String champion = "";
        int denominator = 1;
        // select the random champion using Knuth's method
        while(!StdIn.isEmpty()){
            cur_word = StdIn.readString();
            if(StdRandom.bernoulli(1.0/(double) denominator)){
                champion = cur_word;
            }
            denominator++;
        }
        // print the champion string
        StdOut.println(champion);
    }
}
