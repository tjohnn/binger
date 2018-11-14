package ng.max.binger.receivers

import android.app.AlarmManager
import android.app.PendingIntent
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import ng.max.binger.services.SyncService

class BootReceiver : BroadcastReceiver() {

    override fun onReceive(context: Context, intent: Intent) {
        val alarmMgr = context.getSystemService(Context.ALARM_SERVICE) as AlarmManager
        //Assert the it's Boot BroadcastReceiver
        if (intent.action == "android.intent.action.BOOT_COMPLETED") {
            val startIntent = Intent(context, SyncService::class.java)
            val pendingIntent = PendingIntent.getService(context, 0, startIntent, 0)

            val dailyInterval = AlarmManager.INTERVAL_DAY

            val triggerTime = System.currentTimeMillis()

            // Configure Alarmmanager to trigger syncing daily
            alarmMgr.setRepeating(AlarmManager.RTC_WAKEUP,
                    triggerTime,
                    dailyInterval, pendingIntent)
        }
    }
}
