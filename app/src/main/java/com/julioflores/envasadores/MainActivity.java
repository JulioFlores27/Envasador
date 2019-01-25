package com.julioflores.envasadores;

import android.support.design.widget.TabLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.os.Bundle;

public class MainActivity extends AppCompatActivity {

   private SectionsPagerAdapter mSectionsPagerAdapter;
    private ViewPager mViewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        mSectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager());
        mViewPager = (ViewPager) findViewById(R.id.container);
        mViewPager.setAdapter(mSectionsPagerAdapter);
        TabLayout tabLayout = (TabLayout) findViewById(R.id.tabs);
        mViewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));
        tabLayout.addOnTabSelectedListener(new TabLayout.ViewPagerOnTabSelectedListener(mViewPager));
    }

    public class SectionsPagerAdapter extends FragmentPagerAdapter {

        public SectionsPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            //return PlaceholderFragment.newInstance(position + 1);
            switch (position){
                case 0:
                    Envasador_Eduardo eduardo = new Envasador_Eduardo();
                    return eduardo;
                case 1:
                    Envasador_Cesar cesar = new Envasador_Cesar();
                    return cesar;
                case 2:
                    Envasador_Joseluis joseluis = new Envasador_Joseluis();
                    return joseluis;
                case 3:
                    Envasador_Leonardo leonardo = new Envasador_Leonardo();
                    return leonardo;
                case 4:
                    Envasador_Miguel miguel = new Envasador_Miguel();
                    return miguel;
                case 5:
                    Envasador_Silver silver = new Envasador_Silver();
                    return silver;
                case 6:
                    Envasador_Francisco francisco = new Envasador_Francisco();
                    return francisco;
            }
            return null;
        }

        @Override
        public int getCount() {
            // Show 7 total pages.
            return 7;
        }
    }
}
