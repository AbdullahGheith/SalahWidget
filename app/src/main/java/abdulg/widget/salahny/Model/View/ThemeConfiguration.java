package abdulg.widget.salahny.Model.View;


import abdulg.widget.salahny.R;
import android.content.Context;
import androidx.room.PrimaryKey;

/**
 * Created by Abdullah on 06/01/2018.
 */

public class ThemeConfiguration {
    //Theme UI bruges til at generere en ThemeConfiguration som er hvordan widgeten skal se ud

    private String backgroundColor;
    private String timesColor;
    private String textColor;
    private String cornerColor;
    private int timesSize;
    private int textSize;

    public String getBackgroundColor() {
        return backgroundColor;
    }

    public void setBackgroundColor(String backgroundColor) {
        this.backgroundColor = backgroundColor;
    }

    public String getTimesColor() {
        return timesColor;
    }

    public void setTimesColor(String timesColor) {
        this.timesColor = timesColor;
    }

    public String getTextColor() {
        return textColor;
    }

    public void setTextColor(String textColor) {
        this.textColor = textColor;
    }

    public String getCornerColor() {
        return cornerColor;
    }

    public void setCornerColor(String cornerColor) {
        this.cornerColor = cornerColor;
    }

    public int getTimesSize() {
        return timesSize;
    }

    public void setTimesSize(int timesSize) {
        this.timesSize = timesSize;
    }

    public int getTextSize() {
        return textSize;
    }

    public void setTextSize(int textSize) {
        this.textSize = textSize;
    }

    public ThemeConfiguration() {
        //defaults
        backgroundColor = new abdulg.widget.salahny.Model.View.Color(255,255,255,255).toString();
        cornerColor = new abdulg.widget.salahny.Model.View.Color(177, 188, 190, 255).toString();
        textColor = new abdulg.widget.salahny.Model.View.Color(0, 0, 0, 255).toString();
        timesColor = new abdulg.widget.salahny.Model.View.Color(0, 0, 0, 255).toString();
        textSize = -1;
        timesSize = -1;
    }
}
