package abdulg.widget.salahny.UI.Fragments.PositionSelection;

import abdulg.widget.salahny.Network.GoogleMaps.GeocodingResult;
import abdulg.widget.salahny.Network.GoogleMaps.GoogleMapsAPI;
import abdulg.widget.salahny.Network.GoogleMaps.Result;
import abdulg.widget.salahny.Network.NetworkCallback;
import android.Manifest;
import android.animation.Animator;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.provider.Settings.SettingNotFoundException;
import android.text.TextUtils;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

import abdulg.widget.salahny.R;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import com.google.android.gms.location.*;

/**
 * Created by Abdullah on 06/02/2018.
 */

public class GPSSelected extends Fragment {

    @BindView(R.id.WhenAdded_Button_UseThisCity)
    Button UseThisCity;
    @BindView(R.id.WhenAdded_TV_PleaseWaitText)
    TextView pleasewaitGPSText;
    @BindView(R.id.WhenAdded_TV_GPSCityText)
    TextView cityGPSText;
    @BindView(R.id.WhenAdded_PB_GPSSpinner)
    ProgressBar GPSSpinner;

    PositionSelectionNavigation salahSelection;

    boolean foundCity = false;

    String userCity;

    private boolean locationUpdating = false;

    private LocationRequest mLocationRequest;
    private FusedLocationProviderClient client;
    private Location location;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_gpsselected, container, false);
        ButterKnife.bind(this, view);

        client = LocationServices.getFusedLocationProviderClient(getActivity());
        mLocationRequest = new LocationRequest();
        mLocationRequest.setInterval(1000);
        mLocationRequest.setFastestInterval(1000);
        mLocationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);

        startGPS();

        return view;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        try {
            salahSelection = (PositionSelectionNavigation) getActivity();
        } catch (ClassCastException e) {
            throw new ClassCastException(getActivity().toString()
                    + " must implement SalahSelection");
        }
    }

    @Override
    public void onPause() {
        super.onPause();
        client.removeLocationUpdates(locationCallback);
    }


    @OnClick(R.id.WhenAdded_Button_UseThisCity)
    public void useCityClick(){
        if (foundCity && location != null){
            salahSelection.onCoordinatesChosen(location.getLatitude(), location.getLongitude(), userCity);
        }
    }

    public void startGPS() {
        String[] permissions = {Manifest.permission.ACCESS_COARSE_LOCATION, Manifest.permission.ACCESS_FINE_LOCATION};

        if (ActivityCompat.checkSelfPermission(getActivity(), permissions[0]) == PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(getActivity(), permissions[1]) == PackageManager.PERMISSION_GRANTED) {
            if (isLocationEnabled(getContext())){
                registerForLocationUpdates();
            } else {
                Toast.makeText(getContext(), R.string.enablegpsplease2, Toast.LENGTH_LONG).show();
                salahSelection.onManualSelected();
            }
        } else {
            this.requestPermissions(permissions, 0);
        }
    }

    public static boolean isLocationEnabled(Context context) {
        int locationMode = 0;
        String locationProviders;

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT){
            try {
                locationMode = Settings.Secure.getInt(context.getContentResolver(), Settings.Secure.LOCATION_MODE);

            } catch (SettingNotFoundException e) {
                e.printStackTrace();
                return false;
            }

            return locationMode != Settings.Secure.LOCATION_MODE_OFF;

        }else{
            locationProviders = Settings.Secure.getString(context.getContentResolver(), Settings.Secure.LOCATION_PROVIDERS_ALLOWED);
            return !TextUtils.isEmpty(locationProviders);
        }


    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == 0) {
            if (grantResults.length != 0){
                if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    registerForLocationUpdates();
                } else {
                    Toast.makeText(getContext(), R.string.enablegpsplease, Toast.LENGTH_LONG).show();
                    salahSelection.onManualSelected();
                }
            }
        }
    }

    public void registerForLocationUpdates() {
        if (!locationUpdating){
            client.requestLocationUpdates(mLocationRequest, locationCallback, null);
            locationUpdating = true;
        }
    }

    private void foundGPSLocation(Location location){
        foundCity = true;
        this.location = location;
        GoogleMapsAPI googleMapsAPI = new GoogleMapsAPI();
        googleMapsAPI.geocodeCoordinates(String.valueOf(location.getLatitude()), String.valueOf(location.getLongitude()), new NetworkCallback<GeocodingResult>() {
            @Override
            public void onResponse(GeocodingResult callbackValue, Exception exception) {
                if (callbackValue != null){
                    String cityFromGeocodeResult = getCityFromGeocodeResult(callbackValue);
                    if (cityFromGeocodeResult != null){
                        userCity = cityFromGeocodeResult;
                        hideSpinnerAndShowCityAndButton(cityFromGeocodeResult);
                    } else {
                        hideSpinnerAndShowCityAndButton("N/A");
                    }
                }
            }
        });
    }

    private String getCityFromGeocodeResult(GeocodingResult geocode){
        for (Result result : geocode.getResults()) {
            return result.getFormattedAddress();
        }
        return null;
    }

    private void hideSpinnerAndShowCityAndButton(final String city){
        cityGPSText.setAlpha(0f);
        GPSSpinner.animate().alpha(0f).setDuration(3000).setListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animation) {

            }

            @Override
            public void onAnimationEnd(Animator animation) {
                try {
                    pleasewaitGPSText.setText(R.string.foundcity);
                    GPSSpinner.setVisibility(View.GONE);
                    cityGPSText.setText(city);
                    cityGPSText.animate().alpha(1f).setDuration(1000).setListener(new Animator.AnimatorListener() {
                        @Override
                        public void onAnimationStart(Animator animation) {

                        }

                        @Override
                        public void onAnimationEnd(Animator animation) {

                        }

                        @Override
                        public void onAnimationCancel(Animator animation) {

                        }

                        @Override
                        public void onAnimationRepeat(Animator animation) {

                        }
                    }).start();
                    UseThisCity.setAlpha(0f);
                    UseThisCity.setVisibility(View.VISIBLE);
                    UseThisCity.animate().alpha(1f).setDuration(4000).start();
                } catch(Exception e){
                    e.printStackTrace();
                }
            }

            @Override
            public void onAnimationCancel(Animator animation) {

            }

            @Override
            public void onAnimationRepeat(Animator animation) {

            }
        }).start();
    }

    private final LocationCallback locationCallback = new LocationCallback() {
        @Override
        public void onLocationResult(LocationResult locationResult) {
            super.onLocationResult(locationResult);
            if (locationResult.getLastLocation().hasAccuracy()){
                if (locationResult.getLastLocation().getAccuracy() < 50){
                    foundGPSLocation(locationResult.getLastLocation());
                    client.removeLocationUpdates(locationCallback);
                }
            }
        }

        @Override
        public void onLocationAvailability(LocationAvailability locationAvailability) {
            super.onLocationAvailability(locationAvailability);
            if (!locationAvailability.isLocationAvailable()){
                if (getContext() != null){
                    Toast.makeText(getContext(), R.string.couldntfindlocation, Toast.LENGTH_LONG).show();
                    salahSelection.onManualSelected();
                }
            }
        }
    };
}
