package com.jiajunyang.emosoundratingcontrol;


import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.Toast;


/*
* Initial set up should go here.
* This include:
* IP, studyprefix, UserID, Username, Sound model, Run, NrStim.
* */


public class MainActivity extends AppCompatActivity {
    boolean validIP;
    public static String ip  = "192.168.0.1";
    private RadioGroup modelChoice;
    private int modelIdx = 0; // By default abstract

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        modelChoice = (RadioGroup) findViewById(R.id.modelRadioGroup);

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
        // as you specify shapea parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private String getIP(){
        String myIP;
        EditText yourIP = (EditText) findViewById(R.id.ipText);
        IPAddressValidator ipvalidator = new IPAddressValidator();
        try{
            myIP = yourIP.getText().toString();
        }
        catch (NullPointerException e){
            Toast.makeText(getApplicationContext(),
                    R.string.nullInput, Toast.LENGTH_SHORT).show();
            myIP = "192.168.0.1";
        }
        validIP = ipvalidator.validate(myIP);
        return myIP;
    }


    private String getVariable(int id){
        String myVar  = "0";
        EditText yourIP = (EditText) findViewById(id);
        try {
            myVar =yourIP.getText().toString();
        }
        catch (NullPointerException e){
            Toast.makeText(getApplicationContext(),
                    R.string.nullInput, Toast.LENGTH_SHORT).show();
        }
        return myVar;
    }

    public void onModelChoice(View view){
        modelIdx =  modelChoice.indexOfChild(findViewById(modelChoice.getCheckedRadioButtonId()));
    }


    public void onStartButton(View view) {
        ip = getIP();
        // Need to OSC the init info to python here.
        String myPrefix = getVariable(R.id.prefixText); // init prefix.
        String myUserid = getVariable(R.id.idText);
        String myUsername = getVariable(R.id.usernameText);
        String myRun = getVariable(R.id.runText);
        String myNrStim = getVariable(R.id.nrstimText);



        // Replace this with OSC.
        // Conver string to int for int par in Python.



        if (validIP){
            Toast.makeText(getApplicationContext(), "New IP: "+ ip, Toast.LENGTH_LONG).show();
            if (view.getId() == R.id.StartTest) {
                Intent i = new Intent(MainActivity.this, Test.class);
                startActivity(i);
            }
        }
        else {
            Toast.makeText(getApplicationContext(), R.string.invalidIP, Toast.LENGTH_LONG).show();
        }
    }

    // This is to let Test.java retrive IP address.
    public static String retriveIP()
    {
        return ip;
    }
}
