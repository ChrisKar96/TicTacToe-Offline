package net.ddns.christoskaramo.tictactoe;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class Players extends AppCompatActivity {

    EditText player1, player2;
    TextView player2label;
    CheckBox vsComputer;
    Button start;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_players);

        final int gameType = getIntent().getIntExtra("gameType", 0 );

        player1 = findViewById(R.id.player1);
        player2 = findViewById(R.id.player2);
        player2label = findViewById(R.id.player2label);
        vsComputer = findViewById(R.id.vsComputer);
        start = findViewById(R.id.start);

        vsComputer.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if(b){
                    player2.setVisibility(View.INVISIBLE);
                    player2label.setVisibility(View.INVISIBLE);
                }
                else {
                    player2.setVisibility(View.VISIBLE);
                    player2label.setVisibility(View.VISIBLE);
                }
            }
        });

        start.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (player1.getText().toString().isEmpty() || (!vsComputer.isChecked() && player2.getText().toString().isEmpty())){
                    Toast t = Toast.makeText(getApplicationContext(), "Fill the player names to start the game", Toast.LENGTH_SHORT);
                    t.show();
                }
                else {
                    Intent i;
                    switch (gameType) {
                        case 3:
                            i = new Intent(getApplicationContext(), Game3x3.class);
                            break;
                        case 4:
                            i = new Intent(getApplicationContext(), Game4x4.class);
                            break;
                        default:
                            i = new Intent(getApplicationContext(), MainActivity.class);
                    }
                    i.putExtra("Player1", player1.getText().toString());
                    i.putExtra("Player2", player2.getText().toString());
                    i.putExtra("vsComputer", vsComputer.isChecked());
                    startActivity(i);
                    finish();
                }
            }
        });

    }
}
