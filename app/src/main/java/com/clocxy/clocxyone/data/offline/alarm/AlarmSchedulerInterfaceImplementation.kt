package com.clocxy.clocxyone.data.offline.alarm

import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.Build
import android.util.Log
import android.widget.Toast
import com.clocxy.clocxyone.domain.Functions.getExactTime
import com.clocxy.clocxyone.domain.Functions.getId
import com.clocxy.clocxyone.domain.dataclass.Event
import com.clocxy.clocxyone.domain.repositoryInterface.alarmSchedulerInterface
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.time.LocalDate
import javax.inject.Inject

class AlarmSchedulerInterfaceImplementation @Inject constructor(
    private val alarmDbDao: AlarmDbDAO,
    private val context: Context,
    private val alarmManager: AlarmManager,
    private val week: Week
) : alarmSchedulerInterface {
    /**
     * schedule() fun is used to schedule the alarm.
     *@param alarmItem : it is the data of the alarm that is to be scheduled.
     * @return Event to notify the user if it is a success or not
     * we are using AlarmManager from hilt to schedule the alarm.
     * if scheduling the alarm is successful then we are returning Event.SUCCESS else Event.FAILURE
     */
    override suspend fun schedule(alarmItem: Alarms): Event {
        val day = LocalDate.now().dayOfWeek.value
        week.setDay(day)
        var count = 0

        do {
            //check for the day
            withContext(Dispatchers.Default) {

                if (week.toSet(week.getDay(), alarmItem)) {
                    // schedule alarm after (timeInMs+count*24*60*60*1000)
                    /**
                     * making a pending intent for the alarm.
                     * the id of the pending intent is set as the id of the alarm.
                     * the AlarmReceiver class is the Broadcast receiver that is to be called when the alarm is triggered.
                     * FLAG_IMMUTABLE is used to make the pending intent immutable.
                     */
                    val intent = Intent(context, AlarmReceiver::class.java).putExtra(
                        "alarmId",
                        getId(alarmItem.id, week.getDay())
                    )
                        .putExtra("alarmName", alarmItem.name)
                    val alarmIntent = PendingIntent.getBroadcast(
                        context,
                        getId(alarmItem.id, week.getDay()),
                        intent,
                        PendingIntent.FLAG_IMMUTABLE

                    )

                    try {
                        if (Build.VERSION.SDK_INT > 30 && !alarmManager.canScheduleExactAlarms()) {
                            CoroutineScope(Dispatchers.Main).launch {
                                Toast.makeText(
                                    context,
                                    "Please allow the app to schedule exact alarms",
                                    Toast.LENGTH_LONG
                                ).show()
                            }
                            return@withContext Event.FAILURE
                        }
                        alarmManager.setAlarmClock(
                            AlarmManager.AlarmClockInfo(
                                getExactTime(
                                    alarmItem.timeInMs,
                                    count
                                ),          // this should be the epoch time in milliseconds
                                alarmIntent
                            ),
                            alarmIntent
                        )


                    } catch (e: Exception) {
                        Log.d(
                            "from alarmSchedulerInterfaceImplementation",
                            "error in scheduling alarm id ${getId(alarmItem.id, week.getDay())}"
                        )
                        return@withContext Event.FAILURE
                    }

                }else{

                }
            }
            count++
            week.incDay() // this increases the day by 1 every time
        } while (day != week.getDay())
        try{
            // insert the alarm to the dataBase
            /**
             * if the alarmId exist in the database then we are updating it else we are inserting it.
             */
            println("alarm id ${alarmItem.id} updating or saving ")
            if (alarmDbDao.getAlarmById(alarmItem.id).firstOrNull() != null) {
                println("in if")
                alarmDbDao.updateAlarm(alarmItem)
                println("alarm updated")
            } else {
                println("in else")
                alarmDbDao.insertAlarm(alarmItem)
                println("alarm inserted")
            }
        }catch (e: Exception){
            Log.d("asii",e.message.toString())
        }
        println("from asii did leave the do while loop")
        return Event.SUCCESS


    }

    /**
     * cancel() fun is used to cancel the alarm.
     * @param alarmItem : it is the data of the alarm that is to be cancelled.
     * @return Event to notify the user if it is a success or not
     */
    override fun cancel(alarmItem: Alarms): Event {
        val day = LocalDate.now().dayOfWeek.value
        week.setDay(day)
        var count = 0;
        do {
            if (week.toSet(week.getDay(), alarmItem)) {
                val intent = Intent(context, AlarmReceiver::class.java).putExtra(
                    "alarmId",
                    getId(alarmItem.id, week.getDay())
                )
                    .putExtra("alarmName", alarmItem.name)
                val alarmIntent = PendingIntent.getBroadcast(
                    context,
                    getId(alarmItem.id, week.getDay()),
                    intent,
                    PendingIntent.FLAG_IMMUTABLE
                )
                try {
                    if (alarmIntent != null) {
                        alarmManager.cancel(alarmIntent)
                    }
                } catch (e: Exception) {
                    Log.d("from alarmSchedulerInterfaceImplementation", "error in cancelling alarm")
                    return Event.FAILURE

                }
            }
            week.incDay()
            count++
        } while (day != week.getDay())
        return Event.SUCCESS

    }

    /**
     * cancel() fun is used to cancel the alarm.
     * @param alarmId : it is the id of the alarm that is to be cancelled.
     * @return Event to notify the user if it is a success or not
     * alarmItem if is found then only the code will execute else it will return failure.
     * we are first finding the exact alarm using the id from database and then creating the corresponding intent and then we are cancelling the intent
     */
    override suspend fun cancel(alarmId: Int): Event {
        try {
            var day = LocalDate.now().dayOfWeek.value
            val alarmItem = alarmDbDao.getAlarmById(alarmId).firstOrNull() ?: return Event.FAILURE
            var count = 0
            week.setDay(day)
            do {
                if (week.toSet(week.getDay(), alarmItem)) {
                    val intent = Intent(context, AlarmReceiver::class.java).putExtra(
                        "alarmId",
                        getId(alarmItem.id, week.getDay())
                    )
                        .putExtra("alarmName", alarmItem.name)
                    val alarmIntent = PendingIntent.getBroadcast(
                        context,
                        getId(alarmItem.id, week.getDay()),
                        intent,
                        PendingIntent.FLAG_IMMUTABLE
                    )
                    try {
                        if (alarmIntent != null) {
                            alarmManager.cancel(alarmIntent)
                            CoroutineScope(Dispatchers.IO).launch {
                                alarmDbDao.deleteAlarm(alarmItem)
                            }
                        }
                    } catch (e: Exception) {
                        Log.d(
                            "from alarmSchedulerInterfaceImplementation",
                            "error in cancelling alarm"
                        )
                        return Event.FAILURE

                    }
                }
                week.incDay()
                count++

            } while (day != week.getDay())
            try {

                    CoroutineScope(Dispatchers.IO).launch {
                        alarmDbDao.deleteAlarm(alarmItem)
                    }

            } catch (e: Exception) {
                Log.d(
                    "from alarmSchedulerInterfaceImplementation",
                    "error in cancelling alarm"
                )
                return Event.FAILURE

            }

        } catch (e: Exception) {
            Log.d("from alarmSchedulerInterfaceImplementation", "error in cancelling alarm")
            return Event.FAILURE

        }
        return Event.SUCCESS
    }

    /**
     * rescheduleAllAlarm() fun is used to reschedule all the alarms.
     * fetch all the alarms that are active and schedule them
     * @return Event to notify the user if it is a success or not
     */
    override suspend fun rescheduleAllAlarm(): Event {
        try {
            val currentTime = System.currentTimeMillis()
            val alarmItems = alarmDbDao.getAllAlarms()
                for (alarm in alarmItems) {
                    schedule(alarm)
                }


            return Event.SUCCESS
        } catch (e: Exception) {
            Log.d("from alarmSchedulerInterfaceImplementation", "error in rescheduling alarm")
            return Event.FAILURE
        }

    }

    /**
     * updateAlarm() fun is used to update the alarm.
     * @param alarmItem : it is the new alarm time object and it will be replaced in the database. Id will however be the same.
     * @return Event to notify the user.
     * update alarm should delete old alarm and then insert the new alarm.
     */
    override suspend fun updateAlarm(alarmItem: Alarms): Event {
        return try {
            withContext(Dispatchers.IO) {
                val oldAlarm = async { alarmDbDao.getAlarmById(alarmItem.id).firstOrNull() }.await()
                if (oldAlarm != null) {
                    cancel(oldAlarm)
                }
                alarmDbDao.updateAlarm(alarmItem)
                schedule(alarmItem)
            }
        } catch (e: Exception) {
            e.printStackTrace()
            return Event.FAILURE
        }
    }

    /**
     * getAllAlarm() fun is used to get all the alarms.
     */
    override suspend fun getAllAlarm(): List<Alarms> {

        return try {
            withContext(Dispatchers.Default){
            val alarmItems = alarmDbDao.getAllAlarms()
            println("recieved all the alarms")
                println(alarmItems)
            alarmItems }
        }catch (e: Exception){
            e.printStackTrace()
            return emptyList()
        }
    }
}