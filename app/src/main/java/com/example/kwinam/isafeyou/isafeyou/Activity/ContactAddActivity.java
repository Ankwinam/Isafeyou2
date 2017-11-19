package com.example.kwinam.isafeyou.isafeyou.Activity;

import android.content.Intent;
import android.database.Cursor;
import android.provider.ContactsContract;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.example.kwinam.isafeyou.R;
import com.example.kwinam.isafeyou.isafeyou.Fragment.TabFragment3;
import com.google.android.gms.vision.text.Text;

public class ContactAddActivity extends AppCompatActivity {

    Button add_decision;
    ImageButton contact_plus;

    String name = null;
    String number = null;

    TextView textname;
    TextView textphone;
    TextView textemergency;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.contact_add);

        add_decision = (Button)findViewById(R.id.add_decision_btn);
        contact_plus = (ImageButton)findViewById(R.id.contact_plus);

        textname = (TextView)findViewById(R.id.contactadd_nameinput);
        textphone = (TextView)findViewById(R.id.contactadd_phoneinput);
        textemergency = (TextView)findViewById(R.id.contactadd_messageinput);

        //연락처 추가 버튼
        add_decision.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!textname.getText().toString().isEmpty() && !textphone.getText().toString().isEmpty()) {
                    Intent intent = getIntent();
                    intent.putExtra("textname", textname.getText().toString());
                    intent.putExtra("textphone", textphone.getText().toString());
                    intent.putExtra("textemergency", textemergency.getText().toString());
                    setResult(RESULT_OK, intent);
                    finish();
                }
                else {
                    Toast.makeText(getApplicationContext(), "연락처를 입력하세요.", Toast.LENGTH_SHORT).show();
                }
            }
        });

        //연락처 목록 불러오기 버튼
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
        if(resultCode == RESULT_OK) {
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
