package gr.ckaramolegkos.tictactoe.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

/**
 * Easy: random empty cell.
 * Hard: alpha-beta minimax (full tree on 3x3; depth-limited on 4x4).
 */
public final class GameAi {
    private final Random random;

    public GameAi() {
        this(new Random());
    }

    public GameAi(Random random) {
        this.random = random;
    }

    /**
     * @return int[]{row, col} or null if no moves
     */
    public int[] chooseMove(Board board, int aiPlayer, Difficulty difficulty) {
        List<int[]> empty = board.emptyCells();
        if (empty.isEmpty()) {
            return null;
        }
        if (difficulty == Difficulty.EASY) {
            return empty.get(random.nextInt(empty.size()));
        }
        return minimaxMove(board, aiPlayer);
    }

    private int[] minimaxMove(Board board, int aiPlayer) {
        int opponent = aiPlayer == Board.P1 ? Board.P2 : Board.P1;
        int depthLimit = board.getSize() == 3 ? 9 : 6;

        int bestScore = Integer.MIN_VALUE;
        List<int[]> bestMoves = new ArrayList<>();

        List<int[]> candidates = board.emptyCells();
        // Prefer center-ish cells first for better pruning.
        candidates.sort((a, b) -> {
            int ca = Math.abs(a[0] - board.getSize() / 2) + Math.abs(a[1] - board.getSize() / 2);
            int cb = Math.abs(b[0] - board.getSize() / 2) + Math.abs(b[1] - board.getSize() / 2);
            return Integer.compare(ca, cb);
        });

        for (int[] cell : candidates) {
            board.place(cell[0], cell[1], aiPlayer);
            int score = minimax(board, depthLimit - 1, false, aiPlayer, opponent,
                    Integer.MIN_VALUE, Integer.MAX_VALUE);
            board.clear(cell[0], cell[1]);
            if (score > bestScore) {
                bestScore = score;
                bestMoves.clear();
                bestMoves.add(cell);
            } else if (score == bestScore) {
                bestMoves.add(cell);
            }
        }

        if (bestMoves.isEmpty()) {
            return candidates.get(0);
        }
        Collections.shuffle(bestMoves, random);
        return bestMoves.get(0);
    }

    private int minimax(Board board, int depth, boolean maximizing,
                        int aiPlayer, int opponent, int alpha, int beta) {
        int outcome = board.outcome();
        if (outcome == Board.WIN_P1) {
            return aiPlayer == Board.P1 ? 10 + depth : -10 - depth;
        }
        if (outcome == Board.WIN_P2) {
            return aiPlayer == Board.P2 ? 10 + depth : -10 - depth;
        }
        if (outcome == Board.DRAW || depth == 0) {
            return 0;
        }

        if (maximizing) {
            int best = Integer.MIN_VALUE;
            for (int[] cell : board.emptyCells()) {
                board.place(cell[0], cell[1], aiPlayer);
                best = Math.max(best, minimax(board, depth - 1, false, aiPlayer, opponent, alpha, beta));
                board.clear(cell[0], cell[1]);
                alpha = Math.max(alpha, best);
                if (beta <= alpha) {
                    break;
                }
            }
            return best;
        } else {
            int best = Integer.MAX_VALUE;
            for (int[] cell : board.emptyCells()) {
                board.place(cell[0], cell[1], opponent);
                best = Math.min(best, minimax(board, depth - 1, true, aiPlayer, opponent, alpha, beta));
                board.clear(cell[0], cell[1]);
                beta = Math.min(beta, best);
                if (beta <= alpha) {
                    break;
                }
            }
            return best;
        }
    }
}
