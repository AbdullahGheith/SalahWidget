package abdulg.widget.salahny.Misc;

import abdulg.widget.salahny.Model.Prayer.PrayerPack;
import abdulg.widget.salahny.Model.Prayer.SinglePrayer;
import abdulg.widget.salahny.R;
import android.content.Context;
import androidx.annotation.Nullable;
import android.util.DisplayMetrics;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

/**
 * Created by Abdullah Gheith on 16/05/2018.
 */
public class Utils {

    public static int getDip(Context context, int pixel)
    {
        float scale = context.getResources().getDisplayMetrics().density;
        return (int) (pixel * scale + 0.5f);
    }

    public static int getScreenWidthInPx(Context context){
        DisplayMetrics displayMetrics = context.getResources().getDisplayMetrics();
        return displayMetrics.widthPixels;
    }
    public static int getScreenHeightInPx(Context context){
        DisplayMetrics displayMetrics = context.getResources().getDisplayMetrics();
        return displayMetrics.heightPixels;
    }

    public static SinglePrayer WhatPrayerIsNext(PrayerPack prayerPack, Context context){ //if prayertime (hour and minute) = current hour and minute then we will find the next salah
        List<SinglePrayer> list = new ArrayList<>();
        SinglePrayer fajr = new SinglePrayer(context.getText(R.string.fajr).toString(), prayerPack.getFajr()); list.add(fajr);
        SinglePrayer shuruk = new SinglePrayer(context.getText(R.string.shuruk).toString(), prayerPack.getShuruk()); list.add(shuruk);
        SinglePrayer dhuhr = new SinglePrayer(context.getText(R.string.dhuhr).toString(), prayerPack.getDhuhr()); list.add(dhuhr);
        SinglePrayer asr = new SinglePrayer(context.getText(R.string.asr).toString(), prayerPack.getAsr()); list.add(asr);
        SinglePrayer maghrib = new SinglePrayer(context.getText(R.string.maghrib).toString(), prayerPack.getMaghrib()); list.add(maghrib);
        SinglePrayer isha = new SinglePrayer(context.getText(R.string.isha).toString(), prayerPack.getIsha()); list.add(isha);

        Calendar currentCalender = Calendar.getInstance();
        int secondsremaining = 99999999;

        for (SinglePrayer prayer : list) {
            if (prayer.getTime().after(currentCalender)) //In order, if we get one that is after the current time, then thats it.
                return prayer;
        }
        return fajr; //If nothing is after, then it must mean that isha has arrived, and next salah will be fajr.
    }

    public static String getString(String textResourceName, String defaultText, Context context) {
        int identifier = context.getResources().getIdentifier(textResourceName, "string", "abdulg.widget.salahny");
        if (identifier != 0) {
            return context.getResources().getString(identifier);
        } else {
            return defaultText;
        }
    }
}
