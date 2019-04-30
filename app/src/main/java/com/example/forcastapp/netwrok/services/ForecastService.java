package netwrok.services;

import io.reactivex.Observable;
import model.ForecastResponse;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Created by eman
 */

public interface ForecastService {
    @GET("forecast")
    Observable<ForecastResponse> getForecastByCity(@Query("q") String q, @Query("appid") String appId);
}
