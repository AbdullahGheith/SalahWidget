package abdulg.widget.salahny.Model.Enums;

import abdulg.widget.salahny.R;

/**
 * Created by Abdullah Gheith on 05/05/2018.
 */
public enum AsrJuristicMethod {
    Shaafi(R.string.shaafi),
    Hanafi(R.string.hanafi);

    int nameKey;
    AsrJuristicMethod(int nameKey){
        this.nameKey = nameKey;
    }

    public int getNameKey() {
        return nameKey;
    }
}
