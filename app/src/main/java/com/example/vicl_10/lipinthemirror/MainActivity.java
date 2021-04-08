package com.example.vicl_10.lipinthemirror;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.Signature;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.media.ThumbnailUtils;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import android.app.Activity;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

import java.io.File;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class MainActivity extends Activity{
    static MainActivity before;
    private Button mBtnSend;
    private TextView tv;
    private Button mBtnGoToMain;

    public static int n;
    public static String str;
    public static Activity AActivity;

   @Override
   public void onCreate(Bundle savedInstanceState) {
       super.onCreate(savedInstanceState);
       setContentView(R.layout.activity_main);
       AActivity = MainActivity.this;

       int n = 0;


       final DBHelper dbHelper = new DBHelper(getApplicationContext(), "CHECKEDU.db", null, 1);

       mBtnGoToMain = (Button)findViewById(R.id.btnGoToMain);


       n = dbHelper.getNumberOfWords();

       switch(n)
       {
           case 0: str="가"; break;
           case 1: str="나"; break;
           case 2: str="다"; break;
           case 3: str="라"; break;
           case 4: str="마"; break;
       }


       int img[] =
               {R.drawable.a, R.drawable.ya, R.drawable.uh, R.drawable.yuh, R.drawable.oh,
                       R.drawable.yo, R.drawable.wu, R.drawable.yu, R.drawable.uu, R.drawable.i,
                       R.drawable.null1, R.drawable.null1, R.drawable.null1, R.drawable.null1, R.drawable.null1,
                       R.drawable.ga, R.drawable.na, R.drawable.da, R.drawable.ra, R.drawable.ma,
                       R.drawable.ba, R.drawable.sa, R.drawable.ja, R.drawable.cha, R.drawable.ca,
                       R.drawable.ta, R.drawable.pa, R.drawable.ha, R.drawable.null1, R.drawable.null1,
                       R.drawable.null1, R.drawable.null1, R.drawable.null1, R.drawable.null1, R.drawable.null1,
                       R.drawable.go, R.drawable.no, R.drawable.do1, R.drawable.ro, R.drawable.mo,
                       R.drawable.gi, R.drawable.ni, R.drawable.di, R.drawable.ri, R.drawable.mi,
                       R.drawable.null1, R.drawable.null_white};

       // 커스텀 아답타 생성
       MyAdapter adapter = new MyAdapter(
               getApplicationContext(),
               R.layout.row,       // GridView 항목의 레이아웃 row.xml
               img);    // 데이터

       GridView gv = (GridView) findViewById(R.id.gridView_main);
       gv.setAdapter(adapter);  // 커스텀 아답타를 GridView 에 적용


       try {
           PackageInfo info = getPackageManager().getPackageInfo(getPackageName(), PackageManager.GET_SIGNATURES);
           for (Signature signature : info.signatures) {
               MessageDigest md = MessageDigest.getInstance("SHA");
               md.update(signature.toByteArray());
               System.out.println("PPP" + Base64.encodeToString(md.digest(), Base64.DEFAULT));
           }
       } catch (PackageManager.NameNotFoundException e) {

       } catch (NoSuchAlgorithmException e) {

       }

       gv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
           @Override
           public void onItemClick(AdapterView<?> parent, View view,
                                   int position, long id) {
               Intent edu_intent = new Intent(getApplicationContext(), EduActivity.class);
               edu_intent.putExtra("numberOfWords", position);
               startActivity(edu_intent);
           }
       });

       mBtnGoToMain.setOnClickListener(new View.OnClickListener()
       {
           @Override
           public void onClick(View v)
           {
               Intent goto_intent = new Intent(MainActivity.this, MenuActivity.class);
               startActivity(goto_intent); //학습하기 액티비티 실행
           }
       });
   }
}

class MyAdapter extends BaseAdapter {
    Context context;
    int layout;
    int img[];
    LayoutInflater inf;

    public MyAdapter(Context context, int layout, int[] img) {
        this.context = context;
        this.layout = layout;
        this.img = img;
        inf = (LayoutInflater) context.getSystemService
                (Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        return img.length;
    }

    @Override
    public Object getItem(int position) {
        return img[position];
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView==null)
            convertView = inf.inflate(layout, null);
        ImageView iv = (ImageView)convertView.findViewById(R.id.imageView1);
        iv.setImageResource(img[position]);

        return convertView;
    }
}