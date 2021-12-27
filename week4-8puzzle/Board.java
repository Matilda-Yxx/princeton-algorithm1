import java.util.Arrays;
import java.util.Stack;

import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.In;


public class Board {

    private final int[][] board;
    private final int n;
    private int blankRow;
    private int blankCol;

    // create a board from an n-by-n array of tiles,
    // where tiles[row][col] = tile at (row, col)
    public Board(int[][] tiles) {
        if (tiles == null) {
            throw new IllegalArgumentException("Argument to Board constructor is null");
        }

        n = tiles.length;
        board = copyOf(tiles);
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                if (board[i][j] == 0) {
                    blankRow = i;
                    blankCol = j;
                }
            }
        }
    }
                                           
    // string representation of this board
    public String toString() {
        StringBuilder s = new StringBuilder();
        s.append(n + "\n");
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                s.append(String.format("%2d ", board[i][j]));
            }
            s.append("\n");
        }
        return s.toString();
    }

    // board dimension n
    public int dimension() {
        return n;
    }

    // number of board out of place
    public int hamming() {
        if(isGoal()) return 0;

        int hammingDist = 0;
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                int goalTile = (i * n + j + 1)  % (n*n);
                if ( !(i == blankRow && j == blankCol) ) {
                    if (board[i][j] != goalTile) {
                        hammingDist += 1;
                    }
                }
            }
        }
        return hammingDist;
    }

    // sum of Manhattan distances between board and goal
    public int manhattan() {
        if(isGoal()) return 0;

        int manhattanDist = 0;
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                int goalRow = (board[i][j] - 1) / n;
                int goalCol = (board[i][j] - 1) % n;
                if (!(i == blankRow && j == blankCol)) {
                    manhattanDist += (Math.abs(i - goalRow) + Math.abs(j - goalCol));
                }
            }
        }
        return manhattanDist;
    }

    // is this board the goal board?
    public boolean isGoal() {
        boolean isGoal = true;
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                int goalTile = (i * n + j + 1) % (n*n);
                if(board[i][j] != goalTile) {
                    return false;
                }
            }
        }
        return isGoal;
    }

    // does this tile equal y?
    // implemented wrt https://algs4.cs.princeton.edu/12oop/Date.java.html
    public boolean equals(Object y) {
        if (y == this) return true;
        if (y == null) return false;
        if (y.getClass() != this.getClass()) return false;
        Board that = (Board) y;

        // check all values
        if ((that.n != this.n) || (that.blankRow != this.blankRow) || (that.blankCol != this.blankCol)) return false;
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                if (that.board[i][j] != this.board[i][j]) return false;
            }
        }
        return true;
    }

    private void exchangeTiles(int[][] matrix, int a_i, int a_j, int b_i, int b_j) {
        // this function switch a pair of tiles
        int temp = matrix[b_i][b_j];
        matrix[b_i][b_j] = matrix[a_i][a_j];
        matrix[a_i][a_j] = temp;
    }

    private int[][] copyOf(int[][] matrix) {
        int[][] copy = new int[matrix.length][];
        for (int i = 0; i < matrix.length; i++) {
            copy[i] = matrix[i].clone(); // clone() is only safe for copying single dim arrays 
        }
        return copy;
    }

    // all neighboring boards
    public Iterable<Board> neighbors() {

        Stack<Board> neighbors = new Stack<>();
        // left
        if (blankCol > 0) {
            int[][] left = copyOf(board);
            exchangeTiles(left, blankRow, blankCol, blankRow, blankCol - 1);
            neighbors.add(new Board(left));
        }
        // right
        if (blankCol < (n-1)) {
            int[][] right = copyOf(board);
            exchangeTiles(right, blankRow, blankCol, blankRow, blankCol + 1);
            neighbors.add(new Board(right));

        }

        // up
        if (blankRow > 0) {
            int[][] up = copyOf(board);
            exchangeTiles(up, blankRow, blankCol, blankRow - 1, blankCol);
            neighbors.add(new Board(up));

        }

        // down
        if (blankRow < (n-1)) {
            int[][] down = copyOf(board);
            exchangeTiles(down, blankRow, blankCol, blankRow + 1, blankCol);
            neighbors.add(new Board(down));

        }
        return neighbors;
    }

    // a board that is obtained by exchanging any pair of tiles
    public Board twin() {
        int[][] updatedTiles = copyOf(board);
        if (blankRow != 0) {
            exchangeTiles(updatedTiles, 0, 0, 0, 1);
        } else {
            exchangeTiles(updatedTiles, 1, 0, 1, 1);
        }
        return new Board(updatedTiles);
    }

    // unit testing (not graded)
    public static void main(String[] args) {

    }

}