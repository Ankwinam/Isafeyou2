package com.example.kwinam.isafeyou.isafeyou.Activity;

import android.annotation.TargetApi;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TimePicker;

import com.example.kwinam.isafeyou.R;

/**
 * Created by Taewoong on 2017-08-04.
 */

public class CommunityAddActivity extends AppCompatActivity{
    private static final int SEARCH_ADDRESS_ACTIVITY = 10000;
    private EditText et_address;
    ImageButton btn_serach;
    TimePicker start_time;
    boolean processed = true;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.community_add);

        et_address = (EditText)findViewById(R.id.address_result);
        et_address.setClickable(false);
        et_address.setEnabled(false);
        et_address.setFocusable(false);
        et_address.setFocusableInTouchMode(false);
        btn_serach = (ImageButton)findViewById(R.id.search_address_btn);

        btn_serach.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                Intent i = new Intent(CommunityAddActivity.this,WebViewActivity.class);
                startActivityForResult(i, SEARCH_ADDRESS_ACTIVITY);
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
}
