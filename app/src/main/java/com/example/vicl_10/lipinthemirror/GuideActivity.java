package com.example.vicl_10.lipinthemirror;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;

/**
 * Created by VICL-10
 */

public class GuideActivity extends Activity {

    TCPClient edu_ok;

    @Override
    protected void onCreate(Bundle savedInstanceState){

        super.onCreate(savedInstanceState);
        setContentView(R.layout.guide);}

    @Override
    public void onBackPressed() //뒤로가기 눌렀을 때
    {
        AlertDialog.Builder alert_confirm = new AlertDialog.Builder(GuideActivity.this);
        alert_confirm.setMessage("메뉴 화면으로 돌아가시겠습니까?").setCancelable(false).setPositiveButton("YES",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        try {
                            edu_ok = new TCPClient("edu_ok\n");
                            Intent main_intent = new Intent(getApplicationContext(), MenuActivity.class);
                            startActivity(main_intent);
                        }
                        catch (Exception e) {}
                        edu_ok.start();
                        dialog.dismiss();//닫기
                    }
                }).setNegativeButton("NO",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                        return;
                    }
                });
        AlertDialog alert = alert_confirm.create();
        alert.show();
        // }
    }
}
