package abdulg.widget.salahny.Model.Time;


import java.util.Calendar;

public class MonthDayDate
{
	private int day;
	private int month; //0=jan

	public MonthDayDate() {
	}

	public MonthDayDate(int day, int month) {
		this.day = day;
		this.month = month;
	}
	public MonthDayDate(Calendar date) {
		this.day = date.get(Calendar.DATE);
		this.month = date.get(Calendar.MONTH);
	}

	public int getDay(){ return day; }
	public int getMonth() { return month; }

	public void setDay(int day) {
		this.day = day;
	}

	public void setMonth(int month) {
		this.month = month;
	}
}
