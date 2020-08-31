package abdulg.widget.salahny.Model.Prayer;

import abdulg.widget.salahny.Model.Enums.AsrJuristicMethod;
import abdulg.widget.salahny.Model.Enums.HighAltitudeAdjustmentMethod;
import abdulg.widget.salahny.Model.Enums.PrayerTimesCalculationMethod;
import abdulg.widget.salahny.Model.Time.MonthDayPeriod;
import androidx.room.Embedded;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class PrayerTimesMethodPeriod
{
  @PrimaryKey
  private int id;
  private int widgetSettingsModelId;
  @Embedded
  private MonthDayPeriod period;
  private String chosenCalculationMethodString;
  private int fajrDifference;
  private int shurukDifference;
  private int dhuhrDifference;
  private int asrDifference;
  private int maghribDifference;
  private int ishaDifference;
  private String asrJuristicMethodString;
  private String highAltitudeAdjustmentMethodString;


  public PrayerTimesMethodPeriod() {
  }

  public PrayerTimesMethodPeriod(MonthDayPeriod period, PrayerTimesCalculationMethod chosenCalculationMethodString, int timeDifference)
  {
    this.period = period;
    this.chosenCalculationMethodString = chosenCalculationMethodString.name();
    fajrDifference = timeDifference;
    shurukDifference = timeDifference;
    dhuhrDifference = timeDifference;
    asrDifference = timeDifference;
    maghribDifference = timeDifference;
    ishaDifference = timeDifference;
    asrJuristicMethodString = AsrJuristicMethod.Shaafi.name();
    highAltitudeAdjustmentMethodString = HighAltitudeAdjustmentMethod.AngleBased.name();
  }

  public PrayerTimesMethodPeriod(PrayerTimesCalculationMethod chosenCalculationMethodString){
    this(new MonthDayPeriod(1,0,31,11), chosenCalculationMethodString, 0);
  }

  public PrayerTimesMethodPeriod(PrayerTimesCalculationMethod chosenCalculationMethodString, HighAltitudeAdjustmentMethod method){
    this(new MonthDayPeriod(1,0,31,11), chosenCalculationMethodString, 0);
    highAltitudeAdjustmentMethodString = method.name();
  }

  public PrayerTimesMethodPeriod(PrayerTimesCalculationMethod chosenCalculationMethodString, int timedifference){
    this(new MonthDayPeriod(1,0,31,11), chosenCalculationMethodString, timedifference);
  }

  public MonthDayPeriod getPeriod()
  {
    return period;
  }

  public void setPeriod(MonthDayPeriod period)
  {
    this.period = period;
  }

  public String getChosenCalculationMethodString()
  {
    return chosenCalculationMethodString;
  }
  public PrayerTimesCalculationMethod getChosenCalculationMethod()
  {
    return PrayerTimesCalculationMethod.valueOf(chosenCalculationMethodString);
  }

  public void setChosenCalculationMethod(PrayerTimesCalculationMethod chosenCalculationMethod)
  {
    this.chosenCalculationMethodString = chosenCalculationMethod.name();
  }

  public int getFajrDifference()
  {
    return fajrDifference;
  }

  public void setFajrDifference(int fajrDifference)
  {
    this.fajrDifference = fajrDifference;
  }

  public int getShurukDifference()
  {
    return shurukDifference;
  }

  public void setShurukDifference(int shurukDifference)
  {
    this.shurukDifference = shurukDifference;
  }

  public int getDhuhrDifference()
  {
    return dhuhrDifference;
  }

  public void setDhuhrDifference(int dhuhrDifference)
  {
    this.dhuhrDifference = dhuhrDifference;
  }

  public int getAsrDifference()
  {
    return asrDifference;
  }

  public void setAsrDifference(int asrDifference)
  {
    this.asrDifference = asrDifference;
  }

  public int getMaghribDifference()
  {
    return maghribDifference;
  }

  public void setMaghribDifference(int maghribDifference)
  {
    this.maghribDifference = maghribDifference;
  }

  public int getIshaDifference()
  {
    return ishaDifference;
  }

  public void setIshaDifference(int ishaDifference)
  {
    this.ishaDifference = ishaDifference;
  }

  public AsrJuristicMethod getAsrJuristicMethod() {
    return AsrJuristicMethod.valueOf(asrJuristicMethodString);
  }

  public void setAsrJuristicMethodString(AsrJuristicMethod asrJuristicMethod) {
    this.asrJuristicMethodString = asrJuristicMethod.name();
  }

  public String getHighAltitudeAdjustmentMethodString() {
    return highAltitudeAdjustmentMethodString;
  }
  public HighAltitudeAdjustmentMethod getHighAltitudeAdjustmentMethod() {
    return HighAltitudeAdjustmentMethod.valueOf(highAltitudeAdjustmentMethodString);
  }

  public void setHighAltitudeAdjustmentMethod(HighAltitudeAdjustmentMethod highAltitudeAdjustmentMethod) {
    this.highAltitudeAdjustmentMethodString = highAltitudeAdjustmentMethod.name();
  }

  public int getId() {
    return id;
  }

  public void setId(int id) {
    this.id = id;
  }

  public int getWidgetSettingsModelId() {
    return widgetSettingsModelId;
  }

  public void setWidgetSettingsModelId(int widgetSettingsModelId) {
    this.widgetSettingsModelId = widgetSettingsModelId;
  }

  public void setChosenCalculationMethodString(String chosenCalculationMethodString) {
    this.chosenCalculationMethodString = chosenCalculationMethodString;
  }

  public String getAsrJuristicMethodString() {
    return asrJuristicMethodString;
  }

  public void setAsrJuristicMethodString(String asrJuristicMethodString) {
    this.asrJuristicMethodString = asrJuristicMethodString;
  }

  public void setHighAltitudeAdjustmentMethodString(String highAltitudeAdjustmentMethodString) {
    this.highAltitudeAdjustmentMethodString = highAltitudeAdjustmentMethodString;
  }
}
