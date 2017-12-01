package com.example.kwinam.isafeyou.isafeyou.Fragment;

import android.app.Fragment;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.kwinam.isafeyou.R;

/**
 * Created by Taewoong on 2017-11-21.
 */

public class GuideFragment5 extends android.support.v4.app.Fragment {
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        LinearLayout linearlayout= (LinearLayout)inflater.inflate(R.layout.guide_fragment,container,false);
        LinearLayout background = (LinearLayout)linearlayout.findViewById(R.id.background);
        TextView page_num = (TextView)linearlayout.findViewById(R.id.page_num);
        page_num.setText(String.valueOf(5));
        return linearlayout;
    }
}
