package com.futuremind.recyclerviewfastscroll.example.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.futuremind.recyclerviewfastscroll.FastScroller;
import com.futuremind.recyclerviewfastscroll.example.R;
import com.futuremind.recyclerviewfastscroll.example.adapters.CountriesAdapter;


public class HorizontalFragment extends Fragment {

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View layout = LayoutInflater.from(getActivity()).inflate(R.layout.fragment_horizontal, container, false);

        RecyclerView recyclerView = (RecyclerView) layout.findViewById(R.id.recyclerview);
        FastScroller fastScroller = (FastScroller) layout.findViewById(R.id.fastscroll);

        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false));
        CountriesAdapter adapter = new CountriesAdapter(getActivity(), true);
        recyclerView.setAdapter(adapter);

        //has to be called AFTER RecyclerView.setAdapter()
        fastScroller.setRecyclerView(recyclerView);

        return layout;
    }

}
