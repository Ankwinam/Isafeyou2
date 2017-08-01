package com.example.kwinam.isafeyou.isafeyou.Fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ListView;


import com.example.kwinam.isafeyou.R;
import com.example.kwinam.isafeyou.isafeyou.Activity.ContactAddActivity;
import com.example.kwinam.isafeyou.isafeyou.Adapter.myCustomAdapter;
import com.example.kwinam.isafeyou.isafeyou.Item.Item;

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

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case REQUEST_CODE:
                if(resultCode == RESULT_OK) {
                    String textname = data.getStringExtra("textname");
                    String textphone = data.getStringExtra("textphone");
                    String textemergency = data.getStringExtra("textemergency");
                    Item item = new Item(textname, textphone, textemergency);
                    item_list.add(item);
                    ca.notifyDataSetChanged();
                    break;
                }
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_3, container, false);
        lv = (ListView) view.findViewById(R.id.contact_list);
        item_list = new ArrayList<Item>();
        ca = new myCustomAdapter(getActivity(), R.layout.item_layout, item_list);
        lv.setAdapter(ca);
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

}





