import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.StdStats;
import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.WeightedQuickUnionUF;
/**
* Use WeightedQuickUnionUF in your solution.
* find(p) returns the canonical element of the set containing p. The find operation returns the same value for two elements if and only if they are in the same set.
* union(p, q) merges the set containing element p with the set containing element q. That is, if p and q are in different sets, replace these two sets with a new set that is the union of the two.
* count() returns the number of sets.
*/

public class Percolation {
    private final int n, sourceIdx, sinkIdx;
    private int[][] grid;
    private WeightedQuickUnionUF connection;
    private int numOpenSites;
    
    // creates n-by-n grid, with all sites initially blocked
    public Percolation(int n) {
        // this.grid stores the information about which sites are open
        // this.connection stores the information about site connections
        if (n <=0) throw new IllegalArgumentException("Input grid size out of bounds");
        this.grid = new int[n][n];
        this.n = n;
        this.numOpenSites = 0;
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                this.grid[i][j] = 0; // 0 indicated blockage
            }
        }
        // initialize connection array
        this.connection = new WeightedQuickUnionUF(n * n + 2);
        // connecting the virtual sites to the first and last row of the grid
        this.sourceIdx = 0;
        this.sinkIdx = n * n + 1;
        for (int arrayIdx = 1; arrayIdx <= n; arrayIdx++) {
            this.connection.union(this.sourceIdx, arrayIdx);
        }
        for (int arrayIdx = 1 + n*(n-1); arrayIdx <= n*n; arrayIdx++) {
            this.connection.union(this.sinkIdx, arrayIdx);
        }
    }
    
    private int xyTo1D(int x, int y) {
        // manipulate the grid such that it becomes 1D and can be passed to union-find
        return (x - 1) * (this.n) + y;
    }
    
    private void isValidIdx(int idx) {
        if (idx <= 0 || idx > n) {
            throw new IllegalArgumentException("row index or col index out of bounds");
        } 
    }
    
    private void isValidNode(int row, int col) {
        isValidIdx(row);
        isValidIdx(col);
    }
    
    // opens the site (row, col) if it is not open already
    public void open(int row, int col) {
        // not that java is index based while row and col numbers are 0-based
        isValidNode(row, col);
        if(!isOpen(row, col)) {
            this.numOpenSites++;
            this.grid[row-1][col-1] = 1;
        }

        // find neighbors, applies union
        // up
        if(row > 1) {
            if(isOpen(row-1, col)) {this.connection.union(xyTo1D(row, col), xyTo1D(row-1, col));}
        }
        // left
        if(col > 1) {
            if(isOpen(row, col-1)) {this.connection.union(xyTo1D(row, col), xyTo1D(row, col-1));}
        }
        // right
        if(col < n) {
            if(isOpen(row, col+1)) {this.connection.union(xyTo1D(row, col), xyTo1D(row, col+1));}
        }
        // down
        if(row < n) {
            if(isOpen(row+1, col)){this.connection.union(xyTo1D(row, col), xyTo1D(row+1, col));}
        }

        // // check if the current opened site is in the last row && isFull.
        // // if so, need to connect to sink site
        // if(row == n && isFull(row, col)) {
        //     this.connection.union(this.sinkIdx, xyTo1D(row, col));
        // }

    }
    
    // is the site (row, col) open?
    public boolean isOpen(int row, int col) {
        isValidNode(row, col);
        return (this.grid[row-1][col-1] == 1);
    }
    
    // is the site (row, col) full? meaning that the current site is open and connected to the top row open sites
    public boolean isFull(int row, int col) {
        isValidNode(row, col);
        return isOpen(row, col) && (this.connection.find(xyTo1D(row, col)) == this.connection.find(this.sourceIdx));
    }
    
    // returns the number of open sites
    public int numberOfOpenSites() {
        return this.numOpenSites;
    }
    
    // does the system percolate?
    public boolean percolates() {
        return (this.connection.find(this.sourceIdx) == this.connection.find(this.sinkIdx));
    }
    
    // test client (optional)
    public static void main(String[] args) {
    }
}

