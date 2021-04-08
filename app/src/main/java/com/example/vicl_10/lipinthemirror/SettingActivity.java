package com.example.vicl_10.lipinthemirror;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

/**
 * Created by VICL-10
 */

public class SettingActivity extends Activity
{
    private EditText et_IP;
    private TextView tv_currentIP;
    private Button btn_setIP;
    private EditText nick;
    private Button btn_setname;

    private String current_IP;
    public static String db_IP;
    public static String smartmirror_IP;
    public static int smartmirror_PORT;
    public static Context context_main;
    public String nicks;
    static String nickName;

    private SharedPreferences prefs;
    private SharedPreferences.Editor editor;


    @Override
    protected void onCreate(Bundle savedInstanceState){

        final DBHelper dbHelper = new DBHelper(getApplicationContext(), "SettingIP.db", null, 1);

        super.onCreate(savedInstanceState);
        setContentView(R.layout.setting);

        tv_currentIP = (TextView)findViewById(R.id.tv_currentIP);
        et_IP = (EditText)findViewById(R.id.et_ip);
        btn_setIP = (Button)findViewById(R.id.btn_setIP);
        nick = (EditText)findViewById(R.id.nickName);
        btn_setname = (Button)findViewById(R.id.setName);

        db_IP = dbHelper.getResult();

        prefs = SettingActivity.this.getSharedPreferences("login", 0);
        editor = prefs.edit();

        current_IP = prefs.getString("SMARTMIRROR_IP", "0.0.0.0");
        tv_currentIP.setText(db_IP);
        smartmirror_PORT = 6667;

        et_IP.setText(""+db_IP); //아이피 기존에 있는 거 들어있도록

        btn_setIP.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                smartmirror_IP = et_IP.getText().toString();
                editor.putString("SMARTMIRROR_IP", smartmirror_IP);
                editor.commit();
                dbHelper.insert_IP(smartmirror_IP);
                Toast.makeText(SettingActivity.this,"IP가 "+smartmirror_IP+"로 설정되었습니다.", Toast.LENGTH_SHORT).show();
            }
        });
        btn_setname.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                nickName = nick.getText().toString();
                //DB에도 닉네임 넣을거면 DB에 닉네임 컬럼 만들고 DB삽입 코드 한줄 넣기
                Toast.makeText(SettingActivity.this,"닉네임이 "+nickName+"로 설정되었습니다.", Toast.LENGTH_SHORT).show();

            }
        });
        context_main = this;
    }
}
