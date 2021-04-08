
package com.example.vicl_10.lipinthemirror;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.media.Image;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.kakao.network.ErrorResult;
import com.kakao.usermgmt.UserManagement;
import com.kakao.usermgmt.callback.LogoutResponseCallback;
import com.kakao.usermgmt.callback.MeResponseCallback;
import com.kakao.usermgmt.response.model.UserProfile;

import org.w3c.dom.Text;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.Socket;

/**
 * Created by VICL-10 on 2017-02-15.
 */

public class MenuActivity extends Activity{

    String path = "";
    String name = "";

    public static Socket m_gsocket;
    public static DataInputStream m_dis;
    public static DataOutputStream m_dos;

    @Override
    protected void onCreate(Bundle savedInstanceState){

        super.onCreate(savedInstanceState);
        setContentView(R.layout.menu);

        final DBHelper dbHelper = new DBHelper(getApplicationContext(), "SettingIP.db", null, 1);

        try{
            Intent intent = getIntent();
            path = intent.getExtras().getString("path");
            name = intent.getExtras().getString("name");
        }catch(NullPointerException e)
        {
            e.printStackTrace();
        }
        try {

        }
        catch (Exception e) {
            Log.e("error",e.getMessage());
        }


        Button edu = (Button)findViewById(R.id.educateBtn);
        Button set = (Button)findViewById(R.id.settingBtn);
        Button ter = (Button)findViewById(R.id.terminateBtn);
        Button cid = (Button)findViewById(R.id.idBtn); //check id
        Button use = (Button)findViewById(R.id.idUse);

        edu.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                Intent edu_intent = new Intent(MenuActivity.this, MainActivity.class);
                startActivity(edu_intent); //학습하기 액티비티 실행
            }
        });

        set.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                Intent set_intent = new Intent(MenuActivity.this, SettingActivity.class);
                startActivity(set_intent); //IP 세팅 액티비티 실행
            }
        });

        cid.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                Intent set_intent = new Intent(MenuActivity.this, CheckIDActivity.class);
                set_intent.putExtra("path", path);
                set_intent.putExtra("name", name);
                startActivity(set_intent);
            }
        });

        use.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                TCPClient use_start = new TCPClient("wuse\n");
                use_start.start(); //서버로 종료하라는 메세지
                Intent use_intent = new Intent(MenuActivity.this, GuideActivity.class);
                startActivity(use_intent);
            }
        });



    }
    public void ter(View v) {
        final MainActivity aa = (MainActivity)MainActivity.AActivity;
        AlertDialog.Builder builder = new AlertDialog.Builder(MenuActivity.this);
        builder.setMessage("정말로 종료하시겠습니까?");
        builder.setTitle("종료 알림창")
                .setCancelable(false)
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int i) {
                        finishAffinity();
                    }
                })
                .setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int i) {
                        dialog.cancel();
                    }
                });
        AlertDialog alert = builder.create();
        alert.setTitle("종료 알림창");
        alert.show();
    }
}
