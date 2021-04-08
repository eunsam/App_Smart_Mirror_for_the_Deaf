package com.example.vicl_10.lipinthemirror;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.io.IOException; //???

import static com.example.vicl_10.lipinthemirror.MenuActivity.m_dis;
import static com.example.vicl_10.lipinthemirror.MenuActivity.m_dos;
import static com.example.vicl_10.lipinthemirror.MenuActivity.m_gsocket;



public class TCPClient extends Thread {
    private String mMessage = null;
    static String line;

    Message msg;

    private Handler mHandler;

    public TCPClient(String msg)
    {
        super();
        this.mMessage = msg;
    }

    public TCPClient(Handler handler){
        mHandler = handler;
    }

    public void send()
    {
        try{
            Thread.sleep(200);
            if(line != null) {
                msg = Message.obtain();
                msg.obj = line;


                mHandler.sendMessage(msg);
            }
        }catch(InterruptedException e){
            e.printStackTrace();
        }
    }

    @Override
    public void run() {
 
        try {
           
            Log.e("TCP", "TCP C: Start...");
            
            if (m_gsocket == null){
                m_gsocket = new Socket();

            }

            if (!m_gsocket.isConnected()) {
                m_gsocket.connect(new InetSocketAddress(SettingActivity.smartmirror_IP, 6667));//SettingActivity.smartmirror_IP, SettingActivity.smartmirror_PORT);
            }

            if(m_dos == null)
                m_dos = new DataOutputStream(m_gsocket.getOutputStream());
            if(m_dis == null)
                m_dis = new DataInputStream(m_gsocket.getInputStream());

            Log.e("TCP", "TCP C: Connecting...");
            try {
                Log.e("TCP", "TCP C: Sending...");
                m_dos.writeBytes(mMessage);
                m_dos.flush();
                Log.e("TCP", "TCP C: Sent.(" + mMessage + ")");
                Log.e("TCP", "TCP C: Done.");
				
				BufferedReader networkReader = new BufferedReader(new InputStreamReader(m_gsocket.getInputStream()));
                line = networkReader.readLine();

                Log.d("TCP", "C:Received..." + line);

            }catch (Exception e) {
                Log.e("TCP", "TCP C: Error1", e);
            }finally {
            }
        } catch (Exception e) {
            Log.e("TCP", "TCP C: Error2", e);
        }
    }

}