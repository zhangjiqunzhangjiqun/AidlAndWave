package com.itep.wave;

import android.app.Service;
import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.IBinder;
import android.os.RemoteException;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private Button mBtn;
    String s;

    /**
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
        mBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                s = getIntent().getBundleExtra("test").toString();

                mBtn.setText(s);

            }
        });
    }

    private void initView() {
        mBtn = (Button) findViewById(R.id.mBtn);
        Intent intent = new Intent();
        intent.setAction("com.example.aidlmy.aidl");
        intent.setPackage("com.example.aidlmy");
        //bindService建立连接


    }

    @Override
    public void onClick(View v) {
        mBtn.setText(s);
    }
}
