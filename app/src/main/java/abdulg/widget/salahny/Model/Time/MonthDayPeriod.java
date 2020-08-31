package abdulg.widget.salahny.Model.Time;

import androidx.room.Embedded;
import androidx.room.Ignore;
import java.util.Calendar;

public class MonthDayPeriod
{
  private int fromDay;
  private int fromMonth;
  private int toDay;
  private int toMonth;


  public MonthDayPeriod() {
  }

  @Ignore
  public MonthDayPeriod(MonthDayDate fromDate, MonthDayDate toDate)
  {
    this.fromDay = fromDate.getDay();
    this.fromMonth = fromDate.getMonth();

    this.toDay = toDate.getDay();
    this.toMonth = toDate.getMonth();
  }

  @Ignore
  public MonthDayPeriod(int fromDay, int fromMonth, int toDay, int toMonth)
  {
    this.fromDay = fromDay;
    this.fromMonth = fromMonth;

    this.toDay = toDay;
    this.toMonth = toMonth;
  }

  public boolean isDateInPeriod(MonthDayDate date)
  {
    if (fromMonth == date.getMonth() || toMonth == date.getMonth()){
      if (date.getDay() >= fromDay && date.getDay() <= toDay)
      {
        return true;
      }
    }

    if (date.getMonth() > fromMonth && date.getMonth() < toMonth)
    {
      return true;
    }
    return false;
  }

  public static void main(String[] args)
  {
    MonthDayPeriod period = new MonthDayPeriod(new MonthDayDate(1, 0), new MonthDayDate(31, 11));
    System.out.println(period.isDateInPeriod(new MonthDayDate(Calendar.getInstance())));
  }

  public int getFromDay() {
    return fromDay;
  }

  public void setFromDay(int fromDay) {
    this.fromDay = fromDay;
  }

  public int getFromMonth() {
    return fromMonth;
  }

  public void setFromMonth(int fromMonth) {
    this.fromMonth = fromMonth;
  }

  public int getToDay() {
    return toDay;
  }

  public void setToDay(int toDay) {
    this.toDay = toDay;
  }

  public int getToMonth() {
    return toMonth;
  }

  public void setToMonth(int toMonth) {
    this.toMonth = toMonth;
  }
}
