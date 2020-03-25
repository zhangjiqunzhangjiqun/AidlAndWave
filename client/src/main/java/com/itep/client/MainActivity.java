package com.itep.client;

import android.content.ComponentName;
import android.content.Context;
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

import java.util.List;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    Button button;
    private ServiceConnection mServiceConnection;

    //由AIDL文件生成的Java类
    private MessageCenter messageCenter = null;

    //标志当前与服务端连接状况的布尔值，false为未连接，true为连接中
    private boolean mBound = false;

    //包含Book对象的list
    private List<Info> mInfoList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        init();
        button = findViewById(R.id.bt_send);
        button.setOnClickListener(MainActivity.this);
    }

    private void init() {
        mServiceConnection= new ServiceConnection() {
            @Override
            public void onServiceConnected(ComponentName name, IBinder service) {
                Log.e(getLocalClassName(), "service connected+1111");
                messageCenter = MessageCenter.Stub.asInterface(service);
                mBound = true;

                if (messageCenter != null) {
                    try {
                        mInfoList = messageCenter.getInfo();
                        Log.e(getLocalClassName(), mInfoList.toString());
                    } catch (RemoteException e) {
                        e.printStackTrace();
                    }
                }
            }

            @Override
            public void onServiceDisconnected(ComponentName name) {
                Log.e(getLocalClassName(), "service disconnected");
                mBound = false;
            }
        };
    }

    /**
     * 按钮的点击事件，点击之后调用服务端的addBookIn方法
     */
    public void addMessage(String content) {
        //如果与服务端的连接处于未连接状态，则尝试连接
        if (!mBound) {
            attemptToBindService();
            Toast.makeText(this, "当前与服务端处于未连接状态，正在尝试重连，请稍后再试", Toast.LENGTH_SHORT).show();
            return;
        }
        if (messageCenter == null) return;

        Info info = new Info();
        info.setContent(content);
        try {
            messageCenter.addInfo(info);
            Log.e(getLocalClassName(), "客户端：" + info.toString());
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }

    /**
     * 尝试与服务端建立连接
     */
    private void attemptToBindService() {
        Intent intent = new Intent();
        intent.setAction("com.aidl");
        intent.setPackage("com.itep.server");
        bindService(intent, mServiceConnection, Context.BIND_AUTO_CREATE);
    }

    @Override
    protected void onStart() {
        super.onStart();
        if (!mBound) {
            attemptToBindService();
        }
    }

    @Override
    protected void onStop() {
        super.onStop();
        if (mBound) {
            unbindService(mServiceConnection);
            mBound = false;
        }
    }


    @Override
    public void onClick(View view) {
        String content = "123";
        addMessage(content);

    }

}
