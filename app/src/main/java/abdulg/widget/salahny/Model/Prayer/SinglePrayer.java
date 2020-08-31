package abdulg.widget.salahny.Model.Prayer;

import java.util.Calendar;

/**
 * Created by Abdullah Gheith on 17/05/2018.
 */
public class SinglePrayer {
    private String name;
    private Calendar time;

    public SinglePrayer(String name, Calendar time) {
        this.name = name;
        this.time = time;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Calendar getTime() {
        return time;
    }

    public void setTime(Calendar time) {
        this.time = time;
    }
}
