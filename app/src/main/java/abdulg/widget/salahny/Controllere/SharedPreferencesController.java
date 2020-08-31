package abdulg.widget.salahny.Controllere;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

/**
 * Created by ag on 14-06-2017.
 */
public class SharedPreferencesController {
    private static final String PREFERENCES_FILE = "SalahWidgetData";
    private static final String TIME_DIFFERENCE_PREFIX = "TimeDifference_";
    private static final String DEFAULTWIDGET = "DefaultWidget";

    Context appContext;



    public void storeAsPrimaryWidget(int widgetId){
        String code = DEFAULTWIDGET;
        setInteger(code, widgetId);
    }

    public int getPrimaryWidget(){
        String code = DEFAULTWIDGET;
        return getInteger(code, -1);
    }

    public boolean getAdhanFajr(){
        SharedPreferences sharedPref = PreferenceManager.getDefaultSharedPreferences(appContext);
        return sharedPref.getBoolean("pref_fajr", false);
    }
    public boolean getAdhanDhuhr(){
        SharedPreferences sharedPref = PreferenceManager.getDefaultSharedPreferences(appContext);
        return sharedPref.getBoolean("pref_dhuhr", false);
    }
    public boolean getAdhanAsr(){
        SharedPreferences sharedPref = PreferenceManager.getDefaultSharedPreferences(appContext);
        return sharedPref.getBoolean("pref_asr", false);
    }
    public boolean getAdhanMaghrib(){
        SharedPreferences sharedPref = PreferenceManager.getDefaultSharedPreferences(appContext);
        return sharedPref.getBoolean("pref_maghrib", false);
    }
    public boolean getAdhanIsha(){
        SharedPreferences sharedPref = PreferenceManager.getDefaultSharedPreferences(appContext);
        return sharedPref.getBoolean("pref_isha", false);
    }
    public boolean getAdhanNotification(){
        SharedPreferences sharedPref = PreferenceManager.getDefaultSharedPreferences(appContext);
        return sharedPref.getBoolean("pref_notification", false);
    }
    public boolean getAdhanVibrate(){
        SharedPreferences sharedPref = PreferenceManager.getDefaultSharedPreferences(appContext);
        return sharedPref.getBoolean("pref_vibrate", false);
    }
    public boolean getAdhanSound(){
        SharedPreferences sharedPref = PreferenceManager.getDefaultSharedPreferences(appContext);
        return sharedPref.getBoolean("pref_adhan", false);
    }

    public int getAdhanVolume(){
        SharedPreferences sharedPref = PreferenceManager.getDefaultSharedPreferences(appContext);
        return sharedPref.getInt("pref_adhan_volume", 30);
    }


    public SharedPreferencesController(Context appContext) {
        this.appContext = appContext;
    }

    private String getString(String code, String defaultValue){
        SharedPreferences sharedPref = appContext.getSharedPreferences(PREFERENCES_FILE ,Context.MODE_PRIVATE);
        String storedValue = sharedPref.getString(code, defaultValue);
        return storedValue;
    }

    private Integer getInteger(String code, Integer defaultValue){
        SharedPreferences sharedPref = appContext.getSharedPreferences(PREFERENCES_FILE ,Context.MODE_PRIVATE);
        Integer storedValue = sharedPref.getInt(code, defaultValue);
        return storedValue;
    }

    private Boolean getBoolean(String code, Boolean defaultValue){
        SharedPreferences sharedPref = appContext.getSharedPreferences(PREFERENCES_FILE ,Context.MODE_PRIVATE);
        Boolean storedValue = sharedPref.getBoolean(code, defaultValue);
        return storedValue;
    }

    private void setString(String code, String value){
        SharedPreferences sharedPref = appContext.getSharedPreferences(PREFERENCES_FILE ,Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putString(code, value);
        editor.commit();
    }
    private void setBoolean(String code, Boolean value){
        SharedPreferences sharedPref = appContext.getSharedPreferences(PREFERENCES_FILE ,Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putBoolean(code, value);
        editor.commit();
    }

    private void setInteger(String code, Integer value){
        SharedPreferences sharedPref = appContext.getSharedPreferences(PREFERENCES_FILE ,Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putInt(code, value);
        editor.commit();
    }

}
