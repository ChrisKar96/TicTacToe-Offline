package net.ddns.christoskaramo.tictactoe;

import android.content.Intent;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.style.ForegroundColorSpan;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import static java.lang.System.exit;

public class MainActivity extends AppCompatActivity {

    TextView title;
    Button start3x3, start4x4, exit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        title = findViewById(R.id.title);
        Spannable wordtoSpan = new SpannableString("TicTacToe");
        wordtoSpan.setSpan(new ForegroundColorSpan(Color.RED), 0, 0, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        wordtoSpan.setSpan(new ForegroundColorSpan(Color.BLUE), 1, 3, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        wordtoSpan.setSpan(new ForegroundColorSpan(Color.RED), 4, 4, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        wordtoSpan.setSpan(new ForegroundColorSpan(Color.BLUE), 4, 6, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        wordtoSpan.setSpan(new ForegroundColorSpan(Color.RED), 6, 6, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        wordtoSpan.setSpan(new ForegroundColorSpan(Color.BLUE), 7, 9, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        title.setText(wordtoSpan);

        start3x3 = findViewById(R.id.start3x3);
        start3x3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getApplicationContext(),Players.class);
                i.putExtra("gameType", 3);
                startActivity(i);
                finish();
            }
        });

        start4x4 = findViewById(R.id.start4x4);
        start4x4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getApplicationContext(),Players.class);
                i.putExtra("gameType", 4);
                startActivity(i);
                finish();
            }
        });

        exit = findViewById(R.id.exit);
        exit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                exit(0);
            }
        });
    }
}
