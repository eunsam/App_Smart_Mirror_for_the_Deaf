package com.example.vicl_10.lipinthemirror;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.kakao.usermgmt.UserManagement;
import com.kakao.usermgmt.callback.LogoutResponseCallback;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Set;

/**
 * Created by VICL-10
 */

public class CheckIDActivity extends Activity{

    String path = "";
    String name = ((SettingActivity)SettingActivity.context_main).nickName;

    int page = 0;

    Bitmap bm;

    ImageView profile;
    TextView nickname;
    TextView pageNumber;



    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.checkid);


        Intent intent = getIntent();
        path = intent.getExtras().getString("path");
        //name = intent.getExtras().getString("name");

        profile = (ImageView)findViewById(R.id.profile);
        nickname = (TextView)findViewById(R.id.tv_nickname);

        ImageButton out = (ImageButton)findViewById(R.id.logoutBtn);

        nickname.setText("닉네임 : "+name);

        Thread mThread = new Thread(){
            @Override
            public void run(){

                try {
                        URL url = new URL(path);
                        HttpURLConnection conn = (HttpURLConnection)url.openConnection();
                        conn.setDoInput(true);
                        conn.connect();

                        InputStream is = conn.getInputStream();
                        bm = BitmapFactory.decodeStream(is);
                   
                }catch(IOException e)
                {
                    e.printStackTrace();
                }
            }
        };
        mThread.start();

        try{
            mThread.join();
            profile.setImageBitmap(bm);
        }catch(InterruptedException e){}
    }



    public void mClickLogout(View v) {
        Toast.makeText(CheckIDActivity.this,"성공적으로 로그아웃 되었습니다.", Toast.LENGTH_SHORT).show();
        nickname.setText("로그인 필요");
    }
}
