package abdulg.widget.salahny.Model.Prayer;

import java.text.SimpleDateFormat;
import java.util.Calendar;

/**
 * Created by ag on 11-06-2017.
 */

public class PrayerPack {
    //Maybe replace calendars with strings as the dates are irrelevant as seen in PrayerTimeFinder
    private Calendar fajr;
    private Calendar shuruk;
    private Calendar dhuhr;
    private Calendar asr;
    private Calendar maghrib;
    private Calendar isha;

    private SimpleDateFormat timeFormatter = new SimpleDateFormat("HH:mm");

    public PrayerPack(Calendar fajr, Calendar shuruk, Calendar dhuhr, Calendar asr, Calendar maghrib, Calendar isha) {
        this.fajr = fajr;
        this.shuruk = shuruk;
        this.dhuhr = dhuhr;
        this.asr = asr;
        this.maghrib = maghrib;
        this.isha = isha;
    }

    public String getFajrInHHmm() {
        return timeFormatter.format(getFajr().getTime());
    }

    public String getShurukInHHmm() {
        return timeFormatter.format(getShuruk().getTime());
    }

    public String getDhuhrInHHmm() {
        return timeFormatter.format(getDhuhr().getTime());
    }
    public String getAsrInHHmm() {
        return timeFormatter.format(getAsr().getTime());
    }
    public String getMaghribInHHmm() {
        return timeFormatter.format(getMaghrib().getTime());
    }
    public String getIshaInHHmm() {
        return timeFormatter.format(getIsha().getTime());
    }

    public Calendar getFajr() {
       return fajr;
    }

    public Calendar getShuruk() {
        return shuruk;
    }

    public Calendar getDhuhr() {
        return dhuhr;
    }

    public Calendar getAsr() {
        return asr;
    }

    public Calendar getMaghrib() {
        return maghrib;
    }

    public Calendar getIsha() {
        return isha;
    }

    public static void main(String[] args){
        PrayerPack e = new PrayerPack(Calendar.getInstance(), null, null, null, null, null);
        System.out.println(e.getFajrInHHmm());
    }
}
