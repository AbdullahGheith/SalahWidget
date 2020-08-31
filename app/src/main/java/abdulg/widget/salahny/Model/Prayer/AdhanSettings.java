package abdulg.widget.salahny.Model.Prayer;

/**
 * Created by Abdullah Gheith on 21/05/2018.
 */
public class AdhanSettings {
    
    //Two sets of adhan. One for weekends and holidays, and one for regular workdays
    private boolean fajr;
    private boolean dhuhr;
    private boolean asr;
    private boolean maghrib;
    private boolean isha;
    private boolean fajrHoliday;
    private boolean dhuhrHoliday;
    private boolean asrHoliday;
    private boolean maghribHoliday;
    private boolean ishaHoliday;
    
    public AdhanSettings() {
        fajr = false;
        dhuhr = false;
        asr = false;
        maghrib = false;
        isha = false;
        fajrHoliday = false;
        dhuhrHoliday = false;
        asrHoliday = false;
        maghribHoliday = false;
        ishaHoliday = false;
    }
    
    public boolean isFajr() {
        return fajr;
    }

    public void setFajr(boolean fajr) {
        this.fajr = fajr;
    }

    public boolean isDhuhr() {
        return dhuhr;
    }

    public void setDhuhr(boolean dhuhr) {
        this.dhuhr = dhuhr;
    }

    public boolean isAsr() {
        return asr;
    }

    public void setAsr(boolean asr) {
        this.asr = asr;
    }

    public boolean isMaghrib() {
        return maghrib;
    }

    public void setMaghrib(boolean maghrib) {
        this.maghrib = maghrib;
    }

    public boolean isIsha() {
        return isha;
    }

    public void setIsha(boolean isha) {
        this.isha = isha;
    }

    public boolean isFajrHoliday() {
        return fajrHoliday;
    }

    public void setFajrHoliday(boolean fajrHoliday) {
        this.fajrHoliday = fajrHoliday;
    }

    public boolean isDhuhrHoliday() {
        return dhuhrHoliday;
    }

    public void setDhuhrHoliday(boolean dhuhrHoliday) {
        this.dhuhrHoliday = dhuhrHoliday;
    }

    public boolean isAsrHoliday() {
        return asrHoliday;
    }

    public void setAsrHoliday(boolean asrHoliday) {
        this.asrHoliday = asrHoliday;
    }

    public boolean isMaghribHoliday() {
        return maghribHoliday;
    }

    public void setMaghribHoliday(boolean maghribHoliday) {
        this.maghribHoliday = maghribHoliday;
    }

    public boolean isIshaHoliday() {
        return ishaHoliday;
    }

    public void setIshaHoliday(boolean ishaHoliday) {
        this.ishaHoliday = ishaHoliday;
    }
}
