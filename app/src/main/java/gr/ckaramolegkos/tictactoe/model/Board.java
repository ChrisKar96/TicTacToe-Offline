package gr.ckaramolegkos.tictactoe.model;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Pure tic-tac-toe board. Win is any 3-in-a-row (including on 4x4 boards),
 * matching the original app behaviour.
 */
public final class Board {
    public static final int EMPTY = 0;
    public static final int P1 = 1;
    public static final int P2 = 2;

    public static final int ONGOING = 0;
    public static final int WIN_P1 = 1;
    public static final int WIN_P2 = 2;
    public static final int DRAW = 3;

    private final int size;
    private final int[][] cells;
    private int moves;

    public Board(int size) {
        if (size != 3 && size != 4) {
            throw new IllegalArgumentException("size must be 3 or 4");
        }
        this.size = size;
        this.cells = new int[size][size];
        this.moves = 0;
    }

    private Board(int size, int[][] cells, int moves) {
        this.size = size;
        this.cells = cells;
        this.moves = moves;
    }

    public int getSize() {
        return size;
    }

    public int getMoves() {
        return moves;
    }

    public int getCell(int row, int col) {
        return cells[row][col];
    }

    public boolean isEmpty(int row, int col) {
        return cells[row][col] == EMPTY;
    }

    public int maxMoves() {
        return size * size;
    }

    /** Place a mark. Returns false if the cell is occupied or out of range. */
    public boolean place(int row, int col, int player) {
        if (row < 0 || col < 0 || row >= size || col >= size) {
            return false;
        }
        if (cells[row][col] != EMPTY) {
            return false;
        }
        if (player != P1 && player != P2) {
            return false;
        }
        cells[row][col] = player;
        moves++;
        return true;
    }

    public void clear(int row, int col) {
        if (cells[row][col] != EMPTY) {
            cells[row][col] = EMPTY;
            moves--;
        }
    }

    public List<int[]> emptyCells() {
        List<int[]> out = new ArrayList<>();
        for (int r = 0; r < size; r++) {
            for (int c = 0; c < size; c++) {
                if (cells[r][c] == EMPTY) {
                    out.add(new int[]{r, c});
                }
            }
        }
        return out;
    }

    /**
     * Outcome after the latest move, if any.
     * Checks for three consecutive marks of the same player.
     */
    public int outcome() {
        if (hasThreeInARow(P1)) {
            return WIN_P1;
        }
        if (hasThreeInARow(P2)) {
            return WIN_P2;
        }
        if (moves >= maxMoves()) {
            return DRAW;
        }
        return ONGOING;
    }

    public boolean hasThreeInARow(int player) {
        // rows
        for (int r = 0; r < size; r++) {
            for (int c = 0; c <= size - 3; c++) {
                if (cells[r][c] == player
                        && cells[r][c + 1] == player
                        && cells[r][c + 2] == player) {
                    return true;
                }
            }
        }
        // cols
        for (int c = 0; c < size; c++) {
            for (int r = 0; r <= size - 3; r++) {
                if (cells[r][c] == player
                        && cells[r + 1][c] == player
                        && cells[r + 2][c] == player) {
                    return true;
                }
            }
        }
        // main diagonals
        for (int r = 0; r <= size - 3; r++) {
            for (int c = 0; c <= size - 3; c++) {
                if (cells[r][c] == player
                        && cells[r + 1][c + 1] == player
                        && cells[r + 2][c + 2] == player) {
                    return true;
                }
            }
        }
        // anti-diagonals
        for (int r = 0; r <= size - 3; r++) {
            for (int c = 2; c < size; c++) {
                if (cells[r][c] == player
                        && cells[r + 1][c - 1] == player
                        && cells[r + 2][c - 2] == player) {
                    return true;
                }
            }
        }
        return false;
    }

    public Board copy() {
        int[][] copy = new int[size][size];
        for (int r = 0; r < size; r++) {
            copy[r] = Arrays.copyOf(cells[r], size);
        }
        return new Board(size, copy, moves);
    }

    /** Flat snapshot for savedInstanceState. */
    public int[] toFlat() {
        int[] flat = new int[size * size];
        int i = 0;
        for (int r = 0; r < size; r++) {
            for (int c = 0; c < size; c++) {
                flat[i++] = cells[r][c];
            }
        }
        return flat;
    }

    public static Board fromFlat(int size, int[] flat) {
        Board b = new Board(size);
        int i = 0;
        for (int r = 0; r < size; r++) {
            for (int c = 0; c < size; c++) {
                int v = flat[i++];
                if (v != EMPTY) {
                    b.cells[r][c] = v;
                    b.moves++;
                }
            }
        }
        return b;
    }
}
