package com.example.kwinam.isafeyou.isafeyou.Fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.kwinam.isafeyou.R;
import com.example.kwinam.isafeyou.isafeyou.Activity.CommunityAddActivity;

/**
 * Created by KwiNam on 2017-07-19.
 */

public class TabFragment4 extends Fragment {
    FloatingActionButton add;
    static final int REQUEST_CODE = 123;
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

        return view;
    }

}
