package com.example.tictactoe;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Random;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{
    private TextView playerOneScore,playerTwoScore, PlayerStatus;
    private Button [] buttons = new Button[9];
    private Button resetGame;


    private int playerOneScoreCount, playerTwoScoreCount, rountCount;
    boolean activePlayer;

    int [] gameState = {2,2,2,2,2,2,2,2,2};

    int [][] winningPosition = {
            {0,1,2}, {3,4,5}, {6,7,8}, //rows
            {0,3,6}, {1,4,7}, {2,5,8},  //columns
            {0,4,8}, {2,4,6} //cross
    };
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        playerOneScore = (TextView) findViewById(R.id.PlayerOneScore);
        playerTwoScore = (TextView) findViewById(R.id.PlayerTwoScore);
        PlayerStatus = (TextView) findViewById(R.id.playerStatus);

        resetGame = (Button) findViewById(R.id.resetGame);

        for(int i = 0; i < buttons.length; i++){
            String buttonID = "btn_" + i;
            int resourceID = getResources().getIdentifier(buttonID,"id",getPackageName());
            buttons[i] = (Button) findViewById(resourceID);
            buttons[i].setOnClickListener(this);
        }

        rountCount = 0;
        playerOneScoreCount = 0;
        playerTwoScoreCount = 0;
        activePlayer = true;
    }

    public void BuClick(View view){
        Button buSelected = (Button) view;
        buSelected.setBackgroundColor(Color.RED);
        int CellID = 0;
        switch ((buSelected.getId())){
            case R.id.btn_0:
                CellID = 0;
                break;

            case R.id.btn_1:
                CellID = 1;
                break;

            case R.id.btn_2:
                CellID = 2;
                break;

            case R.id.btn_3:
                CellID = 3;
                break;

            case R.id.btn_4:
                CellID = 4;
                break;

            case R.id.btn_5:
                CellID = 5;
                break;

            case R.id.btn_6:
                CellID = 6;
                break;

            case R.id.btn_7:
                CellID = 7;
                break;

            case R.id.btn_8:
                CellID = 8;
                break;
        }
       PlayGame(CellID, buSelected);

    }


    int ActivePlayer = 1;

    ArrayList<Integer> player1 = new ArrayList<Integer>();
    ArrayList<Integer> player2 = new ArrayList<Integer>();

    void PlayGame(int CellID,Button buSelected){
        Log.d("player:" ,String.valueOf(CellID));

        if(ActivePlayer == 1){
            buSelected.setText("X");
            buSelected.setBackgroundColor(Color.GREEN);
            player1.add(CellID);
            ActivePlayer = 2;

            AutoPlay();
        }else if(ActivePlayer == 2){
            buSelected.setText("O");
            buSelected.setBackgroundColor(Color.BLUE);
            player2.add(CellID);
            ActivePlayer = 1;
        }
        buSelected.setEnabled(false);
        checkWinner();

    }


    @Override
    public void onClick(View v) {
        if(!((Button)v).getText().toString().equals("")){
            return;
        }
        String buttonID = v.getResources().getResourceEntryName(v.getId());
        int gameStatePointer = Integer.parseInt(buttonID.substring(buttonID.length()-1, buttonID.length()));

        //int ActivePlayer = 1;

       /* if(ActivePlayer==1){
            ((Button) v).setText("X");
            ((Button) v).setTextColor(Color.parseColor("#FFC34A"));
            ActivePlayer=2;
            player1.add();
            gameState[gameStatePointer] = 0;
        }else {
            ((Button) v).setText("O");
            ((Button) v).setTextColor(Color.parseColor("#70FFEA"));
            gameState[gameStatePointer] = 1;
        }*/
        rountCount++;

        if(checkWinner()){
            if(activePlayer){
                  playerOneScoreCount++;
                  updatePlayerScore();
                Toast.makeText(this, "Player One Won!", Toast.LENGTH_SHORT).show();
                playAgain();
            }else {
                playerTwoScoreCount++;
                updatePlayerScore();
                Toast.makeText(this, "Player Two Won!", Toast.LENGTH_SHORT).show();
                playAgain();

            }

        }else if(rountCount == 9){
            playAgain();
            Toast.makeText(this, "No Winner!", Toast.LENGTH_SHORT).show();

        }else {
            activePlayer = !activePlayer;
        }

        if(playerOneScoreCount > playerTwoScoreCount){
            PlayerStatus.setText("Player One is Winning!");
        }else if(playerTwoScoreCount > playerOneScoreCount){
            PlayerStatus.setText("Player Two is Winning!");
        }else
        {
            PlayerStatus.setText("");
        }

        resetGame.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                playAgain();
                playerOneScoreCount = 0;
                playerTwoScoreCount = 0;
                PlayerStatus.setText("");
                updatePlayerScore();
            }
        });
    }

    public boolean checkWinner(){
        boolean winnerResult = false;
        for(int [] winningPositions : winningPosition){
            if(gameState[winningPositions[0]] == gameState[winningPositions[1]] &&
                    gameState[winningPositions[1]] == gameState[winningPositions[2]]
                    && gameState[winningPositions[0]] !=2)
            {
                winnerResult = true;
            }
        }
        return winnerResult;
    }


    public void updatePlayerScore(){
        playerOneScore.setText(Integer.toString(playerOneScoreCount));
        playerTwoScore.setText(Integer.toString(playerTwoScoreCount));
    }



    public void playAgain(){
        rountCount = 0;
        activePlayer = true;

        for(int i=0;i<buttons.length;i++){
            gameState[i] = 2;
            buttons[i].setText("");
        }
    }

    void AutoPlay(){
        ArrayList<Integer> EmptyCells = new ArrayList<Integer>();
        for(int cellID=0;cellID<10;cellID++){
            if(player1.contains(cellID) || player2.contains(1)){
                  EmptyCells.add(cellID);
            }
        }

        Random r = new Random();
        int RandIndex = r.nextInt(EmptyCells.size()-0)+ 0;
        int CellID = EmptyCells.get(RandIndex);
        Button buSelected;
        switch (CellID){
            case 0:
                buSelected = (Button) findViewById(R.id.btn_0);
                break;

            case 1:
                buSelected = (Button) findViewById(R.id.btn_1);
                break;

            case 2:
                buSelected = (Button) findViewById(R.id.btn_2);
                break;

            case 3:
                buSelected = (Button) findViewById(R.id.btn_3);
                break;

            case 4:
                buSelected = (Button) findViewById(R.id.btn_4);
                break;

            case 5:
                buSelected = (Button) findViewById(R.id.btn_5);
                break;

            case 6:
                buSelected = (Button) findViewById(R.id.btn_6);
                break;

            case 7:
                buSelected = (Button) findViewById(R.id.btn_7);
                break;

            case 8:
                buSelected = (Button) findViewById(R.id.btn_8);
                break;
            default:
                buSelected = (Button) findViewById(R.id.btn_0);
                break;

        }
        PlayGame(CellID,buSelected);
    }

}