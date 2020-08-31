package abdulg.widget.salahny.UI;

import android.app.Activity;
import android.appwidget.AppWidgetManager;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import com.shawnlin.numberpicker.NumberPicker;

import abdulg.widget.salahny.Controllere.MainSalahTimesController;
import abdulg.widget.salahny.Model.Prayer.PrayerPack;
import abdulg.widget.salahny.Providers.SalahWidgetProvider;
import abdulg.widget.salahny.R;

/**
 * Created by ag on 14-06-2017.
 */

public class WhenAddedUI_depracted extends AppCompatActivity {

    MainSalahTimesController mainSalahTimesController;

    TextView fajr;
    TextView shuruk;
    TextView dhuhr;
    TextView asr;
    TextView maghrib;
    TextView isha;
    NumberPicker minutesNP;
    CheckBox hanafiCB;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.whenadded_depracted);
        setTitle(getText(R.string.app_name));
        TextView headline = (TextView) findViewById(R.id.headline);
        headline.setText(getText(R.string.configure_widget));

        fajr = (TextView) findViewById(R.id.WhenAdded_FajrPreview);
        shuruk = (TextView) findViewById(R.id.WhenAdded_ShurukPreview);
        dhuhr = (TextView) findViewById(R.id.WhenAdded_DhuhrPreview);
        asr = (TextView) findViewById(R.id.WhenAdded_AsrPreview);
        maghrib = (TextView) findViewById(R.id.WhenAdded_MagribPreview);
        isha = (TextView) findViewById(R.id.WhenAdded_IshaPreview);
        minutesNP = (NumberPicker) findViewById(R.id.salahboxtimedifferencenp);
        hanafiCB = (CheckBox) findViewById(R.id.WhenAdded_HanafiCB);

        mainSalahTimesController = new MainSalahTimesController(getApplicationContext());
        final int appWidgetId = getIntent().getExtras().getInt(AppWidgetManager.EXTRA_APPWIDGET_ID);
        if (appWidgetId <= 0){
            Toast.makeText(getApplicationContext(), "Error", Toast.LENGTH_LONG);
            setResult(Activity.RESULT_CANCELED);
            finish();
        }
        Button save = (Button) findViewById(R.id.WhenAddedSaveButton);
        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               // mainSalahTimesController.initializeWidget(appWidgetId, minutesNP.getValue(), hanafiCB.isEnabled());
                setResult(Activity.RESULT_OK);
                updateWidget(appWidgetId);
                finish();
            }
        });
        Button cancel = (Button) findViewById(R.id.WhenAddedCancelButton);
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setResult(Activity.RESULT_CANCELED);
                finish();
            }
        });

        minutesNP.setOnValueChangedListener(new NumberPicker.OnValueChangeListener() {
            @Override
            public void onValueChange(NumberPicker picker, int oldVal, int newVal) {
                updatePreviewSalahTimes();
            }
        });

        CheckBox hanafiCB = (CheckBox) findViewById(R.id.WhenAdded_HanafiCB);
     //   ImageButton countdown = (ImageButton) findViewById(R.id.WhenAdded_CountDownButton);
     //   ImageButton countup = (ImageButton) findViewById(R.id.WhenAdded_CountUpButton);

        View.OnClickListener updateSalahTimesListener = new View.OnClickListener() {@Override public void onClick(View v) {updatePreviewSalahTimes(); }};

        hanafiCB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                updatePreviewSalahTimes();
            }
        });
//        countdown.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                minutesTV.setText(Integer.valueOf(getIntegerFromTextView(minutesTV)-1).toString());
//                updatePreviewSalahTimes();
//            }
//        });
//        countup.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                minutesTV.setText(Integer.valueOf(getIntegerFromTextView(minutesTV)+1).toString());
//                updatePreviewSalahTimes();
//            }
//        });

        updatePreviewSalahTimes();
    }

    private void updatePreviewSalahTimes(){

        Boolean hanafi = hanafiCB.isChecked();
        Integer minutes = minutesNP.getValue();


        PrayerPack previewTodaysPrayerTimes = mainSalahTimesController.getPreviewTodaysPrayerTimes(hanafi, minutes);

        fajr.setText(previewTodaysPrayerTimes.getFajrInHHmm());
        shuruk.setText(previewTodaysPrayerTimes.getShurukInHHmm());
        dhuhr.setText(previewTodaysPrayerTimes.getDhuhrInHHmm());
        asr.setText(previewTodaysPrayerTimes.getAsrInHHmm());
        maghrib.setText(previewTodaysPrayerTimes.getMaghribInHHmm());
        isha.setText(previewTodaysPrayerTimes.getIshaInHHmm());
    }

    private void updateWidget(int widgetId){
        try {
            SalahWidgetProvider.onUpdate(getApplicationContext(), AppWidgetManager.getInstance(this), new int[]{widgetId});
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
