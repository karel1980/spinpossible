package spinpossible;

import java.util.List;
import java.util.ArrayList;

public class Spinpossible {

	int[][] board;
	long last;
	long solveCalls = 0;

  public static final MAX_MOVES = 5;

	public static void main(String[] args) {
		int[][] board = new int[][] { { 4, 2, 3 }, { 1, 5, 9 }, { 7, 8, 6 } };
		Spinpossible s = new Spinpossible(board);

		s.solve();
		System.out.println("done (no solution found)");
	}

	public Spinpossible(int[][] board) {
		this.board = board;
		last = System.currentTimeMillis();
	}

	public void solve() {
		List moves = new ArrayList();
		solve(moves);
	}

	public void solve(List moves) {
		solveCalls++;
		if (moves.size() > MAX_MOVES) {
			return;
		}

		long now = System.currentTimeMillis();
		if ((now - last) > 10000) {
			System.out.println("Current status: ");
			printBoard();
			printMoves(moves);
			last = now;
		}

		if (isSolved()) {
			System.out.println("solution:");
			printBoard();
			printMoves(moves);
			System.exit(0);
		}

		if (moves.size() == MAX_MOVES) {
			return;
		}

		tryMoves(1, 1, moves);
		tryMoves(1, 2, moves);
		tryMoves(2, 1, moves);
		tryMoves(1, 3, moves);
		tryMoves(3, 1, moves);
		tryMoves(2, 3, moves);
		tryMoves(3, 2, moves);
		tryMoves(3, 3, moves);
	}

	public void tryMoves(int h, int w, List moves) {
		for (int row = 0; row < 4 - h; row++) {
			for (int col = 0; col < 4 - w; col++) {
				int[] move = new int[] { row, col, h, w };
				moves.add(move);
				applyMove(move);
				solve(moves);
				applyMove(move);
				moves.remove(moves.size() - 1);
			}

		}
	}

	public void applyMove(int[] move) {
		applyMove(move, false);
	}

	public void applyMove(int[] move, boolean verbose) {
  int h = move[2];
  int w = move[3];

  // make negative
  for (int row = move[0]; row < move[0] + h; row++) {
    for (int col = move[1]; col < move[1] + w; col++) {
      board[row][col] = -board[row][col];
    }
  }

  // swap elements

  int size = w*h;

  for (int num=0; num < size/2; num++) {
    int rowA = move[0] + (num/w);
    int colA = move[1] + (num%w);
    
    int rowB = move[0] + ((size - 1 - num)/w);
    int colB = move[1] + ((size - 1 - num)%w);

    /*//
    int[] cellA = new int[] { rowA, colA, 1, 1 };
    int[] cellB = new int[] { rowB, colB, 1, 1 };
    System.out.printf("swapping\n%s and \n%s\n", formatMoves(cellA), formatMoves(cellB));
    //*/

    int tmp = board[rowA][colA];
    board[rowA][colA] = board[rowB][colB];
    board[rowB][colB] = tmp;
  }

  if (verbose) {
    printMoves(move);
    printBoard();
  }
}

	public boolean isSolved() {
		int c = 1;
		for (int row = 0; row < 3; row++) {
			for (int col = 0; col < 3; col++) {
				if (board[row][col] != c) {
					return false;
				}
				c++;
			}
		}
		return true;
	}

	public void printBoard() {
		for (int row = 0; row < 3; row++) {
			System.out.printf("%2d %2d %2d\n", board[row][0], board[row][1],
					board[row][2]);
		}
	}

	public String formatMoves(int[] ... moves) {
		StringBuilder b = new StringBuilder();

		for (int r = 0; r < 3; r++) {
			for (int m = 0; m < moves.length; m++) {
				int[]move = moves[m];
				for (int c = 0; c < 3; c++) {
					boolean inrange = r >= move[0] && r < move[0] + move[2]
							&& c >= move[1] && c < move[1] + move[3];
	
					b.append(inrange ? "o" : ".");
				}
				b.append(" -- ");
			}
			b.append("\n");
		}
		return b.toString();
	}

	public void printMoves(List<int[]> moves) {
		System.out.println(formatMoves((int[][]) moves.toArray(new int[moves.size()][])));
	}
	public void printMoves(int[] ... moves) {
		System.out.println(formatMoves(moves));
	}

}
