package ng.max.binger.receivers

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.widget.Toast
import ng.max.binger.services.SyncService

class BootReceiver : BroadcastReceiver() {

    override fun onReceive(context: Context, intent: Intent) {
        // This method is called when the BroadcastReceiver is receiving an Intent broadcast.
        val intent1 = Intent(context, SyncService::class.java)
        context.startService(intent1)
        Toast.makeText(context, "Service started", Toast.LENGTH_SHORT).show()
    }
}
