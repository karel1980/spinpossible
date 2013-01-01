package spinpossible;

public class Board {

    private int[][] cells;

    public Board() {
        this(new int[] { 1,2,3,4,5,6,7,8,9 });
    }

    public Board(int[] cells) {
        this.cells = new int[3][];
        for (int i = 0; i < 3; i++) {
            this.cells[i] = new int[3];
        }
        for (int i = 0; i < 9; i++) {
            this.cells[i/3][i%3] = cells[i];
        }
    }

    int get(int row, int col) {
        return cells[row][col];
    }

    public String toString() {
        StringBuilder b = new StringBuilder();
        for (int r = 0; r < 3; r++) {
            b.append(String.format(" * %2d %2d %2d *\n", cells[r][0], cells[r][1], cells[r][2]));
        }
        return b.toString();
    }

    public void apply(Move move) {
        apply(move, false);
    }

    public void apply(Move move, boolean verbose) {

        // make negative
        for (int row = move.top; row <= move.bottom; row++) {
            for (int col = move.left; col <= move.right; col++) {
                cells[row][col] = -cells[row][col];
            }
        }

        // even # rows => midRow is first row of the second half
        // odd # rows => midRow is the center row
        int centerRow = move.top + (move.bottom-move.top+1)/2;

        for (int row = move.top; row < centerRow; row++) {
            for (int col = move.left; col <= move.right; col++) {
                int row2 = move.bottom - (row - move.top);
                int col2 = move.right  - (col - move.left);
                swap(row,col,row2,col2);
            }
        }

        // if the number of rows is odd
        if ((move.bottom - move.top +1)%2 == 1) {
            int centerCol = move.left + (move.right-move.left+1)/2;
            for (int col = move.left; col < centerCol; col++) {
                int col2 = move.right  - (col - move.left);
                swap(centerRow,col,centerRow,col2);
            }
        }

        if (verbose) {
            System.out.println(move);
            System.out.println(this.toString());
        }
    }

    private void swap(int r1, int c1, int r2, int c2) {
        int tmp = cells[r1][c1];
        cells[r1][c1] = cells[r2][c2];
        cells[r2][c2] = tmp;
    }

    public boolean isSolved() {
        int c = 1;
        for (int row = 0; row < 3; row++) {
            for (int col = 0; col < 3; col++) {
                if (cells[row][col] != c) {
                    return false;
                }
                c++;
            }
        }
        return true;
    }


}
