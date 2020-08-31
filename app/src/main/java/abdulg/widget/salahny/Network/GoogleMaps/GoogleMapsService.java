package abdulg.widget.salahny.Network.GoogleMaps;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface GoogleMapsService {
	@GET("geocode/json?result_type=locality")
	Call<GeocodingResult> geocodeCoordinates(@Query("key") String key, @Query("latlng") String latlng);

}
