package abdulg.widget.salahny.UI.Fragments.MainActivity;

import abdulg.widget.salahny.Controllere.MainSalahTimesController;
import abdulg.widget.salahny.Logic.CustomPrayerTimes.DenmarkAndMalmo.SalahTider;
import abdulg.widget.salahny.Model.Enums.PrayerType;
import abdulg.widget.salahny.Model.Time.HolidaySettings;
import abdulg.widget.salahny.Model.View.WidgetSettingsModel;
import abdulg.widget.salahny.Providers.AdhanBroadcastReceiver;
import abdulg.widget.salahny.R;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import androidx.fragment.app.Fragment;
import androidx.appcompat.app.AppCompatActivity;
import android.text.InputType;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.TextView;
import android.widget.Toast;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.MaterialDialog;
import de.galgtonold.jollydayandroid.Holiday;
import de.galgtonold.jollydayandroid.HolidayCalendar;
import de.galgtonold.jollydayandroid.HolidayManager;

import java.text.SimpleDateFormat;
import java.util.*;

@Deprecated
public class AdhanSettings extends Fragment {

    @BindView(R.id.message)
    public TextView mTextMessage;
    @BindView(R.id.adhansettingFajr)
    public CheckBox fajr;
    @BindView(R.id.adhansettingDhuhr)
    public CheckBox dhuhr;
    @BindView(R.id.adhansettingAsr)
    public CheckBox asr;
    @BindView(R.id.adhansettingMaghrib)
    public CheckBox maghrib;
    @BindView(R.id.adhansettingIsha)
    public CheckBox isha;

    @BindView(R.id.adhansettingsholidaysetup)
    public Button button;

    @BindView(R.id.navigation)
    public BottomNavigationView navigation;

    WidgetSettingsModel widget;
    MainSalahTimesController controller;

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.navigation_home:
                    button.setVisibility(View.INVISIBLE);
                    updateCheckboxes(false);
                    return true;
                case R.id.navigation_adhan:
                    button.setVisibility(View.VISIBLE);
                    updateCheckboxes(true);
                    return true;
            }
            return false;
        }
    };

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_adhan_settings, container, false);
        ButterKnife.bind(this, view);

        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);

        controller = new MainSalahTimesController(getContext());
        widget = controller.getPrimaryWidget();
        if (navigation.getSelectedItemId() ==  R.id.navigation_home){
            button.setVisibility(View.INVISIBLE);
            updateCheckboxes(false);
        }

        return view;
    }

    static String selectedCountryString;
    static String args = "";
    @OnClick(R.id.adhansettingsholidaysetup)
    public void onHolidaySetupClick(View view){


        final MaterialDialog.Builder countryDialog = new MaterialDialog.Builder(getContext())
                .title(R.string.selectcountry)
                .items(Arrays.asList(HolidayCalendar.values()));

        final MaterialDialog.Builder USDialog = new MaterialDialog.Builder(getContext())
                .title(R.string.selectstate)
                .content(R.string.state)
                .inputType(InputType.TYPE_CLASS_TEXT)
                .input("", "", new MaterialDialog.InputCallback() {
                    @Override
                    public void onInput(MaterialDialog dialog, CharSequence input) {

                    }
                });

        final MaterialDialog.Builder selectedHolidays = new MaterialDialog.Builder(getContext())
                .title(R.string.holidays)
                .content(R.string.holidaysfound)
                .positiveText(R.string.ok)
                .onAny(new MaterialDialog.SingleButtonCallback() {
                    @Override
                    public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                      try {
                          HolidayCalendar holidayCalendar = HolidayCalendar.valueOf(selectedCountryString);
                          widget.setHolidaySettings(new HolidaySettings(holidayCalendar, args));
                          widget.update();
                          AdhanBroadcastReceiver.startAdhanSetup(getContext());
                      } catch(Exception e){
                          e.printStackTrace();
                          Toast.makeText(getContext(), getText(R.string.couldntsaveholidays), Toast.LENGTH_LONG).show();
                      }
                    }
                });

        MaterialDialog.ListCallback countryCallback = new MaterialDialog.ListCallback() {
            @Override
            public void onSelection(MaterialDialog dialog, View view, int which, CharSequence text) {
                if (HolidayCalendar.UNITED_STATES.equals(HolidayCalendar.values()[which])){
                    USDialog.show();
                } else {
                    StringBuilder b = new StringBuilder();
                    SimpleDateFormat f = new SimpleDateFormat("EEE, d MMM yyyy");
                    List<Holiday> holidays = new ArrayList<>(HolidayManager.getInstance(HolidayCalendar.values()[which]).getHolidays(Calendar.getInstance().get(Calendar.YEAR)));
                    Collections.sort(holidays, new Comparator<Holiday>() {
                        @Override
                        public int compare(Holiday o1, Holiday o2) {
                            if (o1.getDate().toDate().after(o2.getDate().toDate()))
                                return 1;
                            return -1;
                        }
                    });
                    for (Holiday holiday : holidays) {
                        b.append(f.format(holiday.getDate().toDate()) +" : "+holiday.getDescription()+"\n");
                    }
                    selectedCountryString = HolidayCalendar.values()[which].name();
                    selectedHolidays.content(getString(R.string.holidaysfound)+":\n"+b.toString());
                    selectedHolidays.show();
                }
            }
        };
        countryDialog.itemsCallback(countryCallback);

        MaterialDialog.ListCallback USCallBack = new MaterialDialog.ListCallback() {
            @Override
            public void onSelection(MaterialDialog dialog, View view, int which, CharSequence text) {
                Toast.makeText(getContext(), HolidayCalendar.values()[which].toString(), Toast.LENGTH_LONG).show();
            }
        };

        countryDialog.show();

    }

    @OnClick({R.id.adhansettingFajr, R.id.adhansettingDhuhr, R.id.adhansettingAsr, R.id.adhansettingMaghrib, R.id.adhansettingIsha})
    public void onclick(View view){
        boolean isholidaytab = navigation.getSelectedItemId() !=  R.id.navigation_home;
        switch (((CheckBox)view).getId()){
            case R.id.adhansettingFajr:
                setAdhanForSalah(((CheckBox)view).isChecked(), PrayerType.Fajr, isholidaytab);
                break;
            case R.id.adhansettingDhuhr:
                setAdhanForSalah(((CheckBox)view).isChecked(), PrayerType.Dhuhr, isholidaytab);
                break;
            case R.id.adhansettingAsr:
                setAdhanForSalah(((CheckBox)view).isChecked(), PrayerType.Asr, isholidaytab);
                break;
            case R.id.adhansettingMaghrib:
                setAdhanForSalah(((CheckBox)view).isChecked(), PrayerType.Maghrib, isholidaytab);
                break;
            case R.id.adhansettingIsha:
                setAdhanForSalah(((CheckBox)view).isChecked(), PrayerType.Isha, isholidaytab);
                break;
        }
        System.out.println("");
    }

    public void updateCheckboxes(boolean holidays){
//        abdulg.widget.salahny.Model.Prayer.AdhanSettings adhanSettings = widget.getAdhanSettings();
//        fajr.setChecked(holidays ? adhanSettings.isFajrHoliday() : adhanSettings.isFajr());
//        dhuhr.setChecked(holidays ? adhanSettings.isDhuhrHoliday() : adhanSettings.isDhuhr());
//        asr.setChecked(holidays ? adhanSettings.isAsrHoliday() : adhanSettings.isAsr());
//        maghrib.setChecked(holidays ? adhanSettings.isMaghribHoliday() : adhanSettings.isMaghrib());
//        isha.setChecked(holidays ? adhanSettings.isIshaHoliday() : adhanSettings.isIsha());
    }

    public void setAdhanForSalah(boolean enabled, PrayerType salah, boolean holiday){
//        abdulg.widget.salahny.Model.Prayer.AdhanSettings adhanSettings = widget.getAdhanSettings();
//        switch (salah) {
//            case Fajr:
//                if (holiday) adhanSettings.setFajrHoliday(enabled); else adhanSettings.setFajr(enabled);
//                break;
//            case Dhuhr:
//                if (holiday) adhanSettings.setDhuhrHoliday(enabled); else adhanSettings.setDhuhr(enabled);
//                break;
//            case Asr:
//                if (holiday) adhanSettings.setAsrHoliday(enabled); else adhanSettings.setAsr(enabled);
//                break;
//            case Maghrib:
//                if (holiday) adhanSettings.setMaghribHoliday(enabled); else adhanSettings.setMaghrib(enabled);
//                break;
//            case Isha:
//                if (holiday) adhanSettings.setIshaHoliday(enabled); else adhanSettings.setIsha(enabled);
//                break;
//        }
//        widget.update();
//        AdhanBroadcastReceiver.startAdhanSetup(getContext());
    }

}
