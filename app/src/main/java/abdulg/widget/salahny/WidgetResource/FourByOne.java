package abdulg.widget.salahny.WidgetResource;

import abdulg.widget.salahny.Providers.FiveByOneProvider;
import abdulg.widget.salahny.Providers.FourByOneProvider;
import abdulg.widget.salahny.R;

/**
 * Created by ag on 17-06-2017.
 */

public class FourByOne extends WidgetResource {
    @Override
    public int getFajrTime() {
        return R.id.fajrTimeTV_FourByOne;
    }

    @Override
    public int getShurukTime() {
        return R.id.shurukTimeTV_FourByOne;
    }

    @Override
    public int getDhuhrTime() {
        return R.id.dhuhrTimeTV_FourByOne;
    }

    @Override
    public int getAsrTime() {
        return R.id.asrTimeTV_FourByOne;
    }

    @Override
    public int getMaghribTime() {
        return R.id.maghribTimeTV_FourByOne;
    }

    @Override
    public int getIshaTime() {
        return R.id.ishaTimeTV_FourByOne;
    }

    @Override
    public int getFajrText() {
        return R.id.fajrTV_FourByOne;
    }

    @Override
    public int getShurukText() {
        return R.id.shurukTV_FourByOne;
    }

    @Override
    public int getDhuhrText() {
        return R.id.dhuhrTV_FourByOne;
    }

    @Override
    public int getAsrText() {
        return R.id.asrTV_FourByOne;
    }

    @Override
    public int getMaghribText() {
        return R.id.maghribTV_FourByOne;
    }

    @Override
    public int getIshaText() {
        return R.id.ishaTV_FourByOne;
    }

    @Override
    public int getLayoutWrapper() {
        return R.id.widgetfourbyone;
    }

    @Override
    public int getLayout() {
        return R.layout.fourbyone;
    }

    @Override
    public Class getProvider() {
        return FourByOneProvider.class;
    }
}
