package com.clocxy.clocxyone.data.offline.alarm

import android.app.NotificationManager
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import com.clocxy.clocxyone.framework.MediaPlayerManager
import dagger.hilt.EntryPoint
import dagger.hilt.InstallIn
import dagger.hilt.android.AndroidEntryPoint
import dagger.hilt.android.EntryPointAccessors
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

/**
 * This class is used to receive the notification from the alarm manager when the alarm is triggered and AlarmReceiver class is used to show the notification.
 * alarmTriggered -> alarmReceiver receives it -> alarmReceiver shows the notification and plays the sound -> when notification's button is clicked -> this class receives it -> stops the alarm
 */
@AndroidEntryPoint
class AlarmNotificationReceiver : BroadcastReceiver() {

    private lateinit var mediaPlayerManager: MediaPlayerManager

    override fun onReceive(context: Context?, intent: Intent?) {
        try {
            // Manually inject dependencies using Hilt
            if (!::mediaPlayerManager.isInitialized) {
                val entryPoint = EntryPointAccessors.fromApplication(
                    context!!.applicationContext,
                    AlarmNotificationReceiverEntryPoint::class.java
                )
                mediaPlayerManager = entryPoint.mediaPlayerManager()
            }

            intent?.let {
                println("in intent")
                val action = it.getStringExtra("action")
                val alarmId = it.getIntExtra("alarmId",0)
                if (action == "stop") {
                    println("in stop")
                    CoroutineScope(Dispatchers.Main).launch {
                    mediaPlayerManager.stop()}
                    val n = context?.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
                    n.cancel(alarmId)
                }
            }

        } catch (e: Exception) {
            e.printStackTrace()
        }
    }
}

@EntryPoint
@InstallIn(SingletonComponent::class)
interface AlarmNotificationReceiverEntryPoint {
    fun mediaPlayerManager(): MediaPlayerManager
}
