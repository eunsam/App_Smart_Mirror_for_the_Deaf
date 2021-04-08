package com.example.vicl_10.lipinthemirror;

/**
 * Created by VICL-10
 */

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.Menu;

import com.kakao.auth.ApiResponseCallback;
import com.kakao.auth.ErrorCode;
import com.kakao.network.ErrorResult;
import com.kakao.usermgmt.UserManagement;
import com.kakao.usermgmt.callback.MeResponseCallback;
import com.kakao.usermgmt.response.model.UserProfile;

import com.kakao.util.helper.log.Logger;

public class KakaoSignupActivity extends Activity{

    String profilePath;
    String nickname;
    String page = "0";

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestMe();
    }

    protected void requestMe() { //유저의 정보를 받아오는 함수
        UserManagement.requestMe(new MeResponseCallback() {
            @Override
            public void onFailure(ErrorResult errorResult) {
                String message = "failed to get user info. msg=" + errorResult;
                Logger.d(message);

                ErrorCode result = ErrorCode.valueOf(errorResult.getErrorCode());
                if (result == ErrorCode.CLIENT_ERROR_CODE) {
                    finish();
                } else {
                    redirectLoginActivity();
                }
            }

            @Override
            public void onSessionClosed(ErrorResult errorResult) {
                redirectLoginActivity();
            }

            @Override
            public void onNotSignedUp() {} // 카카오톡 회원이 아닐 시 showSignup(); 호출해야함

            @Override
            public void onSuccess(UserProfile userProfile)
            {  //성공 시 userProfile 형태로 반환
                Logger.d("UserProfile : " + userProfile);
                profilePath = userProfile.getProfileImagePath().toString();
                nickname = userProfile.getNickname().toString();
				redirectMainActivity(profilePath, nickname);
            }
        });
    }

    private void redirectMainActivity(String path, String name) {
        Intent intent = new Intent(KakaoSignupActivity.this, MenuActivity.class);
        intent.putExtra("path", path);
        intent.putExtra("name", name);
        startActivity(intent);
        finish();
    }

    protected void redirectLoginActivity() {
        final Intent intent = new Intent(this, MainActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
        startActivity(intent);
        finish();
    }

}

