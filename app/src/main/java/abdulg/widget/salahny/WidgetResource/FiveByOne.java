package abdulg.widget.salahny.WidgetResource;

import abdulg.widget.salahny.Providers.FiveByOneProvider;
import abdulg.widget.salahny.R;

/**
 * Created by ag on 17-06-2017.
 */

public class FiveByOne extends WidgetResource {
    @Override
    public int getFajrTime() {
        return R.id.fajrTimeTV_FiveByOne;
    }

    @Override
    public int getShurukTime() {
        return R.id.shurukTimeTV_FiveByOne;
    }

    @Override
    public int getDhuhrTime() {
        return R.id.dhuhrTimeTV_FiveByOne;
    }

    @Override
    public int getAsrTime() {
        return R.id.asrTimeTV_FiveByOne;
    }

    @Override
    public int getMaghribTime() {
        return R.id.maghribTimeTV_FiveByOne;
    }

    @Override
    public int getIshaTime() {
        return R.id.ishaTimeTV_FiveByOne;
    }

    @Override
    public int getFajrText() {
        return R.id.fajrTV_FiveByOne;
    }

    @Override
    public int getShurukText() {
        return R.id.shurukTV_FiveByOne;
    }

    @Override
    public int getDhuhrText() {
        return R.id.dhuhrTV_FiveByOne;
    }

    @Override
    public int getAsrText() {
        return R.id.asrTV_FiveByOne;
    }

    @Override
    public int getMaghribText() {
        return R.id.maghribTV_FiveByOne;
    }

    @Override
    public int getIshaText() {
        return R.id.ishaTV_FiveByOne;
    }

    @Override
    public int getLayoutWrapper() {
        return R.id.widgetfivebyone;
    }

    @Override
    public int getLayout() {
        return R.layout.fivebyone;
    }

    @Override
    public Class getProvider() {
        return FiveByOneProvider.class;
    }
}
