package com.jiajunyang.emosoundratingcontrol;

import android.util.Log;

import com.illposed.osc.*;
import java.net.InetAddress;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.util.ArrayList;

/**
 * Created by jiajunyang on 29/05/16.
 */
public class OSCSend implements Runnable{
    String myIP;
    int myPort;
    String action; // action define what function it uses.
    OSCPortOut oscPortOut;

    // Updating parameters and setup OSC port out.
    public OSCSend(String myIP, int myPort, String action){
        this.myIP = myIP;
        this.myPort = myPort;
        this.action = action;
        try{
            // Connect to IP and port
            this.oscPortOut  = new OSCPortOut(InetAddress.getByName(myIP), myPort);
            Log.d("OSCSendInitalisation", "OSC Port Establised.");
        } catch(UnknownHostException e) {
            Log.d("OSCSendInitalisation", "OSC Port Out UnknownHoseException");
            //Error handling when your IP is not found
            return;
        } catch (SocketException e){
            // Report error
            Log.d("OSCSendInitalisation", "Socket exception error!");
        }
    }

    // This function is for the play button
    private void play(){
        ArrayList<Object> sendBang = new ArrayList<>();
        sendBang.add("bang");
        OSCMessage message = new OSCMessage("/play", sendBang);
        Log.d("OSCRun", "Play Sound.");
        try{
            // Send messages
            oscPortOut.send(message);
        } catch (Exception e){
            Log.d("OSC2", "Failed to send.");
        }
    }

    private void next(){
        ArrayList<Object> sendBang = new ArrayList<>();
        sendBang.add("bang");
        OSCMessage message = new OSCMessage("/next", sendBang);
        Log.d("OSCRun", "Next Sound.");
        try{
            // Send messages
            oscPortOut.send(message);
        } catch (Exception e){
            Log.d("OSC2", "Failed to send.");
        }
    }


    // Run the thread.
    @Override
    public void run(){
        if (oscPortOut != null){
            Log.d("OSCRun", "The action is : " + action );
            // Dont know why swich case doesnt work.
            if (action == "play"){
                play();

            }
            else if (action == "next") {
                next();
            }
        }
    }
}
