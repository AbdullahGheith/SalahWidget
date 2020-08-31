package abdulg.widget.salahny.Model;

import androidx.room.migration.Migration;
import androidx.sqlite.db.SupportSQLiteDatabase;

public class DatabaseMigrations {

	public static final Migration MIGRATION_1_2 = new Migration(1, 2) {
		@Override
		public void migrate(SupportSQLiteDatabase database) {
			//database.execSQL("CREATE TABLE PaymentsDTO ('id' INTEGER, 'adFree' INTEGER, PRIMARY KEY('id'), FOREIGN KEY('id') REFERENCES UserDTO('paymentId'));");
			database.execSQL("ALTER TABLE WidgetSettingsModel ADD COLUMN timesSize INTEGER");
			database.execSQL("ALTER TABLE WidgetSettingsModel ADD COLUMN textSize INTEGER");
			database.execSQL("UPDATE WidgetSettingsModel SET timesSize = -1");
			database.execSQL("UPDATE WidgetSettingsModel SET textSize = -1");
		}
	};
	public static final Migration MIGRATION_2_3 = new Migration(2, 3) {
		@Override
		public void migrate(SupportSQLiteDatabase database) {
			database.execSQL("PRAGMA foreign_keys=off;");
			database.execSQL("ALTER TABLE WidgetSettingsModel RENAME TO wsmold;");
			database.execSQL("CREATE TABLE WidgetSettingsModel ('widgetId' INTEGER NOT NULL, 'widgetName' TEXT, 'prayerTimesMethodPeriodListJSON' TEXT, 'userCity' TEXT, 'mosqueName' TEXT, 'mosqueLatitude' TEXT, 'mosqueLongitude' TEXT, 'mosqueAddress' TEXT, 'mosqueZipCode' TEXT, 'latitude' TEXT, 'longitude' TEXT, 'accuracy' INTEGER, 'backgroundColor' TEXT, 'timesColor' TEXT, 'textColor' TEXT, 'cornerColor' TEXT, 'timesSize' INTEGER, 'textSize' INTEGER, 'country' TEXT, 'arg1' TEXT, PRIMARY KEY('widgetId'));");
			database.execSQL("INSERT INTO WidgetSettingsModel (widgetId, widgetName, prayerTimesMethodPeriodListJSON, userCity, mosqueName, mosqueLatitude, mosqueLongitude, mosqueAddress, mosqueZipCode, latitude, longitude, accuracy, backgroundColor, timesColor, textColor, cornerColor, timesSize, textSize, country, arg1) SELECT widgetId, widgetName, prayerTimesMethodPeriodListJSON, userCity, mosqueName, mosqueLatitude, mosqueLongitude, mosqueAddress, mosqueZipCode, latitude, longitude, accuracy, backgroundColor, timesColor, textColor, cornerColor, timesSize, textSize, country, arg1 FROM wsmold;");
			database.execSQL("DROP TABLE wsmold;");
			database.execSQL("PRAGMA foreign_keys=on;");
		}
	};
}
