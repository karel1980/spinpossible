package spinpossible;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.List;
import java.util.ArrayList;

public class Spinpossible {

	int[][] board;
    int[][] bestSolution;
    int maxMoves;

	long last;
	long solveCalls = 0;

    public static void main(String[] args) {
        for (String arg: args) {
            Spinpossible s = loadLevel(new File(arg));
            System.out.println("Trying to find best solution for\n" + formatBoard(s.board));
            int moves[][] = s.solve();
            if (moves == null) {
                System.out.println("Found no solution for " + arg);
            } else {
                System.out.println("Best solution for solving " + arg + ":");
                System.out.println(formatMoves(moves));
            }
        }
	}

    private static Spinpossible loadLevel(File file) {
        BufferedReader reader = null;
        List<int[]> board = new ArrayList<int[]>();
        int maxMoves = 0;

        try {
            reader = new BufferedReader(new FileReader(file));

            maxMoves = Integer.parseInt(reader.readLine());

            for (int i = 0; i<3; i++) {
                String line = reader.readLine().trim();
                String[] cells = line.split(" +");

                int[] intCells = new int[cells.length];
                for (int j = 0; j < cells.length; j++) {
                    intCells[j] = Integer.parseInt(cells[j]);
                }
                board.add(intCells);
            }

        } catch (IOException e) {

            if (reader != null) { try {reader.close();} catch (Exception e2) {} }
        }

        int [][] sboard = board.toArray(new int[][] {});
        return new Spinpossible(sboard, maxMoves);
    }

    public Spinpossible(int[][] board, int maxMoves) {
		this.board = board;
        this.maxMoves = maxMoves;
		last = System.currentTimeMillis();
	}

	public int[][] solve() {
        bestSolution = null;
		solve(new ArrayList());
        return bestSolution;
	}

    public void solve(List moves) {
        //TODO: avoid solutions with unnecesary steps
        /*if (bestSolution != null && bestSolution.length <= maxMoves) {
            return;
        }*/
		solveCalls++;
		if (moves.size() > maxMoves) {
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
			if (bestSolution == null || moves.size() < bestSolution.length) {
                System.out.println("Found a " + moves.size() + " move solution:");
                printMoves(moves);
                bestSolution = (int[][]) moves.toArray(new int[][] {});
                return; // this prevents us from looking for solutions with more moves than bestSolution
            }
		}

		if (moves.size() == maxMoves) {
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
        System.out.println(formatBoard(board));
    }

    private static String formatBoard(int[][] board) {
        StringBuilder b = new StringBuilder();
        for (int r = 0; r < 3; r++) {
            b.append(String.format(" * %2d %2d %2d *\n", board[r][0], board[r][1], board[r][2]));
        }
        return b.toString();
    }

    public static String formatMoves(List<int[][]> moves) {
        return formatMoves(moves.toArray(new int[moves.size()][]));
    }
	public static String formatMoves(int[] ... moves) {
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

	public void printMoves(List<int[][]> moves) {
		System.out.println(formatMoves((int[][]) moves.toArray(new int[moves.size()][])));
	}
	public void printMoves(int[] ... moves) {
		System.out.println(formatMoves(moves));
	}

}
