

import abdulg.widget.salahny.Controllere.MainSalahTimesController;
import abdulg.widget.salahny.Controllere.MyApplication;
import abdulg.widget.salahny.Model.AppDatabase;
import abdulg.widget.salahny.Model.Enums.AsrJuristicMethod;
import abdulg.widget.salahny.Model.Enums.PrayerTimesCalculationMethod;
import abdulg.widget.salahny.Model.Places.Coordinates;
import abdulg.widget.salahny.Model.Time.MonthDayDate;
import abdulg.widget.salahny.Model.View.ThemeConfiguration;
import abdulg.widget.salahny.Model.View.WidgetSettingsModel;
import abdulg.widget.salahny.Model.View.WidgetSettingsModelDao;
import android.content.Context;
import androidx.room.Room;
import androidx.test.core.app.ApplicationProvider;
import java.util.GregorianCalendar;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class DatabaseTest {
	private WidgetSettingsModelDao wsmd;
	private AppDatabase db;
	private Context context;

	@Before
	public void createDb() {
		context = ApplicationProvider.getApplicationContext();
		db = Room.inMemoryDatabaseBuilder(context, AppDatabase.class).build();
		wsmd = db.widgetSettingsModelDao();
	}

	@After
	public void closeDb() {
		db.close();
	}

	@Test
	public void createSaveAndRetrieveWSM() {
		MyApplication.setDatabase(db);

		MainSalahTimesController m = new MainSalahTimesController(context);
		WidgetSettingsModel widgetsettings = m.createNewDefaultWidgetSettingsForLocation(new Coordinates(55.403755, 10.402370), 15);
		AsrJuristicMethod asrJuristicMethod = widgetsettings.getCurrentPeriod().getAsrJuristicMethod();
		int widgetId = widgetsettings.getWidgetId();
		widgetsettings.update();
		WidgetSettingsModel w = WidgetSettingsModel.getWidgetSettingsWithId(widgetId);
		assertEquals(w.getWidgetId(), widgetId);
		assertEquals(w.getPeriodlist().size(), 1);

		PrayerTimesCalculationMethod chosenCalculationMethod = w.getPeriodForDate(new MonthDayDate(new GregorianCalendar())).getChosenCalculationMethod();
		assertEquals(PrayerTimesCalculationMethod.DenmarkMalmo_OldTimes.name(), chosenCalculationMethod.name());

		WidgetSettingsModel widgetsettings2 = m.createNewDefaultWidgetSettingsForLocation(new Coordinates(55.503755, 10.502370), 16);
		AsrJuristicMethod asrJuristicMethod2 = widgetsettings2.getCurrentPeriod().getAsrJuristicMethod();
		int widgetId2 = widgetsettings2.getWidgetId();
		widgetsettings2.update();
		WidgetSettingsModel w2 = WidgetSettingsModel.getWidgetSettingsWithId(widgetId2);
		assertEquals(w2.getWidgetId(), widgetId2);
		assertEquals(w2.getPeriodlist().size(), 1);

		m.setCurrentCalculationMethod(w, PrayerTimesCalculationMethod.IGUT);
		w = WidgetSettingsModel.getWidgetSettingsWithId(widgetId);
		assertEquals(w.getCurrentPeriod().getChosenCalculationMethod().getNameKey(), PrayerTimesCalculationMethod.IGUT.getNameKey());

		m.setCurrentCalculationMethod(w2, PrayerTimesCalculationMethod.EGAS);

		w2 = WidgetSettingsModel.getWidgetSettingsWithId(widgetId2);
		assertEquals(w2.getCurrentPeriod().getChosenCalculationMethod().getNameKey(), PrayerTimesCalculationMethod.EGAS.getNameKey());

		AsrJuristicMethod newAsr = AsrJuristicMethod.Hanafi == asrJuristicMethod ? AsrJuristicMethod.Shaafi : AsrJuristicMethod.Hanafi;
		m.setCurrentAsrJuristicMethod(w, newAsr);

		w = WidgetSettingsModel.getWidgetSettingsWithId(widgetId);
		w2 = WidgetSettingsModel.getWidgetSettingsWithId(widgetId2);
		assertEquals(w.getCurrentPeriod().getAsrJuristicMethod().name(), newAsr.name());
		assertEquals(w2.getCurrentPeriod().getAsrJuristicMethod().name(), asrJuristicMethod2.name());

		w.setThemeConfiguration(new ThemeConfiguration());
		w.update();

		w = WidgetSettingsModel.getWidgetSettingsWithId(widgetId);
		chosenCalculationMethod = w.getPeriodForDate(new MonthDayDate(new GregorianCalendar())).getChosenCalculationMethod();
		assertEquals(PrayerTimesCalculationMethod.IGUT.name(), chosenCalculationMethod.name());
	}

}
