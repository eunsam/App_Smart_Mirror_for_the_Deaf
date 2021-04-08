package com.example.vicl_10.lipinthemirror;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

/**
 * Created by VICL-10
 */



public class TempActivity extends Activity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Intent intent = getIntent();
        int num = intent.getExtras().getInt("next");

        Intent new_edu_intent = new Intent(getApplicationContext(), EduActivity.class);
        new_edu_intent.putExtra("numberOfWords", num);
        startActivity(new_edu_intent);
    }
}
