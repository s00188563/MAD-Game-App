package edu.lfernandez.gameapp;



import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;


import android.os.CountDownTimer;
import android.util.Log;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    private final int WEST = 1;
    private final int EAST = 2;
    private final int SOUTH = 3;
    private final int NORTH = 4;
    Animation anim;
    Button bEast, bWest, bSouth, bNorth;
    TextView textField;
    int sequenceCount = 4, n = 0;
    int[] gameSequence = new int[120];
    int arrayIndex = 0;

    CountDownTimer ct = new CountDownTimer(6000,  1500) {

        public void onTick(long millisUntilFinished) {
            //mTextField.setText("seconds remaining: " + millisUntilFinished / 1500);
            oneButton();
            //here you can have your logic to set text to edittext
        }

        public void onFinish() {
            // we now have the game sequence


            // start next activity
            Intent gameActivity = new Intent(MainActivity.this,GameActivity.class);
            gameActivity.putExtra("sequence", gameSequence);
            //Move to GameActivity page
            startActivity(gameActivity);
            // int[] arrayB = extras.getIntArray("numbers");
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        textField = findViewById(R.id.textView);
        bEast = findViewById(R.id.east);
        bWest = findViewById(R.id.west);
        bSouth = findViewById(R.id.south);
        bNorth = findViewById(R.id.north);

        ct.start();

    }

    private void oneButton() {
        n = getRandom(sequenceCount);

        switch (n) {
            case 1:
                flashButton(bWest);
                gameSequence[arrayIndex++] = WEST;
                break;
            case 2:
                flashButton(bEast);
                gameSequence[arrayIndex++] = EAST;
                break;
            case 3:
                flashButton(bSouth);
                gameSequence[arrayIndex++] = SOUTH;
                break;
            case 4:
                flashButton(bNorth);
                gameSequence[arrayIndex++] = NORTH;
                break;
            default:
                break;
        }   // end switch
    }

    //
    // return a number between 1 and maxValue
    private int getRandom(int maxValue) {
        return ((int) ((Math.random() * maxValue) + 1));
    }

    private void flashButton(Button button) {
        anim = new AlphaAnimation(1,0);
        anim.setDuration(1000); //You can manage the blinking time with this parameter

        anim.setRepeatCount(0);
        button.startAnimation(anim);
    }
}
