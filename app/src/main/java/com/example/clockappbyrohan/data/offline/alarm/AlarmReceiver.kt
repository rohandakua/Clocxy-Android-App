package com.example.clockappbyrohan.data.offline.alarm
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.media.MediaPlayer
import android.os.Build
import android.widget.Toast
import androidx.core.app.NotificationCompat
import com.example.clockappbyrohan.R
import com.example.clockappbyrohan.data.Constants.CHANNEL_ID
import com.example.clockappbyrohan.domain.repositoryInterface.alarmSchedulerInterface
import com.example.clockappbyrohan.framework.MediaPlayerManager
import dagger.hilt.EntryPoint
import dagger.hilt.InstallIn
import dagger.hilt.android.AndroidEntryPoint
import dagger.hilt.android.EntryPointAccessors
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

/**
 * This class is used to receive the notification from the alarm manager when the alarm is triggered and AlarmReceiver class is used to show the notification.
 */
@AndroidEntryPoint
class AlarmReceiver : BroadcastReceiver() {

    private lateinit var mediaPlayer: MediaPlayer
    private lateinit var notificationManager: NotificationManager
    private lateinit var alarmDbDAO: AlarmDbDAO
    private lateinit var alarmSchedulerInterface: alarmSchedulerInterface
    private lateinit var mediaPlayerManager: MediaPlayerManager

    override fun onReceive(context: Context?, intent: Intent?) {
        try {
            // Manually inject dependencies using Hilt
            if (!::mediaPlayerManager.isInitialized) {
                val entryPoint = EntryPointAccessors.fromApplication(
                    context!!.applicationContext,
                    AlarmReceiverEntryPoint::class.java
                )
                mediaPlayer = entryPoint.mediaPlayer()
                notificationManager = entryPoint.notificationManager()
                alarmDbDAO = entryPoint.alarmDbDAO()
                alarmSchedulerInterface = entryPoint.alarmSchedulerInterface()
                mediaPlayerManager = entryPoint.mediaPlayerManager()
            }
            createNotificationChannel(context = context!!.applicationContext)

            val alarmName = intent?.getStringExtra("alarmName")
            val alarmId = intent?.getIntExtra("alarmId", 0)
            CoroutineScope(Dispatchers.Main).launch {
            mediaPlayerManager.play()}

            val stopIntent = Intent(context, AlarmNotificationReceiver::class.java).putExtra("action", "stop").putExtra("alarmId",alarmId)
            val stopPendingIntent = alarmId?.let {
                PendingIntent.getBroadcast(
                    context,
                    it,
                    stopIntent,
                    PendingIntent.FLAG_IMMUTABLE
                )
            }

            val notification = NotificationCompat.Builder(context!!, CHANNEL_ID)
                .setContentTitle("$alarmName Alarm")
                .setContentText("Your alarm is triggered")
                .setSmallIcon(R.drawable.ic_launcher_foreground)
                .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                .addAction(
                    R.drawable.baseline_stop_circle_24,
                    "Stop",
                    stopPendingIntent

                ).setDeleteIntent(stopPendingIntent)
                .build()

            alarmId?.let { notificationManager.notify(it, notification) }

            // after 1 min stop the mediaplayer and schedule the alarm for next week
            CoroutineScope(Dispatchers.IO).launch {
                delay(60000)
                withContext(Dispatchers.Main) {
                    try {
                        mediaPlayerManager.stop()
                        alarmId?.let { notificationManager.cancel(it) }
                        withContext(Dispatchers.IO) {

                            val oldAlarm = async {
                                alarmId?.let { id ->
                                    alarmDbDAO.getAlarmById(id).firstOrNull()
                                }
                            }.await()
                            val newAlarm = oldAlarm?.let {
                                Alarms(
                                    it.id,
                                    it.name,
                                    it.timeInMs + 604800000
                                )
                            }  // for next week
                            newAlarm?.let { alarmSchedulerInterface.schedule(it) }
                        }
                    } catch (e: Exception) {
                        e.printStackTrace()
                    }
                }
            }

        } catch (e: Exception) {
            e.printStackTrace()
            Toast.makeText(context, "Error occurred", Toast.LENGTH_SHORT).show()
        }
    }

    private fun createNotificationChannel(context: Context) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val name = "ClockAppNotificationChannel"
            val descriptionText = "ClockAppNotificationChannel"
            val importance = NotificationManager.IMPORTANCE_DEFAULT
            val channel = NotificationChannel(CHANNEL_ID, name, importance).apply {
                description = descriptionText
            }

            val existingChannel = notificationManager.getNotificationChannel(CHANNEL_ID)
            if (existingChannel == null) {
                notificationManager.createNotificationChannel(channel)
            }
            channel.enableVibration(true)
            channel.vibrationPattern = longArrayOf(100, 200, 300, 400, 500)
        }
    }
}

@EntryPoint
@InstallIn(SingletonComponent::class)
interface AlarmReceiverEntryPoint {
    fun mediaPlayer(): MediaPlayer
    fun notificationManager(): NotificationManager
    fun alarmDbDAO(): AlarmDbDAO
    fun alarmSchedulerInterface(): alarmSchedulerInterface
    fun mediaPlayerManager(): MediaPlayerManager
}
