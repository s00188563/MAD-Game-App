package edu.lfernandez.gameapp;


import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;


import java.util.List;


public class ResultActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.result_activity);

        DatabaseHandler db = new DatabaseHandler(this);

        //db.emptyHiScores();     // empty table if required

        // Inserting hi scores
        db.addHiScore(new Scores("20 OCT 2020", "Frodo", 12));
        db.addHiScore(new Scores("28 OCT 2020", "Dobby", 16));
        db.addHiScore(new Scores("20 NOV 2020", "DarthV", 20));
        db.addHiScore(new Scores("20 NOV 2020", "Bob", 18));
        db.addHiScore(new Scores("22 NOV 2020", "Gemma", 22));
        db.addHiScore(new Scores("30 NOV 2020", "Joe", 30));
        db.addHiScore(new Scores("01 DEC 2020", "DarthV", 22));
        db.addHiScore(new Scores("02 DEC 2020", "Gandalf", 132));


        // Reading all high scores
        List<Scores> hiScores = db.getAllHiScores();

        // Getting Top 5 Scores
        List<Scores> top5 = db.getTopFiveScores();

        Scores hiScore = top5.get(top5.size() - 1);

        // simple test to add a hi score
        int myCurrentScore = 40;
        // if 5th highest score < myCurrentScore, then insert new score
        if (hiScore.getScore() < myCurrentScore) {
            db.addHiScore(new Scores("08 DEC 2020", "Elrond", 40));
        }

        // Update "top5" to accommodate new high score
        top5 = db.getTopFiveScores();
        ArrayAdapter<Scores> adapter = new ArrayAdapter<Scores>(this,
                android.R.layout.simple_list_item_1, top5);
        //setAdapter(adapter);
    }
}
