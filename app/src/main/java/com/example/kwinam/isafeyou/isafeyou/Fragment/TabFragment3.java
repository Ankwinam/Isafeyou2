package com.example.kwinam.isafeyou.isafeyou.Fragment;

import android.app.Activity;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.ListFragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toolbar;

import com.example.kwinam.isafeyou.R;

/**
 * Created by KwiNam on 2017-07-19.
 */

public class TabFragment3 extends Fragment {

    //String tag = null;
    //ListView list;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_3, container, false);

        //list = (ListView) view.findViewById(R.id.contact_list);
        //getList();

        return view;
    }

//    private void getList() {
//        //주소록 URI
//        Uri people = ContactsContract.CommonDataKinds.Phone.CONTENT_URI;
//
//        //검색할 컬럼 정하기
//        String[] projection = new String[] {
//                ContactsContract.CommonDataKinds.Phone._ID,
//                ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME,
//                ContactsContract.CommonDataKinds.Phone.NUMBER
//        };
//
//        @SuppressWarnings("deprecation");
//        //Cursor cursor = getContentResolver().query(people, projection, null, null, null); //전화번호부 가져오기
//        Cursor cursor = managedQuery(people, projection, null, null, null); //전화번호부 가져오기
//
//        int end = cursor.getCount(); //전화번호부 갯수 세기
//        Log.d(tag, "end = "+end);
//
//        String[] name = new String[end]; //전화번호부의 이름을 저장할 배열 선언
//        String[] phone = new String[end];
//
//        int count = 0;
//
//        if(cursor.moveToFirst()){
//            //컬럼명으로 컬럼 인덱스 찾기
//            int idIndex = cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone._ID);
//            int nameIndex = cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME);
//            int phoneIndex = cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER);
//
//            do {
//                //요소값 얻기
//                int id = cursor.getInt(idIndex);
//                name[count] = cursor.getString(nameIndex);
//                phone[count] = cursor.getString(phoneIndex);
//
//                //LogCat에 로그 남기기
//                Log.d(tag, "id = " + id + ", name[" + count + "]=" + name[count]);
//                count++;
//
//                Log.d(tag, "id, count=" + id + "," + count +", 이름 =" + name[count] +", 번호=" +phone[count]);
//
//            } while(cursor.moveToNext() || count > end);
//
//
//        }


    }





