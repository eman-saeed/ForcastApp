package netwrok.model;


/**
 * Created by EMAN
 * this class for creating retrofit object
 */
public class ServiceProvider {

    //just one method to create the retrofit service
    public static <T> T createRetrofitService(final Class<T> T){
        return RetrofitObject.getInstance().create(T);
    }

}
