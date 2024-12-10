package com.example.clockappbyrohan.framework

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.Intent.ACTION_BOOT_COMPLETED
import com.example.clockappbyrohan.domain.repositoryInterface.alarmSchedulerInterface
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

/**
 * This broadcast receiver is used to receive the broadcast when the device is rebooted.
 * After the device reboots , we need to reschedule all the alarm that are there , bcs all the alarm are cancelled when device reboots.
 */
@AndroidEntryPoint
class RebootBroadCastReceiver :BroadcastReceiver() {
    @Inject lateinit var alarmSchedulerInterface: alarmSchedulerInterface

    override fun onReceive(p0: Context?, p1: Intent?) {
        if(p1!!.action == ACTION_BOOT_COMPLETED || p1!!.action == Intent.ACTION_REBOOT){
            CoroutineScope(Dispatchers.IO).launch {
               alarmSchedulerInterface.rescheduleAllAlarm()
            }
        }
    }
}