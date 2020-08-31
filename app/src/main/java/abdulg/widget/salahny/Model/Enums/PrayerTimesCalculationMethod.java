package abdulg.widget.salahny.Model.Enums;


import abdulg.widget.salahny.R;

/**
 * Created by Abdullah on 11/01/2018.
 */

public enum PrayerTimesCalculationMethod
{
  MWL(R.string.mwl),
  ISNA(R.string.isna),
  EGAS(R.string.egypt),
  UQU(R.string.makkah),
  UIS(R.string.karachi),
  IGUT(R.string.tehran),
  Denmark_NewTimes(R.string.dknewtimes),
  DenmarkMalmo_OldTimes(R.string.dkmalmooldtimes);

  int nameKey;

  public int getNameKey() {
    return nameKey;
  }

  PrayerTimesCalculationMethod(int nameKey)
  {
    this.nameKey = nameKey;
  }
}
