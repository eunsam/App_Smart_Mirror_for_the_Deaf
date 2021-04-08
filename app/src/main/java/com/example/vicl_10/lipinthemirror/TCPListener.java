package com.example.vicl_10.lipinthemirror;

import android.content.Context;
import android.os.Handler;
import android.util.Log;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.InetAddress;
import java.net.Socket;
import static com.example.vicl_10.lipinthemirror.MenuActivity.m_gsocket;

/**
 * Created by VICL-10
 */

public class TCPListener extends Thread {

    private Handler m_Handler;
    private Socket m_socket;
    private DataInputStream m_dis;
    private String readMsg ;
    private Context m_context;

    private String return_msg;

    private InputStream in;

    public TCPListener(Context context, Handler handler)
    {
        m_context = context;
        m_Handler = handler;
     
    }

    @Override
    public void run()
    {
        int temp;
        while(true) {
            try{
                Log.e("TCP", "try");
                in = m_gsocket.getInputStream();
                m_dis = new DataInputStream(in);
                readMsg = m_dis.readUTF();
                temp = 0;
                m_Handler.post(new Runnable() {
                @Override
                public void run() {
                    Log.e("TCP", "run");
                    Toast.makeText(m_context, readMsg, Toast.LENGTH_SHORT).show();
                }
            });

            }
            catch (IOException e)
            {

            }
        }
    }
}
