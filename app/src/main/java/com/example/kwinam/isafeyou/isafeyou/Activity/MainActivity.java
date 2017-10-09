package com.example.kwinam.isafeyou.isafeyou.Activity;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.location.LocationManager;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TabLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.preference.PreferenceManager;
import android.support.v7.widget.Toolbar;

import android.support.v4.view.ViewPager;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.example.kwinam.isafeyou.R;
import com.example.kwinam.isafeyou.isafeyou.Adapter.TabPagerAdapter;

public class MainActivity extends AppCompatActivity {

    //private SectionsPagerAdapter mSectionsPagerAdapter;
    boolean isFirst;
    private TabLayout tabLayout;
    private ViewPager mViewPager;
    LocationManager lm = null;

    private TextView toolbar_title;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //첫 실행확인
        isFirst = CheckAppFirstExecute();
        if(isFirst){
            Intent intent = new Intent(MainActivity.this, GuideActivity.class);
            startActivity(intent);
        }

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        // Create the adapter that will return a fragment for each of the three
        // primary sections of the activity.
        //mSectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager());

        toolbar_title = (TextView) findViewById(R.id.toolbar_title);

        getSupportActionBar().setDisplayShowTitleEnabled(false);

        tabLayout = (TabLayout) findViewById(R.id.tabs);

        tabLayout.addTab(tabLayout.newTab().setIcon(R.drawable.home_pink));  //set Icon
        tabLayout.addTab(tabLayout.newTab().setIcon(R.drawable.map_grey));
        tabLayout.addTab(tabLayout.newTab().setIcon(R.drawable.contacts_grey));
        tabLayout.addTab(tabLayout.newTab().setIcon(R.drawable.communication_grey));
        //tabLayout.addTab(tabLayout.newTab().setIcon(R.drawable.settings_grey));

        // Set up the ViewPager with the sections adapter.
        mViewPager = (ViewPager) findViewById(R.id.container);
        //mViewPager.setAdapter(mSectionsPagerAdapter);

        //TabLayout tabLayout = (TabLayout) findViewById(R.id.tabs);
        //tabLayout.setupWithViewPager(mViewPager);

        // Creating TabPagerAdapter adapter
        TabPagerAdapter pagerAdapter = new TabPagerAdapter(getSupportFragmentManager(), tabLayout.getTabCount());
        mViewPager.setAdapter(pagerAdapter);
        mViewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));

        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                mViewPager.setCurrentItem(tab.getPosition());
                switch (tab.getPosition()) {
                    case 0:
                        tab.setIcon(R.drawable.home_pink);
                        toolbar_title.setText("I·SAFE·U");
                        break;
                    case 1:
                        tab.setIcon(R.drawable.map_pink);
                        toolbar_title.setText("지도");
                        break;
                    case 2:
                        tab.setIcon(R.drawable.contacts_pink);
                        toolbar_title.setText("지키미");
                        break;
                    case 3:
                        tab.setIcon(R.drawable.communication_pink);
                        toolbar_title.setText("커뮤니티");
                        break;
//                    case 4:
//                        tab.setIcon(R.drawable.settings_pink);
//                        toolbar_title.setText("설정");
//                        break;

                }
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {
                switch (tab.getPosition()) {
                    case 0:
                        tab.setIcon(R.drawable.home_grey);
                        break;
                    case 1:
                        tab.setIcon(R.drawable.map_grey);
                        break;
                    case 2:
                        tab.setIcon(R.drawable.contacts_grey);
                        break;
                    case 3:
                        tab.setIcon(R.drawable.communication_grey);
                        break;
//                    case 4:
//                        tab.setIcon(R.drawable.settings_grey);
//                        break;

                }
            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });

        tabLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Snackbar.make(v, v.toString(), Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        switch (keyCode) {
            //하드웨어 뒤로가기 버튼에 따른 이벤트 설정
            case KeyEvent.KEYCODE_BACK:

                new AlertDialog.Builder(this)
                        .setTitle("프로그램 종료")
                        .setMessage("프로그램을 종료 하시겠습니까?")
                        .setPositiveButton("예", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                // 프로세스 종료.
                                android.os.Process.killProcess(android.os.Process.myPid());
                            }
                        })
                        .setNegativeButton("아니오", null)
                        .show();
                break;
            default:
                break;
        }
        return super.onKeyDown(keyCode, event);
    }

    @Override
    protected void onStop() {
        super.onStop();
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            Intent intent = new Intent(this,SettingActivity.class);
            startActivity(intent);
        }
        return super.onOptionsItemSelected(item);
    }

//앱최초실행확인 (true - 최초실행)
    public boolean CheckAppFirstExecute() {
        SharedPreferences pref = getSharedPreferences("IsFirst", Activity.MODE_PRIVATE);
        boolean isFirst = pref.getBoolean("isFirst", false);
        if (!isFirst) { //최초 실행시 true 저장
            SharedPreferences.Editor editor = pref.edit();
            editor.putBoolean("isFirst", true);
            editor.commit();
        }
        return !isFirst;
    }

    @Override
    protected void onResume() {
        super.onResume();

        SharedPreferences pref = PreferenceManager.getDefaultSharedPreferences(this);
        boolean click_3 = pref.getBoolean("click_3", false);
        String ringtone = pref.getString("ringtone", "");
        int time_interval = Integer.parseInt(pref.getString("time_interval", "0"));
        String message = pref.getString("message", "도와주세요! 위급 상황입니다.");
    }
}

