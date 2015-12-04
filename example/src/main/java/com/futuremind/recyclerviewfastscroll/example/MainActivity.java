package com.futuremind.recyclerviewfastscroll.example;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.futuremind.recyclerviewfastscroll.FastScroller;

public class MainActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private FastScroller fastScroller;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        recyclerView = (RecyclerView) findViewById(R.id.recyclerview);
        fastScroller = (FastScroller) findViewById(R.id.fastscroll);

        recyclerView.setLayoutManager(new LinearLayoutManager(this));
//        recyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));

        CountriesAdapter adapter = new CountriesAdapter(this);

        recyclerView.setAdapter(adapter);

        //currently has to be called AFTER RecyclerView.setAdapter()
        fastScroller.setRecyclerView(recyclerView);

    }

}
