package abdulg.widget.salahny.Providers;

import abdulg.widget.salahny.Consts.Consts;
import abdulg.widget.salahny.Controllere.MainSalahTimesController;
import abdulg.widget.salahny.Controllere.SharedPreferencesController;
import abdulg.widget.salahny.Model.Enums.PrayerType;
import abdulg.widget.salahny.Model.Prayer.PrayerPack;
import abdulg.widget.salahny.Model.View.WidgetSettingsModel;
import abdulg.widget.salahny.R;
import android.app.AlarmManager;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Build;
import android.os.Build.VERSION_CODES;
import android.os.SystemClock;
import android.widget.Toast;

import androidx.core.app.NotificationCompat;
import androidx.core.app.TaskStackBuilder;
import java.util.Calendar;

/**
 * Created by Abdullah Gheith on 28/05/2018.
 */
public class AdhanBroadcastReceiver extends BroadcastReceiver {

	private AlarmManager alarmMgr;
	private boolean testcase = false;

	@Override
	public void onReceive(Context context, Intent intent) {
//		if (intent == null) {
//		} else{
//			showNotification(context, MainActivity.class, "Tid til salah", intent.getStringExtra(Consts.ADHANNAME));
//			Log.e("SALAHADHAN", "SALAAAAAAAAAHHHHHHHH");
//		}
			MainSalahTimesController controller = new MainSalahTimesController(context);
			WidgetSettingsModel primaryWidget = controller.getPrimaryWidget();
			if (testcase) {
				setTestAdhans(context);
			} else {
				if (primaryWidget != null) {
					//if (controller.getIsHolidayOrWeekend(primaryWidget)) {
					//	setAdhansForHolidaysOrWeekend(controller, primaryWidget, context);
					//} else {
					setAdhansForRegularDays(controller, primaryWidget, context);
					//}
				}
				//rescheduleNextTime(context);
			}


	}

	public static void startAdhanSetup(Context context) {
		new AdhanBroadcastReceiver().onReceive(context, null);
	}

	public static void startTestAdhan(Context context){
		new AdhanBroadcastReceiver().setTestAdhans(context);
	}

	public static void startAdhanSnooze(Context context, PrayerType prayerType, int seconds){
		new AdhanBroadcastReceiver().setAdhanSnooze(context, prayerType, seconds);
	}

	private void setAdhanSnooze(Context context, PrayerType prayerType, int seconds){
		Calendar instance = Calendar.getInstance();
		instance.set(Calendar.SECOND, instance.get(Calendar.SECOND) + seconds);
		setAdhan(instance, prayerType, context, true);
	}

	private void setTestAdhans(Context context) {
		Toast.makeText(context, R.string.starting_test_adhan, Toast.LENGTH_SHORT).show();
		setAdhanSnooze(context, PrayerType.Fajr, 10);
	}

	private void rescheduleNextTime(Context context) {
		Calendar calendar = Calendar.getInstance();

		if (testcase) {
			calendar.set(Calendar.SECOND, calendar.get(Calendar.SECOND) + 40);
			Toast.makeText(context, "TESTCASE: setting AdhanSetterService to reschedule alarms in +40 sec", Toast.LENGTH_SHORT).show();
		} else {
			calendar.set(Calendar.DATE, calendar.get(Calendar.DATE) + 1);
		}

		Intent intent = new Intent(context, AdhanBroadcastReceiver.class);
		PendingIntent alarmIntent = PendingIntent.getBroadcast(context, 999, intent, 0);
		getAlarmMgr(context).set(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), alarmIntent);
	}

	private AlarmManager getAlarmMgr(Context context) {
		if (alarmMgr == null) {
			alarmMgr = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
		}
		return alarmMgr;
	}

	private void setAdhansForRegularDays(MainSalahTimesController controller, WidgetSettingsModel primaryWidget, Context context) {
		Calendar now = Calendar.getInstance();
		PrayerPack todaysPrayerPack = controller.getTodaysPrayerPack(primaryWidget.getWidgetId());
		Calendar calendar = Calendar.getInstance();
		calendar.set(Calendar.DATE, calendar.get(Calendar.DATE) + 1);
		calendar.set(Calendar.HOUR_OF_DAY, 0);
		calendar.set(Calendar.MINUTE, 10);
		PrayerPack tomorrowsPrayerPack = controller.getPrayerPackForDate(primaryWidget.getWidgetId(), calendar);

		cancelAdhan(PrayerType.Fajr, context);
		cancelAdhan(PrayerType.Dhuhr, context);
		cancelAdhan(PrayerType.Asr, context);
		cancelAdhan(PrayerType.Maghrib, context);
		cancelAdhan(PrayerType.Isha, context);

		SharedPreferencesController prefs = new SharedPreferencesController(context);
		Calendar time;
		if (prefs.getAdhanFajr()) {
			if (todaysPrayerPack.getFajr().after(now)) {
				time = todaysPrayerPack.getFajr();
			} else {
				time = tomorrowsPrayerPack.getFajr();
			}
			setAdhan(time, PrayerType.Fajr, context);
		}
		if (prefs.getAdhanDhuhr()) {
			if (todaysPrayerPack.getDhuhr().after(now)) {
				time = todaysPrayerPack.getDhuhr();
			} else {
				time = tomorrowsPrayerPack.getDhuhr();
			}
			setAdhan(time, PrayerType.Dhuhr, context);
		}
		if (prefs.getAdhanAsr()) {
			if (todaysPrayerPack.getAsr().after(now)) {
				time = todaysPrayerPack.getAsr();
			} else {
				time = tomorrowsPrayerPack.getAsr();
			}
			setAdhan(time, PrayerType.Asr, context);
		}
		if (prefs.getAdhanMaghrib()) {
			if (todaysPrayerPack.getMaghrib().after(now)) {
				time = todaysPrayerPack.getMaghrib();
			} else {
				time = tomorrowsPrayerPack.getMaghrib();
			}
			setAdhan(time, PrayerType.Maghrib, context);
		}
		if (prefs.getAdhanIsha()) {
			if (todaysPrayerPack.getIsha().after(now)) {
				time = todaysPrayerPack.getIsha();
			} else {
				time = tomorrowsPrayerPack.getIsha();
			}
			setAdhan(time, PrayerType.Isha, context);
		}
	}

	private void setAdhansForHolidaysOrWeekend(MainSalahTimesController controller, WidgetSettingsModel primaryWidget, Context context) {
//		PrayerPack todaysPrayerPack = controller.getTodaysPrayerPack(primaryWidget.getWidgetId());
//		AdhanSettings adhanSettings = primaryWidget.getAdhanSettings();
//		if (adhanSettings.isFajrHoliday()) {
//			setAdhan(todaysPrayerPack.getFajr(), PrayerType.Fajr, context);
//		}
//		if (adhanSettings.isDhuhrHoliday()) {
//			setAdhan(todaysPrayerPack.getDhuhr(), PrayerType.Dhuhr, context);
//		}
//		if (adhanSettings.isAsrHoliday()) {
//			setAdhan(todaysPrayerPack.getAsr(), PrayerType.Asr, context);
//		}
//		if (adhanSettings.isMaghribHoliday()) {
//			setAdhan(todaysPrayerPack.getMaghrib(), PrayerType.Maghrib, context);
//		}
//		if (adhanSettings.isIshaHoliday()) {
//			setAdhan(todaysPrayerPack.getIsha(), PrayerType.Isha, context);
//		}
	}

	private void setAdhan(Calendar prayerTime, PrayerType prayerType, Context context) {
		setAdhan(prayerTime, prayerType, context, false);
	}

	private void setAdhan(Calendar prayerTime, PrayerType prayerType, Context context, Boolean isSingleAdhan) {
		Calendar nowTime = Calendar.getInstance(); //Slower but takes care of timezone for us
		if (prayerTime.before(nowTime)) {
			return;
		}

		long timeTillPrayer = prayerTime.getTimeInMillis() - nowTime.getTimeInMillis();
		System.out.println("Triggering "+prayerType.name()+ " in "+timeTillPrayer/1000/60 + " minutes");

		int requestCode = prayerType.getIndexNo();

		Intent intent = newAdhanIntent(context, prayerType);
		if (isSingleAdhan){
			intent.putExtra(Consts.ADHANTEST, true);
			requestCode = 541;
		}
//		PendingIntent alarmIntent = PendingIntent.getService(context, requestCode, intent, 0); //FLAG_CANCEL_CURRENT = cancels the alarm but the pendingintent keeps living. FLAG_UPDATE_CURRENT = updates the alarms extras only. 0 = reschedules the current alarm with the new time since the pending intent is a duplicate of a previously set alarm or it's a new alarm
		PendingIntent alarmIntent = PendingIntent.getBroadcast(context, requestCode, intent, 0); //FLAG_CANCEL_CURRENT = cancels the alarm but the pendingintent keeps living. FLAG_UPDATE_CURRENT = updates the alarms extras only. 0 = reschedules the current alarm with the new time since the pending intent is a duplicate of a previously set alarm or it's a new alarm
 		if (Build.VERSION.SDK_INT <= Build.VERSION_CODES.KITKAT) { //Before the battery improvements alarms would trigger at the designated time.
			getAlarmMgr(context).set(AlarmManager.ELAPSED_REALTIME_WAKEUP, SystemClock.elapsedRealtime() + timeTillPrayer, alarmIntent);
		} else if (Build.VERSION.SDK_INT <= VERSION_CODES.M) { //in above API 19, android triggers alarms whenever possible unless setExact is used. This is to save battery
			getAlarmMgr(context).setExact(AlarmManager.ELAPSED_REALTIME_WAKEUP, SystemClock.elapsedRealtime() + timeTillPrayer, alarmIntent);
		} else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M){
			getAlarmMgr(context).setExactAndAllowWhileIdle(AlarmManager.ELAPSED_REALTIME_WAKEUP, SystemClock.elapsedRealtime() + timeTillPrayer, alarmIntent);
		}
	}

	private void cancelAdhan(PrayerType prayerType, Context context) {
		//not needed because we set the alarms with FLAG_UPDATE_CURRENT
//		PendingIntent pendingIntent = PendingIntent.getBroadcast(context, prayerType.getIndexNo(), newAdhanIntent(context, prayerType), 0);
//		getAlarmMgr(context).cancel(pendingIntent);
	}

	private Intent newAdhanIntent(Context context, PrayerType prayerType) {
		Intent intent = new Intent(context, AdhanTriggeredBroadcastReceiver.class);
		intent.putExtra(Consts.ADHANNAME, prayerType.name());
		return intent;
	}

	public static void showNotification(Context context,Class<?> cls,String title,String content)
	{
		Uri alarmSound = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);

		Intent notificationIntent = new Intent(context, cls);
		notificationIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

		TaskStackBuilder stackBuilder = TaskStackBuilder.create(context);
		stackBuilder.addParentStack(cls);
		stackBuilder.addNextIntent(notificationIntent);

		PendingIntent pendingIntent = stackBuilder.getPendingIntent(
				44,PendingIntent.FLAG_UPDATE_CURRENT);

		NotificationCompat.Builder builder = new NotificationCompat.Builder(context);
		Notification notification = builder.setContentTitle(title)
				.setContentText(content).setAutoCancel(true)
				.setSound(alarmSound).setSmallIcon(R.mipmap.ic_launcher_round)
				.setContentIntent(pendingIntent).build();

		NotificationManager notificationManager = (NotificationManager)
				context.getSystemService(Context.NOTIFICATION_SERVICE);
		notificationManager.notify(44, notification);
	}
}
