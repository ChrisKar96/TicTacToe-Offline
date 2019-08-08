package net.ddns.christoskaramo.tictactoe;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;

import java.util.Random;

public class Game4x4 extends Game {

    ImageButton pos1,pos2,pos3,pos4,pos5,pos6,pos7,pos8,pos9,pos10,pos11,pos12,pos13,pos14,pos15,pos16;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game4x4);

        board = new int[4][4];

        if(savedInstanceState == null){
            for (int i = 0; i < 4; i++) {
                for (int j = 0; j < 4; j++) {
                    board[i][j] = 0;
                }
            }
        }
        else{
            for(int j=0;j<4;j++){
                board[0][j] = savedInstanceState.getIntArray("board0")[j];
                board[1][j] = savedInstanceState.getIntArray("board1")[j];
                board[2][j] = savedInstanceState.getIntArray("board2")[j];
                board[3][j] = savedInstanceState.getIntArray("board3")[j];
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
        pos10 = findViewById(R.id.pos10);
        pos11 = findViewById(R.id.pos11);
        pos12 = findViewById(R.id.pos12);
        pos13 = findViewById(R.id.pos13);
        pos14 = findViewById(R.id.pos14);
        pos15 = findViewById(R.id.pos15);
        pos16 = findViewById(R.id.pos16);

        maxTurns = 16;

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
        pos10.setOnClickListener(this);
        pos11.setOnClickListener(this);
        pos12.setOnClickListener(this);
        pos13.setOnClickListener(this);
        pos14.setOnClickListener(this);
        pos15.setOnClickListener(this);
        pos16.setOnClickListener(this);

        for (int i=0;i<4;i++){
            for (int j=0;j<4;j++){
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
        outState.putIntArray("board3", board[3]);
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
                i=0;
                j=3;
                pos4.setImageResource(img);
                pos4.setClickable(false);
                break;
            case R.id.pos5:
                i=1;
                j=0;
                pos5.setImageResource(img);
                pos5.setClickable(false);
                break;
            case R.id.pos6:
                i=1;
                j=1;
                pos6.setImageResource(img);
                pos6.setClickable(false);
                break;
            case R.id.pos7:
                i=1;
                j=2;
                pos7.setImageResource(img);
                pos7.setClickable(false);
                break;
            case R.id.pos8:
                i=1;
                j=3;
                pos8.setImageResource(img);
                pos8.setClickable(false);
                break;
            case R.id.pos9:
                i=2;
                j=0;
                pos9.setImageResource(img);
                pos9.setClickable(false);
                break;
            case R.id.pos10:
                i=2;
                j=1;
                pos10.setImageResource(img);
                pos10.setClickable(false);
                break;
            case R.id.pos11:
                i=2;
                j=2;
                pos11.setImageResource(img);
                pos11.setClickable(false);
                break;
            case R.id.pos12:
                i=2;
                j=3;
                pos12.setImageResource(img);
                pos12.setClickable(false);
                break;
            case R.id.pos13:
                i=3;
                j=0;
                pos13.setImageResource(img);
                pos13.setClickable(false);
                break;
            case R.id.pos14:
                i=3;
                j=1;
                pos14.setImageResource(img);
                pos14.setClickable(false);
                break;
            case R.id.pos15:
                i=3;
                j=2;
                pos15.setImageResource(img);
                pos15.setClickable(false);
                break;
            case R.id.pos16:
                i=3;
                j=3;
                pos16.setImageResource(img);
                pos16.setClickable(false);
                break;
        }
        board[i][j]=player;
        currentTurn++;
        switch (checkWin(i,j,4)){
            case WIN1:
                replay(player1name + " won the game!", 4);
                break;
            case WIN2:
                replay(player2name + " won the game!", 4);
                break;
            case DRAW:
                replay("The game was a draw!", 4);
                break;
            default:
                changeTurn();
        }
    }

    @Override
    protected void makeMove() {
        Random rand = new Random();
        int x,y;
        do {
            x = rand.nextInt(4);
            y = rand.nextInt(4);
        }while( board[x][y] != 0);
        board[x][y] = 2;
        ImageButton ib = findViewById(findButton(x,y));
        ib.callOnClick();
    }

    @Override
    protected int findButton(int i, int j) {
        int butId;
        if(i == 0 && j == 0)
            butId = R.id.pos1;
        else if(i == 0 && j == 1)
            butId = R.id.pos2;
        else if(i == 0 && j == 2)
            butId = R.id.pos3;
        else if(i == 0 && j == 3)
            butId = R.id.pos4;
        else if(i == 1 && j == 0)
            butId = R.id.pos5;
        else if(i == 1 && j == 1)
            butId = R.id.pos6;
        else if(i == 1 && j == 2)
            butId = R.id.pos7;
        else if(i == 1 && j == 3)
            butId = R.id.pos8;
        else if(i == 2 && j == 0)
            butId = R.id.pos9;
        else if(i == 2 && j == 1)
            butId = R.id.pos10;
        else if(i == 2 && j == 2)
            butId = R.id.pos11;
        else if(i == 2 && j == 3)
            butId = R.id.pos12;
        else if(i == 3 && j == 0)
            butId = R.id.pos13;
        else if(i == 3 && j == 1)
            butId = R.id.pos14;
        else if(i == 3 && j == 2)
            butId = R.id.pos15;
        else
            butId = R.id.pos16;
        return butId;
    }

    private void changeButtonBack(int i, int j, int player) {
        ImageButton ib = findViewById(findButton(i,j));
        int img;
        if(player == 1)
            img = R.drawable.player1;
        else if(player == 2)
            img = R.drawable.player2;
        else
            img = R.color.white;
        ib.setImageResource(img);
    }
}
