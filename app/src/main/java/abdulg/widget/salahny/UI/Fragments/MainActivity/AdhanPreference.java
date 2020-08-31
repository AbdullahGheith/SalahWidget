package abdulg.widget.salahny.UI.Fragments.MainActivity;


import abdulg.widget.salahny.Controllere.MainSalahTimesController;
import abdulg.widget.salahny.Model.Enums.PrayerType;
import abdulg.widget.salahny.Providers.AdhanBroadcastReceiver;
import abdulg.widget.salahny.R;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.OnSharedPreferenceChangeListener;
import android.os.Build;
import android.os.Build.VERSION_CODES;
import android.os.Bundle;
import android.os.SystemClock;
import android.widget.Toast;
import androidx.preference.Preference;
import androidx.preference.Preference.OnPreferenceClickListener;
import androidx.preference.PreferenceFragmentCompat;
import java.util.Calendar;

public class AdhanPreference extends PreferenceFragmentCompat implements OnSharedPreferenceChangeListener {

	MainSalahTimesController controller;
	@Override
	public void onCreatePreferences(Bundle savedInstanceState, String rootKey) {
		addPreferencesFromResource(R.xml.adhan_preferences);
		controller = new MainSalahTimesController(getContext());
		Preference adhantest = (Preference) findPreference("pref_test_adhan");
		adhantest.setOnPreferenceClickListener(new OnPreferenceClickListener() {
			@Override
			public boolean onPreferenceClick(Preference preference) {
				controller.startTestAdhan();
				return true;
			}
		});
	}

	@Override
	public void onResume() {
		super.onResume();
		getPreferenceManager().getSharedPreferences().registerOnSharedPreferenceChangeListener(this);

	}

	@Override
	public void onPause() {
		getPreferenceManager().getSharedPreferences().unregisterOnSharedPreferenceChangeListener(this);
		super.onPause();
	}

	@Override
	public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String key) {
		switch (key){
			case "pref_fajr":
			case "pref_dhuhr":
			case "pref_asr":
			case "pref_maghrib":
			case "pref_isha":
				controller.rescheduleAdhans();
		}
	}
}
