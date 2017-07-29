package com.example.kwinam.isafeyou.isafeyou.Activity;

import android.content.Intent;
import android.database.Cursor;
import android.provider.ContactsContract;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.example.kwinam.isafeyou.R;
import com.google.android.gms.vision.text.Text;

public class ContactAddActivity extends AppCompatActivity {

    ImageButton add_decision;
    ImageButton contact_plus;

    String name = null;
    String number = null;

    TextView textname;
    TextView textphone;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.contact_add);

        add_decision = (ImageButton)findViewById(R.id.add_decision_btn);
        contact_plus = (ImageButton)findViewById(R.id.contact_plus);

        textname = (TextView)findViewById(R.id.contactadd_nameinput);
        textphone = (TextView)findViewById(R.id.contactadd_phoneinput);

        add_decision.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast toast = Toast.makeText(getApplicationContext(),
                        "추가 버튼 눌림", Toast.LENGTH_LONG);
                toast.setGravity(Gravity.BOTTOM, 0, 0);
                toast.show();
            }
        });

        contact_plus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_PICK);
                intent.setData(ContactsContract.CommonDataKinds.Phone.CONTENT_URI);
                startActivityForResult(intent, 0);
            }
        });

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(resultCode == RESULT_OK)
        {
            Cursor cursor = getContentResolver().query(data.getData(),
                    new String[]{ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME,
                            ContactsContract.CommonDataKinds.Phone.NUMBER}, null, null, null);
            cursor.moveToFirst();
            name = cursor.getString(0);        //0은 이름을 얻어옵니다.
            number = cursor.getString(1);   //1은 번호를 받아옵니다.
            textname.setText(name);
            textphone.setText(number);
            cursor.close();
        }
        super.onActivityResult(requestCode, resultCode, data);
    }



}
