package com.example.alexandra.x0;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Layout;
import android.widget.GridLayout;
import android.widget.ImageView;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    boolean gameIsActive = true;

    //1 = x     0 = 0

    int activePlayer = 1;

    //2 inseamna ca acea casuta nu a fost vizitata

    int[] gameState = {2, 2, 2, 2, 2, 2, 2, 2, 2};

    int [][] winngingPositions = { {0,1,2}, {3,4,5}, {6,7,8}, {0,3,6}, {1,4,7}, {2,5,8}, {0,4,8}, {2,4,6}};

    public void dropIn(View view) {

        String message = "X";

        ImageView counter = (ImageView) view;

        //System.out.println(counter.getTag().toString());    //printeaza tagul fiecare imagini, tagul este pe post de indice

        int tappedCounter = Integer.parseInt(counter.getTag().toString());

        if(gameState[tappedCounter] == 2 && gameIsActive) {

            gameState[tappedCounter] = activePlayer;

            counter.setTranslationY(-1000f);

            if(activePlayer == 1) {

                counter.setImageResource(R.drawable.x);
                activePlayer = 0;
            }
            else {

                counter.setImageResource(R.drawable.o);
                activePlayer = 1;
            }

            counter.animate().translationYBy(1000f).alpha(300);

            for(int[] winningPosition : winngingPositions) {

                if (gameState[winningPosition[0]] == gameState[winningPosition[1]]  //conditii pentru a castiga
                        && gameState[winningPosition[1]] == gameState[winningPosition[2]]
                        && gameState[winningPosition[0]] != 2) {

                    //Cineva a castigat!

                    //Vrem sa apara mesajul si butonul de 'play again' => trebuie schimbata vizibilitatea layoutului si mesajul corespunzator

                    //System.out.println(gameState[winningPosition[0]]);    //tipareste in consola cine a castigat

                    //Mesajul trebuie actualizat inainte ca layoutul sa devina vizibil

                    gameIsActive = false;

                    if(gameState[winningPosition[0]] == 0) message = "0";

                    TextView winnerMessage = (TextView) findViewById(R.id.winnerMessage);

                    winnerMessage.setText(message + " won!");

                    LinearLayout layout = (LinearLayout) findViewById(R.id.playAgainLayout);

                    layout.setVisibility(View.VISIBLE);
                }
                else {

                    boolean gameIsOver = true;

                    for(int counterState : gameState) {

                        if(counterState == 2) gameIsOver = false;
                    }

                    if(gameIsOver) {

                        TextView winnerMessage = (TextView) findViewById(R.id.winnerMessage);

                        winnerMessage.setText("It's a draw");

                        LinearLayout layout = (LinearLayout) findViewById(R.id.playAgainLayout);

                        layout.setVisibility(View.VISIBLE);
                    }
                }
            }
        }
    }

    public void playAgain(View view) {

        LinearLayout layout = (LinearLayout) findViewById(R.id.playAgainLayout);
                                                                    //layoutul sa dispara din nou cand reincepem jocul
        layout.setVisibility(View.INVISIBLE);                       //si jocul sa fie adus in starea initiala  =>

        gameIsActive = true;

        activePlayer = 1;

        for(int i=0; i<gameState.length; i++) gameState[i] = 2;

        GridLayout gridLayout = (GridLayout) findViewById(R.id.gridLayout);

        for(int i=0; i<gridLayout.getChildCount(); i++) {

            ((ImageView) gridLayout.getChildAt(i)).setImageResource(0);
        }

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }
}
