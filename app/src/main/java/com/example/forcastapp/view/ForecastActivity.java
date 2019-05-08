package com.example.forcastapp.view;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.databinding.DataBindingUtil;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.example.forcastapp.R;
import com.example.forcastapp.adapters.ForecastAdapter;
import com.example.forcastapp.databinding.ActivityForecastBinding;
import com.example.forcastapp.model.Forecast;
import com.example.forcastapp.model.ForecastResponse;
import com.example.forcastapp.utils.Constants;
import com.example.forcastapp.viewmodel.ForecastActivityViewModel;

import java.util.ArrayList;

public class ForecastActivity extends AppCompatActivity {

    private ActivityForecastBinding forecastBinding;
    private ForecastActivityViewModel forecastViewModel;
    private String cityName;
    private ForecastAdapter forecastAdapter;
    private ArrayList<Forecast> forecasts;
    private boolean fromSearchScreen;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //bind the views
        forecastBinding = DataBindingUtil.setContentView(this, R.layout.activity_forecast);
        //get the view model
        forecastViewModel = ViewModelProviders.of(this).get(ForecastActivityViewModel.class);
        //get the city name form the intent
        if (getIntent().hasExtra(Constants.CITY_NAME_EXTRA)) {
            cityName = getIntent().getStringExtra(Constants.CITY_NAME_EXTRA);
        }
        if (getIntent().hasExtra(Constants.FROM_SEARCH)) {
            fromSearchScreen = getIntent().getBooleanExtra(Constants.FROM_SEARCH, false);
        }
        //get the Forecast from Service
        getForecast();
    }

    private void getForecast() {
        forecastViewModel
                .getFiveDaysForecast(cityName)
                .observe(this, new Observer<ForecastResponse>() {
                    @Override
                    public void onChanged(@Nullable ForecastResponse forecastResponse) {
                        forecastBinding.progressBar.setVisibility(View.GONE);
                        forecastBinding.forecastRecyclerView.setVisibility(View.VISIBLE);
                        forecasts = forecastResponse.getForecasts();
                        if (forecastAdapter == null) {
                            forecastAdapter = new ForecastAdapter(forecasts);
                            forecastBinding.forecastRecyclerView.setAdapter(forecastAdapter);
                        } else {
                            forecastBinding.forecastRecyclerView.getAdapter().notifyDataSetChanged();
                        }
                    }
                });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.add_menu, menu);

        MenuItem addItem = menu.findItem(R.id.action_add);

        if (!fromSearchScreen) {
            addItem.setVisible(false);
        }
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.action_add) {
            Toast.makeText(this, "from add", Toast.LENGTH_SHORT).show();
        }
        return super.onOptionsItemSelected(item);
    }
}
