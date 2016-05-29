package com.jiajunyang.emosoundratingcontrol;


import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.illposed.osc.OSCPortOut;

/**
 * Created by jiajunyang on 27/05/16.
 */


public class Test extends Activity {

//
    private String myIP = "192.168.0.3";
    private int myPort = 50010;
    private String action = "play";
    private OSCPortOut oscPortOut;

    public void onPlayClick(View view){
        action = "play";
        Thread play = new Thread(new OSCSend(myIP, myPort, action));
        play.start();
        Toast.makeText(getApplicationContext(), "Play", Toast.LENGTH_LONG).show();
    }





    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.test); // Include the correspondent xml filename.

        }


    }
