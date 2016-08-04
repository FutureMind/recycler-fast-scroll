package com.futuremind.recyclerviewfastscroll.example.fragments;

import com.futuremind.recyclerviewfastscroll.example.R;


public class StyledFragment extends ExampleFragment {

    @Override
    public int getLayoutId() {
        return R.layout.fragment_styled;
    }

    @Override
    public void onResume() {
        super.onResume();

        /*
         * These arguments are set in xml, but these settings can be overridden
         * in code like here
         *
         * getFastScroller().setBubbleColor(0xffff0000);
         * getFastScroller().setHandleColor(0xffff0000);
         * getFastScroller().setBubbleTextAppearance(R.style.StyledScrollerTextAppearance);
         */

    }
}
