package com.pradipta.mytictactoe;

import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private TextView playerOneScore, playerTwoScore, playerStatus;
    private final Button[] buttons = new Button[9];
    private Button resetGame, play;
    private int playerOneScoreCount, playerTwoScoreCount, rountCount;
    boolean activePlayer;
    //p1>=0:
    //p2 >=1;
    //empty >= 2;
    int [] gameState = {2,2,2,2,2,2,2,2,2};
    int [][] winningPositions = {
            {0,1,2}, {3,4,5},{6,7,8}, //rows
            {0,3,6},{1,4,7},{2,5,8}, //col
            {0,4,8}, {2,4,6}//cross
    };


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
       // EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);

        playerOneScore = findViewById(R.id.oneScoreId);
        playerTwoScore = findViewById(R.id.twoScoreId);
        playerStatus = findViewById(R.id.playerStatus);

        resetGame = findViewById(R.id.resetGame);
        play = findViewById(R.id.playId);

        for(int i=0; i < buttons.length; i++){
            String buttonID = "btn_" + i;
            int resourceID = getResources().getIdentifier(buttonID, "id", getPackageName());
            buttons[i] = findViewById(resourceID);
            buttons[i].setOnClickListener(this);
        }
        rountCount =0;
        playerOneScoreCount = 0;
        playerTwoScoreCount =0;
        activePlayer = true;






    }
    @Override
    public void onClick(View v) {
        if(!((Button)v).getText().toString().equals("")){
            return;
        }
        String buttonID =v.getResources().getResourceEntryName(v.getId());
        int gameStatePointer = Integer.parseInt(buttonID.substring(buttonID.length()-1, buttonID.length()));

        if(activePlayer){
            ((Button) v).setText("X");
            ((Button) v).setTextColor(Color.parseColor("#FFAA9A0F"));
            gameState[gameStatePointer] = 0;
        }else {
            ((Button) v).setText("O");
            ((Button) v).setTextColor(Color.parseColor("#FFD5084D"));
            gameState[gameStatePointer] = 1;
        }
        rountCount++;

        if(checkWinner()){
            if(activePlayer){
                playerOneScoreCount++;
                updatePlayerScore();
                Toast.makeText(this, "First Player is Win", Toast.LENGTH_LONG).show();

            }else {
                playerTwoScoreCount++;
                updatePlayerScore();
                Toast.makeText(this, "Second Player is Win", Toast.LENGTH_LONG).show();

            }

        }else if(rountCount == 9){

            Toast.makeText(this, "!Opps it;s tie", Toast.LENGTH_LONG).show();

        }else {
            activePlayer =!activePlayer;
        }
        /*if (playerOneScoreCount > playerTwoScoreCount){
            playerStatus.setText("First player is win");
        }else if (playerTwoScoreCount > playerOneScoreCount){
            playerStatus.setText("second player is win");
        }else {
            playerStatus.setText("Round is tie");
        }*/
        resetGame.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                playAgain();
                playerOneScoreCount = 0;
                playerTwoScoreCount =0;
                playerStatus.setText("");
                updatePlayerScore();
            }
        });
        play.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                playAgain();
            }
        });

    }
    public boolean checkWinner(){
        boolean winnerResult = false;

        for (int [] winningposition : winningPositions){
            if(gameState[winningposition[0]] == gameState[winningposition[1]] &&
                    gameState[winningposition[1]] == gameState[winningposition[2]] &&
                    gameState[winningposition[0]] !=2)
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
        activePlayer= true;

        for (int i = 0; i < buttons.length; i++) {
            gameState[i] = 2;
            buttons[i].setText("");

        }

    }


}