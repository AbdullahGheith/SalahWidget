package abdulg.widget.salahny;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.LocationListener;
import android.location.LocationManager;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;

/**
 * Created by Abdullah on 09/01/2018.
 */

@Deprecated
public class LocationHelper {

    private Activity activity;
    public final int requestCode = 124;
    private boolean gpsStarted = false;
    private LocationManager locationManager;
    private LocationListener locationListener;

    public LocationHelper(Fragment fragment) {
        this.activity = fragment.getActivity();
    }

    public void askForPermission() {
        ActivityCompat.requestPermissions(activity, new String[]{Manifest.permission.ACCESS_COARSE_LOCATION, Manifest.permission.ACCESS_FINE_LOCATION}, requestCode);
    }

    public boolean startGPS(LocationListener locationListener) {
        if (gpsStarted)
            return false;
        this.locationListener = locationListener;
        locationManager = (LocationManager) activity.getSystemService(Context.LOCATION_SERVICE);

        if (ActivityCompat.checkSelfPermission(activity, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED || ActivityCompat.checkSelfPermission(activity, Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            String serviceToStart = null;
            if (ActivityCompat.checkSelfPermission(activity, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED){
                if (locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)){
                    serviceToStart = LocationManager.GPS_PROVIDER;
                }
            }

            if (locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER)) {
                serviceToStart = LocationManager.NETWORK_PROVIDER;
            }

            if (serviceToStart == null)
                return false;
            locationManager.requestLocationUpdates(serviceToStart, 0, 0, this.locationListener);
            gpsStarted = true;

            return true;
        }
        return false;
    }

    public void stopGPS(){
        locationManager.removeUpdates(this.locationListener);
        gpsStarted = false;
    }



}
