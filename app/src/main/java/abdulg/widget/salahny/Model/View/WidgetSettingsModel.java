package abdulg.widget.salahny.Model.View;

import abdulg.widget.salahny.Controllere.MyApplication;
import abdulg.widget.salahny.Model.Prayer.AdhanSettings;
import abdulg.widget.salahny.Model.Time.HolidaySettings;
import abdulg.widget.salahny.Model.Time.MonthDayDate;
import abdulg.widget.salahny.Model.Places.Coordinates;
import abdulg.widget.salahny.Model.Places.Mosque;
import abdulg.widget.salahny.Model.Prayer.PrayerTimesMethodPeriod;
import androidx.room.Embedded;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import java.util.*;


/**
 * Created by Abdullah on 11/01/2018.
 */

@Entity
public class WidgetSettingsModel {

	@PrimaryKey
	private int widgetId;
	private String widgetName;
//	@Relation(parentColumn = "widgetId", entityColumn = "widgetSettingsModelId")
//	private List<PrayerTimesMethodPeriod> periodlist;
	//private String periodListString;
	@Embedded
	private Mosque mosque;
	@Embedded
	private Coordinates userCoordinates;
	@Embedded
	private ThemeConfiguration themeConfiguration;
	@Embedded
	private HolidaySettings holidaySettings;

	private String prayerTimesMethodPeriodListJSON;

	private String userCity;

	@Ignore
	List<PrayerTimesMethodPeriod> prayerTimesMethodPeriodList;

	public int getWidgetId() {
		return widgetId;
	}

	public void setWidgetId(int widgetId) {
		this.widgetId = widgetId;
	}

	public WidgetSettingsModel() {
		prayerTimesMethodPeriodList = new ArrayList<>();
	}

	public List<PrayerTimesMethodPeriod> getPeriodlist() {
		if ((prayerTimesMethodPeriodList == null || prayerTimesMethodPeriodList.isEmpty()) && (prayerTimesMethodPeriodListJSON != null && !prayerTimesMethodPeriodListJSON.isEmpty())){
			prayerTimesMethodPeriodList = new Gson().fromJson(prayerTimesMethodPeriodListJSON, new TypeToken<ArrayList<PrayerTimesMethodPeriod>>() {}.getType());
		}
		return prayerTimesMethodPeriodList;
	}

	public void setPeriodlist(List<PrayerTimesMethodPeriod> list) {
		this.prayerTimesMethodPeriodList = new ArrayList<>();
		for (PrayerTimesMethodPeriod prayerTimesMethodPeriod : list) {
			prayerTimesMethodPeriod.setWidgetSettingsModelId(this.widgetId);
			this.prayerTimesMethodPeriodList.add(prayerTimesMethodPeriod);
		}
		prayerTimesMethodPeriodListJSON = new Gson().toJson(this.prayerTimesMethodPeriodList);

		//WidgetSettingsModelPeriodLists widgetSettingsModelPeriodList = MyApplication.getDatabase().widgetSettingsModelDao().getWidgetSettingsModelPeriodList(this.widgetId);
		//widgetSettingsModelPeriodList.setPeriodlist(prayerTimesMethodPeriodList);
		//
	}

	//public List<PrayerTimesMethodPeriod> getPeriodlist() {
	//	if (periodlist == null) {
	//		periodlist = new Gson().fromJson(periodListString, new TypeToken<ArrayList<PrayerTimesMethodPeriod>>() {
	//		}.getType());
	//	}
	//	return periodlist;
	//}

	public String getPrayerTimesMethodPeriodListJSON() {
		prayerTimesMethodPeriodListJSON = new Gson().toJson(this.prayerTimesMethodPeriodList);
		return prayerTimesMethodPeriodListJSON;
	}

	public void setPrayerTimesMethodPeriodListJSON(String prayerTimesMethodPeriodListJSON) {
		this.prayerTimesMethodPeriodListJSON = prayerTimesMethodPeriodListJSON;
		getPeriodlist(); //Vi sikre os at JSON bliver sat ude i arrayet
	}

	public List<PrayerTimesMethodPeriod> getPrayerTimesMethodPeriodList() {
		return getPeriodlist();
	}

	public void setPrayerTimesMethodPeriodList(List<PrayerTimesMethodPeriod> prayerTimesMethodPeriodList) {
		this.prayerTimesMethodPeriodList = prayerTimesMethodPeriodList;
	}

	public String getUserCity() {
		return userCity;
	}

	public void setUserCity(String userCity) {
		this.userCity = userCity;
	}

	public String getWidgetName() {
		return widgetName;
	}

	public void setWidgetName(String widgetName) {
		this.widgetName = widgetName;
	}

	//public void setPeriodlist(List<PrayerTimesMethodPeriod> periodlist) {
	//	this.periodlist = periodlist;
	//}
//
	//public void setPeriodlist(PrayerTimesMethodPeriod period) {
	//	this.periodlist = new ArrayList<>();
	//	this.periodlist.add(period);
	//}

	public PrayerTimesMethodPeriod getCurrentPeriod() {
		for (PrayerTimesMethodPeriod prayerTimesMethodPeriod : getPeriodlist()) {
			if (prayerTimesMethodPeriod.getPeriod().isDateInPeriod(new MonthDayDate(Calendar.getInstance()))) {
				return prayerTimesMethodPeriod;
			}
		}
		return null;
	}

	public PrayerTimesMethodPeriod getPeriodForDate(MonthDayDate date) {
		for (PrayerTimesMethodPeriod prayerTimesMethodPeriod : getPeriodlist()) {
			if (prayerTimesMethodPeriod.getPeriod().isDateInPeriod(date)) {
				return prayerTimesMethodPeriod;
			}
		}
		return null;
	}

	public Mosque getMosque() {
		return mosque;
	}

	public void setMosque(Mosque mosque) {
		this.mosque = mosque;
	}

	public Coordinates getUserCoordinates() {
		return userCoordinates;
	}

	public void setUserCoordinates(Coordinates userCoordinates) {
		this.userCoordinates = userCoordinates;
	}

	public ThemeConfiguration getThemeConfiguration() {
		return themeConfiguration;
	}

	public void setThemeConfiguration(ThemeConfiguration themeConfiguration) {
		this.themeConfiguration = themeConfiguration;
	}

	public HolidaySettings getHolidaySettings() {
		return holidaySettings;
	}

	public void setHolidaySettings(HolidaySettings holidaySettings) {
		this.holidaySettings = holidaySettings;
	}

	public static WidgetSettingsModel getWidgetSettingsWithId(Integer widgetId) {
		return MyApplication.getDatabase().widgetSettingsModelDao().getWidgetSettingsModel(widgetId);
	}

	public static List<WidgetSettingsModel> getAllWidgetSettings() {
		return MyApplication.getDatabase().widgetSettingsModelDao().getAllWidgetSettingsModel();
	}

	public void save() {
		MyApplication.getDatabase().widgetSettingsModelDao().save(this);
	}

	public void update() {
		MyApplication.getDatabase().widgetSettingsModelDao().save(this);
	}
}
