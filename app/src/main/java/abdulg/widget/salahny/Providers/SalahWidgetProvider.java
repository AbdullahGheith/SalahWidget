package abdulg.widget.salahny.Providers;

import abdulg.widget.salahny.Model.View.Color;
import abdulg.widget.salahny.Model.View.ThemeConfiguration;
import abdulg.widget.salahny.Model.View.WidgetSettingsModel;
import abdulg.widget.salahny.WidgetResource.TwoByTwo;
import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.content.Context;
import android.content.Intent;
import android.widget.RemoteViews;

import abdulg.widget.salahny.Consts.Consts;
import abdulg.widget.salahny.Controllere.MainSalahTimesController;
import abdulg.widget.salahny.Model.Prayer.PrayerPack;
import abdulg.widget.salahny.R;
import abdulg.widget.salahny.UI.WidgetSettings;
import abdulg.widget.salahny.WidgetResource.FiveByOne;
import abdulg.widget.salahny.WidgetResource.FourByOne;
import abdulg.widget.salahny.WidgetResource.WidgetResource;

import java.util.List;

/**
 * Created by ag on 17-06-2017.
 */

public class SalahWidgetProvider {

    public static void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) throws Exception {
        MainSalahTimesController mainSalahTimesController = new MainSalahTimesController(context);

        for (int i = 0; i < appWidgetIds.length; i++) {
            int widgetId = appWidgetIds[i];
            if (widgetId == 0)
                continue;
            WidgetResource widgetResource = getWidgetResourceFromWidgetId(context, widgetId);
            if (widgetResource == null) {
                continue;
            }
            RemoteViews remoteViews = new RemoteViews(context.getPackageName(), widgetResource.getLayout());
            PrayerPack todaysPrayerTimes = mainSalahTimesController.getTodaysPrayerPack(widgetId);
            remoteViews.setTextViewText(widgetResource.getFajrTime(), todaysPrayerTimes.getFajrInHHmm());
            remoteViews.setTextViewText(widgetResource.getShurukTime(), todaysPrayerTimes.getShurukInHHmm());
            remoteViews.setTextViewText(widgetResource.getDhuhrTime(), todaysPrayerTimes.getDhuhrInHHmm());
            remoteViews.setTextViewText(widgetResource.getAsrTime(), todaysPrayerTimes.getAsrInHHmm());
            remoteViews.setTextViewText(widgetResource.getMaghribTime(), todaysPrayerTimes.getMaghribInHHmm());
            remoteViews.setTextViewText(widgetResource.getIshaTime(), todaysPrayerTimes.getIshaInHHmm());

            applyWidgetThemeConfiguration(remoteViews, widgetResource, WidgetSettingsModel.getWidgetSettingsWithId(widgetId).getThemeConfiguration(), context);

            Intent intent = new Intent(context, widgetResource.getProvider());
            intent.setAction(Consts.WIDGET_ONCLICK_IDENTIFIER);
            intent.putExtra(Consts.PROVIDER_TO_MAINSETTINGSUI_WIDGETID, widgetId);
            PendingIntent d = PendingIntent.getBroadcast(context, widgetId, intent, PendingIntent.FLAG_UPDATE_CURRENT);
            remoteViews.setOnClickPendingIntent(widgetResource.getLayoutWrapper(), d);

            appWidgetManager.updateAppWidget(widgetId, remoteViews);
        }
    }

    public static void applyWidgetThemeConfiguration(RemoteViews remoteViews, WidgetResource widgetResource, ThemeConfiguration theme, Context context){
        remoteViews.setInt(widgetResource.getFajrTime(), "setTextColor", Color.fromString(theme.getTimesColor()).toAndroidColorWithAlpha());
        remoteViews.setInt(widgetResource.getShurukTime(), "setTextColor", Color.fromString(theme.getTimesColor()).toAndroidColorWithAlpha());
        remoteViews.setInt(widgetResource.getDhuhrTime(), "setTextColor", Color.fromString(theme.getTimesColor()).toAndroidColorWithAlpha());
        remoteViews.setInt(widgetResource.getAsrTime(), "setTextColor", Color.fromString(theme.getTimesColor()).toAndroidColorWithAlpha());
        remoteViews.setInt(widgetResource.getMaghribTime(), "setTextColor", Color.fromString(theme.getTimesColor()).toAndroidColorWithAlpha());
        remoteViews.setInt(widgetResource.getIshaTime(), "setTextColor", Color.fromString(theme.getTimesColor()).toAndroidColorWithAlpha());

        float timesTextSize = theme.getTimesSize();
        if (timesTextSize == -1){
            timesTextSize = context.getResources().getDimension(R.dimen.textsize) / context.getResources().getDisplayMetrics().density;
        }
        remoteViews.setFloat(widgetResource.getFajrTime(), "setTextSize", timesTextSize);
        remoteViews.setFloat(widgetResource.getShurukTime(), "setTextSize", timesTextSize);
        remoteViews.setFloat(widgetResource.getDhuhrTime(), "setTextSize", timesTextSize);
        remoteViews.setFloat(widgetResource.getAsrTime(), "setTextSize", timesTextSize);
        remoteViews.setFloat(widgetResource.getMaghribTime(), "setTextSize", timesTextSize);
        remoteViews.setFloat(widgetResource.getIshaTime(), "setTextSize", timesTextSize);

        remoteViews.setInt(widgetResource.getFajrText(), "setTextColor", Color.fromString(theme.getTextColor()).toAndroidColorWithAlpha());
        remoteViews.setInt(widgetResource.getShurukText(), "setTextColor", Color.fromString(theme.getTextColor()).toAndroidColorWithAlpha());
        remoteViews.setInt(widgetResource.getDhuhrText(), "setTextColor", Color.fromString(theme.getTextColor()).toAndroidColorWithAlpha());
        remoteViews.setInt(widgetResource.getAsrText(), "setTextColor", Color.fromString(theme.getTextColor()).toAndroidColorWithAlpha());
        remoteViews.setInt(widgetResource.getMaghribText(), "setTextColor", Color.fromString(theme.getTextColor()).toAndroidColorWithAlpha());
        remoteViews.setInt(widgetResource.getIshaText(), "setTextColor", Color.fromString(theme.getTextColor()).toAndroidColorWithAlpha());

        float textTextSize = theme.getTextSize();
        if (textTextSize == -1){
            textTextSize = context.getResources().getDimension(R.dimen.textsize) / context.getResources().getDisplayMetrics().density;
        }
           remoteViews.setFloat(widgetResource.getFajrText(), "setTextSize", textTextSize);
           remoteViews.setFloat(widgetResource.getShurukText(), "setTextSize", textTextSize);
           remoteViews.setFloat(widgetResource.getDhuhrText(), "setTextSize", textTextSize);
           remoteViews.setFloat(widgetResource.getAsrText(), "setTextSize", textTextSize);
           remoteViews.setFloat(widgetResource.getMaghribText(), "setTextSize", textTextSize);
           remoteViews.setFloat(widgetResource.getIshaText(), "setTextSize", textTextSize);


        remoteViews.setInt(widgetResource.getLayoutWrapper(), "setBackgroundColor", Color.fromString(theme.getBackgroundColor()).toAndroidColorWithAlpha());

        //remoteViews.setInt(widgetResource.getLayoutWrapper(), "setColor", Color.fromString(theme.getCornerColor()).toAndroidColorWithAlpha());
    }

    public static void onReceive(Context context, Intent intent) {
        if (Consts.WIDGET_ONCLICK_IDENTIFIER.equals(intent.getAction())) {
            Integer widgetId = intent.getIntExtra(Consts.PROVIDER_TO_MAINSETTINGSUI_WIDGETID, 0);
            if (widgetId != null && widgetId != 0) {
                Intent mainsettings = new Intent(context, WidgetSettings.class);
                mainsettings.putExtra(Consts.PROVIDER_TO_MAINSETTINGSUI_WIDGETID, widgetId);
                mainsettings.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(mainsettings);
            }
        } else if (AppWidgetManager.ACTION_APPWIDGET_UPDATE.equals(intent.getAction())) {
            AppWidgetManager widgetManager = AppWidgetManager.getInstance(context);
            List<WidgetSettingsModel> allWidgetSettings = WidgetSettingsModel.getAllWidgetSettings();
            int[] appWidgetIds = new int[allWidgetSettings.size()];
            for (int i = 0; i < appWidgetIds.length; i++) {
                appWidgetIds[i] = allWidgetSettings.get(i).getWidgetId();
            }
            try {
                onUpdate(context, widgetManager, appWidgetIds);
            } catch (Exception e) {
                e.printStackTrace();
            }

        } else if (AppWidgetManager.ACTION_APPWIDGET_DISABLED.equals(intent.getAction())){
            //Widget deleted.
        }
    }

    public static WidgetResource getWidgetResourceFromWidgetId(Context context, int widgetId) {
        try {
            int initialLayout = AppWidgetManager.getInstance(context).getAppWidgetInfo(widgetId).initialLayout;
            switch (initialLayout) {
                case R.layout.fivebyone:
                    return new FiveByOne();
                case R.layout.fourbyone:
                    return new FourByOne();
                case R.layout.twobytwo:
                    return new TwoByTwo();
            }
        } catch (Exception e) {
            System.out.println("maybe widget doesnt exist or you forget to implement new widget in SalahWidgetProvider");
        }
        return null;
    }
}
