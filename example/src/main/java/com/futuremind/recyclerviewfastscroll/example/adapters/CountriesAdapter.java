package com.futuremind.recyclerviewfastscroll.example.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.futuremind.recyclerviewfastscroll.SectionTitleProvider;
import com.futuremind.recyclerviewfastscroll.example.R;

import java.util.Arrays;
import java.util.List;

/**
 * Created by mklimczak on 31/07/15.
 */
public class CountriesAdapter extends RecyclerView.Adapter<CountriesAdapter.CountriesHolder> implements SectionTitleProvider {

    private boolean isHorizontal = false;
    private List<String> countries;
    private LayoutInflater inflater;

    public CountriesAdapter(Context cxt) {
        countries = Arrays.asList(cxt.getResources().getStringArray(R.array.countries_array));
        inflater = LayoutInflater.from(cxt);
    }

    public CountriesAdapter(Context cxt, boolean isHorizontal) {
        this(cxt);
        this.isHorizontal = isHorizontal;
    }

    @Override
    public CountriesHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        CountriesHolder holder;
        View view = inflater.inflate(isHorizontal ? R.layout.item_country_horizontal : R.layout.item_country, parent, false);
        holder = new CountriesHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(CountriesHolder holder, int position) {
        holder.nameTv.setText(getCountry(position));
    }

    private String getCountry(int position) {
        return countries.get(position);
    }

    @Override
    public int getItemCount() {
        return countries.size();
    }

    @Override
    public String getSectionTitle(int position) {
        return getCountry(position).substring(0, 1);
    }

    public static class CountriesHolder extends RecyclerView.ViewHolder{

        public TextView nameTv;

        public CountriesHolder(View itemView) {
            super(itemView);
            nameTv = (TextView) itemView;
        }
    }

}
