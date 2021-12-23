package edu.lfernandez.gameapp;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        DatabaseHandler db = new DatabaseHandler(this);

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
        }

    }
}
