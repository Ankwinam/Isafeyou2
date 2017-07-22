package com.example.kwinam.isafeyou.isafeyou.Activity;

import android.support.design.widget.Snackbar;
import android.support.design.widget.TabLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import android.support.v4.view.ViewPager;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.example.kwinam.isafeyou.R;
import com.example.kwinam.isafeyou.isafeyou.Adapter.TabPagerAdapter;

public class MainActivity extends AppCompatActivity {

    //private SectionsPagerAdapter mSectionsPagerAdapter;

    private TabLayout tabLayout;
    private ViewPager mViewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        // Create the adapter that will return a fragment for each of the three
        // primary sections of the activity.
        //mSectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager());

        tabLayout = (TabLayout)findViewById(R.id.tabs);

        tabLayout.addTab(tabLayout.newTab().setIcon(R.drawable.home_icon_selected));  //set Icon
        tabLayout.addTab(tabLayout.newTab().setIcon(R.drawable.search_icon));
        tabLayout.addTab(tabLayout.newTab().setIcon(R.drawable.suggest_icon));
        tabLayout.addTab(tabLayout.newTab().setIcon(R.drawable.alarm_icon));
        tabLayout.addTab(tabLayout.newTab().setIcon(R.drawable.mypage_icon));

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
                switch(tab.getPosition()){
                    case 0: tab.setIcon(R.drawable.home_pink); break;
                    case 1: tab.setIcon(R.drawable.map_pink); break;
                    case 2: tab.setIcon(R.drawable.contacts_pink); break;
                    case 3: tab.setIcon(R.drawable.communication_pink); break;
                    case 4: tab.setIcon(R.drawable.settings_pink); break;

                }
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {
                switch(tab.getPosition()){
                    case 0: tab.setIcon(R.drawable.home_grey); break;
                    case 1: tab.setIcon(R.drawable.map_grey); break;
                    case 2: tab.setIcon(R.drawable.contacts_grey); break;
                    case 3: tab.setIcon(R.drawable.communication_grey); break;
                    case 4: tab.setIcon(R.drawable.settings_grey); break;

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

//        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
//        fab.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
//                        .setAction("Action", null).show();
//            }
//        });

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
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

//    /**
//     * A placeholder fragment containing a simple view.
//     */
//    public static class PlaceholderFragment extends Fragment {
//        /**
//         * The fragment argument representing the section number for this
//         * fragment.
//         */
//        private static final String ARG_SECTION_NUMBER = "section_number";
//
//        public PlaceholderFragment() {
//        }
//
//        /**
//         * Returns a new instance of this fragment for the given section
//         * number.
//         */
//        public static PlaceholderFragment newInstance(int sectionNumber) {
//            PlaceholderFragment fragment = new PlaceholderFragment();
//            Bundle args = new Bundle();
//            args.putInt(ARG_SECTION_NUMBER, sectionNumber);
//            fragment.setArguments(args);
//            return fragment;
//        }
//
//        @Override
//        public View onCreateView(LayoutInflater inflater, ViewGroup container,
//                                 Bundle savedInstanceState) {
//            View rootView = inflater.inflate(R.layout.fragment_1, container, false);
//
//
//            if(getArguments().getInt(ARG_SECTION_NUMBER) == 2) {
//                Fragment fragment1 = new Fragment();
//                FragmentManager fragmentManager = getFragmentManager();
//                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
//                fragmentTransaction.replace(android.R.id.content, fragment1);
//                fragmentTransaction.commit();
//                //View View2 = inflater.inflate(R.layout.fragment_2, container, false);
//                //return View2;
//            }
//            if(getArguments().getInt(ARG_SECTION_NUMBER) == 3) {
//                View View3 = inflater.inflate(R.layout.fragment_3, container, false);
//                return View3;
//            }
//            if(getArguments().getInt(ARG_SECTION_NUMBER) == 4) {
//                View View4 = inflater.inflate(R.layout.fragment_4, container, false);
//                return View4;
//            }
//            if(getArguments().getInt(ARG_SECTION_NUMBER) == 5) {
//                View View5 = inflater.inflate(R.layout.fragment_5, container, false);
//                return View5;
//            }
//            //TextView textView = (TextView) rootView.findViewById(R.id.section_label);
//            //textView.setText(getString(R.string.section_format, getArguments().getInt(ARG_SECTION_NUMBER)));
//
//            ImageButton safebutton = (ImageButton)rootView.findViewById(R.id.safebutton);
//
//
//            return rootView;
//
//        }
//    }
//
//    /**
//     * A {@link FragmentPagerAdapter} that returns a fragment corresponding to
//     * one of the sections/tabs/pages.
//     */
//    public class SectionsPagerAdapter extends FragmentPagerAdapter {
//
//        public SectionsPagerAdapter(FragmentManager fm) {
//            super(fm);
//        }
//
//        @Override
//        public Fragment getItem(int position) {
//            // getItem is called to instantiate the fragment for the given page.
//            // Return a PlaceholderFragment (defined as a static inner class below).
//            return PlaceholderFragment.newInstance(position + 1);
//        }
//
//        @Override
//        public int getCount() {
//            // Show 5 total pages.
//            return 5;
//        }
//
//        @Override
//        public CharSequence getPageTitle(int position) {
//            switch (position) {
//                case 0:
//                    return "메인";
//                case 1:
//                    return "지도";
//                case 2:
//                    return "연락목록";
//                case 3:
//                    return "커뮤니티";
//                case 4:
//                    return "설정";
//            }
//            return null;
//        }
//    }




}

