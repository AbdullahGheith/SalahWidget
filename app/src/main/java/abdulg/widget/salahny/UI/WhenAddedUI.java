package abdulg.widget.salahny.UI;

import abdulg.widget.salahny.Controllere.MainSalahTimesController;
import abdulg.widget.salahny.Model.Places.Coordinates;
import abdulg.widget.salahny.Providers.SalahWidgetProvider;
import abdulg.widget.salahny.R;
import abdulg.widget.salahny.UI.Fragments.PositionSelection.GPSSelected;
import abdulg.widget.salahny.UI.Fragments.PositionSelection.ManualSelected;
import abdulg.widget.salahny.UI.Fragments.PositionSelection.PositionSelectionNavigation;
import abdulg.widget.salahny.UI.Fragments.PositionSelection.SelectionScreen;
import android.app.Activity;
import android.appwidget.AppWidgetManager;
import android.content.Intent;
import android.os.Bundle;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

/**
 * Created by Abdullah on 09/01/2018.
 */

public class WhenAddedUI extends AppCompatActivity implements PositionSelectionNavigation
{

  //My fragments
  SelectionScreen selectionScreen;
  GPSSelected gpsSelected;
  ManualSelected manualSelected;
  MainSalahTimesController controller;
  int widgetId;

  private int fragmentContainer = R.id.WhenAdded_Fragment_Container;

  //boolean hasFineLocationPermission = false;

  //boolean hasCoarseLocationPermission = false;

  @Override
  protected void onCreate(@Nullable Bundle savedInstanceState)
  {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.whenadded);
    setTitle(getText(R.string.app_name));
    widgetId = getIntent().getExtras().getInt(AppWidgetManager.EXTRA_APPWIDGET_ID);
    //Init Fragments used in this activity
    selectionScreen = new SelectionScreen();
    gpsSelected = new GPSSelected();
    manualSelected = new ManualSelected();
    controller = new MainSalahTimesController(getApplicationContext());
    //Show the selection screen
    getSupportFragmentManager()
            .beginTransaction()
            .add(fragmentContainer, selectionScreen)
            .commit();

  }

  @Override
  public void onGPSSelected()
  {
    swapToFragment(gpsSelected);
  }

  @Override
  public void onManualSelected()
  {
    swapToFragment(manualSelected);
  }

  @Override
  public void onCoordinatesChosen(Double Latitude, Double Longitude, String city)
  {
    controller.createAndSaveNewDefaultWidgetSettingsForLocation(new Coordinates(Latitude, Longitude), widgetId, city);
    Intent resultValue = new Intent();
    resultValue.putExtra(AppWidgetManager.EXTRA_APPWIDGET_ID, widgetId);
    setResult(Activity.RESULT_OK, resultValue);
    updateWidget(widgetId);
    finish();
  }

  private void swapToFragment(Fragment fragment)
  {
    FragmentManager manager = getSupportFragmentManager();
    FragmentTransaction ft = manager.beginTransaction();

    ft.setCustomAnimations(android.R.anim.fade_in,
            android.R.anim.fade_out);
    ft.replace(fragmentContainer, fragment);
    ft.commit();
  }

  private void updateWidget(int widgetId)
  {
    try
    {
      SalahWidgetProvider.onUpdate(getApplicationContext(), AppWidgetManager.getInstance(this), new int[]{widgetId});
    }
    catch (Exception e)
    {
      e.printStackTrace();
    }
  }

 /*   private void hideSpinnerAndShowCityAndButton(final String city){
        cityGPSText.setAlpha(0f);
        GPSSpinner.animate().alpha(0f).setDuration(3000).setListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animation) {

            }

            @Override
            public void onAnimationEnd(Animator animation) {
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
                UseThisCity.animate().alpha(1f).setDuration(4000).setListener(new Animator.AnimatorListener() {
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
            }

            @Override
            public void onAnimationCancel(Animator animation) {

            }

            @Override
            public void onAnimationRepeat(Animator animation) {

            }
        }).start();
    }
    private void changePleaseWaitGPSText(final String newText){
        pleasewaitGPSText.animate().alpha(0f).setDuration(1000).setListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animation) {

            }

            @Override
            public void onAnimationEnd(Animator animation) {
                pleasewaitGPSText.setText(newText);
                pleasewaitGPSText.animate().alpha(1f).setDuration(1000).setListener(new Animator.AnimatorListener() {
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
            }

            @Override
            public void onAnimationCancel(Animator animation) {

            }

            @Override
            public void onAnimationRepeat(Animator animation) {

            }
        }).start();
    }

    public void saveWidgetSettings(Long widgetId, String userLatitude, String userLongitude, String userCityName, String cityLatitude, String cityLongitude){
        WidgetSettingsModel existingWidgetSM = WidgetSettingsModel.getWidgetSettingsWithId(Long.valueOf(widgetId));
        if (existingWidgetSM != null){
            WidgetSettingsModel wsm = new WidgetSettingsModel();
            wsm.setCityLatitude(cityLatitude);
            wsm.setCityLongitude(cityLongitude);
            wsm.setCityName(userCityName);
            wsm.setUserLatitude(userLatitude);
            wsm.setUserLongitude(userLongitude);
            wsm.setWidgetId(widgetId);
            wsm.save();
        }
    }

    public void useGPSForLocation(View v) {
        locationHelper.askForPermission();
    }

    private void ShowGPSView(final Location location) throws SecurityException {
        if (currentView == WhenAddedUIViews.Choice) {
            switchView(WhenAddedUIViews.Auto);
        } else if (currentView == WhenAddedUIViews.Auto) {

            if (timesWeGotGPS >= 2){
                //We got signal 3 times, thats fine
                APIHelper s = new APIHelper();
                s.reverseLookupCoordinates(String.valueOf(location.getLatitude()),String.valueOf(location.getLongitude()), new APIResultListener() {
                    @Override
                    public <T> T onResult(T result) {
                        if (result != null){
                            userLocation = location;
                            OpenStreetMapReverseLookup result1 = (OpenStreetMapReverseLookup) result;
                            gotCity(result1.getWidgetSettingsCityName());
                        }
                        return null;
                    }
                });
                timesWeGotGPS = 0;
            } else if (timesWeGotGPS == 1) {
                changePleaseWaitGPSText("Hold on.. Maybe i've found something..");
                timesWeGotGPS++;
            } else {
                timesWeGotGPS++;
            }
        }

        //Start GPS Location manager

    }

    private void gotCity(String userCity){

        if (currentView == WhenAddedUIViews.Auto){
            gpsResult = result;
            this.userCity = userCity;
            changePleaseWaitGPSText("I think i got it. Is this it?");
            hideSpinnerAndShowCityAndButton(result.getWidgetSettingsCityName());

            locationHelper.stopGPS();
        }
    }

    public void useThisCity(View v){
        if (gpsResult != null){
            System.out.println("");
            //saveWidgetSettings(widgetId, gpsResult.getLat(), gpsResult.getLon(), gpsResult.getAddress().get);
        }
    }

    private void switchView(WhenAddedUIViews targetView) {
        if (currentView == targetView)
            return;

        if (currentView == WhenAddedUIViews.Choice) {
            if (targetView == WhenAddedUIViews.Auto) {
                //Using fade animations, show the next view..
                final LinearLayout choice = (LinearLayout) findViewById(R.id.WhenAdded_LL_ChoiceStep);
                final LinearLayout gps = (LinearLayout) findViewById(R.id.WhenAdded_LL_GPSStep);
                gps.setVisibility(View.VISIBLE);
                gps.setAlpha(0f);
                choice.animate().alpha(0f).setDuration(2000).setListener(new Animator.AnimatorListener() {
                    @Override
                    public void onAnimationStart(Animator animation) {

                    }

                    @Override
                    public void onAnimationEnd(Animator animation) {
                        choice.setVisibility(View.GONE);
                        gps.animate().alpha(1f).setDuration(2000).start();
                    }

                    @Override
                    public void onAnimationCancel(Animator animation) {

                    }

                    @Override
                    public void onAnimationRepeat(Animator animation) {

                    }
                }).start();
                currentView = WhenAddedUIViews.Auto;
            }
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == locationHelper.requestCode) {
            for (int grantResult : grantResults) {
                if (grantResult == PackageManager.PERMISSION_GRANTED) {
                    Boolean s = locationHelper.startGPS(new LocationListener() {
                        @Override
                        public void onLocationChanged(Location location) {
                            ShowGPSView(location);
                        }

                        @Override
                        public void onStatusChanged(String provider, int status, Bundle extras) {
                            Log.e("asasdfasdfasdf", "asdasd");
                        }

                        @Override
                        public void onProviderEnabled(String provider) {
                            Log.e("asasdfasdfasdf", "aaaaaaaa" + provider);
                        }

                        @Override
                        public void onProviderDisabled(String provider) {
                            Log.e("asasdfasdfasdf", "bbbbbbbbbbb" + provider);
                        }
                    });

                    if (!s)
                        Log.e("asdasd", "Er GPS Slået til??");
                }
            }
            //TODO: Mangage No Permission
        }
    }

    public void useAPIForLocation(View v) {

    }*/
}
