package com.example.kwinam.isafeyou.isafeyou.DataBase;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;


/**
 * Created by KwiNam on 2017-08-04.
 */

//SQLite
public class dbHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "mycontacts.db";  //데이터베이스 파일 이름
    private static final int DATABASE_VERSION = 1;

    /*
     *먼저 SQLiteOpenHelper클래스를 상속받은 dbHelper클래스가 정의 되어 있다. 데이터베이스 파일 이름은 "mycontacts.db"가되고,
     *데이터베이스 버전은 1로 되어있다. 만약 데이터베이스가 요청되었는데 데이터베이스가 없으면 onCreate()를 호출하여 데이터베이스
     *파일을 생성해준다.
     */

    public dbHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        Log.d("테이블", "데이터베이스생성됨");
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE contact (" +
                "_id INTEGER PRIMARY KEY, " +
                "name TEXT, " +
                "phone TEXT);");
        Log.d("테이블", "테이블 생성됨");
    }
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXITS contact");
        Log.d("테이블", "업그레이드됨");
        onCreate(db);
    }

}
