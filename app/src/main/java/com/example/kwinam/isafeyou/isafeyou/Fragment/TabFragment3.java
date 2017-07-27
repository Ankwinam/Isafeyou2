package com.example.kwinam.isafeyou.isafeyou.Fragment;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;


import com.example.kwinam.isafeyou.R;
import com.example.kwinam.isafeyou.isafeyou.Activity.ContactAddActivity;

/**
 * Created by KwiNam on 2017-07-19.
 */

public class TabFragment3 extends Fragment{

    ImageButton contactadd_btn;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_3, container, false);

        contactadd_btn = (ImageButton)view.findViewById(R.id.contact_add_btn);
        contactadd_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), ContactAddActivity.class);
                startActivity(intent);
            }
        });

        return view;
    }

}





