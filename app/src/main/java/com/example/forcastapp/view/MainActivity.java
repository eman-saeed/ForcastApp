package com.example.forcastapp.view;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProvider;
import android.arch.lifecycle.ViewModelProviders;
import android.databinding.DataBindingUtil;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.example.forcastapp.R;
import com.example.forcastapp.adapters.WeatherRecyclerViewAdapter;
import com.example.forcastapp.databinding.ActivityMainBinding;
import com.example.forcastapp.model.WeatherResponse;
import com.example.forcastapp.viewmodel.MainActivityViewModel;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private ActivityMainBinding mainBinding;
    private MainActivityViewModel mainActivityViewModel;
    private ArrayList<WeatherResponse> weatherResponseArrayList;
    private WeatherRecyclerViewAdapter weatherRecyclerViewAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mainBinding = DataBindingUtil.setContentView(this, R.layout.activity_main);

        weatherResponseArrayList = new ArrayList<>();

        mainActivityViewModel = ViewModelProviders.of(this).get(MainActivityViewModel.class);

        mainBinding.progressBar.setVisibility(View.VISIBLE);
        mainBinding.weatherRecyclerView.setVisibility(View.GONE);

        mainActivityViewModel
                .getWeather("london,UK", "b5b19a4019f771d7da6ccddc1ce9f213")
                .observe(this, new Observer<WeatherResponse>() {
                    @Override
                    public void onChanged(@Nullable WeatherResponse weatherResponse) {
                        if (weatherResponseArrayList.size() < 4 && weatherResponse != null) {
                            weatherResponseArrayList.add(weatherResponse);
                            if (weatherRecyclerViewAdapter == null) {
                                weatherRecyclerViewAdapter = new WeatherRecyclerViewAdapter(weatherResponseArrayList);
                                mainBinding.weatherRecyclerView.setAdapter(weatherRecyclerViewAdapter);
                            } else {
                                mainBinding.weatherRecyclerView.getAdapter().notifyDataSetChanged();
                            }
                            mainBinding.progressBar.setVisibility(View.GONE);
                            mainBinding.weatherRecyclerView.setVisibility(View.VISIBLE);
                        }
                    }
                });
    }
}
