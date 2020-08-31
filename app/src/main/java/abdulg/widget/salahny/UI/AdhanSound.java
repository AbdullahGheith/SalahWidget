package abdulg.widget.salahny.UI;

import abdulg.widget.salahny.Consts.Consts;
import abdulg.widget.salahny.Controllere.MainSalahTimesController;
import abdulg.widget.salahny.Controllere.SharedPreferencesController;
import abdulg.widget.salahny.Misc.Utils;
import abdulg.widget.salahny.Model.Enums.PrayerType;
import abdulg.widget.salahny.R;
import android.app.KeyguardManager;
import android.content.Context;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.PowerManager;
import android.os.PowerManager.WakeLock;
import android.util.Log;
import android.view.WindowManager;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class AdhanSound extends AppCompatActivity {


	@BindView(R.id.tv_salahName)
	TextView salahName;

	MediaPlayer mp = null;

	PrayerType prayerType;

	SharedPreferencesController preferencesController;

	private Boolean forceStopMP = false;

	private WakeLock wakeLock = null;
	AudioManager audioManager;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		wakeDevice();
		setContentView(R.layout.adhansound);
		ButterKnife.bind(this);

		preferencesController = new SharedPreferencesController(getApplicationContext());
		audioManager = (AudioManager)getSystemService(Context.AUDIO_SERVICE);
		Log.e("AdhanSound", "onCreate");

		String salahName = getIntent().getStringExtra(Consts.ADHANNAME);
		if (salahName != null && !salahName.isEmpty()){
			prayerType = PrayerType.valueOf(salahName);
			this.salahName.setText(Utils.getString(salahName, salahName, getApplicationContext()));
			new Handler().postDelayed(() -> playAdhan(), 3000);

		}
	}

	int flags = WindowManager.LayoutParams.FLAG_DISMISS_KEYGUARD | WindowManager.LayoutParams.FLAG_SHOW_WHEN_LOCKED | WindowManager.LayoutParams.FLAG_TURN_SCREEN_ON | WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON;
	public void wakeDevice(){

		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O_MR1) {
			setShowWhenLocked(true);
			setTurnScreenOn(true);
			KeyguardManager keyguardManager = (KeyguardManager) getSystemService(Context.KEYGUARD_SERVICE);
			keyguardManager.requestDismissKeyguard(this, null);
		} else {
			getWindow().addFlags(flags);
			PowerManager power = (PowerManager) getSystemService(POWER_SERVICE);
			if (wakeLock == null)
				wakeLock = power.newWakeLock(PowerManager.FULL_WAKE_LOCK | PowerManager.ACQUIRE_CAUSES_WAKEUP | PowerManager.ON_AFTER_RELEASE, getClass().getName());

			if (!wakeLock.isHeld())
				wakeLock.acquire();
		}
	}

	private int previousMediaVolume = 0;
	private boolean currentAltered = false;
	public void setVolumeToAdhanPref(){
		if (!currentAltered){
			previousMediaVolume = audioManager.getStreamVolume(AudioManager.STREAM_MUSIC);
			int max =	audioManager.getStreamMaxVolume(AudioManager.STREAM_MUSIC);
			int newVolume = (int)(((double)max/100)*preferencesController.getAdhanVolume());
			audioManager.setStreamVolume(AudioManager.STREAM_MUSIC, newVolume, 0);
			currentAltered = true;
		}
	}

	public void setVolumeToPreviousValue(){
		if (currentAltered){
			audioManager.setStreamVolume(AudioManager.STREAM_MUSIC, previousMediaVolume, 0);
			currentAltered = false;
		}
	}

	boolean prepared = false;
	public void playAdhan(){
		if (mp == null) {
			mp = MediaPlayer.create(this, R.raw.adhan);
			mp.setOnCompletionListener(mp ->
			{
				mp.stop();
				finish();
			});
			setVolumeToAdhanPref();
		}
		if (prepared){
			if (!mp.isPlaying())
				mp.start();
		} else {
			mp.setOnPreparedListener(mp -> {
				if (!forceStopMP)
					mp.start();
				prepared = true;
			});
		}
	}

	public void stopAdhan(){
		forceStopMP = true;
		if (mp != null && mp.isPlaying())
			mp.stop();
	}

	@Override
	protected void onResume() {
		super.onResume();
		Log.e("AdhanSound", "onResume");
		//setVolumeToAdhanPref();
		//playAdhan();

	}

	//Der er noget galt med android 6.0? (testet på BLU R1 HD), som gør at onpause, ondestroy, onresume kaldes når vi har fået wakelock. Derfor tjekker vi på om prepared er færdig samt derfor
	//der er kommet forsinkelse på playadhan. Teorien er at fordi devicen er langsom til at vågne, så bliver alle de metoder kaldt flere gange.
	@Override
	protected void onPause() {
		super.onPause();
		if (prepared)
			activityDone();
		Log.e("AdhanSound", "onPause");
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
		if (prepared) {
			activityDone();
			getWindow().clearFlags(flags);
		}
		Log.e("AdhanSound", "onDestroy");
	}

	@Override
	protected void onStop() {
		super.onStop();
		if (prepared) {
			activityDone();
		}
		Log.e("AdhanSound", "onStop");
	}

	private void activityDone() {
		stopAdhan();
		setVolumeToPreviousValue();
		if (wakeLock != null && wakeLock.isHeld())
			wakeLock.release();
		finish();
	}

	@OnClick(R.id.btn_snooze45)
	public void snooze45(){
		snooze(2700);
	}

	@OnClick(R.id.btn_snooze30)
	public void snooze30(){
		snooze(1800);
	}

	@OnClick(R.id.btn_snooze15)
	public void snooze15(){
		snooze(900);
	}

	@OnClick(R.id.stopbutton)
	public void stopButton(){
		activityDone();
	}

	public void snooze(int seconds){
		new MainSalahTimesController(getApplicationContext()).startAdhanSnooze(prayerType, seconds);
		finish();
	}

}
