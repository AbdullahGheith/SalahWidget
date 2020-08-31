package abdulg.widget.salahny.UI.Fragments.MainActivity;

import abdulg.widget.salahny.Misc.Utils;
import abdulg.widget.salahny.Model.View.WidgetSettingsModel;
import abdulg.widget.salahny.R;
import android.app.DatePickerDialog;
import android.content.Context;
import android.graphics.Rect;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.DatePicker;
import android.widget.LinearLayout;
import android.widget.TextView;
import butterknife.BindView;
import butterknife.ButterKnife;

import java.text.SimpleDateFormat;
import java.util.Calendar;

/**
 * Created by Abdullah Gheith on 15/05/2018.
 */
public class CalendarSelectionFragment extends Fragment {

    private MainActivityNavigation navigation;
    private WidgetSettingsModel widget;

    private boolean butterKnifeInitialized = false;

    private Calendar calendar;

    @BindView(R.id.calendarselection_currentdate)
    TextView currentDateTextView;

    @BindView(R.id.calendarselection_layout)
    LinearLayout layout;

    DatePickerDialog.OnDateSetListener date;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_calendarselection, container, false);
        ButterKnife.bind(this, view);
        butterKnifeInitialized = true;
        dateUpdated();
        date = new DatePickerDialog.OnDateSetListener() {

            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear,
                                  int dayOfMonth) {
                // TODO Auto-generated method stub
                calendar.set(Calendar.YEAR, year);
                calendar.set(Calendar.MONTH, monthOfYear);
                calendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                dateUpdated();

            }

            };

        View.OnClickListener showDatePickerListener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openDatePicker();
            }
        };

        layout.setOnClickListener(showDatePickerListener);
        currentDateTextView.setOnClickListener(showDatePickerListener);

        return view;
    }

    public void openDatePicker(){
        new DatePickerDialog(getContext(), date, calendar
          .get(Calendar.YEAR), calendar.get(Calendar.MONTH),
          calendar.get(Calendar.DAY_OF_MONTH)).show();
    }

    public void dateUpdated(){
        SimpleDateFormat format = new SimpleDateFormat("EEE, d MMM yyyy"); // 15 chars
        currentDateTextView.setText(format.format(calendar.getTime()));
        //currentDateTextView.setTextSize(TypedValue.COMPLEX_UNIT_PX, Utils.getScreenWidthInPx(getContext())/12);
    }

    public Calendar getCalendar() {
        return calendar;
    }

    public void setCalendar(Calendar calendar) {
        this.calendar = calendar;
        if (butterKnifeInitialized)
            dateUpdated();
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
