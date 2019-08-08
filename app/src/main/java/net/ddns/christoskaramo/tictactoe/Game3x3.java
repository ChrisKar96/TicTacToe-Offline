package net.ddns.christoskaramo.tictactoe;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;

import java.util.Random;

public class Game3x3 extends Game{

    ImageButton pos1,pos2,pos3,pos4,pos5,pos6,pos7,pos8,pos9;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game3x3);

        board = new int[3][3];

        if(savedInstanceState == null){
            for (int i = 0; i < 3; i++) {
                for (int j = 0; j < 3; j++) {
                    board[i][j] = 0;
                }
            }
        }
        else{
            for(int j=0;j<3;j++){
                board[0][j] = savedInstanceState.getIntArray("board0")[j];
                board[1][j] = savedInstanceState.getIntArray("board1")[j];
                board[2][j] = savedInstanceState.getIntArray("board2")[j];
            }
        }

        turn = findViewById(R.id.turn);

        pos1 = findViewById(R.id.pos1);
        pos2 = findViewById(R.id.pos2);
        pos3 = findViewById(R.id.pos3);
        pos4 = findViewById(R.id.pos4);
        pos5 = findViewById(R.id.pos5);
        pos6 = findViewById(R.id.pos6);
        pos7 = findViewById(R.id.pos7);
        pos8 = findViewById(R.id.pos8);
        pos9 = findViewById(R.id.pos9);

        maxTurns = 9;

        changeTurn();

        pos1.setOnClickListener(this);
        pos2.setOnClickListener(this);
        pos3.setOnClickListener(this);
        pos4.setOnClickListener(this);
        pos5.setOnClickListener(this);
        pos6.setOnClickListener(this);
        pos7.setOnClickListener(this);
        pos8.setOnClickListener(this);
        pos9.setOnClickListener(this);

        for (int i=0;i<3;i++){
            for (int j=0;j<3;j++){
                changeButtonBack(i, j, board[i][j]);
            }
        }
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putIntArray("board0", board[0]);
        outState.putIntArray("board1", board[1]);
        outState.putIntArray("board2", board[2]);
    }

    @Override
    protected void makeMove() {
        Random rand = new Random();
        int x,y;
        do {
            x = rand.nextInt(3);
            y = rand.nextInt(3);
        }while( board[x][y] != 0);
        ImageButton ib = findViewById(findButton(x, y));
        ib.callOnClick();
    }

    @Override
    public void onClick(View v) {
        int player,i=0,j=0,img;
        if(currentTurn%2!=0){
            player=1;
            img = R.drawable.player1;
        }
        else{
            player=2;
            img = R.drawable.player2;
        }
        switch (v.getId()){
            case R.id.pos1:
                i=0;
                j=0;
                pos1.setImageResource(img);
                pos1.setClickable(false);
                break;
            case R.id.pos2:
                i=0;
                j=1;
                pos2.setImageResource(img);
                pos2.setClickable(false);
                break;
            case R.id.pos3:
                i=0;
                j=2;
                pos3.setImageResource(img);
                pos3.setClickable(false);
                break;
            case R.id.pos4:
                i=1;
                j=0;
                pos4.setImageResource(img);
                pos4.setClickable(false);
                break;
            case R.id.pos5:
                i=1;
                j=1;
                pos5.setImageResource(img);
                pos5.setClickable(false);
                break;
            case R.id.pos6:
                i=1;
                j=2;
                pos6.setImageResource(img);
                pos6.setClickable(false);
                break;
            case R.id.pos7:
                i=2;
                j=0;
                pos7.setImageResource(img);
                pos7.setClickable(false);
                break;
            case R.id.pos8:
                i=2;
                j=1;
                pos8.setImageResource(img);
                pos8.setClickable(false);
                break;
            case R.id.pos9:
                i=2;
                j=2;
                pos9.setImageResource(img);
                pos9.setClickable(false);
                break;
        }
        board[i][j]=player;
        currentTurn++;
        switch (checkWin(i,j,3)){
            case WIN1:
                replay(player1name + " won the game!", 3);
                break;
            case WIN2:
                replay(player2name + " won the game!", 3);
                break;
            case DRAW:
                replay("The game was a draw!", 3);
                break;
            default:
                changeTurn();
        }
    }

    private void changeButtonBack(int i, int j, int player) {
        ImageButton ib = findViewById(findButton(i, j));
        int img;
        if(player == 1)
            img = R.drawable.player1;
        else if(player == 2)
            img = R.drawable.player2;
        else
            img = R.color.white;

        ib.setImageResource(img);

    }

    protected int findButton(int i, int j){
        int butId;
        if(i == 0 && j == 0)
            butId = R.id.pos1;
        else if(i == 0 && j == 1)
            butId = R.id.pos2;
        else if(i == 0 && j == 2)
            butId = R.id.pos3;
        else if(i == 1 && j == 0)
            butId = R.id.pos4;
        else if(i == 1 && j == 1)
            butId = R.id.pos5;
        else if(i == 1 && j == 2)
            butId = R.id.pos6;
        else if(i == 2 && j == 0)
            butId = R.id.pos7;
        else if(i == 2 && j == 1)
            butId = R.id.pos8;
        else
            butId = R.id.pos9;

        return butId;
    }
}
