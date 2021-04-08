package com.example.vicl_10.lipinthemirror;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;

import com.example.vicl_10.lipinthemirror.MainActivity;

public class SplashActivity extends Activity{
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        try{
            Thread.sleep(2500);
        }
        catch(InterruptedException e){
            e.printStackTrace();
        }
        startActivity(new Intent(this, KakaoSignupActivity.class));
        finish();
    }
}
