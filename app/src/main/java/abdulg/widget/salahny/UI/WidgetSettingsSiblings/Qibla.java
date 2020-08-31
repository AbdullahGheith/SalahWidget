package abdulg.widget.salahny.UI.WidgetSettingsSiblings;

import abdulg.widget.salahny.Logic.CompassView.CompassSensorManager;
import abdulg.widget.salahny.Logic.CompassView.CompassView;
import abdulg.widget.salahny.Model.View.WidgetSettingsModel;
import abdulg.widget.salahny.R;
import abdulg.widget.salahny.UI.Fragments.TabFragment;
import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.graphics.Matrix;
import android.hardware.*;
import android.location.Location;
import android.os.Build;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.core.app.ActivityCompat;
import butterknife.BindView;
import butterknife.ButterKnife;
import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.MaterialDialog;
import com.google.android.gms.location.*;


/**
 * Created by ag on 16-06-2017.
 */

public class Qibla extends TabFragment implements SensorEventListener {

    private WidgetSettingsModel widget;
    private MaterialDialog compassdialog;
    private boolean locationUpdating = false;

    private LocationRequest mLocationRequest;
    private FusedLocationProviderClient client;
    private Location location;

    private Sensor accelerometer;
    private Sensor magnetometer;

    private CompassSensorManager compassSensorManager;

    @BindView(R.id.qibla_compass_arrow)
    CompassView compassView;
    @BindView(R.id.qiblaTopText)
    TextView textAboveCompass;

    private SensorManager sensorManager;
    float[] mGravity;
    float[] mGeomagnetic;
    private GeomagneticField geoField;

    private Location qiblaLocation;

//    @BindView(R.id.qibla_compass_arrow)
//    ImageView arrowView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        ViewGroup rootView = (ViewGroup) inflater.inflate(
                R.layout.widgetsettings_qibla, container, false);
        ButterKnife.bind(this, rootView);

        if (CompassSensorManager.isDeviceCompatible(getContext())) {
            compassSensorManager = new CompassSensorManager(getContext());

            client = LocationServices.getFusedLocationProviderClient(getActivity());

            mLocationRequest = new LocationRequest();
            mLocationRequest.setInterval(1000);
            mLocationRequest.setFastestInterval(1000);
            mLocationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);

            qiblaLocation = new Location("");
            qiblaLocation.setLatitude(21.422505);
            qiblaLocation.setLongitude(39.826190);

            sensorManager = (SensorManager) getActivity().getSystemService(Context.SENSOR_SERVICE);
            accelerometer = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
            magnetometer = sensorManager.getDefaultSensor(Sensor.TYPE_MAGNETIC_FIELD);
            registerListener();
            startQiblaCompass();
        } else {
            textAboveCompass.setText(R.string.qiblacompass_not_available);
        }
        return rootView;
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == 0) {
            if (grantResults.length != 0){
                if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
//                locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 2000, 10, locationListener);
                    //PendingIntent p = PendingIntent.getActivity(getActivity(), 100, null, PendingIntent.FLAG_UPDATE_CURRENT);
                    registerForLocationUpdates();
                } else {
                    //TODO: use the user sat location from widget
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

    //@Override
    //public void onActivityResult(int requestCode, int resultCode, Intent data) {
    //    super.onActivityResult(requestCode, resultCode, data);
    //}

   //private int sensorcount = 0;
   //private int sensoramount = 0;
   //private int previous = 0;
   //private void rotateArrow(float angle) {
   //    //Doesnt rotate the arrow immediately. We will take x measurementes, get the average, and move there
   //    int sampleamount = 2; //gets this many samples, calculates the average, and shows their result
   //    int minpctchange = 2; //If the change is between
   //    int maxpctchange = 25;
   //    if (sensorcount < sampleamount){
   //        sensoramount += Math.round(angle);
   //        sensorcount++;
   //    } else {
   //        int newangle = sensoramount/sampleamount;
   //        if (getPctDeviation(newangle, previous) > minpctchange && getPctDeviation(newangle, previous) < maxpctchange) {
   //            Matrix matrix = new Matrix();
   //            arrowView.setScaleType(ImageView.ScaleType.MATRIX);
   //            matrix.postRotate(newangle, arrowView.getWidth() / 2, arrowView.getHeight() / 2);
   //            arrowView.setImageMatrix(matrix);
   //            System.out.println("moving to: " + newangle);
   //        }
   //        previous = sensoramount/sampleamount;
   //        sensorcount = 0;
   //        sensoramount = 0;
   //    }
   //}

   //private float getPctDeviation(float value1, float value2){
   //    float highno;
   //    float lowno;
   //    if (value1 > value2){
   //        highno = value1;
   //        lowno = value2;
   //    } else {
   //        highno = value2;
   //        lowno = value1;
   //    }

   //    float diff = highno - lowno;
   //    float pct = diff / highno * 100;
   //    return pct;

   //}

    //private float normalizeDegree(float value) {
    //    if (value < 0) {
    //        return value + 360;
    //        //bearTo = -100 + 360  = 260;
    //    }
    //    return value;
    //}

    public void startQiblaCompass() {
      String[] permissions = {Manifest.permission.ACCESS_COARSE_LOCATION, Manifest.permission.ACCESS_FINE_LOCATION};

      if (ActivityCompat.checkSelfPermission(getActivity(), permissions[0]) == PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(getActivity(), permissions[1]) == PackageManager.PERMISSION_GRANTED) { //Before marshmallow, all is granted
          registerForLocationUpdates();
      } else {
          this.requestPermissions(permissions, 0);
      }
    }

    //@Override
    //public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
    //    super.onViewCreated(view, savedInstanceState);
    //}

    public WidgetSettingsModel getWidget() {
        return widget;
    }

    public void setWidget(WidgetSettingsModel widget) {
        this.widget = widget;
    }

    public void showDialogOrChangeAccuracy(int accuracy) {
        boolean wrapInScrollView = true;

        if (compassdialog == null) {
            compassdialog = new MaterialDialog.Builder(getActivity())
                    .customView(R.layout.dialog_compasscalibration, wrapInScrollView)
                    .onAny(new MaterialDialog.SingleButtonCallback() {
                        @Override
                        public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                            //unregisterListener();
                        }
                    })
                    .cancelListener(new DialogInterface.OnCancelListener() {
                        @Override
                        public void onCancel(DialogInterface dialogInterface) {
                            //unregisterListener();
                        }
                    })
                    .positiveText(R.string.done)
                    .show();
        }

        if (!compassdialog.isShowing()) {
            compassdialog.show();
        }

        TextView accuracyTV = (TextView) compassdialog.findViewById(R.id.compassaccuracy);
        switch (accuracy) {
            case SensorManager.SENSOR_STATUS_ACCURACY_HIGH:
                accuracyTV.setText(R.string.high);
                accuracyTV.setTextColor(Color.GREEN);
                break;
            case SensorManager.SENSOR_STATUS_ACCURACY_MEDIUM:
                accuracyTV.setText(R.string.medium);
                accuracyTV.setTextColor(Color.parseColor("#f48c42")); //ORANGE
                break;
            case SensorManager.SENSOR_STATUS_ACCURACY_LOW:
                accuracyTV.setText(R.string.low);
                accuracyTV.setTextColor(Color.RED);
                break;

        }
    }

    private final LocationCallback locationCallback = new LocationCallback() {
        @Override
        public void onLocationResult(LocationResult locationResult) {
            super.onLocationResult(locationResult);
            geoField = new GeomagneticField(
                    Double.valueOf(locationResult.getLastLocation().getLatitude()).floatValue(),
                    Double.valueOf(locationResult.getLastLocation().getLongitude()).floatValue(),
                    Double.valueOf(locationResult.getLastLocation().getAltitude()).floatValue(),
                    System.currentTimeMillis()
            );

            location = locationResult.getLastLocation();
            if (location.getAccuracy() < 50) {
                compassView.init(compassSensorManager, location, qiblaLocation, R.drawable.qiblacompass);
                client.removeLocationUpdates(locationCallback);
            }
        }
    };

    @Override
    public void onResume() {
        super.onResume();
        if (CompassSensorManager.isDeviceCompatible(getContext())) {
	        compassSensorManager.onResume();
	        registerListener();
        }
    }

    @Override
    public void onPause() {
        super.onPause();
        if (CompassSensorManager.isDeviceCompatible(getContext())) {
	        compassSensorManager.onPause();
	        unregisterListener();
        }
    }

    @Override
    public void onTabInFocus(final Activity activity) {

    }

    public void registerListener() {
        sensorManager.registerListener(this, accelerometer, SensorManager.SENSOR_DELAY_UI);
        sensorManager.registerListener(this, magnetometer, SensorManager.SENSOR_DELAY_UI);
    }

    public void unregisterListener() {
        sensorManager.unregisterListener(this, accelerometer);
        sensorManager.unregisterListener(this, magnetometer);
    }

   @Override
   public void onSensorChanged(SensorEvent event) {

   }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {
        if (accuracy != SensorManager.SENSOR_STATUS_ACCURACY_HIGH)
            showDialogOrChangeAccuracy(accuracy);
    }

}
