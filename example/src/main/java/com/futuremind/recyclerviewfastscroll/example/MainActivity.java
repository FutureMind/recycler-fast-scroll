package com.futuremind.recyclerviewfastscroll.example;

import android.os.Bundle;
import com.google.android.material.tabs.TabLayout;
import androidx.viewpager.widget.ViewPager;
import androidx.appcompat.app.AppCompatActivity;

import com.futuremind.recyclerviewfastscroll.example.adapters.ExampleFragmentsAdapter;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ViewPager pager = findViewById(R.id.pager);
        TabLayout tabs = findViewById(R.id.tab_layout);

        pager.setAdapter(new ExampleFragmentsAdapter(this, getSupportFragmentManager()));
        tabs.setupWithViewPager(pager);
        tabs.setTabMode(TabLayout.MODE_SCROLLABLE);

    }

}
