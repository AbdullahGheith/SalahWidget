package abdulg.widget.salahny.Providers;

import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.Context;
import android.content.Intent;

/**
 * Created by ag on 05-06-2017.
 */

public class FourByOneProvider extends AppWidgetProvider {

    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
        try {
            SalahWidgetProvider.onUpdate(context, appWidgetManager, appWidgetIds);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        SalahWidgetProvider.onReceive(context, intent);
    }

    @Override
    public void onDeleted(Context context, int[] appWidgetIds) {
        super.onDeleted(context, appWidgetIds);
        //Called when a widget is deleted (if more widgets exist)
    }

    @Override
    public void onDisabled(Context context) {
        super.onDisabled(context);
        //Called when the last widget has been removed from the homescreen
    }

}
