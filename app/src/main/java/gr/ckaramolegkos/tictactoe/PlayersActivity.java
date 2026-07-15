package gr.ckaramolegkos.tictactoe;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import gr.ckaramolegkos.tictactoe.model.Difficulty;

public class PlayersActivity extends AppCompatActivity {

    public static final String EXTRA_GAME_TYPE = "gameType";
    public static final String EXTRA_PLAYER1 = "Player1";
    public static final String EXTRA_PLAYER2 = "Player2";
    public static final String EXTRA_VS_COMPUTER = "vsComputer";
    public static final String EXTRA_DIFFICULTY = "difficulty";

    private EditText player1;
    private EditText player2;
    private TextView player2label;
    private CheckBox vsComputer;
    private RadioGroup difficultyGroup;
    private View difficultyLabel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_players);

        final int gameType = getIntent().getIntExtra(EXTRA_GAME_TYPE, 3);

        player1 = findViewById(R.id.player1);
        player2 = findViewById(R.id.player2);
        player2label = findViewById(R.id.player2label);
        vsComputer = findViewById(R.id.vsComputer);
        difficultyGroup = findViewById(R.id.difficultyGroup);
        difficultyLabel = findViewById(R.id.difficultyLabel);
        Button start = findViewById(R.id.start);

        setDifficultyVisible(false);

        vsComputer.setOnCheckedChangeListener((button, checked) -> {
            int visibility = checked ? View.INVISIBLE : View.VISIBLE;
            player2.setVisibility(visibility);
            player2label.setVisibility(visibility);
            setDifficultyVisible(checked);
        });

        start.setOnClickListener(v -> {
            if (player1.getText().toString().trim().isEmpty()
                    || (!vsComputer.isChecked() && player2.getText().toString().trim().isEmpty())) {
                Toast.makeText(this, R.string.fill_names, Toast.LENGTH_SHORT).show();
                return;
            }

            Intent i = new Intent(this, GameActivity.class);
            i.putExtra(EXTRA_GAME_TYPE, gameType);
            i.putExtra(EXTRA_PLAYER1, player1.getText().toString().trim());
            i.putExtra(EXTRA_PLAYER2, player2.getText().toString().trim());
            i.putExtra(EXTRA_VS_COMPUTER, vsComputer.isChecked());
            i.putExtra(EXTRA_DIFFICULTY, selectedDifficulty().name());
            startActivity(i);
            finish();
        });
    }

    private void setDifficultyVisible(boolean visible) {
        int v = visible ? View.VISIBLE : View.GONE;
        difficultyLabel.setVisibility(v);
        difficultyGroup.setVisibility(v);
    }

    private Difficulty selectedDifficulty() {
        int id = difficultyGroup.getCheckedRadioButtonId();
        if (id == R.id.difficultyHard) {
            return Difficulty.HARD;
        }
        return Difficulty.EASY;
    }
}
