package com.fredstrout.connected;

// Connected
// Created by Fred L. Strout
// 9/23/2015
// Java 1 - Project 4 -

import android.app.ProgressDialog;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements VehicleDataTask.VehicleDataReceiver{

    private static final String TAG = "MainActivity";
    private Context context;
    private ProgressDialog pd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button refreshData = (Button) findViewById(R.id.btn_refresh);
        refreshData.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getData();
            }
        });
    }

    @Override
    public void DataReceived(ArrayList<Vehicle> _vehicles) {
        ListView lv = (ListView) findViewById(R.id.list_data);
        VehicleAdapter adapter = new VehicleAdapter(MainActivity.this, _vehicles);
        lv.setAdapter(adapter);
    }

    private void getData(){

        ConnectivityManager mgr = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        if(mgr != null){
            NetworkInfo info = mgr.getActiveNetworkInfo();
            if (info != null && info.isAvailable()){

                VehicleDataTask aTask = new VehicleDataTask(this);
                aTask.execute();

            }
        }
    }
}
