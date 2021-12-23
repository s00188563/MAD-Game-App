package edu.lfernandez.gameapp;



import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;


import android.os.CountDownTimer;

import android.os.Parcelable;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.widget.Button;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private final int WEST = 1;
    private final int EAST = 2;
    private final int SOUTH = 3;
    private final int NORTH = 4;
    Animation anim;
    Button bEast, bWest, bSouth, bNorth;
    int sequenceCount = 4, n = 0, score;
    TextView textField;
    List<Integer> gameSequence = new ArrayList<>();

    CountDownTimer ct = new CountDownTimer(6000,  1500) {

        public void onTick(long millisUntilFinished) {
            //mTextField.setText("seconds remaining: " + millisUntilFinished / 1500);
            oneButton();
            //here you can have your logic to set text to edittext
        }

        public void onFinish() {
            // start next activity
            Intent gameActivity = new Intent(MainActivity.this,GameActivity.class);
            gameActivity.putIntegerArrayListExtra("sequence", (ArrayList<Integer>) gameSequence);
            gameActivity.putExtra("score",score);
            //Move to GameActivity page
            startActivity(gameActivity);
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        bEast = findViewById(R.id.east);
        bWest = findViewById(R.id.west);
        bSouth = findViewById(R.id.south);
        bNorth = findViewById(R.id.north);
        score = getIntent().getIntExtra("score",0);
        textField = findViewById(R.id.textView);
        ct.start();

    }

    private void oneButton() {
        n = getRandom(sequenceCount);

        switch (n) {
            case 1:
                flashButton(bWest);
                gameSequence.add(WEST);
                break;
            case 2:
                flashButton(bEast);
                gameSequence.add(EAST);
                break;
            case 3:
                flashButton(bSouth);
                gameSequence.add(SOUTH);
                break;
            case 4:
                flashButton(bNorth);
                gameSequence.add(NORTH);
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
