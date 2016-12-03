package week4;

import java.util.ArrayList;

import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.MinPQ;
import edu.princeton.cs.algs4.Stack;
import edu.princeton.cs.algs4.StdOut;

public class Solver {
	private class SearchNode implements Comparable<SearchNode>{
		private Board board;
		private int moves;
		private SearchNode previousNode;
		
		public SearchNode(Board board, int moves, SearchNode previousNode) {
			this.board = board;
			this.moves = moves;
			this.previousNode = previousNode;
		}

		@Override
		public int compareTo(SearchNode o) {
			if((this.board.manhattan() + moves)> (o.board.manhattan() + o.moves)) {
				return 1;
			} else if((this.board.manhattan() + moves) < (o.board.manhattan() + o.moves)) {
				return -1;
			} else {
//				if((moves) > (o.moves)) {
//					return 1;
//				} else if((moves) < (o.moves)) {
//					return -1;
//				} else {
//					return 0;
//				}
				return 0;
			}
		}
	}
	private MinPQ<SearchNode> queue = new MinPQ<SearchNode>(1000);
	private MinPQ<SearchNode> twinQueue = new MinPQ<SearchNode>(1000);
	private ArrayList<Board> boards = new ArrayList<Board>();
	private boolean isResovable = false;
	private Stack<Board> stack = new Stack<Board>();
	
	public Solver(Board initial) {
		queue.insert(new SearchNode(initial,0,null));
		twinQueue.insert(new SearchNode(initial.twin(),0,null));
		while(!queue.isEmpty() && !twinQueue.isEmpty()) {
			SearchNode current = queue.delMin();
			boards.add(current.board);
			if(current.board.isGoal()) {
				isResovable = true;
				do {
					stack.push(current.board);
					current = current.previousNode;
				} while(current != null);
				break;
			} else {
				Iterable<Board> neighbors = current.board.neighbors();
				for (Board board : neighbors) {
					if(current.previousNode == null || !board.equals(current.previousNode.board)) {
						queue.insert(new SearchNode(board,current.moves+1,current));
					}
				}
			}
			
			SearchNode twinCur = twinQueue.delMin();
			if(twinCur.board.isGoal()) {
				isResovable = false;
				break;
			} else {
				Iterable<Board> neighbors = twinCur.board.neighbors();
				for (Board board : neighbors) {
					if(twinCur.previousNode == null || !board.equals(twinCur.previousNode.board)) {
						twinQueue.insert(new SearchNode(board,twinCur.moves+1,twinCur));
					}
				}
			}
		}
	}
	public boolean isSolvable() {
		return isResovable;
	}
	public int moves() {
		if(isSolvable()) {
			return stack.size()-1;
		} else {
			return -1;
		}
	}
	public Iterable<Board> solution() {
		if(isSolvable()) {
			return stack;
		} else {
			return null;
		}
	}
	public static void main(String[] args) {
		// create initial board from file
	    In in = new In(args[0]);
	    int n = in.readInt();
	    int[][] blocks = new int[n][n];
	    for (int i = 0; i < n; i++)
	        for (int j = 0; j < n; j++)
	            blocks[i][j] = in.readInt();
	    Board initial = new Board(blocks);

	    // solve the puzzle
	    Solver solver = new Solver(initial);

	    // print solution to standard output
	    if (!solver.isSolvable())
	        StdOut.println("No solution possible");
	    else {
	        StdOut.println("Minimum number of moves = " + solver.moves());
	        for (Board board : solver.solution())
	            StdOut.println(board);
	    }
	}
}
