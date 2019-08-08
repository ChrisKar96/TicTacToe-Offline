package net.ddns.christoskaramo.tictactoe;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;

abstract class Game  extends AppCompatActivity implements View.OnClickListener{
    Boolean vsComputer;
    String player1name,player2name;
    TextView turn;

    final int WIN1 = 150;
    final int WIN2 = 200;
    final int DRAW = 175;

    int board[][];

    int maxTurns;
    int currentTurn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        if( savedInstanceState == null) {
            Intent intent = getIntent();
            player1name = intent.getStringExtra("Player1");
            vsComputer = intent.getBooleanExtra("vsComputer", false);
            if (vsComputer) {
                player2name = "Computer";
            } else {
                player2name = intent.getStringExtra("Player2");
            }

            currentTurn = 1;
        }
        else{
            player1name = savedInstanceState.getString("player1name");
            player2name = savedInstanceState.getString("player2name");
            vsComputer = savedInstanceState.getBoolean("vsComputer");
            currentTurn = savedInstanceState.getInt("currentTurn");
        }
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putString("player1name", player1name);
        outState.putString("player2name", player2name);
        outState.putBoolean("vsComputer", vsComputer);
        outState.putInt("currentTurn", currentTurn);
    }

    protected void changeTurn() {
        if(currentTurn%2!=0){
            turn.setText(String.format(getString(R.string.your_turn), player1name));
        }
        else{
            turn.setText(String.format(getString(R.string.your_turn), player2name));
            if(vsComputer)
                (new Handler()).postDelayed(this::makeMove, 700);
        }
    }

    protected abstract void makeMove();
    protected abstract int findButton(int i, int j);


    int checkWin(int x, int y, int n){
        n--;
        boolean win=false;
        int player;
        if(currentTurn%2==0)
            player=1;
        else
            player=2;

        //check col
        if(x==0) {
            if (board[x][y] == board[x + 1][y] && board[x][y] == board[x + 2][y])
                win = true;
        }
        else if(x==n) {
            if (board[x][y] == board[x - 1][y] && board[x][y] == board[x - 2][y])
                win = true;
        }
        else {
            if(x+2<=n)
                if (board[x][y] == board[x + 1][y] && board[x][y] == board[x + 2][y])
                    win = true;
            if(x-2>=0)
                if (board[x][y] == board[x - 1][y] && board[x][y] == board[x - 2][y])
                    win = true;
            if (board[x][y] == board[x - 1][y] && board[x][y] == board[x + 1][y])
                win = true;
        }

        //check row
        if (y == 0) {
            if (board[x][y] == board[x][y + 1] && board[x][y] == board[x][y + 2])
                win = true;
        } else if (y == n) {
            if (board[x][y] == board[x][y - 1] && board[x][y] == board[x][y - 2])
                win = true;
        } else {
            if(y+2<=n)
                if (board[x][y] == board[x][y + 1] && board[x][y] == board[x][y + 2])
                    win = true;
            if(y-2>=0)
                if (board[x][y] == board[x][y - 1] && board[x][y] == board[x][y - 2])
                    win = true;
            if (board[x][y] == board[x][y - 1] && board[x][y] == board[x][y + 1])
                win = true;
        }

        //check diag
        if(x==0 && y+2<=n) {
            if (board[x][y] == board[x + 1][y + 1] && board[x][y] == board[x + 2][y + 2])
                win = true;
        }
        else if(x==n && y-2>=0) {
            if (board[x][y] == board[x - 1][y - 1] && board[x][y] == board[x - 2][y - 2])
                win = true;
        }
        else {
            if(x+2<=n && y+2<=n)
                if (board[x][y] == board[x + 1][y + 1] && board[x][y] == board[x + 2][y + 2])
                    win = true;
            if(x-2>=0 && y-2>=0)
                if (board[x][y] == board[x - 1][y - 1] && board[x][y] == board[x - 2][y - 2])
                    win = true;
            if(x-1>=0 && y-1>=0 && x+1<=n && y+1<=n)
                if (board[x][y] == board[x - 1][y - 1] && board[x][y] == board[x + 1][y + 1])
                    win = true;
        }

        //check anti diag
        if(x==0 && y-2 >= 0) {
            if (board[x][y] == board[x + 1][y - 1] && board[x][y] == board[x + 2][y - 2])
                win = true;
        }
        else if(x==n && y+2 <= n) {
            if (board[x][y] == board[x - 1][y + 1] && board[x][y] == board[x - 2][y + 2])
                win = true;
        }
        else {
            if(x+2<=n && y-2>=0)
                if (board[x][y] == board[x + 1][y - 1] && board[x][y] == board[x + 2][y - 2])
                    win = true;
            if(x-2>=0 && y+2<=n)
                if (board[x][y] == board[x - 1][y + 1] && board[x][y] == board[x - 2][y + 2])
                    win = true;
            if(x+1<=n && y-1>=0 && y+1<=n && x-1>=0)
                if (board[x][y] == board[x + 1][y - 1] && board[x][y] == board[x - 1][y + 1])
                    win = true;
        }

        if(win){
            switch (player){
                case 1:
                    return WIN1;
                case 2:
                    return WIN2;
            }
        }

        //check draw
        if(currentTurn > maxTurns){
            return DRAW;
        }

        return 0;
    }

    public void replay(String message, final int gameType){
        AlertDialog.Builder builder = new AlertDialog.Builder(Game.this);
        builder.setTitle("Do you want to play again?")
            .setMessage(message)
            .setPositiveButton("Yes! Same names", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int which) {
                    Intent playAgain;
                    switch (gameType) {
                        case 3:
                            playAgain = new Intent(getApplicationContext(), Game3x3.class);
                            break;
                        case 4:
                            playAgain = new Intent(getApplicationContext(), Game4x4.class);
                            break;
                        default:
                            playAgain = new Intent(getApplicationContext(), MainActivity.class);
                    }
                    playAgain.putExtra("Player1", player1name);
                    playAgain.putExtra("Player2", player2name);
                    playAgain.putExtra("vsComputer", vsComputer);
                    startActivity(playAgain);
                    finish();
                }
            }).setNegativeButton("Yes! Different names", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int which) {
                    Intent playAgain;
                    playAgain = new Intent(getApplicationContext(),Players.class);
                    playAgain.putExtra("gameType", gameType);
                    startActivity(playAgain);
                    finish();
                }
            }).setNeutralButton("Quit!", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int which) {
                    Intent goToStart;
                    goToStart = new Intent(getApplicationContext(),MainActivity.class);
                    startActivity(goToStart);
                    finish();
                }
            })
            .setIcon(android.R.drawable.checkbox_on_background)
            .setCancelable(false)
            .show();
    }

}
