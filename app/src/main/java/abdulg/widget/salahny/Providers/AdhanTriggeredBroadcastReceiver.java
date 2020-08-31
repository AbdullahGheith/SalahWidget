package abdulg.widget.salahny.Providers;

import abdulg.widget.salahny.Consts.Consts;
import abdulg.widget.salahny.Controllere.MainSalahTimesController;
import abdulg.widget.salahny.Controllere.SharedPreferencesController;
import abdulg.widget.salahny.Misc.Utils;
import abdulg.widget.salahny.R;
import abdulg.widget.salahny.UI.AdhanSound;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.VibrationEffect;
import android.os.Vibrator;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

public class AdhanTriggeredBroadcastReceiver extends BroadcastReceiver {

	private static String CHANNEL_ID = "AdhanNotificationChannel21982";

	SharedPreferencesController prefs;


	@Override
	public void onReceive(Context context, Intent intent) {
		if (intent != null && intent.getStringExtra(Consts.ADHANNAME) != null) {
			prefs = new SharedPreferencesController(context);
			if (intent.getBooleanExtra(Consts.ADHANTEST, false)){
				triggerAdhan(context, intent);
			} else {
				triggerAdhan(context, intent);
				rescheduleAlarms(context);
			}
		}
	}

	private void rescheduleAlarms(Context context){
		new MainSalahTimesController(context).rescheduleAdhans();
	}

	private void triggerAdhan(Context context, Intent intent){
		if (prefs.getAdhanNotification())
			createNotification(context, intent);
		if (prefs.getAdhanVibrate())
			vibrate(context, intent);
		if (prefs.getAdhanSound())
			playAdhan(context, intent);
	}

	private void vibrate(Context context, Intent intent){
		Vibrator v = (Vibrator) context.getSystemService(Context.VIBRATOR_SERVICE);
		long[] vibrationPattern = new long[]{0, 250, 250, 250}; // Default android vibration pattern
		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
			v.vibrate(VibrationEffect.createWaveform(vibrationPattern, -1));
		} else {
			v.vibrate(vibrationPattern, -1);
		}
	}

	private void playAdhan(Context context, Intent intent){
		Intent i = new Intent(context, AdhanSound.class);
		i.putExtra(Consts.ADHANNAME, intent.getStringExtra(Consts.ADHANNAME));
		i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
		context.startActivity(i);
	}

	private void createNotification(Context context, Intent intent){
		createNotificationChannel(context);

		NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(context, CHANNEL_ID)
				.setSmallIcon(R.drawable.app_icon)
				.setContentTitle("Salah Widget")
				.setAutoCancel(true)
				.setContentIntent(PendingIntent.getBroadcast(context, 0, new Intent(), 0))
				.setContentText(context.getString(R.string.time_for_salah) + " " + Utils.getString(intent.getStringExtra(Consts.ADHANNAME), intent.getStringExtra(Consts.ADHANNAME), context))
				.setPriority(NotificationCompat.PRIORITY_DEFAULT)
				.setDefaults(NotificationCompat.DEFAULT_SOUND | NotificationCompat.DEFAULT_LIGHTS);
		NotificationManagerCompat notificationManager = NotificationManagerCompat.from(context);
		// notificationId is a unique int for each notification that you must define
		//if (prefs.getAdhanVibrate())
		//	mBuilder.setVibrate(new long[]{0, 250, 250, 250});
		//else
		//	mBuilder.setVibrate(null);

		notificationManager.notify(15, mBuilder.build());
	}

	private void createNotificationChannel(Context context) {
		// Create the NotificationChannel, but only on API 26+ because
		// the NotificationChannel class is new and not in the support library
		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
			CharSequence name = context.getString(R.string.app_name);
			String description = "Adhan Channel";
			int importance = NotificationManager.IMPORTANCE_DEFAULT;
			NotificationChannel channel = new NotificationChannel(CHANNEL_ID, name, importance);
			channel.setVibrationPattern(new long[]{0});
			channel.enableVibration(true);
			channel.setDescription(description);
			// Register the channel with the system; you can't change the importance
			// or other notification behaviors after this

			NotificationManager notificationManager = context.getSystemService(NotificationManager.class);
			notificationManager.createNotificationChannel(channel);
		}
	}
}
