package abdulg.widget.salahny.Network.OpenStreetMap;

import android.os.AsyncTask;

import java.io.IOException;

import abdulg.widget.salahny.Network.APIResultListener;
import retrofit2.Call;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by Abdullah on 10/01/2018.
 */

public class OpenStreetMapAPI extends AsyncTask<String, Void, OpenStreetMapReverseLookup> {

    private OpenStreetMapInterface service;
    private Call<OpenStreetMapReverseLookup> call;
    private APIResultListener resultListener;
    public OpenStreetMapAPI() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://nominatim.openstreetmap.org")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        service = retrofit.create(OpenStreetMapInterface.class);

    }

    public void reverseLookupCoordinates(String latitude, String longitude, APIResultListener resultListener){
       call = service.getCity(latitude, longitude);
       this.resultListener = resultListener;
       execute();
    }

    @Override
    protected OpenStreetMapReverseLookup doInBackground(String... strings) {
        try {
            Response<OpenStreetMapReverseLookup> execute = call.execute();
            return execute.body();

        } catch (IOException e) {
            e.printStackTrace();

            resultListener.onResult(null);
        }
        return null;
    }

    @Override
    protected void onPostExecute(OpenStreetMapReverseLookup openStreetMapReverseLookup) {
        super.onPostExecute(openStreetMapReverseLookup);
        resultListener.onResult(openStreetMapReverseLookup);
        resultListener.setResultClassName(openStreetMapReverseLookup.getClass().getName());


    }
}
