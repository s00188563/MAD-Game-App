package edu.lfernandez.gameapp;

import androidx.appcompat.app.AppCompatActivity;

import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import java.util.List;

public class MainActivity extends AppCompatActivity implements SensorEventListener {

    // experimental values for hi and lo magnitude limits
    private final double NORTH_START = 9.0;     // upper mag limit
    private final double NORTH_END = 6.0;
    private final double SOUTH_START = 1.0;
    private final double SOUTH_END = -3.0;
    private final double WEST_START = 0.0;
    private final double WEST_END = -2.0;
    private final double EAST_START = 0.0;
    private final double EAST_END = 3.0;
    // lower mag limit
    boolean nHighLimit = false;
    boolean sHighLimit = false;
    boolean wHighLimit = false;
    boolean eHighLimit = false;
    int nCounter = 0;
    int sCounter = 0;
    int wCounter = 0;
    int eCounter = 0;

    TextView tvx, tvy, tvz, tvSteps;
    private SensorManager mSensorManager;
    private Sensor mSensor;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

       /* DatabaseHandler db = new DatabaseHandler(this);

        db.emptyHiScores();     // empty table if required

        // Inserting hi scores
        Log.i("Insert: ", "Inserting ..");
        db.addHiScore(new Scores("20 OCT 2020", "Frodo", 12));
        db.addHiScore(new Scores("28 OCT 2020", "Dobby", 16));
        db.addHiScore(new Scores("20 NOV 2020", "DarthV", 20));
        db.addHiScore(new Scores("20 NOV 2020", "Bob", 18));
        db.addHiScore(new Scores("22 NOV 2020", "Gemma", 22));
        db.addHiScore(new Scores("30 NOV 2020", "Joe", 30));
        db.addHiScore(new Scores("01 DEC 2020", "DarthV", 22));
        db.addHiScore(new Scores("02 DEC 2020", "Gandalf", 132));


        // Reading all scores
        Log.i("Reading: ", "Reading all scores..");
        List<Scores> hiScores = db.getAllHiScores();


        for (Scores hs : hiScores) {
            String log =
                    "Id: " + hs.getScore_id() +
                            ", Date: " + hs.getGame_date() +
                            " , Player: " + hs.getPlayer_name() +
                            " , Score: " + hs.getScore();

            // Writing HiScore to log
            Log.i("Score: ", log);
        }

        Log.i("divider", "====================");

        // Calling a certain score by ID
        Scores singleScore = db.getHiScore(5);
        Log.i("High Score 5 is by ", singleScore.getPlayer_name() + " with a score of " +
                singleScore.getScore());

        Log.i("divider", "====================");

        // Getting Top 5 Scores
        List<Scores> top5 = db.getTopFiveScores();

        for (Scores hs : top5) {
            String log =
                    "Id: " + hs.getScore_id() +
                            ", Date: " + hs.getGame_date() +
                            " , Player: " + hs.getPlayer_name() +
                            " , Score: " + hs.getScore();

            // Writing HiScore to log
            Log.i("Score: ", log);
        }
        Log.i("divider", "====================");

        Scores hiScore = top5.get(top5.size() - 1);
        // hiScore contains the 5th highest score
        Log.i("fifth Highest score: ", String.valueOf(hiScore.getScore()) );

        // simple test to add a hi score
        int myCurrentScore = 40;
        // if 5th highest score < myCurrentScore, then insert new score
        if (hiScore.getScore() < myCurrentScore) {
            db.addHiScore(new Scores("08 DEC 2020", "Elrond", 40));
        }

        Log.i("divider", "====================");

        // Update "top5" to accommodate new high score
        top5 = db.getTopFiveScores();

        for (Scores hs : top5) {
            String log =
                    "Id: " + hs.getScore_id() +
                            ", Date: " + hs.getGame_date() +
                            " , Player: " + hs.getPlayer_name() +
                            " , Score: " + hs.getScore();

            // Writing HiScore to log
            Log.i("Score: ", log);
        }*/

        tvx = findViewById(R.id.tvX);
        tvy = findViewById(R.id.tvY);
        tvz = findViewById(R.id.tvZ);
        tvSteps = findViewById(R.id.tvSteps);

        // we are going to use the sensor service
        mSensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);
        mSensor = mSensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
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

/*        tvx.setText(String.valueOf(x));
        tvy.setText(String.valueOf(y));
        tvz.setText(String.valueOf(z));*/


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
            nCounter++;
            tvSteps.setText(String.valueOf(nCounter));
            nHighLimit = false;
        }
        if ((z < SOUTH_END) && (sHighLimit)) {
            // we have a tilt to the north
            sCounter++;
            tvx.setText(String.valueOf(sCounter));
            sHighLimit = false;
        }
        if ((y < WEST_END) && (wHighLimit)) {
            // we have a tilt to the north
            wCounter++;
            tvy.setText(String.valueOf(wCounter));
            wHighLimit = false;
        }
        if ((y > EAST_END) && (eHighLimit)) {
            // we have a tilt to the north
            eCounter++;
            tvz.setText(String.valueOf(eCounter));
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

}
