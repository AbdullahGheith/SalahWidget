package abdulg.widget.salahny.Model.Time;

import de.galgtonold.jollydayandroid.HolidayCalendar;
import de.galgtonold.jollydayandroid.HolidayManager;

/**
 * Created by Abdullah Gheith on 27/05/2018.
 */
public class HolidaySettings {

	private String country;
	private String arg1;

	public HolidaySettings(HolidayCalendar country, String arg1) {

	}

	public HolidaySettings() {

	}

	public HolidayCalendar getCountryName() {
		try {
			return HolidayCalendar.valueOf(country);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	public void setCountry(HolidayCalendar country) {
		this.country = country.name();
	}

	public String getArg1() {
		return arg1;
	}

	public void setArg1(String arg1) {
		this.arg1 = arg1;
	}

	public void setCountry(String country) {
		this.country = country;
	}
	public String getCountry(){
		return this.country;
	}

}
