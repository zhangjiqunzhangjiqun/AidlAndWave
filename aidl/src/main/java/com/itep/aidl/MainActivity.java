package com.itep.aidl;

import android.app.Service;
import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.IBinder;
import android.os.RemoteException;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    IMyAidlInterface iMyAidlInterface;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
        Button button = findViewById(R.id.mBtn);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
//                    startNewApp();
                    Toast.makeText(MainActivity.this,
                            iMyAidlInterface.getName() + 111, Toast.LENGTH_SHORT).show();
                } catch (RemoteException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    private void startNewApp() {
        Intent intent = new Intent();
        //第一种方式
        ComponentName cn = new ComponentName("com.itep.wave", "com.itep.wave.MainActivity");
        try {
            intent.setComponent(cn);
            //第二种方式
            //intent.setClassName("com.example.fm", "com.example.fm.MainFragmentActivity");
            intent.putExtra("test", "intent1");
            startActivity(intent);
        } catch (Exception e) {
            //TODO  可以在这里提示用户没有安装应用或找不到指定Activity，或者是做其他的操作
        }
    }


    private void initView() {

        Intent intent = new Intent();
        intent.setAction("com.example.calculate.CalculateService");
        intent.setPackage("com.itep.aidl");
        bindService(intent, new ServiceConnection() {

            @Override
            public void onServiceConnected(ComponentName name, IBinder service) {

                iMyAidlInterface = IMyAidlInterface.Stub.asInterface(service);
            }

            @Override
            public void onServiceDisconnected(ComponentName name) {

            }
        }, BIND_AUTO_CREATE);

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

    }


}
