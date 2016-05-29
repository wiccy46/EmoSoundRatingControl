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
    private String myIP;
    private int myPort;
    private String action; // action define what function it uses.
    private OSCPortOut oscPortOut;

    // Updating parameters
    public OSCSend(String myIP, int myPort, String action){
        this.myIP = myIP;
        this.myPort = myPort;
        Log.d("OSCSendInitalisation", "IP is " + myIP);
        Log.d("OSCSendInitalisation", "Port is " + myPort);
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

    private void play(){
        ArrayList<Object> sendBang = new ArrayList<>();
        sendBang.add(1.0f);
        OSCMessage message = new OSCMessage("/play", sendBang);
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
            ArrayList<Object> sendBang = new ArrayList<>();
            sendBang.add("kick");
            OSCMessage message = new OSCMessage("/play", sendBang);
            Log.d("OSCRun", "Play Sound.");
            try{
                // Send messages
                oscPortOut.send(message);
            } catch (Exception e){
                Log.d("OSC2", "Failed to send.");
            }
//            switch (action){
//                case "play":
//                    play();
//            }
        }
    }
}
