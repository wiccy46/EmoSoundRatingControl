package com.jiajunyang.emosoundratingcontrol;

import android.util.Log;

import com.illposed.osc.OSCMessage;
import com.illposed.osc.OSCPortOut;

import java.net.InetAddress;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.util.ArrayList;

/**
 * Created by jiajunyang on 30/05/16.
 */
public class InitOSC implements Runnable{
    String myIP, prefix, userID, model, userName, run, nrStim;

    OSCPortOut oscInitOut;
    int myPort = 7000;

    public InitOSC(String myIP, String prefix, String userID, String userName
        ,int model, String run, String nrStim ){

        this.myIP = myIP;
        this.prefix = prefix;
        this.userID = userID;
        this.userName = userName;
        this.run = run;
        this.nrStim = nrStim;
        this.model = Integer.toString(model);

        try{
            // Connect to IP and port
            this.oscInitOut  = new OSCPortOut(InetAddress.getByName(myIP), myPort);
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

    @Override
    public void run() {
        ArrayList<Object> sendBang = new ArrayList<>();
        sendBang.add(prefix);
        sendBang.add(userID);
        sendBang.add(userName);
        sendBang.add(model);
        sendBang.add(run);
        sendBang.add(nrStim);
        OSCMessage message = new OSCMessage("/init", sendBang);
        try{
            oscInitOut.send(message);
        } catch (Exception e){
            Log.d("OSC2", "Failed to send.");
        }

    }
}
