package abdulg.widget.salahny.Model.Enums;

/**
 * Created by ag on 11-06-2017.
 */

public enum PrayerType {
    Fajr(1),
    Shuruk(2),
    Dhuhr(3),
    Asr(4),
    Maghrib(5),
    Isha(6);

    private int indexNo;

    PrayerType(int indexNumber) {
        indexNo = indexNumber;
    }


    public int getIndexNo() {
        return indexNo;
    }
}
