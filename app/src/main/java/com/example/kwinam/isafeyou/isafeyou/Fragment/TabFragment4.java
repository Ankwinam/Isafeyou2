package com.example.kwinam.isafeyou.isafeyou.Fragment;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.app.ListFragment;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import com.example.kwinam.isafeyou.R;
import com.example.kwinam.isafeyou.isafeyou.Activity.ContactAddActivity;
import com.example.kwinam.isafeyou.isafeyou.Activity.FireBaseCommunity;
import com.example.kwinam.isafeyou.isafeyou.DataBase.ChatActivity;
import com.example.kwinam.isafeyou.isafeyou.DataBase.ChatData;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Random;
import java.util.Set;

/**
 * Created by KwiNam on 2017-07-19.
 */

public class TabFragment4 extends Fragment {
    FloatingActionButton add;
    static final int REQUEST_CODE = 123;

    ArrayAdapter<Object> adapter ;

    //파이어 베이스
    private FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
    private DatabaseReference databaseReference = firebaseDatabase.getReference();
//    ListView listView;
//    EditText editText;
//    Button button;
//    String name;
//    String chat_user_name;
//    String chat_room_name;

    private ListView listView;
    private EditText editText;
    private Button button;

    private ArrayAdapter<String> arrayAdapter;
    private ArrayList<String> room = new ArrayList<>();
    private DatabaseReference reference = FirebaseDatabase.getInstance().getReference().getRoot();
    private String name;

    private String userName;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_4, container, false);
//        add = (FloatingActionButton)view.findViewById(R.id.fab);
//        add.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent intent = new Intent(getContext(), CommunityAddActivity.class);
//                startActivityForResult(intent, REQUEST_CODE);
//            }
//        });

//        // Adapter 생성 및 Adapter 지정.
//        adapter = new CommunityAdapter();
//        setListAdapter(adapter);
//        // 첫 번째 아이템 추가.
//        adapter.addItem(ContextCompat.getDrawable(getActivity(), R.drawable.box), "테스트1", "가나다라") ;
//        // 두 번째 아이템 추가.
//        adapter.addItem(ContextCompat.getDrawable(getActivity(), R.drawable.box), "테스트2", "ABCD") ;
//        // 세 번째 아이템 추가.
//        adapter.addItem(ContextCompat.getDrawable(getActivity(), R.drawable.box), "테스트3", "룰루") ;
//
//        adapter.addItem(ContextCompat.getDrawable(getActivity(), R.drawable.box), "테스트3", "룰루") ;
//        adapter.addItem(ContextCompat.getDrawable(getActivity(), R.drawable.box), "테스트3", "룰루") ;
//        adapter.addItem(ContextCompat.getDrawable(getActivity(), R.drawable.box), "테스트3", "룰루") ;
//        adapter.addItem(ContextCompat.getDrawable(getActivity(), R.drawable.box), "테스트3", "룰루") ;
//        adapter.addItem(ContextCompat.getDrawable(getActivity(), R.drawable.box), "테스트3", "룰루") ;
//        adapter.addItem(ContextCompat.getDrawable(getActivity(), R.drawable.box), "테스트3", "룰루") ;
//        adapter.addItem(ContextCompat.getDrawable(getActivity(), R.drawable.box), "테스트3", "룰루") ;
//        adapter.addItem(ContextCompat.getDrawable(getActivity(), R.drawable.box), "테스트3", "룰루") ;
//        adapter.addItem(ContextCompat.getDrawable(getActivity(), R.drawable.box), "테스트3", "룰루") ;
//        adapter.addItem(ContextCompat.getDrawable(getActivity(), R.drawable.box), "테스트3", "룰루") ;

        button = (Button) view.findViewById(R.id.fireBasebutton);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), FireBaseCommunity.class);
                startActivity(intent);
            }
        });


       return view;
    }



}
