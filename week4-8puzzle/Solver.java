import java.util.Arrays;
import java.util.LinkedList;
import java.util.Deque;

import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.MinPQ;


public class Solver {

    private MinPQ<SearchNode> minPQ;
    private SearchNode solutionNode;
    private boolean isSolvable;
    
    // find a solutionNode to the initial board (using the A* algorithm)
    public Solver(Board initial) {
        minPQ = new MinPQ<>();
        minPQ.insert(new SearchNode(initial, null, 0));
        isSolvable = false;

        while (true) {

            SearchNode curNode = minPQ.delMin();
            Board curBoard = curNode.getBoard();

            // check if curNode is the solutionNode node
            if (curBoard.isGoal()) {
                solutionNode = curNode;
                isSolvable = true;
                break;
            }

            // check if current board has a twin that is unsolvable
            if (curBoard.hamming() == 2 && curBoard.twin().isGoal()) {
                break;
            }

            // get board of prevNode
            Board prevBoard;
            if (curNode.getPrev() == null) {
                prevBoard = null;
            } else {
                prevBoard = curNode.getPrev().getBoard();
            }

            // get current neighbors, skip those that are identical to prevNode, insert into minPQ
            int newMoves = curNode.getMoves() + 1;
            for (Board neighbor: curBoard.neighbors()) {
                if (prevBoard != null && prevBoard.equals(neighbor)) {
                    continue;
                }
                minPQ.insert(new SearchNode(neighbor, curNode, newMoves));
            }

        }
    }

    private class SearchNode implements Comparable<SearchNode> {
        private final int moves;
        private final Board board;
        private final SearchNode previous;

        SearchNode(Board initial, SearchNode previous, int moves) {
            this.board = initial;
            this.moves = moves;
            this.previous = previous;
        }

        @Override
        public int compareTo(SearchNode other) {
            if(this.getPriority() < other.getPriority()) return -1;
            else if(this.getPriority() == other.getPriority()) return 0;
            else return 1;
        }

        public int getPriority() {
            return moves + this.board.manhattan();
        }
        public Board getBoard() {
            return this.board;
        }
        public int getMoves() {
            return this.moves;
        }
        public SearchNode getPrev() {
            return this.previous;
        }
    }

    // is the initial board solvable? (see below)
    public boolean isSolvable() {
        return isSolvable;
    }

    // min number of moves to solve initial board; -1 if unsolvable
    public int moves() {
        if (isSolvable)
            return solutionNode.getMoves();
        else return -1;
    }

    // sequence of boards in a shortest solutionNode; null if unsolvable
    public Iterable<Board> solution() {
        if(!isSolvable) {
            return null;
        } else {
            Deque<Board> boardStack = new LinkedList<>();
            SearchNode curNode = solutionNode;
            
            while (curNode != null) {
                boardStack.addFirst(curNode.getBoard());
                curNode = curNode.getPrev();
            }
            return boardStack;
        }
    }

    // test client (see below) 
    public static void main(String[] args) {

    // create initial board from file
    In in = new In(args[0]);
    int n = in.readInt();
    int[][] tiles = new int[n][n];
    for (int i = 0; i < n; i++)
        for (int j = 0; j < n; j++)
            tiles[i][j] = in.readInt();
    Board initial = new Board(tiles);

    // solve the puzzle
    Solver solver = new Solver(initial);

    // print solutionNode to standard output
    if (!solver.isSolvable())
        StdOut.println("No solutionNode possible");
    else {
        StdOut.println("Minimum number of moves = " + solver.moves());
        for (Board board : solver.solution())
            StdOut.println(board);
    }
}

}