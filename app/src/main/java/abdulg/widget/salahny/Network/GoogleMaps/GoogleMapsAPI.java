package abdulg.widget.salahny.Network.GoogleMaps;

import abdulg.widget.salahny.Consts.Consts;
import abdulg.widget.salahny.Network.NetworkCallback;
import android.accounts.NetworkErrorException;
import java.io.IOException;
import java.util.concurrent.TimeUnit;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class GoogleMapsAPI {
	GoogleMapsService service;

	public GoogleMapsAPI() {
		Interceptor interceptor = new Interceptor() {
			@Override
			public okhttp3.Response intercept(Chain chain) throws IOException {
				Request newRequest = chain.request().newBuilder().addHeader("Content-Type", "application/json").build();
				return chain.proceed(newRequest);
			}
		};

		OkHttpClient.Builder builder = new OkHttpClient.Builder();
		builder.interceptors().add(interceptor);
		int normalTimeout = 30;
		int writeTimeout = 60;
		builder.connectTimeout(normalTimeout, TimeUnit.SECONDS).readTimeout(normalTimeout, TimeUnit.SECONDS).writeTimeout(writeTimeout, TimeUnit.SECONDS);
		OkHttpClient client = builder.build();

		Retrofit retrofit = new Retrofit.Builder()
				.baseUrl(Consts.GOOGLE_MAPS_API_BASE_URL)
				.addConverterFactory(GsonConverterFactory.create())
				.client(client)
				.build();

		service = retrofit.create(GoogleMapsService.class);
	}

	public void geocodeCoordinates(String latitude, String longitude, final NetworkCallback<GeocodingResult> callback){
		Call<GeocodingResult> geocode = service.geocodeCoordinates(Consts.GOOGLE_MAPS_API_KEY, latitude + "," + longitude);
		geocode.enqueue(new Callback<GeocodingResult>() {
			@Override
			public void onResponse(Call<GeocodingResult> call, Response<GeocodingResult> response) {
				if (response.code() == 200){
					callback.onResponse(response.body(), null);
				} else{
					callback.onResponse(null, new Exception());
				}
			}

			@Override
			public void onFailure(Call<GeocodingResult> call, Throwable t) {
				callback.onResponse(null, new NetworkErrorException());
			}
		});
	}

}
