package abdulg.widget.salahny.WidgetResource;

import abdulg.widget.salahny.Providers.TwoByTwoProvider;
import abdulg.widget.salahny.R;

/**
 * Created by ag on 17-06-2017.
 */

public class TwoByTwo extends WidgetResource {
    @Override
    public int getFajrTime() {
        return R.id.fajrTimeTV_ThreeByThree;
    }

    @Override
    public int getShurukTime() {
        return R.id.shurukTimeTV_ThreeByThree;
    }

    @Override
    public int getDhuhrTime() {
        return R.id.dhuhrTimeTV_ThreeByThree;
    }

    @Override
    public int getAsrTime() {
        return R.id.asrTimeTV_ThreeByThree;
    }

    @Override
    public int getMaghribTime() {
        return R.id.maghribTimeTV_ThreeByThree;
    }

    @Override
    public int getIshaTime() {
        return R.id.ishaTimeTV_ThreeByThree;
    }

    @Override
    public int getFajrText() {
        return R.id.fajrTV_ThreeByThree;
    }

    @Override
    public int getShurukText() {
        return R.id.shurukTV_ThreeByThree;
    }

    @Override
    public int getDhuhrText() {
        return R.id.dhuhrTV_ThreeByThree;
    }

    @Override
    public int getAsrText() {
        return R.id.asrTV_ThreeByThree;
    }

    @Override
    public int getMaghribText() {
        return R.id.maghribTV_ThreeByThree;
    }

    @Override
    public int getIshaText() {
        return R.id.ishaTV_ThreeByThree;
    }

    @Override
    public int getLayoutWrapper() {
        return R.id.widgetTwoByTwo;
    }

    @Override
    public int getLayout() {
        return R.layout.twobytwo;
    }

    @Override
    public Class getProvider() {
        return TwoByTwoProvider.class;
    }
}
