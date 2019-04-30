package com.example.forcastapp.adapters;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.forcastapp.R;
import com.example.forcastapp.databinding.WeatherRecyclerViewCellBinding;
import com.example.forcastapp.model.WeatherResponse;

import java.util.ArrayList;


public class WeatherRecyclerViewAdapter extends RecyclerView.Adapter<WeatherRecyclerViewAdapter.ViewHolder> {

    private ArrayList<WeatherResponse> weathers;

    public WeatherRecyclerViewAdapter(ArrayList<WeatherResponse> weathers) {
        this.weathers = weathers;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        WeatherRecyclerViewCellBinding viewCellBinding =
                DataBindingUtil.inflate(LayoutInflater.from(viewGroup.getContext()), R.layout.weather_recycler_view_cell, viewGroup, false);
        return new ViewHolder(viewCellBinding);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {
        WeatherResponse weatherResponse = weathers.get(i);
        viewHolder.viewCellBinding.setWeather(weatherResponse.getWeatherArrayList().get(i));
        viewHolder.viewCellBinding.setCity(weatherResponse.getCityName());
        viewHolder.viewCellBinding.setClouds(weatherResponse.getClouds());
        viewHolder.viewCellBinding.setWind(weatherResponse.getWind());
        viewHolder.viewCellBinding.setMain(weatherResponse.getMain());
    }

    @Override
    public int getItemCount() {
        return weathers.size();
    }

    //***********************************************view holder*****************************************
    public class ViewHolder extends RecyclerView.ViewHolder {

        public WeatherRecyclerViewCellBinding viewCellBinding;

        public ViewHolder(@NonNull WeatherRecyclerViewCellBinding viewCellBinding) {
            super(viewCellBinding.getRoot());
            this.viewCellBinding = viewCellBinding;
        }
    }
}
