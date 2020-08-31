package abdulg.widget.salahny.Network.SalahWidgetService;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

public interface SalahWidgetApiService {

	@FormUrlEncoded
	@POST("salahwidget.php")
	Call<Void> reportMySalahTimes(@Field("city") String city, @Field("coordinates") String coordinates, @Field("timedifference") String timedifference);
}
