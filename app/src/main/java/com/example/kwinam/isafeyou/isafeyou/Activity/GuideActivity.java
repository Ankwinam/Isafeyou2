package com.example.kwinam.isafeyou.isafeyou.Activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

import com.example.kwinam.isafeyou.R;
import com.example.kwinam.isafeyou.isafeyou.Fragment.GuideFragment1;
import com.example.kwinam.isafeyou.isafeyou.Fragment.GuideFragment2;
import com.example.kwinam.isafeyou.isafeyou.Fragment.GuideFragment3;
import com.example.kwinam.isafeyou.isafeyou.Fragment.GuideFragment4;
import com.example.kwinam.isafeyou.isafeyou.Fragment.GuideFragment5;
import com.example.kwinam.isafeyou.isafeyou.Fragment.GuideFragment6;

/**
 * Created by Taewoong on 2017-08-02.
 */

public class GuideActivity extends AppCompatActivity {
    int MAX_PAGE = 6;
    Fragment cur_fragment = new Fragment();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.guide);
        ViewPager viewPager = (ViewPager) findViewById(R.id.viewpager);
        viewPager.setAdapter(new adapter(getSupportFragmentManager()));
        viewPager.setCurrentItem(0);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if(event.getAction() == MotionEvent.ACTION_OUTSIDE){
            return false;
        }
        return true;
    }

    private class adapter extends FragmentStatePagerAdapter {
        public adapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            if (position < 0 || MAX_PAGE <= position)
                return null;
            switch (position) {
                case 0:
                    cur_fragment = new GuideFragment1();
                    break;
                case 1:
                    cur_fragment = new GuideFragment2();
                    break;
                case 2:
                    cur_fragment = new GuideFragment3();
                    break;
                case 3:
                    cur_fragment = new GuideFragment4();
                    break;
                case 4:
                    cur_fragment = new GuideFragment5();
                    break;
                case 5:
                    cur_fragment = new GuideFragment6();
                    break;
            }
            return cur_fragment;
        }

        @Override
        public int getCount() {
            return MAX_PAGE;
        }
    }
}