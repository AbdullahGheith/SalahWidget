package abdulg.widget.salahny.UI.Fragments.MainActivity;

import abdulg.widget.salahny.Controllere.MainSalahTimesController;
import abdulg.widget.salahny.Model.View.WidgetSettingsModel;
import abdulg.widget.salahny.R;
import abdulg.widget.salahny.UI.Fragments.MainActivity.Adapters.BigPrayerTimeAdapter;
import android.content.Context;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;
import butterknife.ButterKnife;

import java.util.Calendar;

/**
 * Created by Abdullah Gheith on 15/05/2018.
 */
public class BigPrayerTimeFragment extends Fragment {

    private MainActivityNavigation navigation;
    private MainSalahTimesController controller;
    private WidgetSettingsModel widget;
    private Calendar chosenDate;

    private BigPrayerTimeAdapter bigPrayerTimeAdapter;

    private Boolean butterKnifeBound = false;

    @BindView(R.id.recycler_view)
    RecyclerView recyclerView;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_bigprayertime, container, false);
        ButterKnife.bind(this, view);
        butterKnifeBound = true;
        LinearLayoutManager lm = new LinearLayoutManager(getContext());
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        bigPrayerTimeAdapter = new BigPrayerTimeAdapter(getContext(), controller.getPrayerPackForDate(widget.getWidgetId(), getChosenDate()));
        recyclerView.setAdapter(bigPrayerTimeAdapter);
        recyclerView.setLayoutManager(lm);
        recyclerView.setHasFixedSize(true);
        recyclerView.setItemViewCacheSize(20);
        recyclerView.setDrawingCacheEnabled(true);
        recyclerView.setDrawingCacheQuality(View.DRAWING_CACHE_QUALITY_HIGH);

        return view;
    }

    public void updatePrayerTimes(){
        bigPrayerTimeAdapter.setPrayerPack(controller.getPrayerPackForDate(widget.getWidgetId(), getChosenDate()));
        bigPrayerTimeAdapter.notifyDataSetChanged();
    }

    public Calendar getChosenDate() {
        return chosenDate;
    }

    public void setChosenDate(Calendar chosenDate) {
        this.chosenDate = chosenDate;
        if (butterKnifeBound)
            updatePrayerTimes();
    }

    public MainSalahTimesController getController() {
        return controller;
    }

    public void setController(MainSalahTimesController controller) {
        this.controller = controller;
    }

    public WidgetSettingsModel getWidget() {
        return widget;
    }

    public void setWidget(WidgetSettingsModel widget) {
        this.widget = widget;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        try {
            navigation = (MainActivityNavigation) getActivity();
        } catch (ClassCastException e) {
            throw new ClassCastException(getActivity().toString()
                    + " must implement MainActivityNavigation");
        }
    }
}
