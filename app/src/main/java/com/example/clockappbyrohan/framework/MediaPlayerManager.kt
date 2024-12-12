package com.example.clockappbyrohan.framework

import android.content.Context
import android.media.MediaPlayer
import android.media.RingtoneManager
import kotlinx.coroutines.delay
import javax.inject.Inject
import javax.inject.Singleton
@Singleton
class MediaPlayerManager @Inject constructor(private var context: Context) {

    private var mediaPlayer: MediaPlayer? = null

    // Play the alarm sound
    suspend fun play() {
        println("play")
        // If there's an existing mediaPlayer, reset it before reusing
        mediaPlayer?.let {
            if (it.isPlaying) {
                it.stop()
            }
            it.release()
        }
        delay(200)
        println("let to pass kr gya , abb dekho kya hota ha ")

        // Create a new mediaPlayer instance or reset the existing one
        mediaPlayer = MediaPlayer.create(context, RingtoneManager.getDefaultUri(RingtoneManager.TYPE_ALARM))
        delay(100)
        if(mediaPlayer==null){
            println("null ha bc nhi chalega")
        }
        mediaPlayer?.start()
    }

    // Stop the alarm sound
    suspend fun stop() {
        println("stop")
        if(mediaPlayer==null){
            println("null ha bc nhi band hoga")
        }
        mediaPlayer?.apply {
            if (isPlaying) {

                stop()  // Stop the media
            }
            release()  // Release resources
        }
        mediaPlayer = null  // Nullify the mediaPlayer reference
    }
}
