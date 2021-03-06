package com.example.kwinam.isafeyou.isafeyou.Activity;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.os.SystemClock;
import android.util.Log;
import android.widget.Toast;

import com.example.kwinam.isafeyou.isafeyou.DataBase.SQLiteAdapter;
import com.example.kwinam.isafeyou.isafeyou.Fragment.TabFragment1;
import com.example.kwinam.isafeyou.isafeyou.Fragment.TabFragment2;

/**
 * Created by Taewoong on 2017-11-19.
 */

public class OnLock_BroadcastReceiver extends BroadcastReceiver {
    long start = 0;
    int i = 0;
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
        if((System.currentTimeMillis() - start)/1000.0 > 3.0){
            i = 0;
        }
        if (intent.getAction().equals(Intent.ACTION_BOOT_COMPLETED))
        {
            Toast.makeText(context, "BOOT_COMPLETED", Toast.LENGTH_SHORT).show();
            Intent i = new Intent(context, OnLock_Service.class);
            context.startService(i);
        }
        else if(intent.getAction().equals(Intent.ACTION_SCREEN_ON))
        {
            i++;
            if(i == 1){
                start = System.currentTimeMillis();
            }
            Log.e("iii",""+i);
            if(i == 3){
                try {
                    for(int i=0; i<count; i++) {
                        tabFragment1.sendSMS(result[i],37.281727, 127.044132);
                    }
                    i = 0;
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
        else if (intent.getAction().equals(Intent.ACTION_SCREEN_OFF))
        {
            i++;
            Log.e("iii",""+i);
            if(i == 1){
                start = System.currentTimeMillis();
            }
            if(i == 3){

               try {
                   for(int i=0; i<count; i++) {
                       tabFragment1.sendSMS(result[i],37.281727, 127.044132);
                   }
                    i = 0;
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }


}
