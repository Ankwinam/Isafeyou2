package com.example.kwinam.isafeyou.isafeyou.Fragment;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.v4.app.Fragment;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.support.v4.widget.SimpleCursorAdapter;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;


import com.example.kwinam.isafeyou.R;
import com.example.kwinam.isafeyou.isafeyou.Activity.ContactAddActivity;
import com.example.kwinam.isafeyou.isafeyou.Adapter.myCustomAdapter;
import com.example.kwinam.isafeyou.isafeyou.Item.Item;

//SQLite
import com.example.kwinam.isafeyou.isafeyou.DataBase.dbHelper;

import java.util.ArrayList;

import static android.app.Activity.RESULT_OK;

/**
 * Created by KwiNam on 2017-07-19.
 */

public class TabFragment3 extends Fragment {
    ArrayList<Item> item_list;
    ListView lv;
    myCustomAdapter ca;
    ImageButton contactadd_btn;
    static final int REQUEST_CODE = 1234;

    //SQLite
    dbHelper helper;
    SQLiteDatabase db;
    Cursor resultcursor;
    String sql;
    String[] result;
    Item[] resultitem;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_3, container, false);

        //SQLite
        helper = new dbHelper(getActivity());
        try{
            db = helper.getWritableDatabase(); //데이터베이스 객체를 얻기 위해 호출
        } catch (SQLiteException e) {
            db = helper.getReadableDatabase();
        }

        try{
            sql = "SELECT * FROM contact";
            resultcursor = db.rawQuery(sql, null);

            int count = resultcursor.getCount(); //db 데이터 개수
            result = new String[count];
            //resultitem = new Item[count];

            for(int i = 0; i < count; i++){
                resultcursor.moveToNext(); //첫번째에서 다음 레코드가 없을때까지 읽음
                String str_name = resultcursor.getString(1); //첫번째 속성
                String str_phone = resultcursor.getString(2); //두번째 속성
                result[i] = str_name + "    " + str_phone; //각각의 속성들을 해당 배열의 i열에 저장
                //resultitem[i].setName(str_name);
                //resultitem[i].setPhonenumber(str_phone);

            }
            Log.d("확인","셀렉트 성공");
            System.out.println("select ok");

            lv = (ListView) view.findViewById(R.id.contact_list);

            ArrayAdapter adapter = new ArrayAdapter(getContext(), android.R.layout.simple_list_item_1, result);   // ArrayAdapter(this, 출력모양, 배열)
            lv.setAdapter(adapter);   // listView 객체에 Adapter를 붙인다

            //item_list = new ArrayList<Item>();
            //ca = new myCustomAdapter(getActivity(), R.layout.item_layout, item_list);
            //lv.setAdapter(ca);

        } catch (Exception e) {
            System.out.println("select Error :  " + e);
        }


//        lv = (ListView) view.findViewById(R.id.contact_list);
//        item_list = new ArrayList<Item>();
//        ca = new myCustomAdapter(getActivity(), R.layout.item_layout, item_list);
//        lv.setAdapter(ca);
        contactadd_btn = (ImageButton) view.findViewById(R.id.contact_add_btn);
        contactadd_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), ContactAddActivity.class);
                startActivityForResult(intent, REQUEST_CODE);
            }
        });

        return view;
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        switch (requestCode) {
            case REQUEST_CODE:
                if(resultCode == RESULT_OK) {
                    String textname = data.getStringExtra("textname");
                    String textphone = data.getStringExtra("textphone");
                    String textemergency = data.getStringExtra("textemergency");

                    //Item item = new Item(textname, textphone, textemergency);
                    //item_list.add(item);
                    //ca.notifyDataSetChanged();

                    //SQLite
                    db.execSQL("INSERT INTO contact VALUES(null, '"+ textname +"','"+ textphone +"');");
                    Log.d("테이블", "인서트");


                    try{
                        sql = "SELECT * FROM contact";
                        resultcursor = db.rawQuery(sql, null);

                        int count = resultcursor.getCount(); //db 데이터 개수
                        result = new String[count];

                        for(int i = 0; i < count; i++){
                            resultcursor.moveToNext(); //첫번째에서 다음 레코드가 없을때까지 읽음
                            String str_name = resultcursor.getString(1); //첫번째 속성
                            String str_phone = resultcursor.getString(2); //두번째 속성
                            result[i] = str_name + "    " + str_phone; //각각의 속성들을 해당 배열의 i열에 저장
                        }
                        System.out.println("select ok" );

                        ArrayAdapter adapter = new ArrayAdapter(getContext(), android.R.layout.simple_list_item_1, result);   // ArrayAdapter(this, 출력모양, 배열)
                        lv.setAdapter(adapter);   // listView 객체에 Adapter를 붙인다

                    } catch (Exception e) {
                        System.out.println("select Error :  " + e);
                    }

                    break;
                }
        }
    }

}