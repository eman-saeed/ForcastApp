package com.example.forcastapp.view;

import android.app.SearchManager;
import android.arch.lifecycle.LifecycleOwner;
import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.content.pm.PackageManager;
import android.databinding.DataBindingUtil;
import android.location.Address;
import android.location.Criteria;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.SearchView;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;

import com.example.forcastapp.R;
import com.example.forcastapp.adapters.SearchAdapter;
import com.example.forcastapp.adapters.WeatherRecyclerViewAdapter;
import com.example.forcastapp.databinding.ActivityMainBinding;
import com.example.forcastapp.model.City;
import com.example.forcastapp.model.WeatherResponse;
import com.example.forcastapp.viewmodel.MainActivityViewModel;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class MainActivity extends AppCompatActivity implements LocationListener {

    private ActivityMainBinding mainBinding;
    private MainActivityViewModel mainActivityViewModel;
    private ArrayList<WeatherResponse> weatherResponseArrayList;
    private ArrayList<City> cities, citiesSearchList;
    private WeatherRecyclerViewAdapter weatherRecyclerViewAdapter;
    private SearchAdapter searchAdapter;

    private static final int MY_PERMISSION_ACCESS_COURSE_LOCATION = 2;
    private LocationManager locationManager;
    private String provider;
    private String currentCity;


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
        //check for the current city if no cashed cities found
        getCurrentCity();
    }

    private void callServiceIfNoCashedCities() {
        //get the data from the repo
        mainActivityViewModel
                .getWeather(currentCity)
                .observe((LifecycleOwner) this, new Observer<WeatherResponse>() {
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

    private void getCurrentCity() {
        currentCity = "london,UK";/*if there is no city detected so it will be london by default*/

        if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED
                && ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {

            ActivityCompat.requestPermissions(this, new String[]{android.Manifest.permission.ACCESS_COARSE_LOCATION,
                            android.Manifest.permission.ACCESS_COARSE_LOCATION},
                    MY_PERMISSION_ACCESS_COURSE_LOCATION);
            return;
        }
        //get the location then get the current city from the user location
        locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        Criteria criteria = new Criteria();
        provider = locationManager.getBestProvider(criteria, false);
        Location location = locationManager.getLastKnownLocation(provider);

        if (location != null) {
            onLocationChanged(location);
        } else {
            callServiceIfNoCashedCities();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode) {
            case MY_PERMISSION_ACCESS_COURSE_LOCATION: {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    //get the location then get the current city from the user location
                    getCurrentCity();
                } else {
                    //call the service to get the weather with default value
                    callServiceIfNoCashedCities();
                }
            }

        }
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

    @Override
    public void onLocationChanged(Location location) {
        //You had this as int. It is advised to have Lat/Loing as double.
        double lat = location.getLatitude();
        double lng = location.getLongitude();

        Geocoder geoCoder = new Geocoder(this, Locale.getDefault());
        StringBuilder builder = new StringBuilder();
        try {
            List<Address> address = geoCoder.getFromLocation(lat, lng, 1);
            currentCity = address.get(0).getSubAdminArea() + "," + address.get(0).getCountryName();
        } catch (IOException e) {
            // Handle IOException
        } catch (NullPointerException e) {
            // Handle NullPointerException
        }
        //get first city if no cashed cities found
        callServiceIfNoCashedCities();
    }

    @Override
    public void onStatusChanged(String s, int i, Bundle bundle) {
    }

    @Override
    public void onProviderEnabled(String s) {
    }

    @Override
    public void onProviderDisabled(String s) {
    }
}
