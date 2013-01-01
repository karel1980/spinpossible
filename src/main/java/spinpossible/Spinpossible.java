package spinpossible;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Spinpossible {

    Board board;
    List<Move> bestSolution;
    int maxMoves;

    long last;

    public static void main(String[] args) {
        for (String arg: args) {
            Spinpossible s = loadLevel(new File(arg));
            System.out.println("Trying to find best solution for\n" + s.getBoard().toString());
            List<Move> moves = s.solve();
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

        int maxMoves = 0;
        int[] cells = new int[9];

        try {
            reader = new BufferedReader(new FileReader(file));
            String line = reader.readLine().trim();
            char[] chars = line.toCharArray();

            int pos = 0;
            for (int i = 0; i<9;i++) {
                int sign = 1;
                while (chars[pos] == '-') {
                    sign = -sign;
                    pos++;
                }
                cells[i] = sign * Integer.parseInt(new String(new char[] {chars[pos++]}));
            }

            maxMoves = Integer.parseInt(line.substring(line.indexOf("s")+1));

        } catch (IOException e) {

            if (reader != null) { try {reader.close();} catch (Exception ignored) {} }
        }

        return new Spinpossible(new Board(cells), maxMoves);
    }

    public Spinpossible(Board board, int maxMoves) {
        this.board = board;
        this.maxMoves = maxMoves;
        last = System.currentTimeMillis();
    }

    public List<Move> solve() {
        bestSolution = null;
        for (int i = 0; i <= maxMoves; i++) {
            System.out.printf("Looking for %d move solutions.\n", i);
            solve(new ArrayList<Move>(), i);
            if (bestSolution != null) {
                return bestSolution;
            }
            System.out.printf("No %d move solution was found.\n", i);
        }

        return bestSolution;
    }

    public void solve(List<Move> moves, int movesLeft) {
        if (bestSolution != null) {
            return;
        }
        if (movesLeft <= 0) {
            if (board.isSolved()) {
                bestSolution = new ArrayList<Move>(moves);
            }
            return;
        }

        for (int top = 0; top < 3; top++) {
            for (int left = 0; left < 3; left++) {
                for (int bottom = top; bottom < 3; bottom++) {
                    for (int right = 0; right < 3; right++) {
                        tryMove(moves, new Move(top, left, bottom, right), movesLeft);
                    }
                }
            }
        }
    }

    public void tryMove(List<Move> moves, Move newMove, int movesLeft) {
        moves.add(newMove);
        board.apply(newMove);
        solve(moves, movesLeft - 1);
        board.apply(newMove);
        moves.remove(moves.size() - 1);
    }

    public static String formatMoves(List<Move> moves) {
        StringBuilder b = new StringBuilder();

        for (Move move: moves) {
            b.append(move.top*3 + move.left + 1);
            b.append("-");
            b.append(move.bottom*3 + move.right + 1);
            b.append("  ");
        }
        return b.toString();
    }
    public static String formatMoves(Move ... moves) {
        return formatMoves(Arrays.asList(moves));
    }

    public void printMoves(List<Move> moves) {
        System.out.println(formatMoves(moves));
    }
    public void printMoves(Move ... moves) {
        System.out.println(formatMoves(Arrays.asList(moves)));
    }

    public Board getBoard() {
        return board;
    }
}
