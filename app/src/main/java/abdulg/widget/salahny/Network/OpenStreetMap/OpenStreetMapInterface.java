package abdulg.widget.salahny.Network.OpenStreetMap;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

/**
 * Created by Abdullah on 10/01/2018.
 */

public interface OpenStreetMapInterface {
    @GET("reverse?&format=json")
    Call<OpenStreetMapReverseLookup> getCity(@Query("lat") String latitude, @Query("lon") String longitude);
}
