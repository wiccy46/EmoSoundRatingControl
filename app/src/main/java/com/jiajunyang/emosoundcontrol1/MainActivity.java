package com.jiajunyang.emosoundcontrol1;


import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;

import android.os.Build;
import android.view.ViewTreeObserver;
import android.widget.EditText;
import android.widget.TextView;
import android.view.MotionEvent;
import android.widget.Toast;

import java.net.*;
import java.util.*;
import com.illposed.osc.*;




public class MainActivity extends AppCompatActivity {
    // Define UDP prt for send.
    private String myIP = "192.168.11.93";
    private int myPort = 50010;

    private float touchViewWidth; // Locate touchView dimension
    private float touchViewHeight;

    // This is for sending messagign.
    private OSCPortOut oscPortOut;

    // currently there is a bug that simply click on the number box will trigger the validation?
    public void enterIP(View view) {
        EditText yourIP = (EditText) findViewById(R.id.ip_address);
        boolean validIP = true;
        IPAddressValidator ipvalidator = new IPAddressValidator();

        try {
            // This is not robust, you need to detect whether the input is in XXX.XXX.XX.XX form.
            myIP = yourIP.getText().toString(); // myIP is global variable

            validIP = ipvalidator.validate(myIP);
        } catch (NullPointerException e)
        {
            Log.d("Error", "Input address is NULL");
        }
        if (validIP){
            Toast.makeText(getApplicationContext(), "New IP is " + myIP, Toast.LENGTH_LONG).show();
        }
        else {
            Toast.makeText(getApplicationContext(), "Invalid IP, correct format,e.g. 192.168.0.1, 255.255.255.255.", Toast.LENGTH_LONG).show();
        }
    }

    // OSC thread for sending information.
    public class oscThread implements  Runnable{
        private float x;
        private float y;

        // Updating parameters
        public oscThread(float x, float y){
            this.x = x;
            this.y = y;
        }

        // Run the thread.
        @Override
        public void run(){
            try{
                // Connect to IP and port

                oscPortOut  = new OSCPortOut(InetAddress.getByName(myIP), myPort);
            } catch(UnknownHostException e) {
                Log.d("OSC2", "OSC Port Out UnknownHoseException");
                //Error handling when your IP is not found
                return;
            } catch (SocketException e){
                // Report error
                Log.d("OSC2", "Socket exception error!");
            }

            /* 2nd loop infinitely and send messages every x ms.
            * */
            if (oscPortOut != null){
                ArrayList<Object> messageToSend = new ArrayList<>();
                // ArrayList<Object> messageToSend = new ArrayList<Object>();
                // Append message
                messageToSend.add(Float.toString(x));
                messageToSend.add(Float.toString(y));
                OSCMessage message = new OSCMessage("/a", messageToSend);
                try{
                    // Send messages
                    oscPortOut.send(message);
                } catch (Exception e){
                    Log.d("OSC2", "Failed to send.");
                }
            }
        }
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        final TextView textView = (TextView)findViewById(R.id.textView);
        final View touchView = findViewById(R.id.touchView); // listen for touch event;

        // Get the width and height of the control panel.
        touchView.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                    touchView.getViewTreeObserver().removeOnGlobalLayoutListener(this);
                } else {
                    touchView.getViewTreeObserver().removeGlobalOnLayoutListener(this);
                }
                touchViewWidth = touchView.getWidth();
                touchViewHeight = touchView.getHeight();
            }


        });

//

        touchView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                int pointerIndex = event.getActionIndex();
                switch( event.getAction()){
                    case MotionEvent.ACTION_DOWN: {
                        float x = (event.getX(pointerIndex) / touchViewWidth - 0.5f) * 2.f;
                        float y = -2.f * (event.getY(pointerIndex)/(touchViewHeight * 0.9f) - 0.5f);
                        Log.d("CLICK", " click!");
                        textView.setText("Touch coordinates : " +
                                String.valueOf(x) + " x " + String.valueOf(y));
                        Thread t = new Thread(new oscThread(x, y));
                        t.start();

                    }

//                    case MotionEvent.ACTION_POINTER_DOWN: {
//                        float x = (event.getX(pointerIndex) / touchViewWidth - 0.5f) * 2.f;
//                        float y = -2.f * (event.getY(pointerIndex)/(touchViewHeight * 0.9f) - 0.5f);
//                        Log.d("CLICK", " click!");
//                        textView.setText("Touch coordinates : " +
//                                String.valueOf(x) + " x " + String.valueOf(y));
//                        Thread t = new Thread(new oscThread(x, y));
//                        t.start();
//
//                    }

//                    case MotionEvent.ACTION_MOVE: {
//                        float x = (event.getX() / touchViewWidth - 0.5f) * 2.f;
//                        float y = -2.f * (event.getY()/(touchViewHeight * 0.9f) - 0.5f);
//                        Log.d("CLICK", " click!");
//                        textView.setText("Touch coordinates : " +
//                                String.valueOf(x) + " x " + String.valueOf(y));
//                        Thread t = new Thread(new oscThread(x, y));
//                        t.start();
//
//                    }


                }
                return true;
            }
        });


        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
