package gr.ckaramolegkos.tictactoe.model;

import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class BoardTest {

    @Test
    public void placeRejectsOccupiedCell() {
        Board b = new Board(3);
        assertTrue(b.place(0, 0, Board.P1));
        assertFalse(b.place(0, 0, Board.P2));
        assertEquals(1, b.getMoves());
    }

    @Test
    public void detectsRowWin() {
        Board b = new Board(3);
        b.place(1, 0, Board.P1);
        b.place(1, 1, Board.P1);
        b.place(1, 2, Board.P1);
        assertEquals(Board.WIN_P1, b.outcome());
    }

    @Test
    public void detectsColumnWin() {
        Board b = new Board(3);
        b.place(0, 2, Board.P2);
        b.place(1, 2, Board.P2);
        b.place(2, 2, Board.P2);
        assertEquals(Board.WIN_P2, b.outcome());
    }

    @Test
    public void detectsDiagonalWin() {
        Board b = new Board(3);
        b.place(0, 0, Board.P1);
        b.place(1, 1, Board.P1);
        b.place(2, 2, Board.P1);
        assertEquals(Board.WIN_P1, b.outcome());
    }

    @Test
    public void detectsAntiDiagonalWin() {
        Board b = new Board(3);
        b.place(0, 2, Board.P2);
        b.place(1, 1, Board.P2);
        b.place(2, 0, Board.P2);
        assertEquals(Board.WIN_P2, b.outcome());
    }

    @Test
    public void fourByFourUsesThreeInARow() {
        Board b = new Board(4);
        // Win with three consecutive, not needing full row of 4
        b.place(0, 1, Board.P1);
        b.place(0, 2, Board.P1);
        b.place(0, 3, Board.P1);
        assertEquals(Board.WIN_P1, b.outcome());
    }

    @Test
    public void drawWhenBoardFullWithoutWinner() {
        Board b = new Board(3);
        // X O X
        // X O O
        // O X X
        int[][] moves = {
                {0, 0, Board.P1}, {0, 1, Board.P2}, {0, 2, Board.P1},
                {1, 0, Board.P1}, {1, 1, Board.P2}, {1, 2, Board.P2},
                {2, 0, Board.P2}, {2, 1, Board.P1}, {2, 2, Board.P1},
        };
        for (int[] m : moves) {
            assertTrue(b.place(m[0], m[1], m[2]));
        }
        assertEquals(Board.DRAW, b.outcome());
    }

    @Test
    public void copyIsIndependent() {
        Board b = new Board(3);
        b.place(0, 0, Board.P1);
        Board c = b.copy();
        c.place(1, 1, Board.P2);
        assertEquals(Board.EMPTY, b.getCell(1, 1));
        assertEquals(Board.P2, c.getCell(1, 1));
    }

    @Test
    public void flatRoundTrip() {
        Board b = new Board(4);
        b.place(2, 3, Board.P2);
        Board restored = Board.fromFlat(4, b.toFlat());
        assertEquals(Board.P2, restored.getCell(2, 3));
        assertEquals(1, restored.getMoves());
    }
}
