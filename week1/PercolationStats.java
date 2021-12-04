import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.StdStats;
import edu.princeton.cs.algs4.StdOut;

public class PercolationStats {
    private final int trials;
    private double[] estimates;
    private double sumOfEstimates;
    
    // perform independent trials on an n-by-n grid
    public PercolationStats(int n, int trials) {
        // handling exceptions
        if (n <=0) throw new IllegalArgumentException("Input grid size out of bounds");
        if (trials <=0) throw new IllegalArgumentException("Input trial number out of bounds");
        
        // setting instance variables
        this.trials = trials;
        this.estimates = new double[trials];
        this.sumOfEstimates = 0;
        
        // loop over all trials
        for(int i = 0; i < trials; i++) {
            Percolation newTest = new Percolation(n);
            int[] randStream = StdRandom.permutation(n*n);
            // loop over all randomly filled nodes
            for(int j = 0; j < n*n; j++) {
                int curIdx = randStream[j];
                int row = curIdx / n + 1;
                int col = curIdx % n + 1;
                newTest.open(row, col);
                if(newTest.percolates()) {
                    break;
                }
            }
            estimates[i] = (double) newTest.numberOfOpenSites()/(n*n);
            sumOfEstimates += estimates[i];
        }
    }
    
    // sample mean of percolation threshold
    public double mean() {
        return this.sumOfEstimates/this.trials;
    }
    
    // sample standard deviation of percolation threshold
    public double stddev() {
        double sqSum = 0.0;
        double mean = mean();
        for(int i = 0; i < this.trials; i++) {
            sqSum += (this.estimates[i] - mean)*(this.estimates[i] - mean);
        }
        return Math.sqrt(sqSum/(this.trials-1));
    }
    
    // low endpoint of 95% confidence interval
    public double confidenceLo() {
        return mean() - 1.96*stddev()/Math.sqrt(this.trials);
    }
    
    // high endpoint of 95% confidence interval
    public double confidenceHi() {
        return mean() + 1.96*stddev()/Math.sqrt(this.trials);
    }
    
    // test client (see below)
    public static void main(String[] args) {
        int n = Integer.parseInt(args[0]);
        int T = Integer.parseInt(args[1]);
        PercolationStats stats = new PercolationStats(n, T);
        StdOut.println("mean                    = " + stats.mean());
        StdOut.println("stddev                  = " + stats.stddev());
        StdOut.println("95% confidence interval = [" + stats.confidenceLo() + ", " + stats.confidenceHi() + "]"); 
    }
}