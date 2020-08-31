package abdulg.widget.salahny.Logic;

import abdulg.widget.salahny.Logic.CustomPrayerTimes.DenmarkAndMalmo.SalahTider;
import abdulg.widget.salahny.Model.Enums.AsrJuristicMethod;
import abdulg.widget.salahny.Model.Enums.HighAltitudeAdjustmentMethod;
import abdulg.widget.salahny.Model.Enums.PrayerTimesCalculationMethod;
import abdulg.widget.salahny.Model.Prayer.PrayerPack;
import abdulg.widget.salahny.Model.Prayer.PrayerTimesMethodPeriod;
import abdulg.widget.salahny.Model.Time.MonthDayDate;
import abdulg.widget.salahny.Model.View.WidgetSettingsModel;

import java.util.*;

/**
 * Created by Abdullah Gheith on 01/05/2018.
 */
public class PrayerTimeFinder {
    public PrayerPack findPrayerPack(WidgetSettingsModel wsm) {
        return findPrayerPack(wsm, Calendar.getInstance());
    }

    public PrayerPack findPrayerPack(WidgetSettingsModel wsm, Calendar date) {
            switch (wsm.getPeriodForDate(new MonthDayDate(date)).getChosenCalculationMethod()) {
                case MWL:
                case ISNA:
                case EGAS:
                case UQU:
                case UIS:
                case IGUT:
                case Denmark_NewTimes:
                    return getCalculatedPrayerTime(wsm, date);
                case DenmarkMalmo_OldTimes:
                    return getDenmarkMalmo_OldTimes(wsm, date);
            }
        return null;
    }

    private PrayerPack getDenmarkMalmo_OldTimes(WidgetSettingsModel wsm, Calendar date){
        return SalahTider.getSalahForWholeDay(date, AsrJuristicMethod.Hanafi.equals(wsm.getCurrentPeriod().getAsrJuristicMethod()) ? true : false, wsm.getCurrentPeriod().getFajrDifference(), wsm.getCurrentPeriod().getShurukDifference(), wsm.getCurrentPeriod().getDhuhrDifference(), wsm.getCurrentPeriod().getAsrDifference(), wsm.getCurrentPeriod().getMaghribDifference(), wsm.getCurrentPeriod().getIshaDifference());
    }

    private PrayerPack getCalculatedPrayerTime(WidgetSettingsModel wsm, Calendar date) {
        PrayerTimesMethodPeriod currentPeriod = wsm.getCurrentPeriod();
        PrayerTimeCalculator prayerTimeCalculator = new PrayerTimeCalculator();
        prayerTimeCalculator.setCalcMethod(calcMethodToValue(currentPeriod.getChosenCalculationMethod()));
        prayerTimeCalculator.setAsrJuristic(AsrJuristicMethod.Hanafi.equals(currentPeriod.getAsrJuristicMethod()) ? 1 : 0);
        prayerTimeCalculator.setTimeFormat(0); //24h format
        prayerTimeCalculator.setLat(Double.valueOf(wsm.getUserCoordinates().getLatitude()));
        prayerTimeCalculator.setLng(Double.valueOf(wsm.getUserCoordinates().getLongitude()));
        prayerTimeCalculator.setAdjustHighLats(highAltitudeAdjustmentToValue(currentPeriod.getHighAltitudeAdjustmentMethod()));
        int[] offsets = {currentPeriod.getFajrDifference(), currentPeriod.getShurukDifference(), currentPeriod.getDhuhrDifference(), currentPeriod.getAsrDifference(), 0, currentPeriod.getMaghribDifference(), currentPeriod.getIshaDifference()}; // {Fajr,Sunrise,Dhuhr,Asr,Sunset,Maghrib,Isha}
        if (currentPeriod.getChosenCalculationMethod() == PrayerTimesCalculationMethod.Denmark_NewTimes){
            prayerTimeCalculator.setCustomParams(new double[]{15,1,0,0,15});
            offsets[1] = offsets[1] - 2;
            offsets[2] = offsets[2] + 2;
            prayerTimeCalculator.setAdjustHighLats(0);
        }
        prayerTimeCalculator.tune(offsets);

        ArrayList<String> prayerTimes = prayerTimeCalculator.getPrayerTimes(date, prayerTimeCalculator.getLat(), prayerTimeCalculator.getLng(), prayerTimeCalculator.getTimeZoneWithDSTCheck(date.getTime()));
        if (currentPeriod.getChosenCalculationMethod() == PrayerTimesCalculationMethod.Denmark_NewTimes){
            adjustNewTimesForNonCalculationTimes(date, prayerTimes);
        }
        PrayerPack prayerPack = new PrayerPack(calendarCreator(prayerTimes.get(0)),calendarCreator(prayerTimes.get(1)),calendarCreator(prayerTimes.get(2)),calendarCreator(prayerTimes.get(3)),calendarCreator(prayerTimes.get(5)),calendarCreator(prayerTimes.get(6)));
        return prayerPack;
    }

    private void adjustNewTimesForNonCalculationTimes(Calendar date, ArrayList<String> prayerTimes){
        //Between 5. maj and 9. august, fajr and isha are not calculated due to mashaqqa
        Calendar from = new GregorianCalendar();
        from.set(Calendar.MONTH, 4);
        from.set(Calendar.DAY_OF_MONTH, 4);
        from.set(Calendar.HOUR_OF_DAY, 0);
        from.set(Calendar.MINUTE, 0);
        from.set(Calendar.SECOND, 0);
        from.set(Calendar.MILLISECOND, 0);
        Calendar to = new GregorianCalendar();
        to.set(Calendar.MONTH, 7);
        to.set(Calendar.DAY_OF_MONTH, 10);
        to.set(Calendar.HOUR_OF_DAY, 0);
        to.set(Calendar.MINUTE, 0);
        to.set(Calendar.SECOND, 0);
        to.set(Calendar.MILLISECOND, 0);

        if (date.after(from) && date.before(to)){
            boolean setFajrToo = true;
            if (date.get(Calendar.MONTH) == 7 && (date.get(Calendar.DAY_OF_MONTH) == 8 || date.get(Calendar.DAY_OF_MONTH) == 9)){ //Theres an exception for these two days where we use the calculated time
                setFajrToo = false;
            }

            if (setFajrToo)
                prayerTimes.set(0, "02:54");

            prayerTimes.set(6, "23:22");
        }

    }

    private int calcMethodToValue(PrayerTimesCalculationMethod method){
        switch (method) {
            case MWL:
                return 3;
            case ISNA:
                return 2;
            case EGAS:
                return 5;
            case UQU:
                return 4;
            case UIS:
                return 1;
            case IGUT:
                return 6;
            case Denmark_NewTimes:
                return 7;
        }
        throw new UnsupportedOperationException("Prayer time method not supported!");
    }
    private int highAltitudeAdjustmentToValue(HighAltitudeAdjustmentMethod method){
        switch (method) {
            case None:
                return 1;
            case Middle:
                return 1;
            case OneSeventh:
                return 2;
            case AngleBased:
                return 3;
        }
        throw new UnsupportedOperationException("not supported high altitude method!");
    }

    private Calendar calendarCreator(String time){ //Time in format xx:xx

        try{
            String[] split = time.split(":");
            String hour = split[0];
            String minute = split[1];
            Calendar cal = Calendar.getInstance();
            cal.set(Calendar.MILLISECOND, 0);
            cal.set(Calendar.SECOND, 0);
            cal.set(Calendar.HOUR_OF_DAY, Integer.valueOf(hour));
            cal.set(Calendar.MINUTE, Integer.valueOf(minute));
            return cal;
        } catch(Exception e){
            e.printStackTrace();
        }
        return null;
    }

    public static void main(String[] args) {
        //PrayerTimeFinder a = new PrayerTimeFinder();
        //a.getPrayerTimesMWL(new PrayerTimesMethodPeriod(PrayerTimesCalculationMethod.MWL), new Coordinates(55.40739815617391, 10.40265575483079));
    }
}
