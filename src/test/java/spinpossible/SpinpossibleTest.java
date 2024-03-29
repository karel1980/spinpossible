package spinpossible;

import org.junit.Assert;
import org.junit.Test;

public class SpinpossibleTest {

    @Test
    public void testMoves()
    {
        checkMove(0, 0, 1, 2, new int[]{-6,-5,-4,-3,-2,-1,7,8,9});

        // 1x1 moves
        checkMove(0,0,0,0, new int[]{-1,2,3,4,5,6,7,8,9});
        checkMove(0,1,0,1, new int[]{1,-2,3,4,5,6,7,8,9});
        checkMove(0,2,0,2, new int[]{1,2,-3,4,5,6,7,8,9});
        checkMove(1,0,1,0, new int[]{1,2,3,-4,5,6,7,8,9});
        checkMove(1,1,1,1, new int[]{1,2,3,4,-5,6,7,8,9});
        checkMove(1,2,1,2, new int[]{1,2,3,4,5,-6,7,8,9});
        checkMove(2,0,2,0, new int[]{1,2,3,4,5,6,-7,8,9});
        checkMove(2,1,2,1, new int[]{1,2,3,4,5,6,7,-8,9});
        checkMove(2,2,2,2, new int[]{1,2,3,4,5,6,7,8,-9});

        // 1x2 moves
        checkMove(0,0,0,1, new int[]{-2,-1,3,4,5,6,7,8,9});
        checkMove(0,1,0,2, new int[]{1,-3,-2,4,5,6,7,8,9});
        checkMove(1,0,1,1, new int[]{1,2,3,-5,-4,6,7,8,9});
        checkMove(1,1,1,2, new int[]{1,2,3,4,-6,-5,7,8,9});
        checkMove(2,0,2,1, new int[]{1,2,3,4,5,6,-8,-7,9});
        checkMove(2,1,2,2, new int[]{1,2,3,4,5,6,7,-9,-8});

        // 2x1 moves
        checkMove(0,0,0,1, new int[]{-2,-1,3,4,5,6,7,8,9});
        checkMove(0,1,0,2, new int[]{1,-3,-2,4,5,6,7,8,9});
        checkMove(1,0,1,1, new int[]{1,2,3,-5,-4,6,7,8,9});
        checkMove(1,1,1,2, new int[]{1,2,3,4,-6,-5,7,8,9});
        checkMove(2,0,2,1, new int[]{1,2,3,4,5,6,-8,-7,9});
        checkMove(2,1,2,2, new int[]{1,2,3,4,5,6,7,-9,-8});

        // 2x2 moves
        checkMove(0, 0, 1, 1, new int[]{-5,-4,3,-2,-1,6,7,8,9});

        // 2x3 moves
        checkMove(0, 0, 1, 2, new int[]{-6,-5,-4,-3,-2,-1,7,8,9});

        // 3x2 moves
        checkMove(0, 0, 2, 1, new int[]{-8,-7,3,-5,-4,6,-2,-1,9});

        // 3x3 move
        checkMove(0, 0, 2, 2, new int[]{-9,-8,-7,-6,-5,-4,-3,-2,-1});
    }

    public void checkMove(int top, int left, int bottom, int right, int[]initial) {
        Board board = new Board(initial);
        board.apply(new Move(top, left, bottom, right));
        Assert.assertTrue(board.isSolved());
    }
    
    public String boardString(int[][]board) {
    	StringBuilder b = new StringBuilder();
    	for (int i = 0; i < 3; i++) {
    		b.append(String.format("%2d, %2d, %2d", board[i][0],board[i][1],board[i][2]));
    		b.append("\n");
    	}
    	return b.toString();
    }
}
