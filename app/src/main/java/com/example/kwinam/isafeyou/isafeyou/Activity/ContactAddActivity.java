package com.example.kwinam.isafeyou.isafeyou.Activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.ImageButton;
import android.widget.Toast;

import com.example.kwinam.isafeyou.R;

public class ContactAddActivity extends AppCompatActivity {

    ImageButton add_decision;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.contact_add);

        add_decision = (ImageButton)findViewById(R.id.add_decision_btn);

        add_decision.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast toast = Toast.makeText(getApplicationContext(),
                        "추가 버튼 눌림", Toast.LENGTH_LONG);
                toast.setGravity(Gravity.BOTTOM, 0, 0);
                toast.show();
            }
        });

    }
}
