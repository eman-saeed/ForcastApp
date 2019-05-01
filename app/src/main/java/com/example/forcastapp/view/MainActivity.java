package com.example.forcastapp.view;

import android.app.SearchManager;
import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.database.Cursor;
import android.databinding.DataBindingUtil;
import android.support.annotation.Nullable;
import android.support.v4.widget.CursorAdapter;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.SearchView;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import com.example.forcastapp.R;
import com.example.forcastapp.adapters.SearchAdapter;
import com.example.forcastapp.adapters.WeatherRecyclerViewAdapter;
import com.example.forcastapp.databinding.ActivityMainBinding;
import com.example.forcastapp.model.City;
import com.example.forcastapp.model.WeatherResponse;
import com.example.forcastapp.viewmodel.MainActivityViewModel;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private ActivityMainBinding mainBinding;
    private MainActivityViewModel mainActivityViewModel;
    private ArrayList<WeatherResponse> weatherResponseArrayList;
    private ArrayList<City> cities, citiesSearchList;
    private WeatherRecyclerViewAdapter weatherRecyclerViewAdapter;
    private SearchAdapter searchAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //bind the main view
        mainBinding = DataBindingUtil.setContentView(this, R.layout.activity_main);
        //get the viewModel for this layout
        mainActivityViewModel = ViewModelProviders.of(this).get(MainActivityViewModel.class);
        //init the arrayList
        weatherResponseArrayList = new ArrayList<>();
        citiesSearchList = new ArrayList<>();
        //get the cities to be search into
        cities = mainActivityViewModel.getCities(this);
        //show progress bar and hide the recycler view
        mainBinding.progressBar.setVisibility(View.VISIBLE);
        mainBinding.weatherRecyclerView.setVisibility(View.GONE);
        //init search recycler view
        if (searchAdapter == null) {
            searchAdapter = new SearchAdapter(citiesSearchList);
        }
        //set the adapter for the search recyler view
        mainBinding.searchRecyclerView.setAdapter(searchAdapter);
        //get the data from the repo
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

    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.search_menu, menu);

        MenuItem searchItem = menu.findItem(R.id.action_search);

        SearchManager searchManager = (SearchManager) MainActivity.this.getSystemService(Context.SEARCH_SERVICE);

        SearchView searchView = null;
        if (searchItem != null) {
            searchView = (SearchView) searchItem.getActionView();
        }
        if (searchView != null) {
            searchView.setSearchableInfo(searchManager.getSearchableInfo(MainActivity.this.getComponentName()));
        }

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                if (s.isEmpty()) {
                    mainBinding.searchRecyclerView.setVisibility(View.GONE);
                    mainBinding.weatherRecyclerView.setVisibility(View.VISIBLE);
                } else {
                    citiesSearchList.clear();
                    for (City city : cities) {
                        if (city.getName().trim().startsWith(s)) {
                            citiesSearchList.add(city);
                        }
                    }
                    if (!citiesSearchList.isEmpty()) {
                        mainBinding.searchRecyclerView.setVisibility(View.VISIBLE);
                        mainBinding.weatherRecyclerView.setVisibility(View.GONE);
                        searchAdapter.setSearchList(citiesSearchList);
                    }
                }
                return false;
            }
        });
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public void onBackPressed() {
        if (mainBinding.searchRecyclerView.getVisibility() == View.VISIBLE) {
            mainBinding.searchRecyclerView.setVisibility(View.GONE);
            mainBinding.weatherRecyclerView.setVisibility(View.VISIBLE);
        } else {
            super.onBackPressed();
        }
    }
}
