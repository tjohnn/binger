package ng.max.binger.services

import android.app.IntentService
import android.content.Intent
import android.content.Context

/**
 * An [IntentService] subclass for handling asynchronous task requests in
 * a service on a separate handler thread.
 * TODO: Customize class to handle synchronization
 */
class SyncService : IntentService("SyncService") {

    override fun onHandleIntent(intent: Intent?) {
        TODO("Handle action Sync")
    }

}
