package com.jiajunyang.emosoundratingcontrol;


import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;


public class MainActivity extends AppCompatActivity {
//    // Define UDP prt for send.
//    private String myIP = "192.168.11.93";
//    private int myPort = 50010;
//
//
//    // This is for sending messagign.
//    private OSCPortOut oscPortOut;

    // currently there is a bug that simply click on the number box will trigger the validation?
//    public void enterIP(View view) {
//        EditText yourIP = (EditText) findViewById(R.id.ip_address);
//        boolean validIP = true;
//        IPAddressValidator ipvalidator = new IPAddressValidator();
//
//        try {
//            // This is not robust, you need to detect whether the input is in XXX.XXX.XX.XX form.
//            myIP = yourIP.getText().toString(); // myIP is global variable
//
//            validIP = ipvalidator.validate(myIP);
//        } catch (NullPointerException e)
//        {
//            Log.d("Error", "Input address is NULL");
//        }
//        if (validIP){
//            Toast.makeText(getApplicationContext(), "New IP is " + myIP, Toast.LENGTH_LONG).show();
//        }
//        else {
//            Toast.makeText(getApplicationContext(), "Invalid IP, correct format,e.g. 192.168.0.1, 255.255.255.255.", Toast.LENGTH_LONG).show();
//        }
//    }

//    // OSC thread for sending information.
//    public class oscThread implements  Runnable{
//        private float x;
//        private float y;
//
//        // Updating parameters
//        public oscThread(float x, float y){
//            this.x = x;
//            this.y = y;
//        }
//
//        // Run the thread.
//        @Override
//        public void run(){
//            try{
//                // Connect to IP and port
//
//                oscPortOut  = new OSCPortOut(InetAddress.getByName(myIP), myPort);
//            } catch(UnknownHostException e) {
//                Log.d("OSC2", "OSC Port Out UnknownHoseException");
//                //Error handling when your IP is not found
//                return;
//            } catch (SocketException e){
//                // Report error
//                Log.d("OSC2", "Socket exception error!");
//            }
//
//            /* 2nd loop infinitely and send messages every x ms.
//            * */
//            if (oscPortOut != null){
//                ArrayList<Object> messageToSend = new ArrayList<>();
//                // ArrayList<Object> messageToSend = new ArrayList<Object>();
//                // Append message
//                messageToSend.add(Float.toString(x));
//                messageToSend.add(Float.toString(y));
//                OSCMessage message = new OSCMessage("/a", messageToSend);
//                try{
//                    // Send messages
//                    oscPortOut.send(message);
//                } catch (Exception e){
//                    Log.d("OSC2", "Failed to send.");
//                }
//            }
//        }
//    }




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
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

    public void onStartButton(View view) {

        if (view.getId() == R.id.StartTest) {
            Intent i = new Intent(MainActivity.this, Test.class);
            startActivity(i);
        }
    }
}
