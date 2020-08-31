package abdulg.widget.salahny.UI.Fragments.MainActivity;

import abdulg.widget.salahny.Controllere.MainSalahTimesController;
import abdulg.widget.salahny.Misc.Utils;
import abdulg.widget.salahny.Model.Prayer.PrayerPack;
import abdulg.widget.salahny.Model.Prayer.SinglePrayer;
import abdulg.widget.salahny.Model.View.WidgetSettingsModel;
import abdulg.widget.salahny.R;
import android.app.DatePickerDialog;
import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import butterknife.BindView;
import butterknife.ButterKnife;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.TimeZone;
import java.util.Timer;
import java.util.TimerTask;

public class MainContentFragment extends Fragment {


	private MainActivityNavigation navigation;
	private MainSalahTimesController controller;
	private WidgetSettingsModel widget;
	private Calendar chosenDate;
	private Boolean butterKnifeBound = false;

	@BindView(R.id.maincontent_salah_fajr_time)
	TextView fajr;
	@BindView(R.id.maincontent_salah_shuruk_time)
	TextView shuruk;
	@BindView(R.id.maincontent_salah_dhuhr_time)
	TextView dhuhr;
	@BindView(R.id.maincontent_salah_asr_time)
	TextView asr;
	@BindView(R.id.maincontent_salah_maghrib_time)
	TextView maghrib;
	@BindView(R.id.maincontent_salah_isha_time)
	TextView isha;

	private static boolean isTimerRunning;
	private static Timer timer = new Timer();
	SimpleDateFormat format = new SimpleDateFormat("HH:mm:ss");

	private String nextSalah;
	private Calendar nextSalahCalendar;

	@BindView(R.id.countdown_text)
	TextView text;

	@BindView(R.id.countdown_time)
	TextView time;

	@BindView(R.id.calendarselection_currentdate)
	TextView currentDateTextView;

	DatePickerDialog.OnDateSetListener date;
	View view;

	@Nullable
	@Override
	public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
		if (view != null)
			return view;

		view = inflater.inflate(R.layout.content_main, container, false);
		ButterKnife.bind(this, view);
		butterKnifeBound = true;
		updatePrayerTimes();
		determineNextSalah();
		startTimer();
		initDatePicker();
		return view;
	}

	public void updatePrayerTimes(){
		PrayerPack prayerPackForDate = controller.getPrayerPackForDate(widget.getWidgetId(), getChosenDate());

		fajr.setText(prayerPackForDate.getFajrInHHmm());
		shuruk.setText(prayerPackForDate.getShurukInHHmm());
		dhuhr.setText(prayerPackForDate.getDhuhrInHHmm());
		asr.setText(prayerPackForDate.getAsrInHHmm());
		maghrib.setText(prayerPackForDate.getMaghribInHHmm());
		isha.setText(prayerPackForDate.getIshaInHHmm());
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

	public Calendar getChosenDate() {
		return chosenDate;
	}

	public void setChosenDate(Calendar chosenDate) {
		this.chosenDate = chosenDate;
		if (butterKnifeBound)
			updatePrayerTimes();
	}

	public void initDatePicker(){
		date = (view, year, monthOfYear, dayOfMonth) -> {
			chosenDate.set(Calendar.YEAR, year);
			chosenDate.set(Calendar.MONTH, monthOfYear);
			chosenDate.set(Calendar.DAY_OF_MONTH, dayOfMonth);
			dateUpdated();
			setChosenDate(chosenDate);
		};

		View.OnClickListener showDatePickerListener = v -> openDatePicker();

		currentDateTextView.setOnClickListener(showDatePickerListener);
	}

	public void openDatePicker(){
		new DatePickerDialog(getContext(), date, chosenDate
				.get(Calendar.YEAR), chosenDate.get(Calendar.MONTH),
				chosenDate.get(Calendar.DAY_OF_MONTH)).show();
	}

	public void dateUpdated(){
		SimpleDateFormat format = new SimpleDateFormat("EEE, d MMM yyyy"); // 15 chars
		currentDateTextView.setText(format.format(chosenDate.getTime()));
		//currentDateTextView.setTextSize(TypedValue.COMPLEX_UNIT_PX, Utils.getScreenWidthInPx(getContext())/12);
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
