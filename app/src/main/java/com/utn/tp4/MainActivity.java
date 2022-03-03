package com.utn.tp4;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import com.google.android.material.tabs.TabLayout;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ViewPager viewPager = (ViewPager) findViewById(R.id.view_pager);
        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager());
        viewPager.setAdapter(adapter);
        //((TabLayout) findViewById(R.id.tabLayout)).setupWithViewPager(viewPager);
        TabLayout tabLayout = ((TabLayout) findViewById(R.id.tabsLayout));
        tabLayout.setupWithViewPager(viewPager);
    }
}