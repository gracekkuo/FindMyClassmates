package com.example.findmyclassmates.activities.mainFeatures;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager2.widget.ViewPager2;

import android.os.Bundle;

import com.example.findmyclassmates.R;
import com.example.findmyclassmates.activities.mainFeatures.MyPagerAdapter;
import com.google.android.material.tabs.*;

public class TabbedFeatures extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tabbed_features);

        TabLayout tabLayout = findViewById(R.id.tabLayout);
        ViewPager2 viewPager = findViewById(R.id.viewPager);

        MyPagerAdapter pagerAdapter = new MyPagerAdapter(this);
        viewPager.setAdapter(pagerAdapter);

        new TabLayoutMediator(tabLayout, viewPager, (tab, position) -> {
            switch (position) {
                case 0:
                    tab.setText("Course View");
                    break;
                case 1:
                    tab.setText("Chat");
                    break;
                case 2:
                    tab.setText("Profile");
                    break;
            }
        }).attach();
    }
}