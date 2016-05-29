package com.jiajunyang.emosoundratingcontrol;


import android.app.Activity;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RadioGroup.OnCheckedChangeListener;



/**
 * Created by jiajunyang on 27/05/16.
 */


public class Test extends Activity {
    private String myIP = "192.168.0.2";
    private int myPort = 50010;
    private String action;

    private RadioGroup emoChoice;
    private RadioGroup degreeChoice;

    // This count is the num of stimulation, need to be replaced by user input.
    private int count = 4;

    public void onPlayClick(View view) {
        action = "play";
        Thread play = new Thread(new OSCSend(myIP, myPort, action, 0, 0));
        play.start();
    }

    public void onNextClick(View view) {
        action = "next";
        Thread play = new Thread(new OSCSend(myIP, myPort, action, 0, 0));
        play.start();

        count -= 1;
        Log.d("trytry", "count = " + count);
        if (count == 0){
            action = "save";
            Thread play2 = new Thread(new OSCSend(myIP, myPort, action, 0, 0));
            play2.start();

        }
    }

    public void onEmoChoice(View view){
        action = "emo";
        int emoIndex = emoChoice.indexOfChild(findViewById(emoChoice.getCheckedRadioButtonId()));
        Thread play = new Thread(new OSCSend(myIP, myPort, action, emoIndex, 0));
        play.start();
    }


    public void onDegreeChoice(View view){
        action = "degree";
        int degreeIndex = degreeChoice.indexOfChild(findViewById(degreeChoice.getCheckedRadioButtonId()));
        Thread play = new Thread(new OSCSend(myIP, myPort, action, 0,degreeIndex));
        play.start();
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {


        super.onCreate(savedInstanceState);
        setContentView(R.layout.test); // Include the correspondent xml filename.
        emoChoice = (RadioGroup) findViewById(R.id.emoRadioGroup);
        degreeChoice = (RadioGroup) findViewById(R.id.degreeRadioGroup);


    }
}
