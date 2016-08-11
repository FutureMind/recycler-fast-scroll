package com.futuremind.recyclerviewfastscroll.example.fragments;

import com.futuremind.recyclerviewfastscroll.example.customview.CustomScrollerViewProvider;
import com.futuremind.recyclerviewfastscroll.example.R;


public class CustomViewFragment extends ExampleFragment {

    @Override
    public int getLayoutId() {
        return R.layout.fragment_default;
    }

    @Override
    public void onResume() {
        super.onResume();
        getFastScroller().setViewProvider(new CustomScrollerViewProvider());
    }
}
