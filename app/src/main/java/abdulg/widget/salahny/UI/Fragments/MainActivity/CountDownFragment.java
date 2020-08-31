package abdulg.widget.salahny.UI.Fragments.MainActivity;

import abdulg.widget.salahny.Controllere.MainSalahTimesController;
import abdulg.widget.salahny.Misc.Utils;
import abdulg.widget.salahny.Model.Prayer.PrayerPack;
import abdulg.widget.salahny.Model.Prayer.SinglePrayer;
import abdulg.widget.salahny.Model.View.WidgetSettingsModel;
import abdulg.widget.salahny.R;
import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;
import butterknife.BindView;
import butterknife.ButterKnife;
import com.pnikosis.materialishprogress.ProgressWheel;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.TimeZone;
import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by Abdullah Gheith on 15/05/2018.
 */
public class CountDownFragment extends Fragment {

    private static boolean isTimerRunning;
    WidgetSettingsModel widget;
    MainSalahTimesController controller;
    SimpleDateFormat format = new SimpleDateFormat("HH:mm:ss");

    private static Timer timer = new Timer();

    private String nextSalah;
    private Calendar nextSalahCalendar;

    MainActivityNavigation navigation;
    @BindView(R.id.countdown_text)
    TextView text;

    @BindView(R.id.countdown_time)
    TextView time;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_countdown, container, false);
        ButterKnife.bind(this, view);

        determineNextSalah();

        startTimer();
        return view;
    }

    private void determineNextSalah(){
        PrayerPack todaysPrayerPack = controller.getTodaysPrayerPack(widget.getWidgetId());
        SinglePrayer prayer = Utils.WhatPrayerIsNext(todaysPrayerPack, getContext());
        nextSalah = prayer.getName();
        text.setText(nextSalah+" "+getText(R.string.willstartin));
        nextSalahCalendar = prayer.getTime();
    }

    private Handler mHandler = new Handler() {
        public void handleMessage(Message msg) {
            try {
                time.setText(getTimeRemaining());
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    };

    private void stopTimer(){
        if (isTimerRunning) {
            isTimerRunning = false;
            timer.cancel();
            timer.purge();
        }
    }

    private void startTimer() {
        if (!isTimerRunning){

            isTimerRunning = true;
            timer = new Timer();
            timer.scheduleAtFixedRate(new TimerTask() {
                public void run() {
                    mHandler.obtainMessage(1).sendToTarget();
                }
            }, 0, 1000);
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        stopTimer();
    }

    @Override
    public void onPause() {
        super.onPause();
        stopTimer();
    }

    @Override
    public void onResume() {
        super.onResume();
        startTimer();
    }

    public String getTimeRemaining() throws Exception {
        long timedifference = nextSalahCalendar.getTimeInMillis() - (Calendar.getInstance().getTimeInMillis() + TimeZone.getDefault().getDSTSavings());
        if (timedifference <= 1)
            determineNextSalah();
        return format.format(timedifference);
    }


    public WidgetSettingsModel getWidget() {
        return widget;
    }

    public void setWidget(WidgetSettingsModel widget) {
        this.widget = widget;
    }

    public MainSalahTimesController getController() {
        return controller;
    }

    public void setController(MainSalahTimesController controller) {
        this.controller = controller;
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
