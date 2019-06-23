package com.futuremind.recyclerviewfastscroll.example.fragments;

import android.os.Bundle;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.futuremind.recyclerviewfastscroll.FastScroller;
import com.futuremind.recyclerviewfastscroll.example.R;
import com.futuremind.recyclerviewfastscroll.example.adapters.CountriesAdapter;


public abstract class ExampleFragment extends Fragment {

    private FastScroller fastScroller;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View layout = LayoutInflater.from(getActivity()).inflate(getLayoutId(), container, false);

        RecyclerView recyclerView = layout.findViewById(R.id.recyclerview);
        fastScroller = layout.findViewById(R.id.fastscroll);

        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        CountriesAdapter adapter = new CountriesAdapter(getActivity());
        recyclerView.setAdapter(adapter);

        //has to be called AFTER RecyclerView.setAdapter()
        fastScroller.setRecyclerView(recyclerView);

        return layout;
    }

    public abstract int getLayoutId();

    public FastScroller getFastScroller(){
        return fastScroller;
    }

}
