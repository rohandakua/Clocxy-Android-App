package com.example.clockappbyrohan.data.offline.alarm

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.media.MediaPlayer
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

/**
 * This class is used to receive the notification from the alarm manager when the alarm is triggered and AlarmReceiver class is used to show the notification.
 * alarmTriggered -> alarmReceiver receives it -> alarmReceiver shows the notification and plays the sound -> when notification's button is clicked -> this class receives it -> stops the alarm
 */
class AlarmNotificationReceiver @Inject constructor(
    @ApplicationContext private val context: Context,
    private val mediaPlayer: MediaPlayer
) :BroadcastReceiver() {

    /**
     * onReceive() fun is called when the user clicks the stop button on the notification.
     * it should stop the sound.
     */
    override fun onReceive(p0: Context?, p1: Intent?) {
        try {
            p1?.let {
                val action = it.getStringExtra("action")
                if(action=="stop"){
                    mediaPlayer.let {
                        if(it.isPlaying){
                            it.stop()
                            it.release()
                        }
                    }
                }
            }
        }catch (e : Exception){
            e.printStackTrace()
        }
    }
}