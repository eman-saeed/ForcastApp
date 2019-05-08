package com.example.forcastapp.adapters;

import android.databinding.DataBindingUtil;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.forcastapp.R;
import com.example.forcastapp.databinding.CityLayoutBinding;
import com.example.forcastapp.listeners.CityClickedListener;
import com.example.forcastapp.model.City;

import java.util.ArrayList;

public class SearchAdapter extends RecyclerView.Adapter<SearchAdapter.ViewHolder> {

    private ArrayList<City> cities;
    private CityClickedListener cityClickedListener;

    public SearchAdapter(ArrayList<City> cities, CityClickedListener cityClickedListener) {
        this.cities = cities;
        this.cityClickedListener = cityClickedListener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        CityLayoutBinding cityLayoutBinding =
                DataBindingUtil.inflate(LayoutInflater.from(viewGroup.getContext()), R.layout.city_layout, viewGroup, false);
        return new ViewHolder(cityLayoutBinding);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {
        City city = cities.get(i);
        viewHolder.setCity(city.getName());
        viewHolder.cityLayoutBinding.setCity(city);
    }

    @Override
    public int getItemCount() {
        return cities.size();
    }

    public void setSearchList(ArrayList<City> searchList) {
        this.cities = searchList;
        notifyDataSetChanged();
    }

    //*****************************************view holder*******************************************
    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        private CityLayoutBinding cityLayoutBinding;
        private String city;

        public String getCity() {
            return city;
        }

        public void setCity(String city) {
            this.city = city;
        }

        public ViewHolder(@NonNull CityLayoutBinding cityLayoutBinding) {
            super(cityLayoutBinding.getRoot());
            this.cityLayoutBinding = cityLayoutBinding;
            cityLayoutBinding.getRoot().setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            if (cityClickedListener != null) {
                cityClickedListener.onCityClickedListener(city, true);
            }
        }
    }
}
