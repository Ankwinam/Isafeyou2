package com.example.kwinam.isafeyou.isafeyou.Fragment;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.support.v4.widget.SimpleCursorAdapter;
import android.util.Log;
import android.util.StringBuilderPrinter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.Toast;


import com.example.kwinam.isafeyou.R;
import com.example.kwinam.isafeyou.isafeyou.Activity.ContactAddActivity;
import com.example.kwinam.isafeyou.isafeyou.Adapter.myCustomAdapter;
import com.example.kwinam.isafeyou.isafeyou.DataBase.SQLiteAdapter;
import com.example.kwinam.isafeyou.isafeyou.Item.Item;

//SQLite
//import com.example.kwinam.isafeyou.isafeyou.DataBase.dbHelper;
import com.google.android.gms.wallet.Cart;

import java.util.ArrayList;

import static android.app.Activity.RESULT_OK;

/**
 * Created by KwiNam on 2017-07-19.
 */

public class TabFragment3 extends Fragment {
    ArrayList<Item> item_list;
    ListView contact_list;
    myCustomAdapter ca;
    ImageButton contactadd_btn;
    static final int REQUEST_CODE = 1234;

    //SQLite
    private SQLiteAdapter mySQLiteAdapter;
    SQLiteDatabase db;
    Cursor resultcursor;
    String sql;
    String[] result;
    Item[] resultitem;

    SimpleCursorAdapter cursorAdapter;
    Cursor cursor;

    private AlertDialog.Builder build;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_3, container, false);

//        mySQLiteAdapter = new SQLiteAdapter(getContext());
//        mySQLiteAdapter.openToWrite();
//
//        cursor = mySQLiteAdapter.queueAll();

        //SQLite
        mySQLiteAdapter = new SQLiteAdapter(getActivity());
        try{
            db = mySQLiteAdapter.getWritableDatabase(); //데이터베이스 객체를 얻기 위해 호출
        } catch (SQLiteException e) {
            db = mySQLiteAdapter.getReadableDatabase();
        }

        //연락처 출력
        try{
            sql = "SELECT * FROM contact";
            resultcursor = db.rawQuery(sql, null);

            int count = resultcursor.getCount(); //db 데이터 개수
            result = new String[count];
            //resultitem = new Item[count];

            for(int i = 0; i < count; i++){
                resultcursor.moveToNext(); //첫번째에서 다음 레코드가 없을때까지 읽음
                String str_name = resultcursor.getString(1); //첫번째 속성
                String str_phone = resultcursor.getString(2).replace("-",""); //두번째 속성
                result[i] = str_name + "         " + str_phone; //각각의 속성들을 해당 배열의 i열에 저장
                //resultitem[i].setName(str_name);
                //resultitem[i].setPhonenumber(str_phone);
            }
            contact_list = (ListView) view.findViewById(R.id.contact_list);
            ArrayAdapter adapter = new ArrayAdapter(getContext(), android.R.layout.simple_list_item_1, result);   // ArrayAdapter(this, 출력모양, 배열)
            contact_list.setAdapter(adapter);   // listView 객체에 Adapter를 붙인다

            //item_list = new ArrayList<Item>();
            //ca = new myCustomAdapter(getActivity(), R.layout.item_layout, item_list);
            //contact_list.setAdapter(ca);

        } catch (Exception e) {
            System.out.println("select Error :  " + e);
        }

        //리스트 삭제
        contact_list.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(final AdapterView<?> parent, final View view, final int position, long id) {
                build = new AlertDialog.Builder(getContext());
                build.setTitle("리스트 삭제");
                build.setMessage("이 리스트를 삭제하시겠습니까?");
                build.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which){
                                //Log.d("포지션","포지션: "+parent.getItemAtPosition(position));

                                String ss = (String) parent.getItemAtPosition(position);
                                String s[] = ss.split(" ");

                                db.delete("contact","name=?",new String[]{s[0]});
                                refresh();  //리스트 갱신

                                dialog.cancel();
                                Toast.makeText(getActivity(), "리스트가 삭제 되었습니다.", Toast.LENGTH_SHORT).show();
                            }
                        });
                build.setNegativeButton("No", new DialogInterface.OnClickListener(){
                    public void onClick(DialogInterface dialog, int which){
                        dialog.cancel();
                    }
                });
                AlertDialog alert = build.create();
                alert.show();

                return true;
            }
        });


//        contact_list = (ListView) view.findViewById(R.id.contact_list);
//        item_list = new ArrayList<Item>();
//        ca = new myCustomAdapter(getActivity(), R.layout.item_layout, item_list);
//        contact_list.setAdapter(ca);

        //연락처 추가 버튼튼
       contactadd_btn = (FloatingActionButton) view.findViewById(R.id.contact_add_btn);
        contactadd_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), ContactAddActivity.class);
                startActivityForResult(intent, REQUEST_CODE);
            }
        });

        return view;
    }

    private void refresh(){  //리스트 갱신
        FragmentTransaction ft = getFragmentManager().beginTransaction();
        ft.detach(this).attach(this).commit();
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

                    StringBuilder sb = new StringBuilder();
                    sb.append("SELECT * FROM contact ");
                    sb.append("WHERE name='");
                    sb.append(textname);
                    sb.append("' and phone='");
                    sb.append(textphone);
                    sb.append("';");
                    //SQLite
                    Cursor cursor = db.rawQuery(sb.toString(), null);
                    Log.d("select", cursor.getCount()+"");
                    if(cursor != null & cursor.getCount() > 0) {
                        Toast.makeText(getActivity().getBaseContext(), "이미 존재합니다", Toast.LENGTH_SHORT).show();
                    } else {
                        db.execSQL("INSERT INTO contact VALUES(null, '"+ textname +"','"+ textphone +"');");
                    }
                    //db.execSQL("INSERT INTO contact VALUES(null, '"+ textname +"','"+ textphone +"');");
                    Log.d("테이블", "인서트");

                    refresh();  //리스트 갱신

                    db.close();

                    break;
                }
        }
    }

}