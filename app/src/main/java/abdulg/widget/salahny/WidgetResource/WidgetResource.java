package abdulg.widget.salahny.WidgetResource;

/**
 * Created by ag on 17-06-2017.
 */

public abstract class WidgetResource {
    public abstract int getFajrTime();
    public abstract int getShurukTime();
    public abstract int getDhuhrTime();
    public abstract int getAsrTime();
    public abstract int getMaghribTime();
    public abstract int getIshaTime();

    public abstract int getFajrText();
    public abstract int getShurukText();
    public abstract int getDhuhrText();
    public abstract int getAsrText();
    public abstract int getMaghribText();
    public abstract int getIshaText();

    public abstract int getLayoutWrapper();

    public abstract int getLayout();

    public abstract Class getProvider();
}
