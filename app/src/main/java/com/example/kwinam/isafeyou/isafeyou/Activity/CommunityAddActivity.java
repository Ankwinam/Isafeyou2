package com.example.kwinam.isafeyou.isafeyou.Activity;

import android.annotation.TargetApi;
import android.app.ActionBar;
import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.icu.util.Calendar;
import android.icu.util.GregorianCalendar;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.example.kwinam.isafeyou.R;

import java.util.Locale;

/**
 * Created by Taewoong on 2017-08-04.
 */

@RequiresApi(api = Build.VERSION_CODES.N)
public class CommunityAddActivity extends AppCompatActivity{
    private static final int SEARCH_ADDRESS_ACTIVITY = 10000;
    private final int DIALOG_DATE = 1;
    private final int DIALOG_TIME = 2;
    private EditText et_address;
    private EditText extra_et_adress;
    private EditText date;
    private EditText time;
    private EditText comment;
    private Button btn_enroll;
    private Button btn_cancel;
    ImageButton btn_serach;
    TimePicker start_time;
    boolean processed = true;
    GregorianCalendar calendar = new GregorianCalendar(Locale.KOREA);
    int currentyear = calendar.get(Calendar.YEAR);
    int currentdate = calendar.get(Calendar.DATE);
    int currentmonth = calendar.get(Calendar.MONTH) + 1;
    int currenthour = calendar.get(Calendar.HOUR);
    int currentmin = calendar.get(Calendar.MINUTE);
    int currentampm = calendar.get(Calendar.AM_PM);
    Intent intent;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.community_add);
        intent = new Intent(this, FireBaseCommunity.class);
        final Bundle bundle = new Bundle();
        date = (EditText)findViewById(R.id.date);
        date.setHint(" "+currentyear + "년 " +  currentmonth + "월 " + currentdate + "일");
        time = (EditText)findViewById(R.id.time);
        String ampm = currentampm == calendar.AM ? "오전" : "오후";
        time.setHint(" " + currenthour + "시 " + currentmin + "분 " + ampm);
        et_address = (EditText)findViewById(R.id.address_result);
        extra_et_adress = (EditText)findViewById(R.id.extra_address);
        btn_enroll = (Button)findViewById(R.id.enroll);
        btn_cancel = (Button)findViewById(R.id.cancel);
        comment = (EditText) findViewById(R.id.comment);
        unfocusable(et_address);
        unfocusable(date);
        unfocusable(time);
        btn_serach = (ImageButton)findViewById(R.id.search_address_btn);

        btn_serach.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                Intent i = new Intent(CommunityAddActivity.this,WebViewActivity.class);
                startActivityForResult(i, SEARCH_ADDRESS_ACTIVITY);
            }
        });

        date.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDialog(DIALOG_DATE);
            }
        });

        time.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                showDialog(DIALOG_TIME);
            }
        });

        btn_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        btn_enroll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                bundle.putString("ADDRESS",et_address.getText().toString());
                bundle.putString("ADDRESS_EXTRA", extra_et_adress.getText().toString());
                bundle.putString("DATE", date.getText().toString());
                bundle.putString("TIME", time.getText().toString());
                bundle.putString("COMMENT", comment.getText().toString());
                intent.putExtras(bundle);
                setResult(Activity.RESULT_OK,intent);
                finish();
            }
        });
    }

    public void onActivityResult(int requestCode, int resultCode, Intent intent){
        super.onActivityResult(requestCode, resultCode, intent);
        switch(requestCode){
            case SEARCH_ADDRESS_ACTIVITY:
                if(resultCode == RESULT_OK){
                    String data = intent.getExtras().getString("data");
                    if (data != null)
                        et_address.setText(data);
                }
                break;
        }
    }

    @Override
    @Deprecated
    protected Dialog onCreateDialog(int id) {
        switch(id){
            case DIALOG_DATE :
                DatePickerDialog dpd = new DatePickerDialog(CommunityAddActivity.this,new DatePickerDialog.OnDateSetListener() {
                                    @Override
                                    public void onDateSet(DatePicker view, int year, int monthOfYear,int dayOfMonth) {
                                        date.setText(year + "년 " +  monthOfYear + "월 " + dayOfMonth + "일");
                                    }
                                }, // 사용자가 날짜설정 후 다이얼로그 빠져나올때
                                //    호출할 리스너 등록
                                currentyear, currentmonth, currentdate); // 기본값 연월일
                return dpd;
            case DIALOG_TIME :
                TimePickerDialog tpd =
                        new TimePickerDialog(CommunityAddActivity.this, new TimePickerDialog.OnTimeSetListener() {
                                    @Override
                                    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                                        if(hourOfDay > 12){
                                            time.setText(hourOfDay - 12 + "시 " + minute + "분 오후");
                                        }else{
                                            time.setText(hourOfDay + "시 " + minute + "분 오전");
                                        }
                                    }
                                },// 값설정시 호출될 리스너 등록
                                currenthour,currentmin, false); // 기본값 시분 등록
                // true : 24 시간(0~23) 표시
                // false : 오전/오후 항목이 생김
                return tpd;
        }
        return super.onCreateDialog(id);
    }

    public void unfocusable(EditText editText){
        editText.setFocusable(false);
        editText.setFocusableInTouchMode(false);
    }
}
