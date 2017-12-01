package com.example.kwinam.isafeyou.isafeyou.Activity;

import android.Manifest;
import android.app.Activity;
import android.app.Fragment;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.telephony.SmsManager;
import android.util.Log;
import android.widget.Toast;

import com.example.kwinam.isafeyou.R;
import com.example.kwinam.isafeyou.isafeyou.DataBase.SQLiteAdapter;
import com.example.kwinam.isafeyou.isafeyou.Fragment.TabFragment1;
import com.example.kwinam.isafeyou.isafeyou.Fragment.TabFragment2;
import com.example.kwinam.isafeyou.isafeyou.Activity.MainActivity;

import static com.example.kwinam.isafeyou.isafeyou.Fragment.TabFragment2.lm;

/**
 * Created by Taewoong on 2017-11-21.
 */

public class AlarmService_service extends BroadcastReceiver {
    TabFragment1 tabFragment1 = new TabFragment1();
    private SQLiteAdapter mySQLiteAdapter;
    SQLiteDatabase db;
    String sql;
    Cursor cursor;
    String[] result;
    int count;
    @Override
    public void onReceive(Context context, Intent intent) {
        mySQLiteAdapter = new SQLiteAdapter(context);
        try{
            db = mySQLiteAdapter.getWritableDatabase(); //데이터베이스 객체를 얻기 위해 호출
        } catch (SQLiteException e) {
            db = mySQLiteAdapter.getReadableDatabase();
        }
        try{
            sql = "SELECT * FROM contact";
            cursor = db.rawQuery(sql, null);

            count = cursor.getCount(); //db 데이터 개수
            result = new String[count];
            Log.d("번호", "헤헤"+count);

            for(int i = 0; i < count; i++){
                cursor.moveToNext(); //첫번째에서 다음 레코드가 없을때까지 읽음
                String str_phone = cursor.getString(2); //첫번째 속성
                result[i] = str_phone; //각각의 속성들을 해당 배열의 i열에 저장
                Log.d("번호", result[i]);
            }

        } catch (Exception e) {
            System.out.println("select Error :  " + e);
        }

        Log.e("Send Message", "메시지 갑니다");
        try {
            for(int i=0; i<count; i++) {
                tabFragment1.sendSMS(result[i],37.281727, 127.044132,"위의 장소를 지나는 중입니다.");
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
