package com.jiajunyang.emosoundratingcontrol;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.RadioGroup;
import android.widget.Toast;

/**
 * Created by jiajunyang on 27/05/16.
 * This is the actual testin class, which sends actions according to the buttons.
 * Including: play sound, next, what emotion, what degree of emotion.
 *
 */


public class Test extends Activity {
    private String myIP = MainActivity.retriveIP();
    private int nrStim = MainActivity.retriveNrStim();
    private String action;
    private RadioGroup emoChoice;
    private RadioGroup degreeChoice;

    // This count is the num of stimulation, need to be replaced by user input.
    private int count = 0;


//    String myIP, String action, int emoIndex, int degreeIndex, int count, String prefix, String userID, String userName
//    ,int model, String run, int nrStim
    public void onPlayClick(View view) {
        action = "play";

        Thread play = new Thread(new OSCSend(myIP, action, 0, 0, count, "x", "x", "x", "x", "x", 1));
        play.start();
    }

    // Next
    public void onNextClick(View view) {
        action = "next";
        Thread play = new Thread(new OSCSend(myIP, action, 0, 0, count, "x", "x", "x", "x", "x", 1));
        play.start();
        int temp = nrStim - count -1;
        if (temp == 0){
            Toast.makeText(getApplicationContext(), R.string.finishTest, Toast.LENGTH_SHORT).show();
        }else {
            Toast.makeText(getApplicationContext(), temp + " left", Toast.LENGTH_SHORT).show();
        }
        count += 1;
        if (count == nrStim ){
            action = "save";
            Thread play2 = new Thread(new OSCSend(myIP, action, 0, 0, count, "x", "x", "x", "x", "x", 1));
            play2.start();
            count = 0; // Reset count
            Intent i = new Intent(Test.this, MainActivity.class);
            startActivity(i); // Change page.
        }

    }

    // Choose which emo
    public void onEmoChoice(View view){
        action = "emo";
        int emoIndex = emoChoice.indexOfChild(findViewById(emoChoice.getCheckedRadioButtonId()));
        Thread play = new Thread(new OSCSend(myIP, action, emoIndex, 0, count, "x", "x", "x", "x", "x", 1));
        play.start();
    }

    // Choose degree
    public void onDegreeChoice(View view){
        action = "degree";
        int degreeIndex = degreeChoice.indexOfChild(findViewById(degreeChoice.getCheckedRadioButtonId()));
        Thread play = new Thread(new OSCSend(myIP, action, 0, degreeIndex, count, "x", "x", "x", "x", "x", 1));
        play.start();
    }

    public void onRestartClick(View view) {
        Toast.makeText(getApplicationContext(), R.string.return2home, Toast.LENGTH_LONG).show();
        if (view.getId() == R.id.restartButton) {
            Intent i = new Intent(Test.this, MainActivity.class);
            startActivity(i); // Change page.
        }
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.test); // Include the correspondent xml filename.
        emoChoice = (RadioGroup) findViewById(R.id.emoRadioGroup);
        degreeChoice = (RadioGroup) findViewById(R.id.degreeRadioGroup);
    }
}
