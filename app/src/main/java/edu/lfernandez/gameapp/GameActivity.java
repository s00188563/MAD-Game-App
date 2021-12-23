package edu.lfernandez.gameapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.IntentCompat;

import android.content.Intent;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.util.Log;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.widget.Button;
import android.widget.TextView;


import java.util.ArrayList;
import java.util.List;

public class GameActivity extends AppCompatActivity implements SensorEventListener {

    // experimental values for hi and lo magnitude limits
    private final double NORTH_START = 9.0;
    private final double NORTH_END = 6.0;
    private final double SOUTH_START = 1.0;
    private final double SOUTH_END = -3.0;
    private final double WEST_START = 0.0;
    private final double WEST_END = -2.0;
    private final double EAST_START = 0.0;
    private final double EAST_END = 2.5;
    boolean nHighLimit = false;
    boolean sHighLimit = false;
    boolean wHighLimit = false;
    boolean eHighLimit = false;
    private final int WEST = 1;
    private final int EAST = 2;
    private final int SOUTH = 3;
    private final int NORTH = 4;
    Button bEast, bWest, bSouth, bNorth;
    TextView textField;
    List<Integer> sequence,answers = new ArrayList<>();
    int arrayIndex,score=0;
    Animation anim;

    private SensorManager mSensorManager;
    private Sensor mSensor;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.game_activity);
        textField = findViewById(R.id.textView);
        // we are going to use the sensor service
        mSensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);
        mSensor = mSensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);

        bEast = findViewById(R.id.east);
        bWest = findViewById(R.id.west);
        bSouth = findViewById(R.id.south);
        bNorth = findViewById(R.id.north);
        sequence = getIntent().getExtras().getIntegerArrayList("sequence");
        score = getIntent().getIntExtra("score",0);
        answers.clear();
        arrayIndex = 0;
        textField.setText("score: "+score);
    }
    /*
     * When the app is brought to the foreground - using app on screen
     */
    protected void onResume() {
        super.onResume();
        // turn on the sensor
        mSensorManager.registerListener(this, mSensor,
                SensorManager.SENSOR_DELAY_NORMAL);
    }

    /*
     * App running but not on screen - in the background
     */
    protected void onPause() {
        super.onPause();
        mSensorManager.unregisterListener(this);    // turn off listener to save power
    }


    @Override
    public void onSensorChanged(SensorEvent event) {

        double x = round(event.values[0],3);
        double y = round(event.values[1],3);
        double z = round(event.values[2],3);


        // Can we get a north movement
        // you need to do your own mag calculating
        if ((x > NORTH_START) && (!nHighLimit)) {
            nHighLimit = true;
        }
        if ((z > SOUTH_START) && (!sHighLimit)) {
            sHighLimit = true;
        }
        if ((y > WEST_START) && (!wHighLimit)) {
            wHighLimit = true;
        }
        if ((y > EAST_START) && (!eHighLimit)) {
            eHighLimit = true;
        }

        if ((x < NORTH_END) && (nHighLimit)) {
            // we have a tilt to the north
            answers.add(NORTH);
            oneButton(answers.get(arrayIndex++));
            checkIfOver();
            nHighLimit = false;
        }
        if ((z < SOUTH_END) && (sHighLimit)) {
            // we have a tilt to the south
            answers.add(SOUTH);
            oneButton(answers.get(arrayIndex++));
            checkIfOver();
            sHighLimit = false;
        }
        if ((y < WEST_END) && (wHighLimit)) {
            // we have a tilt to the west
            answers.add(WEST);
            oneButton(answers.get(arrayIndex++));
            checkIfOver();
            wHighLimit = false;
        }
        if ((y > EAST_END) && (eHighLimit)) {
            // we have a tilt to the east
            answers.add(EAST);
            oneButton(answers.get(arrayIndex++));
            checkIfOver();
            eHighLimit = false;
        }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {
        // not used
    }

    public static double round(double value, int places) {
        if (places < 0) throw new IllegalArgumentException();

        long factor = (long) Math.pow(10, places);
        value = value * factor;
        long tmp = Math.round(value);
        return (double) tmp / factor;
    }

    private void oneButton(int n) {
        switch (n) {
            case 1:
                flashButton(bWest);
                break;
            case 2:
                flashButton(bEast);
                break;
            case 3:
                flashButton(bSouth);
                break;
            case 4:
                flashButton(bNorth);
                break;
            default:
                break;
        }   // end switch
    }

    private void flashButton(Button button) {
        anim = new AlphaAnimation(1,0);
        anim.setDuration(500); //You can manage the blinking time with this parameter

        anim.setRepeatCount(0);
        button.startAnimation(anim);
    }

    private void checkIfOver() {
        Log.d("game sequence length", String.valueOf(sequence.size()));
        Log.d("answer sequence length", String.valueOf(answers.size()));
        if (sequence.size() == answers.size()) {
            for (int i = 0; i < 4; i++)
                if (sequence.get(i).equals(answers.get(i))){
                    score += 2;
                    Intent intent = new Intent(this,MainActivity.class);
                    intent.putExtra("score",score);
                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    startActivity(intent);
                }
                else{
                    Intent resultActivity = new Intent(this,ResultActivity.class);
                    getIntent().putExtra("score",score);
                    startActivity(resultActivity);
                }
        }
    }

}