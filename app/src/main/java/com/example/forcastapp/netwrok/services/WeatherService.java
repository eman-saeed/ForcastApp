package netwrok.services;

import io.reactivex.Observable;
import model.WeatherResponse;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Created by EMAN
 */
public interface WeatherService {

    @GET("weather")
    Observable<WeatherResponse> getWeatherByCityAndCountry(@Query("q") String q, @Query("appid") String appId);

}
