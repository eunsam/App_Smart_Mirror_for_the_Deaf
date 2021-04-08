package com.example.vicl_10.lipinthemirror;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.widget.Toast;

/**
 * Created by VICL-10
 */

public class DBHelper extends SQLiteOpenHelper {


    // DBHelper 생성자로 관리할 DB 이름과 버전 정보를 받음
    public DBHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    // DB를 새로 생성할 때 호출되는 함수
    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE CHECKEDU (_id INTEGER PRIMARY KEY AUTOINCREMENT, page INTEGER);");
        db.execSQL("CREATE TABLE SETTINGIP (_id INTEGER PRIMARY KEY AUTOINCREMENT, ipaddr TEXT);");
    }

    // DB 업그레이드를 위해 버전이 변경될 때 호출되는 함수
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    public int insert(int page) {
        SQLiteDatabase db = getWritableDatabase();
        db.execSQL("INSERT INTO CHECKEDU VALUES(null, '" + page +"');");
        return page;
    }

    public void insert_IP(String ipaddr)
    {
        SQLiteDatabase db_ip = getWritableDatabase();
        db_ip.execSQL("INSERT INTO SETTINGIP VALUES(null, '" + ipaddr +"');");
    }

    public String getResult() {
        // 읽기가 가능하게 DB 열기
        SQLiteDatabase db = getReadableDatabase();
        String result = "";
        String result1 = "";

        // DB에 있는 데이터를 쉽게 처리하기 위해 Cursor를 사용하여 테이블에 있는 모든 데이터 출력
        Cursor cursor = db.rawQuery("SELECT MAX(_id) FROM SETTINGIP", null);
        while (cursor.moveToNext()) {
            result += cursor.getString(0);
        }
        Cursor cursor1 = db.rawQuery("SELECT * FROM SETTINGIP WHERE _id='" + result + "';", null);
        while (cursor1.moveToNext()){
            result1 += cursor1.getString(1);
        }

        return result1;
    }

    public int getNumberOfWords(){
        SQLiteDatabase db = getReadableDatabase();
        int res=0;
        int res1=0;
        String result = "";
        Cursor cursor = db.rawQuery("SELECT MAX(_id) FROM CHECKEDU", null);
        while (cursor.moveToNext()) {
            result += cursor.getString(0);
        }
        Cursor cursor1 = db.rawQuery("SELECT * FROM CHECKEDU WHERE _id='" + result + "';", null);
        while (cursor1.moveToNext()){
            res1 += cursor1.getInt(1);
        }
        return res1;
    }
}