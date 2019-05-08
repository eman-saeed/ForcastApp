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
import com.example.forcastapp.listeners.CityClickedListener;
import com.example.forcastapp.model.WeatherResponse;

import java.util.ArrayList;


public class WeatherRecyclerViewAdapter extends RecyclerView.Adapter<WeatherRecyclerViewAdapter.ViewHolder> {

    private ArrayList<WeatherResponse> weathers;
    private CityClickedListener cityClickedListener;

    public WeatherRecyclerViewAdapter(ArrayList<WeatherResponse> weathers, CityClickedListener cityClickedListener) {
        this.weathers = weathers;
        this.cityClickedListener = cityClickedListener;
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

        viewHolder.setCityName(weatherResponse.getCityName());
    }

    @Override
    public int getItemCount() {
        return weathers.size();
    }

    //***********************************************view holder*****************************************
    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        public WeatherRecyclerViewCellBinding viewCellBinding;
        private String CityName;

        public void setCityName(String cityName) {
            CityName = cityName;
        }

        public String getCityName() {
            return CityName;
        }

        public ViewHolder(@NonNull WeatherRecyclerViewCellBinding viewCellBinding) {
            super(viewCellBinding.getRoot());
            this.viewCellBinding = viewCellBinding;
            viewCellBinding.getRoot().setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            if (cityClickedListener != null) {
                cityClickedListener.onCityClickedListener(getCityName(), false);
            }
        }
    }
}
