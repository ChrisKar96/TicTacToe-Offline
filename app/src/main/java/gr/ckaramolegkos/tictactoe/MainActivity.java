package gr.ckaramolegkos.tictactoe;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.style.ForegroundColorSpan;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        TextView title = findViewById(R.id.title);
        Spannable wordtoSpan = new SpannableString("TicTacToe");
        wordtoSpan.setSpan(new ForegroundColorSpan(Color.RED), 0, 1, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        wordtoSpan.setSpan(new ForegroundColorSpan(Color.BLUE), 1, 3, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        wordtoSpan.setSpan(new ForegroundColorSpan(Color.RED), 3, 4, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        wordtoSpan.setSpan(new ForegroundColorSpan(Color.BLUE), 4, 6, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        wordtoSpan.setSpan(new ForegroundColorSpan(Color.RED), 6, 7, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        wordtoSpan.setSpan(new ForegroundColorSpan(Color.BLUE), 7, 9, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        title.setText(wordtoSpan);

        Button start3x3 = findViewById(R.id.start3x3);
        start3x3.setOnClickListener(v -> openPlayers(3));

        Button start4x4 = findViewById(R.id.start4x4);
        start4x4.setOnClickListener(v -> openPlayers(4));

        Button exit = findViewById(R.id.exit);
        exit.setOnClickListener(v -> finishAffinity());
    }

    private void openPlayers(int gameType) {
        Intent i = new Intent(this, PlayersActivity.class);
        i.putExtra(PlayersActivity.EXTRA_GAME_TYPE, gameType);
        startActivity(i);
    }
}
