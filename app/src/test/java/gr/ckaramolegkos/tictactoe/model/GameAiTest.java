package gr.ckaramolegkos.tictactoe.model;

import org.junit.Test;

import java.util.Random;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

public class GameAiTest {

    @Test
    public void easyPicksEmptyCell() {
        GameAi ai = new GameAi(new Random(1));
        Board b = new Board(3);
        b.place(0, 0, Board.P1);
        int[] move = ai.chooseMove(b, Board.P2, Difficulty.EASY);
        assertNotNull(move);
        assertTrue(b.isEmpty(move[0], move[1]));
    }

    @Test
    public void hardTakesImmediateWin() {
        GameAi ai = new GameAi(new Random(0));
        Board b = new Board(3);
        // P2 can complete top row
        b.place(0, 0, Board.P2);
        b.place(0, 1, Board.P2);
        b.place(1, 0, Board.P1);
        b.place(2, 0, Board.P1);
        int[] move = ai.chooseMove(b, Board.P2, Difficulty.HARD);
        assertNotNull(move);
        assertEquals(0, move[0]);
        assertEquals(2, move[1]);
    }

    @Test
    public void hardBlocksOpponentWin() {
        GameAi ai = new GameAi(new Random(0));
        Board b = new Board(3);
        // P1 threatens top row; AI (P2) must block at (0,2)
        b.place(0, 0, Board.P1);
        b.place(0, 1, Board.P1);
        b.place(1, 1, Board.P2);
        int[] move = ai.chooseMove(b, Board.P2, Difficulty.HARD);
        assertNotNull(move);
        assertEquals(0, move[0]);
        assertEquals(2, move[1]);
    }

    @Test
    public void hardReturnsMoveOnEmpty4x4() {
        GameAi ai = new GameAi(new Random(42));
        Board b = new Board(4);
        int[] move = ai.chooseMove(b, Board.P2, Difficulty.HARD);
        assertNotNull(move);
        assertTrue(move[0] >= 0 && move[0] < 4);
        assertTrue(move[1] >= 0 && move[1] < 4);
    }
}
