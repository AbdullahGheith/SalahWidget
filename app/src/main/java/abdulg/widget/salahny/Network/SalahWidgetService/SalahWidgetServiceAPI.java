package abdulg.widget.salahny.Network.SalahWidgetService;

import abdulg.widget.salahny.Consts.Consts;
import abdulg.widget.salahny.Model.View.WidgetSettingsModel;
import abdulg.widget.salahny.Network.GoogleMaps.GoogleMapsAPI;
import abdulg.widget.salahny.Network.GoogleMaps.GoogleMapsService;
import abdulg.widget.salahny.Network.GoogleMaps.Result;
import abdulg.widget.salahny.Network.OpenStreetMap.OpenStreetMapAPI;
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

/**
 * Created by Abdullah on 10/01/2018.
 */

public class SalahWidgetServiceAPI {

    SalahWidgetApiService service;

    public SalahWidgetServiceAPI() {
//        Interceptor interceptor = new Interceptor() {
//            @Override
//            public okhttp3.Response intercept(Chain chain) throws IOException {
//                Request newRequest = chain.request().newBuilder().addHeader("Content-Type", "application/json").build();
//                return chain.proceed(newRequest);
//            }
//        };

        OkHttpClient.Builder builder = new OkHttpClient.Builder();
        //builder.interceptors().add(interceptor);
        int normalTimeout = 30;
        int writeTimeout = 60;
        builder.connectTimeout(normalTimeout, TimeUnit.SECONDS).readTimeout(normalTimeout, TimeUnit.SECONDS).writeTimeout(writeTimeout, TimeUnit.SECONDS);
        OkHttpClient client = builder.build();

        Retrofit retrofit = new Retrofit.Builder()
            .baseUrl(Consts.SALAHTIMES_REPORTER_API_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .client(client)
            .build();

        service = retrofit.create(SalahWidgetApiService.class);
    }

    public void reportSalahTimes(String city, String latitude, String longitude, String timedifference, int widgetId){
        if (city == null || city.isEmpty()){
            new GoogleMapsAPI().geocodeCoordinates(latitude, longitude, (geocode, exception) -> {
                String cityName = null;
                for (Result result : geocode.getResults()) {
                    cityName = result.getFormattedAddress();
                    break;
                }
                if (cityName != null && !cityName.isEmpty()){
                    WidgetSettingsModel wsm = WidgetSettingsModel.getWidgetSettingsWithId(widgetId);
                    wsm.setUserCity(cityName);
                    wsm.update();
                }

                reportToAPI(cityName, latitude, longitude, timedifference);
            });
        } else {
            reportToAPI(city, latitude, longitude, timedifference);
        }

    }

    private void reportToAPI(String city, String latitude, String longitude, String timedifference){
        Call<Void> reporter = service.reportMySalahTimes(city, latitude+", "+longitude, timedifference);
        reporter.enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                if (response.code() == 200){
                    // callback.onResponse(response.body(), null);
                } else{
                    //callback.onResponse(null, new Exception());
                }
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                System.out.println("");
            }
        });
    }
}

