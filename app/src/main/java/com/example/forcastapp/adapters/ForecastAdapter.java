package com.example.forcastapp.adapters;

import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.forcastapp.R;
import com.example.forcastapp.databinding.ForecatRecyclerViewCellBinding;
import com.example.forcastapp.model.Forecast;

import java.util.ArrayList;

public class ForecastAdapter extends RecyclerView.Adapter<ForecastAdapter.ViewHolder> {

    private ArrayList<Forecast> forecasts;

    public ForecastAdapter(ArrayList<Forecast> forecasts) {
        this.forecasts = forecasts;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        ForecatRecyclerViewCellBinding forecatRecyclerViewCellBinding
                = DataBindingUtil.inflate(LayoutInflater.from(viewGroup.getContext()), R.layout.forecat_recycler_view_cell, viewGroup, false);
        return new ViewHolder(forecatRecyclerViewCellBinding);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {
        Forecast forecast = forecasts.get(i);
        viewHolder.viewCellBinding.setForecast(forecast);
        viewHolder.viewCellBinding.setClouds(forecast.getClouds());
        viewHolder.viewCellBinding.setWind(forecast.getWind());
        viewHolder.viewCellBinding.setMain(forecast.getMain());
    }

    @Override
    public int getItemCount() {
        return forecasts.size() > 5 ? 5 : forecasts.size();//to make sure that up to 5 days only that shown
    }

    //***********************************************view holder*****************************************
    public class ViewHolder extends RecyclerView.ViewHolder {

        private ForecatRecyclerViewCellBinding viewCellBinding;

        public ViewHolder(@NonNull ForecatRecyclerViewCellBinding viewCellBinding) {
            super(viewCellBinding.getRoot());
            this.viewCellBinding = viewCellBinding;
        }
    }
}
