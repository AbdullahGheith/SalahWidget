package abdulg.widget.salahny.Model.Enums;

import abdulg.widget.salahny.R;

/**
 * Created by Abdullah Gheith on 05/05/2018.
 */
public enum HighAltitudeAdjustmentMethod {
    None(R.string.none),
    Middle(R.string.middle),
    OneSeventh(R.string.oneseventh),
    AngleBased(R.string.anglebased);

    int nameKey;
    HighAltitudeAdjustmentMethod(int nameKey){
        this.nameKey = nameKey;
    }

    public int getNameKey() {
        return nameKey;
    }
}
