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
import dagger.hilt.android.qualifiers.ApplicationContext
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
class AlarmReceiver @Inject constructor(
    @ApplicationContext private val context: Context,
    private val mediaPlayer: MediaPlayer,
    private val notificationManager: NotificationManager,
    private val alarmDbDAO: AlarmDbDAO,
    private val alarmSchedulerInterface: alarmSchedulerInterface
): BroadcastReceiver() {



    /**
     * init block is used to create a notification channel.
     */
    init {
        createNotificationChannel(context)
    }

    /**
     * onReceive() fun is called when the alarm is triggered.
     * we want to do mainly two things when the alarm is triggered.
     * first :- we want to show a notification to the user that the alarm is triggered. It should have a stop button that immediately stops the alarm.
     * second :- we want to make a sound on full volume. If the user stops this sound should stop
     * @param p0 : give the context
     * @param p1 : give the intent which will contain details of the alarm i.e. name of it.
     * we are checking if the noti. channel exists , if not then we are creating a noti. channel
     */
    override fun onReceive(p0: Context?, p1: Intent?) {
        try{
            val alarmName = p1?.getStringExtra("alarmName")
            val alarmId = p1?.getIntExtra("alarmId",0)

            mediaPlayer.start()

            // deleting all the alarms that are older than 2 minutes
            CoroutineScope(Dispatchers.IO).launch {
                alarmDbDAO.deleteOldAlarms(System.currentTimeMillis()-120000)  // for older than 2 minutes
            }

            val stopIntent = Intent(context,AlarmNotificationReceiver::class.java).putExtra("action","stop")
            val stopPendingIntent = alarmId?.let {
                PendingIntent.getBroadcast(
                    context,
                    it,
                    stopIntent,
                    PendingIntent.FLAG_IMMUTABLE)
            }
            val notification = NotificationCompat.Builder(context, CHANNEL_ID).
                    setContentTitle("$alarmName Alarm").
                    setContentText("Your alarm is triggered").
                    setSmallIcon(R.drawable.ic_launcher_foreground).
                    setPriority(NotificationCompat.PRIORITY_DEFAULT).
                    addAction(
                        R.drawable.baseline_stop_circle_24,
                        "Stop",
                        stopPendingIntent
                    ).setAutoCancel(true).build()
            alarmId?.let { notificationManager.notify(alarmId,notification) } // only works if the alarmId is not null

            /**
             * we want to make a sound till 60 seconds or till user cancel the alarm from notification.
             * so we will make sure that after 60 seconds the alarm will stop playing automatically.
             */
            CoroutineScope(Dispatchers.IO).launch {
                delay(60000)
                withContext(Dispatchers.Main){
                    try{
                        mediaPlayer.let{
                            if(it.isPlaying){
                                it.stop()
                                it.release()
                            }
                        }
                        /**
                         * we also want to add a alarm for the next week so updating the alarm for next week
                         */
                        val oldAlarm = async { alarmId?.let { it1 -> alarmDbDAO.getAlarmById(it1).firstOrNull() } }.await()
                        val newAlarm = oldAlarm?.let { Alarms(oldAlarm.id,oldAlarm.name,oldAlarm.timeInMs+604800000)} // to get the next week
                        if (newAlarm != null) {
                            alarmSchedulerInterface.schedule(newAlarm)
                        }

                    }catch (e:Exception){
                        e.printStackTrace()
                    }
                }
            }

        }catch (e : Exception){
            e.printStackTrace()
            Toast.makeText(context,"Error occurred",Toast.LENGTH_SHORT).show()
        }

    }

    /**
     * This fun creates a notification channel if it is not created.
     */
    private fun createNotificationChannel(context:Context) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val name = "ClockAppNotificationChannel"
            val descriptionText = "ClockAppNotificationChannel"
            val importance = NotificationManager.IMPORTANCE_DEFAULT
            val channel = NotificationChannel(CHANNEL_ID, name, importance).apply {   // channel id from Data.Constants
                description = descriptionText
            }
            val existingChannel = notificationManager.getNotificationChannel(CHANNEL_ID)
            if (existingChannel == null) {
                notificationManager.createNotificationChannel(channel)
            }
            channel.enableVibration(true)
            channel.vibrationPattern = longArrayOf(100,200,300,400,500)

        }

    }
}