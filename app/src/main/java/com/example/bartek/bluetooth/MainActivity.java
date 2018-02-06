package com.example.bartek.bluetooth;

import android.app.ProgressDialog;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;

import static android.R.id.message;
import static android.content.ContentValues.TAG;

public class MainActivity extends AppCompatActivity {

    TextView textView;
    EditText editText;


    ClientBluetooth clientBluetooth;

    int duration = Toast.LENGTH_SHORT;
    private final OutputStream outStream=null;
    private static boolean conect=false;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        textView=(TextView) findViewById(R.id.tText);
        BluetoothAdapter ba = BluetoothAdapter.getDefaultAdapter();
        textView.setText("Twój mac: "+ba.getAddress());
        Log.d("INFO","Twój adres urządzenia: "+ba.getAddress());
        if(!ba.isEnabled()){
            Intent i=new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
            startActivityForResult(i, 1);
        }

    }

    private final BroadcastReceiver odbiorca= new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent i) {
            String akcja = 	i.getAction();
            if(BluetoothDevice.ACTION_FOUND.equals(akcja)){
                BluetoothDevice device = i.getParcelableExtra(BluetoothDevice.EXTRA_DEVICE);
                String status="";
                if (device.getBondState() != BluetoothDevice.BOND_BONDED) {
                    status="nie sparowane";
                }else{
                    status="sparowane";
                }
                Log.d("INFO","znaleziono urządzenie: "+device.getName()+" - "+device.getAddress()+" - "+status);
            }
        }
    };

    public void wykryjInne(){
        Log.d("INFO","Szukam innych urządzeń (ok 12s)");
        IntentFilter filtr = new IntentFilter(BluetoothDevice.ACTION_FOUND);
        this.registerReceiver(odbiorca, filtr);
        BluetoothAdapter ba = BluetoothAdapter.getDefaultAdapter();
        ba.startDiscovery();
    }

    public void bPolacz(View view) {

                if (clientBluetooth.connected==true) {
                Toast.makeText(getApplicationContext(), "Połączono", Toast.LENGTH_SHORT).show();
                conect=true;
                }
        else {
                    BluetoothAdapter ba = BluetoothAdapter.getDefaultAdapter();
                    BluetoothDevice serwer = ba.getRemoteDevice("20:16:08:11:21:30");
                    clientBluetooth = new ClientBluetooth(serwer);

                    clientBluetooth.start();
                }



    }

    public void bD1(View view) {

        if(clientBluetooth.connected)
        clientBluetooth.sendData("D1");
        else
            Toast.makeText(getApplicationContext(), "Nie Połączono", Toast.LENGTH_SHORT).show();



    }
    public void bD2(View view) {
        if(clientBluetooth.connected)
        clientBluetooth.sendData("D2");
        else
            Toast.makeText(getApplicationContext(), "Nie Połączono", Toast.LENGTH_SHORT).show();

    }
    public void bD3(View view) {
        if(clientBluetooth.connected)
        clientBluetooth.sendData("D3");
        else
            Toast.makeText(getApplicationContext(), "Nie Połączono", Toast.LENGTH_SHORT).show();

    }
    public void bD4(View view) {
        if(clientBluetooth.connected)
        clientBluetooth.sendData("D4");
        else
            Toast.makeText(getApplicationContext(), "Nie Połączono", Toast.LENGTH_SHORT).show();

    }

    public void bRozlacz(View view) {
        if(clientBluetooth.connected)
            clientBluetooth.Disconnect();
        }

    }
