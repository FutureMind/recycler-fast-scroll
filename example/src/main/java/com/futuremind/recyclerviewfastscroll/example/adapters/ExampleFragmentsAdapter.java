package com.futuremind.recyclerviewfastscroll.example.adapters;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.futuremind.recyclerviewfastscroll.example.R;
import com.futuremind.recyclerviewfastscroll.example.fragments.CustomViewFragment;
import com.futuremind.recyclerviewfastscroll.example.fragments.DefaultFragment;
import com.futuremind.recyclerviewfastscroll.example.fragments.HorizontalFragment;
import com.futuremind.recyclerviewfastscroll.example.fragments.StyledFragment;

import java.util.HashMap;

/**
 * Created by Michal on 04/08/16.
 */
public class ExampleFragmentsAdapter extends FragmentStatePagerAdapter {

    private final Context cxt;
    private final static HashMap<Class<? extends Fragment>, Integer> tabTitles = new HashMap<>();
    static {
        tabTitles.put(DefaultFragment.class, R.string.fragment_title_default);
        tabTitles.put(StyledFragment.class, R.string.fragment_title_styled);
        tabTitles.put(HorizontalFragment.class, R.string.fragment_title_horizontal);
        tabTitles.put(CustomViewFragment.class, R.string.fragment_title_custom);
    }
    private final Fragment fragments[] = new Fragment[] { new DefaultFragment(), new StyledFragment(), new HorizontalFragment(), new CustomViewFragment() };

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
        return cxt.getString(tabTitles.get(fragments[position].getClass()));
    }


}
