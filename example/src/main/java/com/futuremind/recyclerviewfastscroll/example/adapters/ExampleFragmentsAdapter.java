package com.futuremind.recyclerviewfastscroll.example.adapters;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.futuremind.recyclerviewfastscroll.example.R;
import com.futuremind.recyclerviewfastscroll.example.fragments.DefaultFragment;
import com.futuremind.recyclerviewfastscroll.example.fragments.HorizontalFragment;
import com.futuremind.recyclerviewfastscroll.example.fragments.StyledFragment;

/**
 * Created by Michal on 04/08/16.
 */
public class ExampleFragmentsAdapter extends FragmentStatePagerAdapter {

    private final Context cxt;
    private int tabTitles[] = new int[] { R.string.fragment_title_default, R.string.fragment_title_styled, R.string.fragment_title_horizontal };
    private Fragment fragments[] = new Fragment[] { new DefaultFragment(), new StyledFragment(), new HorizontalFragment() };

    public ExampleFragmentsAdapter(Context cxt, FragmentManager fm) {
        super(fm);
        this.cxt = cxt;
    }

    @Override
    public Fragment getItem(int position) {
        return fragments[position];
    }

    @Override
    public int getCount() {
        return fragments.length;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return cxt.getString(tabTitles[position]);
    }


}
