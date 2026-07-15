package gr.ckaramolegkos.tictactoe;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.util.TypedValue;
import android.view.Gravity;
import android.widget.GridLayout;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import gr.ckaramolegkos.tictactoe.model.Board;
import gr.ckaramolegkos.tictactoe.model.Difficulty;
import gr.ckaramolegkos.tictactoe.model.GameAi;

public class GameActivity extends AppCompatActivity {

    private static final String STATE_BOARD = "board";
    private static final String STATE_TURN = "currentTurn";
    private static final String STATE_P1 = "player1name";
    private static final String STATE_P2 = "player2name";
    private static final String STATE_VS = "vsComputer";
    private static final String STATE_DIFF = "difficulty";
    private static final String STATE_SIZE = "size";
    private static final String STATE_BUSY = "inputLocked";

    private Board board;
    private int size;
    private int currentTurn = 1; // odd = P1
    private boolean vsComputer;
    private Difficulty difficulty = Difficulty.EASY;
    private String player1name;
    private String player2name;

    private TextView turnLabel;
    private ImageButton[][] cells;
    private final GameAi ai = new GameAi();
    private final Handler mainHandler = new Handler(Looper.getMainLooper());
    private final ExecutorService aiExecutor = Executors.newSingleThreadExecutor();
    private boolean inputLocked;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);

        turnLabel = findViewById(R.id.turn);
        GridLayout grid = findViewById(R.id.boardGrid);

        if (savedInstanceState == null) {
            Intent intent = getIntent();
            size = intent.getIntExtra(PlayersActivity.EXTRA_GAME_TYPE, 3);
            player1name = intent.getStringExtra(PlayersActivity.EXTRA_PLAYER1);
            vsComputer = intent.getBooleanExtra(PlayersActivity.EXTRA_VS_COMPUTER, false);
            difficulty = Difficulty.fromName(intent.getStringExtra(PlayersActivity.EXTRA_DIFFICULTY));
            if (vsComputer) {
                player2name = getString(R.string.computer_name);
            } else {
                player2name = intent.getStringExtra(PlayersActivity.EXTRA_PLAYER2);
            }
            board = new Board(size);
            currentTurn = 1;
            inputLocked = false;
        } else {
            size = savedInstanceState.getInt(STATE_SIZE, 3);
            player1name = savedInstanceState.getString(STATE_P1);
            player2name = savedInstanceState.getString(STATE_P2);
            vsComputer = savedInstanceState.getBoolean(STATE_VS);
            difficulty = Difficulty.fromName(savedInstanceState.getString(STATE_DIFF));
            currentTurn = savedInstanceState.getInt(STATE_TURN, 1);
            inputLocked = savedInstanceState.getBoolean(STATE_BUSY, false);
            board = Board.fromFlat(size, savedInstanceState.getIntArray(STATE_BOARD));
        }

        buildGrid(grid);
        refreshBoardUi();
        updateTurnLabel();

        if (!inputLocked && isComputerTurn() && board.outcome() == Board.ONGOING) {
            scheduleComputerMove();
        }
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putIntArray(STATE_BOARD, board.toFlat());
        outState.putInt(STATE_TURN, currentTurn);
        outState.putString(STATE_P1, player1name);
        outState.putString(STATE_P2, player2name);
        outState.putBoolean(STATE_VS, vsComputer);
        outState.putString(STATE_DIFF, difficulty.name());
        outState.putInt(STATE_SIZE, size);
        outState.putBoolean(STATE_BUSY, inputLocked);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        aiExecutor.shutdownNow();
        mainHandler.removeCallbacksAndMessages(null);
    }

    private void buildGrid(GridLayout grid) {
        grid.removeAllViews();
        grid.setColumnCount(size);
        grid.setRowCount(size);
        cells = new ImageButton[size][size];

        int cellPx = (int) TypedValue.applyDimension(
                TypedValue.COMPLEX_UNIT_DIP,
                size == 3 ? 100f : 76f,
                getResources().getDisplayMetrics());

        for (int r = 0; r < size; r++) {
            for (int c = 0; c < size; c++) {
                ImageButton btn = new ImageButton(this);
                GridLayout.LayoutParams lp = new GridLayout.LayoutParams(
                        GridLayout.spec(r, 1f),
                        GridLayout.spec(c, 1f));
                lp.width = cellPx;
                lp.height = cellPx;
                lp.setGravity(Gravity.CENTER);
                btn.setLayoutParams(lp);
                btn.setBackgroundColor(ContextCompat.getColor(this, R.color.white));
                btn.setScaleType(ImageButton.ScaleType.FIT_CENTER);
                btn.setPadding(8, 8, 8, 8);
                final int row = r;
                final int col = c;
                btn.setOnClickListener(v -> onCellClicked(row, col));
                cells[r][c] = btn;
                grid.addView(btn);
            }
        }
    }

    private void onCellClicked(int row, int col) {
        if (inputLocked || board.outcome() != Board.ONGOING) {
            return;
        }
        if (isComputerTurn()) {
            return;
        }
        if (!board.isEmpty(row, col)) {
            return;
        }
        applyMove(row, col);
    }

    private void applyMove(int row, int col) {
        int player = (currentTurn % 2 != 0) ? Board.P1 : Board.P2;
        if (!board.place(row, col, player)) {
            return;
        }
        paintCell(row, col, player);
        cells[row][col].setClickable(false);
        currentTurn++;

        int outcome = board.outcome();
        if (outcome != Board.ONGOING) {
            inputLocked = true;
            showEndDialog(outcome);
            return;
        }
        updateTurnLabel();
        if (isComputerTurn()) {
            scheduleComputerMove();
        }
    }

    private boolean isComputerTurn() {
        return vsComputer && currentTurn % 2 == 0;
    }

    private void scheduleComputerMove() {
        inputLocked = true;
        mainHandler.postDelayed(() -> {
            if (isFinishing() || board.outcome() != Board.ONGOING) {
                return;
            }
            aiExecutor.execute(() -> {
                Board snapshot = board.copy();
                int[] move = ai.chooseMove(snapshot, Board.P2, difficulty);
                mainHandler.post(() -> {
                    if (isFinishing() || move == null) {
                        inputLocked = false;
                        return;
                    }
                    inputLocked = false;
                    applyMove(move[0], move[1]);
                });
            });
        }, 500);
    }

    private void updateTurnLabel() {
        String name = (currentTurn % 2 != 0) ? player1name : player2name;
        turnLabel.setText(getString(R.string.your_turn, name));
    }

    private void refreshBoardUi() {
        for (int r = 0; r < size; r++) {
            for (int c = 0; c < size; c++) {
                int v = board.getCell(r, c);
                paintCell(r, c, v);
                cells[r][c].setClickable(v == Board.EMPTY && board.outcome() == Board.ONGOING);
            }
        }
    }

    private void paintCell(int row, int col, int player) {
        ImageButton btn = cells[row][col];
        if (player == Board.P1) {
            btn.setImageResource(R.drawable.player1);
        } else if (player == Board.P2) {
            btn.setImageResource(R.drawable.player2);
        } else {
            btn.setImageDrawable(null);
        }
    }

    private void showEndDialog(int outcome) {
        String message;
        if (outcome == Board.WIN_P1) {
            message = getString(R.string.won_game, player1name);
        } else if (outcome == Board.WIN_P2) {
            message = getString(R.string.won_game, player2name);
        } else {
            message = getString(R.string.draw_game);
        }

        new AlertDialog.Builder(this)
                .setTitle(R.string.play_again_title)
                .setMessage(message)
                .setPositiveButton(R.string.same_names, (d, w) -> {
                    Intent again = new Intent(this, GameActivity.class);
                    again.putExtra(PlayersActivity.EXTRA_GAME_TYPE, size);
                    again.putExtra(PlayersActivity.EXTRA_PLAYER1, player1name);
                    again.putExtra(PlayersActivity.EXTRA_PLAYER2, player2name);
                    again.putExtra(PlayersActivity.EXTRA_VS_COMPUTER, vsComputer);
                    again.putExtra(PlayersActivity.EXTRA_DIFFICULTY, difficulty.name());
                    startActivity(again);
                    finish();
                })
                .setNegativeButton(R.string.different_names, (d, w) -> {
                    Intent names = new Intent(this, PlayersActivity.class);
                    names.putExtra(PlayersActivity.EXTRA_GAME_TYPE, size);
                    startActivity(names);
                    finish();
                })
                .setNeutralButton(R.string.quit, (d, w) -> {
                    startActivity(new Intent(this, MainActivity.class));
                    finish();
                })
                .setCancelable(false)
                .show();
    }
}
