package com.example.kwinam.isafeyou.isafeyou.Fragment;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.app.ListFragment;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ListView;

import com.example.kwinam.isafeyou.R;
import com.example.kwinam.isafeyou.isafeyou.Activity.CommunityAddActivity;
import com.example.kwinam.isafeyou.isafeyou.Adapter.CommunityAdapter;
import com.example.kwinam.isafeyou.isafeyou.Item.Community_Item;

/**
 * Created by KwiNam on 2017-07-19.
 */

public class TabFragment4 extends ListFragment {
    FloatingActionButton add;
    static final int REQUEST_CODE = 123;

    CommunityAdapter adapter ;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_4, container, false);
        add = (FloatingActionButton)view.findViewById(R.id.fab);
        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), CommunityAddActivity.class);
                startActivityForResult(intent, REQUEST_CODE);
            }
        });

        // Adapter 생성 및 Adapter 지정.
        adapter = new CommunityAdapter();
        setListAdapter(adapter);
        // 첫 번째 아이템 추가.
        adapter.addItem(ContextCompat.getDrawable(getActivity(), R.drawable.box), "테스트1", "가나다라") ;
        // 두 번째 아이템 추가.
        adapter.addItem(ContextCompat.getDrawable(getActivity(), R.drawable.box), "테스트2", "ABCD") ;
        // 세 번째 아이템 추가.
        adapter.addItem(ContextCompat.getDrawable(getActivity(), R.drawable.box), "테스트3", "룰루") ;

        adapter.addItem(ContextCompat.getDrawable(getActivity(), R.drawable.box), "테스트3", "룰루") ;
        adapter.addItem(ContextCompat.getDrawable(getActivity(), R.drawable.box), "테스트3", "룰루") ;
        adapter.addItem(ContextCompat.getDrawable(getActivity(), R.drawable.box), "테스트3", "룰루") ;
        adapter.addItem(ContextCompat.getDrawable(getActivity(), R.drawable.box), "테스트3", "룰루") ;
        adapter.addItem(ContextCompat.getDrawable(getActivity(), R.drawable.box), "테스트3", "룰루") ;
        adapter.addItem(ContextCompat.getDrawable(getActivity(), R.drawable.box), "테스트3", "룰루") ;
        adapter.addItem(ContextCompat.getDrawable(getActivity(), R.drawable.box), "테스트3", "룰루") ;
        adapter.addItem(ContextCompat.getDrawable(getActivity(), R.drawable.box), "테스트3", "룰루") ;
        adapter.addItem(ContextCompat.getDrawable(getActivity(), R.drawable.box), "테스트3", "룰루") ;
        adapter.addItem(ContextCompat.getDrawable(getActivity(), R.drawable.box), "테스트3", "룰루") ;


        return view;
    }

    @Override
    public void onListItemClick (ListView l, View v, int position, long id) {
        // get TextView's Text.
        Community_Item item = (Community_Item) l.getItemAtPosition(position) ;

        String titleStr = item.getTitle() ;
        String descStr = item.getDesc() ;
        Drawable iconDrawable = item.getIcon() ;

        // TODO : use item data.
    }

    public void addItem(Drawable icon, String title, String desc) {
        adapter.addItem(icon, title, desc);
    }


}
