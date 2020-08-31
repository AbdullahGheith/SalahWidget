package abdulg.widget.salahny.Model;

import abdulg.widget.salahny.Model.Prayer.PrayerTimesMethodPeriod;
import abdulg.widget.salahny.Model.View.WidgetSettingsModel;
import abdulg.widget.salahny.Model.View.WidgetSettingsModelDao;
import androidx.room.Database;
import androidx.room.RoomDatabase;

/**
 * Created by Abdullah Gheith on 06/09/2018.
 */
@Database(entities = {WidgetSettingsModel.class, PrayerTimesMethodPeriod.class}, version = 3, exportSchema = false)
public abstract class AppDatabase extends RoomDatabase {
	public abstract WidgetSettingsModelDao widgetSettingsModelDao();
}
