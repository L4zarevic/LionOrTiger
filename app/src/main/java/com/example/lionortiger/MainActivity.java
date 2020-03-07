package com.example.lionortiger;

import androidx.appcompat.app.AppCompatActivity;
import androidx.gridlayout.widget.GridLayout;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    //Creating variables that will represent which player is next
    enum Player {
        ONE, TWO, No
    }

    //Setting player ONE is first when app is start
    Player currentPlayer = Player.ONE;

    //Creating array of player choices that will contain Tag values of imageView
    Player[] playerChoices = new Player[9];

    //We assign Tag from imageView values for possible win cases (rows,colums,diagonals)
    int[][] winnerRowColums = {{0, 1, 2}, {3, 4, 5}, {6, 7, 8}, {0, 3, 6}, {1, 4, 7}, {2, 5, 8}, {0, 4, 8}, {2, 4, 6}};

    //Variables
    private boolean gameOver = false;
    private Button btnReset;
    private GridLayout gridLayout;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Setting the field value to default state
        playerChoices[0] = Player.No;
        playerChoices[1] = Player.No;
        playerChoices[2] = Player.No;
        playerChoices[3] = Player.No;
        playerChoices[4] = Player.No;
        playerChoices[5] = Player.No;
        playerChoices[6] = Player.No;
        playerChoices[7] = Player.No;
        playerChoices[8] = Player.No;

        //Initialization
        gridLayout = findViewById(R.id.gridLayout);
        btnReset = findViewById(R.id.btnReset);
        btnReset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                resetTheGame();

            }
        });
    }

    public void imageViewIsTapped(View imageView) {

        ImageView tappedImageView = (ImageView) imageView;

        //Getting tag value from imageView
        int tiTag = Integer.parseInt(tappedImageView.getTag().toString());

        //A condition that prevents an existing field of changing
        if (playerChoices[tiTag] == Player.No && gameOver == false) {

            //Removal imageView from the screen
            tappedImageView.setTranslationX(-2000);

            //Assigning values to array
            playerChoices[tiTag] = currentPlayer;

            //A condition that checks which player is playing
            if (currentPlayer == Player.ONE) {
                tappedImageView.setImageResource(R.drawable.lion);
                currentPlayer = Player.TWO;
            } else if (currentPlayer == Player.TWO) {
                tappedImageView.setImageResource(R.drawable.tiger);
                currentPlayer = Player.ONE;
            }

            //Checking how many times player ONE has played
            //If played 5 times, all fields are filled and there are no winners
            int count = 0;
            for (int i = 0; i < playerChoices.length; i++) {
                if (playerChoices[i] == Player.ONE) {
                    count++;
                }
            }
            if (count == 5) {
                btnReset.setVisibility(View.VISIBLE);
                gameOver = true;
                Toast.makeText(this, "There are no winners", Toast.LENGTH_LONG).show();

            }

            //Returning imageView to screen
            tappedImageView.animate().translationXBy(2000).alpha(1).rotation(3600).setDuration(1000);

            //Check if there is a winner based on the fields filled in
            for (int[] winnerColumn : winnerRowColums) {
                if (playerChoices[winnerColumn[0]] == playerChoices[winnerColumn[1]] && playerChoices[winnerColumn[1]] == playerChoices[winnerColumn[2]]
                        && playerChoices[winnerColumn[0]] != Player.No) {

                    //Showing reset button
                    btnReset.setVisibility(View.VISIBLE);
                    gameOver = true;

                    String winnerOfGame = "";

                    if (currentPlayer == Player.ONE) {
                        winnerOfGame = "Player Two";
                    } else if (currentPlayer == Player.TWO) {
                        winnerOfGame = "Player One";
                    }

                    //Showing a message when some of player wins
                    Toast.makeText(this, winnerOfGame + " is the Winner!", Toast.LENGTH_LONG).show();

                }
            }
        }
    }

    //Reset Game Function
    private void resetTheGame() {

        //For loop goes through the gridLayout child count values and removes images from the field
        for (int index = 0; index < gridLayout.getChildCount(); index++) {
            ImageView imageView = (ImageView) gridLayout.getChildAt(index);
            imageView.setImageDrawable(null);
            imageView.setAlpha(0.2f);
        }


        //Resetting to default state
        currentPlayer = Player.ONE;
        playerChoices[0] = Player.No;
        playerChoices[1] = Player.No;
        playerChoices[2] = Player.No;
        playerChoices[3] = Player.No;
        playerChoices[4] = Player.No;
        playerChoices[5] = Player.No;
        playerChoices[6] = Player.No;
        playerChoices[7] = Player.No;
        playerChoices[8] = Player.No;
        gameOver = false;
        btnReset.setVisibility(View.GONE);

    }
}
