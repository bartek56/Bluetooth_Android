package com.example.bartek.bluetooth;

import android.app.ProgressDialog;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;
import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.util.UUID;

import static android.content.ContentValues.TAG;

/**
 * Created by Bartek on 2016-11-05.
 */

public class ClientBluetooth extends Thread {
    private final BluetoothSocket mmSocket;
    private final BluetoothDevice mmDevice;
    //private final InputStream inStream;
    private final OutputStream outStream=null;
    Context context;
    CharSequence text = "Połączono";
    MainActivity mainActivity;
    public static boolean connected=false;
    public ClientBluetooth(BluetoothDevice device) {
        BluetoothSocket socket = null;

        mmDevice = device;

        try {

            UUID uuid = UUID.fromString("00001101-0000-1000-8000-00805f9b34fb");
            socket = device.createRfcommSocketToServiceRecord(uuid);
        } catch (Exception e) { }
        mmSocket=socket;

    }

    public void run() {
        BluetoothSocket socket = null;
        try {
            Log.d("INFO","Próba połączenia....");
            mmSocket.connect();
            Log.d("INFO","Połączono z serwerem!");
            connected=true;

        } catch (Exception ce) {
            try {
                mmSocket.close();
            } catch (Exception cle) { }
        }

    }

    public void sendData(String message) {

        try
        {
            PrintWriter out = new PrintWriter(mmSocket.getOutputStream(),true);
            out.println(message);
        }
        catch (IOException e){}

    }

    public void Disconnect() {

            try {
                mmSocket.close();
                connected=false;
            } catch (IOException e) {
                Log.e(TAG, "close() of connect socket failed", e);
            }

    }






}