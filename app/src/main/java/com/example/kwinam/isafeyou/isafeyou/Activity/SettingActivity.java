package com.example.kwinam.isafeyou.isafeyou.Activity;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.media.Ringtone;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.SystemClock;
import android.preference.EditTextPreference;
import android.preference.ListPreference;
import android.preference.Preference;
import android.preference.PreferenceActivity;
import android.preference.PreferenceFragment;
import android.preference.PreferenceManager;
import android.preference.RingtonePreference;
import android.preference.SwitchPreference;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.text.TextUtils;
import android.util.Log;

import com.example.kwinam.isafeyou.R;
import com.example.kwinam.isafeyou.isafeyou.DataBase.SQLiteAdapter;
import com.example.kwinam.isafeyou.isafeyou.Fragment.TabFragment1;
import com.example.kwinam.isafeyou.isafeyou.Fragment.TabFragment2;

/**
 * Created by Taewoong on 2017-08-07.
 */

public class SettingActivity extends PreferenceActivity {
    private SwitchPreference switch_pref;
    TabFragment1 tabfragment1 = new TabFragment1();
    static final int REQUEST_CODE = 1234;
    private SQLiteAdapter mySQLiteAdapter;
    SQLiteDatabase db;
    String sql;
    Cursor cursor;
    String[] result;
    int count;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mySQLiteAdapter = new SQLiteAdapter(this);
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
        getFragmentManager()
                .beginTransaction()
                .replace(android.R.id.content,
                        new MyPreferenceFragment()).commit();
    }

    public class MyPreferenceFragment extends PreferenceFragment{
        @Override
        public void onCreate(@Nullable Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            addPreferencesFromResource(R.xml.pref_settings);
            setOnPreferenceChange(findPreference("ringtone"));
            setOnPreferenceChange(findPreference("time_interval"));
            setOnPreferenceChange(findPreference("message"));
            switch_pref = (SwitchPreference)findPreference("safe");
            switch_pref.setOnPreferenceChangeListener(new Preference.OnPreferenceChangeListener() {
                @Override
                public boolean onPreferenceChange(Preference preference, Object newValue) {
                    if((Boolean)newValue == true){
                        for(int i=0; i<count; i++){
                            try {
                                tabfragment1.sendSMS(result[i],37.281727, 127.044132,"안심귀가 서비스를 시작합니다.");
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }
                        }
                        long period = 1000 * 30;
                        long after = 1000 * 30;
                        long t = SystemClock.elapsedRealtime();
                        //알람서비스 시작
                        AlarmManager alarmManager = (AlarmManager)getSystemService(ALARM_SERVICE);
                        Intent receiverIntent = new Intent(SettingActivity.this, AlarmService_service.class);
                        PendingIntent pendingIntent = PendingIntent.getBroadcast(SettingActivity.this,0,receiverIntent,0);
                        alarmManager.setRepeating(AlarmManager.ELAPSED_REALTIME_WAKEUP, t +after,period,pendingIntent);

                    }else if((Boolean)newValue == false){
                        //알람서비스 끄기
                        AlarmManager alarmManager = (AlarmManager)getSystemService(ALARM_SERVICE);
                        Intent receiverIntent = new Intent(SettingActivity.this, AlarmService_service.class);
                        PendingIntent pendingIntent = PendingIntent.getBroadcast(SettingActivity.this,0,receiverIntent,0);
                        alarmManager.cancel(pendingIntent);
                    }
                    return true;
                }
            });

        }
    }

    private void setOnPreferenceChange(Preference mPreference) {
        mPreference.setOnPreferenceChangeListener(onPreferenceChangeListener);
        onPreferenceChangeListener.onPreferenceChange(
                mPreference,
                PreferenceManager.getDefaultSharedPreferences(
                        mPreference.getContext()).getString(
                        mPreference.getKey(), ""));
    }

    private Preference.OnPreferenceChangeListener onPreferenceChangeListener = new Preference.OnPreferenceChangeListener(){
        @Override
        public boolean onPreferenceChange(Preference preference, Object newValue) {
            String stringValue = newValue.toString();
            if (preference instanceof EditTextPreference) {
                preference.setSummary(stringValue);
            } else if (preference instanceof ListPreference) {
                /**
                 * ListPreference의 경우 stringValue가 entryValues이기 때문에 바로 Summary를
                 * 적용하지 못한다 따라서 설정한 entries에서 String을 로딩하여 적용한다
                 */

                ListPreference listPreference = (ListPreference) preference;
                int index = listPreference.findIndexOfValue(stringValue);

                preference
                        .setSummary(index >= 0 ? listPreference.getEntries()[index]
                                : null);

            } else if (preference instanceof RingtonePreference) {
                /**
                 * RingtonePreference의 경우 stringValue가
                 * content://media/internal/audio/media의 형식이기 때문에
                 * RingtoneManager을 사용하여 Summary를 적용한다
                 *
                 * 무음일경우 ""이다
                 */

                if (TextUtils.isEmpty(stringValue)) {
                    // Empty values correspond to 'silent' (no ringtone).
                    preference.setSummary("무음");

                } else {
                    Ringtone ringtone = RingtoneManager.getRingtone(
                            preference.getContext(), Uri.parse(stringValue));

                    if (ringtone == null) {
                        // Clear the summary if there was a lookup error.
                        preference.setSummary(null);

                    } else {
                        String name = ringtone
                                .getTitle(preference.getContext());
                        preference.setSummary(name);
                    }
                }
            }
            return true;
        }
    };
}
