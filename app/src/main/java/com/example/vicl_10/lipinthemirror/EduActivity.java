package com.example.vicl_10.lipinthemirror;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.ActivityNotFoundException;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.speech.RecognitionListener;
import android.speech.RecognizerIntent;
import android.speech.SpeechRecognizer;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.kakao.usermgmt.UserManagement;
import com.kakao.usermgmt.callback.LogoutResponseCallback;

import org.w3c.dom.Text;

import java.io.BufferedOutputStream;
import java.io.DataInput;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.InetAddress;
import java.net.Socket;
import java.util.ArrayList;

/**
 * Created by VICL-10 on
 */

public class EduActivity extends Activity{

    private static final int RESULT_SPEECH = 1;
    private Intent i;
    private ImageButton bt;
    private Button btnSave;
    private TextView sst_view;
    private TextView tv_check; //지우기

    TCPClient word_start;
    TCPClient word_receive;
    TCPClient edu_ok;

    private Handler m_Handler;
    public Socket m_socket;
    public DataInputStream m_dis;
    public DataOutputStream m_dos;
    public String readMsg;
    private TCPListener tl;

    String path = "";
    String name = "";
    String temp2 = null;
    int num=0;

    int page = 0;

    String res;

    Handler mHandler = new Handler(){
        int n = 0;

        @Override
        public void handleMessage(Message msg){

            final DBHelper dbHelper = new DBHelper(getApplicationContext(), "CheckEdu.db", null, 1);
            
            /*res = (msg.obj).toString();

                if (res != null) {
                    if (res.equals("success")) {
                        readMsg = "s";
                    } else if (res.equals("fail")) {
                        readMsg = "r";
                    }

                    switch (readMsg) {
                        case "s":
                            AlertDialog.Builder alert = new AlertDialog.Builder(EduActivity.this);
                            alert.setPositiveButton("확인", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    try {
                                        edu_ok = new TCPClient("wedu_ok\n");
                                        edu_ok.start();
                                        num += 1;
                                    } catch (Exception e) {}
                                    dbHelper.insert(num);
                                    dialog.dismiss();//닫기
                                    Intent new_edu_intent = new Intent(getApplicationContext(), TempActivity.class);
                                    new_edu_intent.putExtra("next", num);
                                    startActivity(new_edu_intent);
                                }
                            });
                            alert.setMessage("딩동댕! 다음 학습으로 넘어갑니다.");
                            alert.show();
                            break;

                        case "r":
                            AlertDialog.Builder alert_fail = new AlertDialog.Builder(EduActivity.this);
                            alert_fail.setPositiveButton("확인", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    dialog.dismiss();
                                }
                            });
                            alert_fail.setMessage("다시 한 번 정확하게 발음해주세요.");
                            alert_fail.show();
                            break;
                        default:
                            break;
                    }
                }
       */ }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.edu);

        final DBHelper dbHelper = new DBHelper(getApplicationContext(), "CheckEdu.db", null, 1);

        bt = (ImageButton) findViewById(R.id.button);

        sst_view = (TextView) findViewById(R.id.result_of_sst);

        bt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TCPClient send_msg = new TCPClient("wvoice\n");
                send_msg.start();
                if (v.getId() == R.id.button) {
                    i = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
                    i.putExtra(RecognizerIntent.EXTRA_CALLING_PACKAGE, getPackageName());
                    i.putExtra(RecognizerIntent.EXTRA_LANGUAGE, "ko-KR");
                    i.putExtra(RecognizerIntent.EXTRA_PROMPT, "말해주세요");

                    Toast.makeText(EduActivity.this, "음성인식을 시작합니다 :)", Toast.LENGTH_SHORT).show();

                    try {
                        startActivityForResult(i, RESULT_SPEECH);
                    } catch (ActivityNotFoundException e) {
                        Toast.makeText(getApplicationContext(), "Speech To Text를 지원하지 않습니다.", Toast.LENGTH_SHORT).show();
                        e.getStackTrace();
                    }
                }
            }
        });

        Intent intent = getIntent();
        num = intent.getExtras().getInt("numberOfWords");

        final TextView tv = (TextView) findViewById(R.id.tv_edu_words);



        switch (num) {
            case 0:
                try {
                    word_start = new TCPClient("w_a\n");
                } catch (Exception e) {
                }
                word_start.start();
                tv.setText("아");
                temp2 = "아";
                break;
            case 1:
                try {
                    word_start = new TCPClient("w_ya\n");
                } catch (Exception e) {
                }
                word_start.start();
                tv.setText("야");
                temp2 = "야";
                break;
            case 2:
                try {
                    word_start = new TCPClient("w_uh\n");
                } catch (Exception e) {
                }
                word_start.start();
                tv.setText("어");
                temp2 = "어";
                break;
            case 3:
                try {
                    word_start = new TCPClient("w_yuh\n");
                } catch (Exception e) {
                }
                word_start.start();
                tv.setText("여");
                temp2 = "여";
                break;
            case 4:
                try {
                    word_start = new TCPClient("w_oh\n");
                } catch (Exception e) {
                }
                word_start.start();
                tv.setText("오");
                temp2 = "오";
                break;
            case 5:
                try {
                    word_start = new TCPClient("w_yo\n");
                } catch (Exception e) {
                }
                word_start.start();
                tv.setText("요");
                temp2 = "요";
                break;
            case 6:
                try {
                    word_start = new TCPClient("w_wu\n");
                } catch (Exception e) {
                }
                word_start.start();
                tv.setText("우");
                temp2 = "우";
                break;
            case 7:
                try {
                    word_start = new TCPClient("w_yu\n");
                } catch (Exception e) {
                }
                word_start.start();
                tv.setText("유");
                temp2 = "유";
                break;
            case 8:
                try {
                    word_start = new TCPClient("w_uu\n");
                } catch (Exception e) {
                }
                word_start.start();
                tv.setText("으");
                temp2 = "으";
                break;
            case 9:
                try {
                    word_start = new TCPClient("w_i\n");
                } catch (Exception e) {
                }
                word_start.start();
                tv.setText("이");
                temp2 = "이";
                break;
            case 15:
                try {
                    word_start = new TCPClient("w_ga\n");
                } catch (Exception e) {
                }
                word_start.start();
                tv.setText("가");
                temp2 = "가";
                break;
            case 16:
                try {
                    word_start = new TCPClient("w_na\n");
                } catch (Exception e) {
                }
                word_start.start();
                tv.setText("나");
                temp2 = "나";
                break;
            case 17:
                try {
                    word_start = new TCPClient("w_da\n");
                } catch (Exception e) {
                }
                word_start.start();
                tv.setText("다");
                temp2 = "다";
                break;
            case 18:
                try {
                    word_start = new TCPClient("w_ra\n");
                } catch (Exception e) {
                }
                word_start.start();
                tv.setText("라");
                temp2 = "라";
                break;
            case 19:
                try {
                    word_start = new TCPClient("w_ma\n");
                } catch (Exception e) {
                }
                word_start.start();
                tv.setText("마");
                temp2 = "마";
                break;
            case 20:
                try {
                    word_start = new TCPClient("w_ma\n");
                } catch (Exception e) {
                }
                word_start.start();
                tv.setText("바");
                temp2 = "바";
                break;
            case 21:
                try {
                    word_start = new TCPClient("w_sa\n");
                } catch (Exception e) {
                }
                word_start.start();
                tv.setText("사");
                temp2 = "사";
                break;
            case 22:
                try {
                    word_start = new TCPClient("w_ja\n");
                } catch (Exception e) {
                }
                word_start.start();
                tv.setText("자");
                temp2 = "자";
                break;
            case 23:
                try {
                    word_start = new TCPClient("w_cha\n");
                } catch (Exception e) {
                }
                word_start.start();
                tv.setText("차");
                temp2 = "차";
                break;
            case 24:
                try {
                    word_start = new TCPClient("w_ca\n");
                } catch (Exception e) {
                }
                word_start.start();
                tv.setText("카");
                temp2 = "카";
                break;
            case 25:
                try {
                    word_start = new TCPClient("w_ta\n");
                } catch (Exception e) {
                }
                word_start.start();
                tv.setText("타");
                temp2 = "타";
                break;
            case 26:
                try {
                    word_start = new TCPClient("w_pa\n");
                } catch (Exception e) {
                }
                word_start.start();
                tv.setText("파");
                temp2 = "파";
                break;
            case 27:
                try {
                    word_start = new TCPClient("w_ha\n");
                } catch (Exception e) {
                }
                word_start.start();
                tv.setText("하");
                temp2 = "하";
                break;
            case 35:
                try {
                    word_start = new TCPClient("w_go\n");
                } catch (Exception e) {
                }
                word_start.start();
                tv.setText("고");
                temp2 = "고";
                break;
            case 36:
                try {
                    word_start = new TCPClient("w_no\n");
                } catch (Exception e) {
                }
                word_start.start();
                tv.setText("노");
                temp2 = "노";
                break;
            case 37:
                try {
                    word_start = new TCPClient("w_do\n");
                } catch (Exception e) {
                }
                word_start.start();
                tv.setText("도");
                temp2 = "도";
                break;
            case 38:
                try {
                    word_start = new TCPClient("w_ro\n");
                } catch (Exception e) {
                }
                word_start.start();
                tv.setText("로");
                temp2 = "로";
                break;
            case 39:
                try {
                    word_start = new TCPClient("w_mo\n");
                } catch (Exception e) {
                }
                word_start.start();
                tv.setText("모");
                temp2 = "모";
                break;
            case 40:
                try {
                    word_start = new TCPClient("w_gi\n");
                } catch (Exception e) {
                }
                word_start.start();
                tv.setText("기");
                temp2 = "기";
                break;
            case 41:
                try {
                    word_start = new TCPClient("w_ni\n");
                } catch (Exception e) {
                }
                word_start.start();
                tv.setText("니");
                temp2 = "니";
                break;
            case 42:
                try {
                    word_start = new TCPClient("w_di\n");
                } catch (Exception e) {
                }
                word_start.start();
                tv.setText("디");
                temp2 = "디";
                break;
            case 43:
                try {
                    word_start = new TCPClient("w_ri\n");
                } catch (Exception e) {
                }
                word_start.start();
                tv.setText("리");
                temp2 = "리";
                break;
            case 44:
                try {
                    word_start = new TCPClient("w_mi\n");
                } catch (Exception e) {
                }
                word_start.start();
                tv.setText("미");
                temp2 = "미";
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data){ //음성인식 전송하는 부분

        final DBHelper dbHelper = new DBHelper(getApplicationContext(), "CheckEdu.db", null, 1);


        int fail_count=0;
        super.onActivityResult(requestCode, resultCode, data);
        if(resultCode == RESULT_OK && (requestCode == RESULT_SPEECH))
        {
            ArrayList<String> sstResult = data.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);

            String result_sst = sstResult.get(0);
            String temp = null;

            if (result_sst.equals(temp2))
                temp = "okok";
            else
                temp = "nono";

            String uni = toUni(temp);



            try {




                word_start = new TCPClient("" + uni +"\n");
                word_start.start();
            }
            catch (Exception e)
            {

            }

            Toast.makeText(EduActivity.this, "음성인식 : "+result_sst, Toast.LENGTH_SHORT).show();
            TCPClient result = new TCPClient(mHandler);
            result.send();

        }
    }

    public String toUni(String str)
    {
        String result = null;
        try{
            if(str.equals("okok"))
                result = "wokok\n";
            else
                result = "wnono\n";
        }catch(Exception e)
        {
        e.printStackTrace();
        }
        return result;
        }

    @Override
    public void onBackPressed()
    {
        final DBHelper dbHelper = new DBHelper(getApplicationContext(), "CheckEdu.db", null, 1);

        AlertDialog.Builder alert_confirm = new AlertDialog.Builder(EduActivity.this);
        alert_confirm.setMessage("학습 목록 화면으로 돌아가시겠습니까?").setCancelable(false).setPositiveButton("YES",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        try {
                            edu_ok = new TCPClient("wedu_ok\n");
                            Intent new_main_intent = new Intent(getApplicationContext(), MainActivity.class);
                            new_main_intent.putExtra("page", num);
                            dbHelper.insert(num);
                            startActivity(new_main_intent);
                        }
                        catch (Exception e) {}
                        dbHelper.insert(num);
                        int value = dbHelper.insert(num);
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
}}