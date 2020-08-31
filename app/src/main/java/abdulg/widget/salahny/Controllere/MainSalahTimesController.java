package abdulg.widget.salahny.Controllere;

import abdulg.widget.salahny.Logic.PrayerTimeFinder;
import abdulg.widget.salahny.Logic.PrayerTimesDefaultFinder;
import abdulg.widget.salahny.Model.Enums.AsrJuristicMethod;
import abdulg.widget.salahny.Model.Enums.HighAltitudeAdjustmentMethod;
import abdulg.widget.salahny.Model.Enums.PrayerTimesCalculationMethod;
import abdulg.widget.salahny.Model.Enums.PrayerType;
import abdulg.widget.salahny.Model.Places.Coordinates;
import abdulg.widget.salahny.Model.Prayer.AdhanSettings;
import abdulg.widget.salahny.Model.Prayer.PrayerTimesMethodPeriod;
import abdulg.widget.salahny.Model.View.ThemeConfiguration;
import abdulg.widget.salahny.Model.View.WidgetSettingsModel;
import abdulg.widget.salahny.Network.SalahWidgetService.SalahWidgetServiceAPI;
import abdulg.widget.salahny.Providers.AdhanBroadcastReceiver;
import abdulg.widget.salahny.Providers.SalahWidgetProvider;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProviderInfo;
import android.content.Context;

import com.google.gson.Gson;
import java.util.*;

import abdulg.widget.salahny.Model.Prayer.PrayerPack;
import abdulg.widget.salahny.Logic.CustomPrayerTimes.DenmarkAndMalmo.SalahTider;
import de.galgtonold.jollydayandroid.Holiday;
import de.galgtonold.jollydayandroid.HolidayManager;

/**
 * Created by ag on 11-06-2017.
 */

public class MainSalahTimesController {

    private Calendar today;
    private SharedPreferencesController sharedPreferences;
    private Context appContext;

    public MainSalahTimesController(Context appContext) {
        today = Calendar.getInstance();
        this.appContext = appContext;
        sharedPreferences = new SharedPreferencesController(this.appContext);
    }

    public PrayerPack getTodaysPrayerPack(int widgetId) throws IllegalArgumentException {
        return getPrayerPackForDate(widgetId, Calendar.getInstance());
    }

    public PrayerPack getPrayerPackForDate(int widgetId, Calendar date) throws IllegalArgumentException {
        PrayerTimeFinder prayerTimeFinder = new PrayerTimeFinder();
        PrayerPack salahForWholeDay = prayerTimeFinder.findPrayerPack(WidgetSettingsModel.getWidgetSettingsWithId(widgetId), date);
        return salahForWholeDay;
    }

    public void setAsPrimaryWidget(WidgetSettingsModel wsm) {
        sharedPreferences.storeAsPrimaryWidget(wsm.getWidgetId());
    }

    public WidgetSettingsModel getPrimaryWidget() {
        WidgetSettingsModel wsm = WidgetSettingsModel.getWidgetSettingsWithId(sharedPreferences.getPrimaryWidget());
        if (wsm != null && checkIfWidgetExists(wsm))
            return wsm;
        else
            return null;
    }

    public Boolean checkIfWidgetExists(WidgetSettingsModel wsm) {
        try {
            AppWidgetProviderInfo appWidgetInfo = AppWidgetManager.getInstance(appContext).getAppWidgetInfo(wsm.getWidgetId());
            if (appWidgetInfo != null)
                return true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    public void setCurrentCalculationMethod(WidgetSettingsModel wsm, PrayerTimesCalculationMethod prayerTimesCalculationMethod) {
        PrayerTimesMethodPeriod currentPeriod = wsm.getCurrentPeriod();

        if (prayerTimesCalculationMethod == PrayerTimesCalculationMethod.Denmark_NewTimes){
            currentPeriod.setHighAltitudeAdjustmentMethod(HighAltitudeAdjustmentMethod.None);
        }
        currentPeriod.setChosenCalculationMethod(prayerTimesCalculationMethod);
        wsm.update();
    }

    public void setWidgetThemeConfiguration(WidgetSettingsModel wsm, ThemeConfiguration configuration) {
        wsm.setThemeConfiguration(configuration);
        wsm.update();
    }

    public PrayerTimesCalculationMethod getCurrentCalculationMethod(WidgetSettingsModel wsm) {
        return PrayerTimesCalculationMethod.valueOf(wsm.getCurrentPeriod().getChosenCalculationMethodString());
    }

    public HighAltitudeAdjustmentMethod getCurrentHighAltitudeAdjustmentMethod(WidgetSettingsModel wsm) {
        return HighAltitudeAdjustmentMethod.valueOf(wsm.getCurrentPeriod().getHighAltitudeAdjustmentMethodString());
    }

    public AsrJuristicMethod getCurrentAsrJuristicMethod(WidgetSettingsModel wsm) {
        return wsm.getCurrentPeriod().getAsrJuristicMethod();
    }

    public int[] getCurrentTimeDifferences(WidgetSettingsModel wsm) {
        return new int[]{wsm.getCurrentPeriod().getFajrDifference(), wsm.getCurrentPeriod().getShurukDifference(), wsm.getCurrentPeriod().getDhuhrDifference(), wsm.getCurrentPeriod().getAsrDifference(), wsm.getCurrentPeriod().getMaghribDifference(), wsm.getCurrentPeriod().getIshaDifference()};
    }

    public void setCurrentTimeDifferences(WidgetSettingsModel wsm, int[] timeDifferences) {
        wsm.getCurrentPeriod().setFajrDifference(timeDifferences[0]);
        wsm.getCurrentPeriod().setShurukDifference(timeDifferences[1]);
        wsm.getCurrentPeriod().setDhuhrDifference(timeDifferences[2]);
        wsm.getCurrentPeriod().setAsrDifference(timeDifferences[3]);
        wsm.getCurrentPeriod().setMaghribDifference(timeDifferences[4]);
        wsm.getCurrentPeriod().setIshaDifference(timeDifferences[5]);
        wsm.update();
        try {
            if (wsm.getCurrentPeriod().getChosenCalculationMethod() == PrayerTimesCalculationMethod.DenmarkMalmo_OldTimes){
                new SalahWidgetServiceAPI().reportSalahTimes(wsm.getUserCity(), wsm.getUserCoordinates().getLatitude(), wsm.getUserCoordinates().getLongitude(), new Gson().toJson(timeDifferences), wsm.getWidgetId());
            }
        } catch(Exception e){
            e.printStackTrace();
        }
    }

    public void setCurrentHighAltitudeAdjustmentMethod(WidgetSettingsModel wsm, HighAltitudeAdjustmentMethod highAltitudeAdjustmentMethod) {
        wsm.getCurrentPeriod().setHighAltitudeAdjustmentMethod(highAltitudeAdjustmentMethod);
        wsm.update();
    }

    public void setCurrentAsrJuristicMethod(WidgetSettingsModel wsm, AsrJuristicMethod asrJuristicMethod) {
        wsm.getCurrentPeriod().setAsrJuristicMethodString(asrJuristicMethod);
        wsm.update();
    }

    public String getName(WidgetSettingsModel wsm) {
        return wsm.getWidgetName();
    }

    public void setName(WidgetSettingsModel wsm, String name) {
        if (name != null && !name.isEmpty()) {
            wsm.setWidgetName(name);
            wsm.update();
        }
    }

    public PrayerPack getPreviewTodaysPrayerTimes(Boolean isHanafi, Integer minuteDifference) throws IllegalArgumentException {
        Boolean hanafi = isHanafi;
        Integer minutesInDifference = minuteDifference;
        PrayerPack salahForWholeDay = SalahTider.getSalahForWholeDay(today, hanafi, minutesInDifference);
        return salahForWholeDay;
    }

    public void updateWidget(WidgetSettingsModel wsm) {
        try {
            SalahWidgetProvider.onUpdate(this.appContext, AppWidgetManager.getInstance(this.appContext), new int[]{wsm.getWidgetId()});
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    public Boolean checkIfFirstWidget() {
        for (WidgetSettingsModel widgetSettingsModel : WidgetSettingsModel.getAllWidgetSettings()) {
            if (checkIfWidgetExists(widgetSettingsModel))
                return false;
        }
        return true;
    }

    public WidgetSettingsModel createNewDefaultWidgetSettingsForLocation(Coordinates location, int widgetId) {
        WidgetSettingsModel widgetSettingsModel = new WidgetSettingsModel();
        widgetSettingsModel.setUserCoordinates(location);
        widgetSettingsModel.setWidgetId(widgetId);
        Random random = new Random();
        widgetSettingsModel.setWidgetName("Widget" + random.nextInt(10000));
        ArrayList<PrayerTimesMethodPeriod> list = new ArrayList<>();
        list.add(PrayerTimesDefaultFinder.matchCoordinates(location));
        widgetSettingsModel.setPeriodlist(list);
        widgetSettingsModel.setThemeConfiguration(new ThemeConfiguration());
        return widgetSettingsModel;
    }

    public void createAndSaveNewDefaultWidgetSettingsForLocation(Coordinates location, int widgetId, String city) {
        WidgetSettingsModel widgetSettingsModel = createNewDefaultWidgetSettingsForLocation(location, widgetId);
        if (city != null){
            widgetSettingsModel.setUserCity(city);
        }
        if (checkIfFirstWidget()) {
            setAsPrimaryWidget(widgetSettingsModel);
        }
        widgetSettingsModel.save();
    }

    public static void main(String[] args) {

    }

    public void rescheduleAdhans(){
        AdhanBroadcastReceiver.startAdhanSetup(appContext);
    }

    public void startTestAdhan(){
        AdhanBroadcastReceiver.startTestAdhan(appContext);
    }

    public void startAdhanSnooze(PrayerType prayerType, int seconds){
        AdhanBroadcastReceiver.startAdhanSnooze(appContext, prayerType, seconds);
    }

    public boolean getIsHolidayOrWeekend(WidgetSettingsModel primaryWidget) {
        Calendar today = Calendar.getInstance();
        if (today.get(Calendar.DAY_OF_WEEK) == Calendar.SATURDAY || today.get(Calendar.DAY_OF_WEEK) == Calendar.SUNDAY)
            return true;

        if (primaryWidget.getHolidaySettings() != null && primaryWidget.getHolidaySettings().getCountry() != null) {
            Set<Holiday> holidays = HolidayManager.getInstance(primaryWidget.getHolidaySettings().getCountry()).getHolidays(today.get(Calendar.YEAR), primaryWidget.getHolidaySettings().getArg1());
            for (Holiday holiday : holidays) {
                if ((holiday.getDate().getMonthOfYear() - 1) == today.get(Calendar.MONTH) && holiday.getDate().getDayOfMonth() == today.get(Calendar.DATE)) //Holiday dates are in true month numbers while date object is -1
                    return true;
            }
        }
        return false;
    }
}
