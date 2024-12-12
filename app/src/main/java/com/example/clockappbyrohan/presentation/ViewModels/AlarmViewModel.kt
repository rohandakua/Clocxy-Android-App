package com.example.clockappbyrohan.presentation.ViewModels

import android.app.AlarmManager
import android.content.Context
import android.media.metrics.Event
import android.widget.Toast
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.toMutableStateList
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.compose.DialogHost
import com.example.clockappbyrohan.data.offline.alarm.Alarms
import com.example.clockappbyrohan.domain.Functions.getCurrentHoursAndMinutes
import com.example.clockappbyrohan.domain.Functions.getHours
import com.example.clockappbyrohan.domain.Functions.getMinutes
import com.example.clockappbyrohan.domain.usecase.alarmUsecase.CancelAlarm
import com.example.clockappbyrohan.domain.usecase.alarmUsecase.DeleteAlarm
import com.example.clockappbyrohan.domain.usecase.alarmUsecase.GetAllAlarm
import com.example.clockappbyrohan.domain.usecase.alarmUsecase.ScheduleAlarm
import com.example.clockappbyrohan.domain.usecase.alarmUsecase.UpdateAlarm
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.FlowCollector
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.emptyFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class AlarmViewModel @Inject constructor(
    @ApplicationContext private val context: Context,
    private val cancelAlarm: CancelAlarm,
    private val deleteAlarm: DeleteAlarm,
    private val scheduleAlarm: ScheduleAlarm,
    private val updateAlarm: UpdateAlarm,
    private val getAllAlarm: GetAllAlarm
) : ViewModel() {
    /**
     * hours, minutes and alarmTitle is used for each alarm in detail to show , and also use the data field in that, it has 7 days info. to display in the screen.
     * alarmList is used to show all the alarms in the list.
     * if clicked then it will take to the detail screen.
     * also if the create+ is clicked then also the same screen is clicked and all the fields are set to the time when the create+ button is clicked.
     * all the info. should be saved when the save is clicked.
     */
    private var _hours = MutableStateFlow("")
    val hours: StateFlow<String> get() = _hours
    private var _minutes = MutableStateFlow("")
    val minutes: StateFlow<String> get() = _minutes
    private var _alarmTitle = MutableStateFlow("")
    val alarmTitle: StateFlow<String> get() = _alarmTitle
    private var _data =
        MutableStateFlow(Alarms(0, "", 0, false, false, false, false, false, false, false))
    val data: StateFlow<Alarms> get() = _data
    private var _alarmList = MutableStateFlow(listOf<Alarms>())
    val alarmList get() = _alarmList
    fun setAlarmList (list: List<Alarms>){
        _alarmList.value = list
    }

    private var _isNew = MutableStateFlow(false)
    val isNew: StateFlow<Boolean> get() = _isNew
    fun setIsNew(a: Boolean) {
        _isNew.value = a
    }

    fun setAlarm(alarm: Alarms){
        _data.value = alarm
    }
    fun setAlarmTitle(title:String){
        _alarmTitle.value = title
    }
    fun setHours(hours:String){
        _hours.value = hours
    }
    fun setMinutes(minutes:String) {
        _minutes.value = minutes
    }

    /**
     * getAllAlarms() is used to get all the alarm in a list
     */
    suspend fun getAllAlarms(): List<Alarms> {
        return getAllAlarm.execute()
    }
    /**
     * getAllAlarm() is used to update the _alarmList
     */
    suspend fun getAllAlarm() {
        _alarmList.value = getAllAlarm.execute()
        setAlarmList(_alarmList.value.toMutableList())
    }

    /**
     * alarmClicked() is used when a alarm from the list is clicked. This will set the data field to the clicked alarm.
     * the hours, minutes and alarmTitle will be set to the clicked alarm.
     */
    fun alarmClicked(alarm: Alarms) {
        _data.value = alarm
        _hours.value = getHours(alarm.timeInMs) ?: "00"
        _minutes.value = getMinutes(alarm.timeInMs) ?: "00"
        _alarmTitle.value = alarm.name ?: "Alarm"
    }

    /**
     * createClicked() is used when the create+ button is clicked.
     * this fun should set all the value of the alarm to default value.
     */
    fun createClicked() {
        _data.value = Alarms(0, "", 0, false, false, false, false, false, false, false)
        val temp = getCurrentHoursAndMinutes()
        _hours.value = temp.first ?: "00"
        _minutes.value = temp.second ?: "00"
        _alarmTitle.value = "Alarm"
    }

    /**
     * saveClicked() is used when the save button is clicked.
     * @param alarm : it is the alarm that is to be saved. Provide it from the viewModel
     * @param isNew : it is to notify that is the alarm newly created or is being updated.
     * @return Returns a event
     * use this returned event when the alarm is new , we have managed here for the case when the alarm is old and being updated.
     */
    suspend fun saveClicked(
        alarm: Alarms,
        isNew: Boolean=_isNew.value
    ){
        if (isNew) {
            scheduleAlarm.execute(alarm) { result ->
                when (result) {
                    com.example.clockappbyrohan.domain.dataclass.Event.SUCCESS -> {
                        viewModelScope.launch {
                            withContext(Dispatchers.Main) {
                                Toast.makeText(
                                    context,
                                    "Successfully Saved alarm",
                                    Toast.LENGTH_SHORT
                                ).show()
                            }
                        }
                    }

                    com.example.clockappbyrohan.domain.dataclass.Event.FAILURE -> {
                        viewModelScope.launch {
                            withContext(Dispatchers.Main) {
                                Toast.makeText(
                                    context,
                                    "Error in saving alarm",
                                    Toast.LENGTH_SHORT
                                ).show()

                            }
                        }
                    }
                }
            }
        } else {
            updateAlarm.execute(alarm) { result ->
                when (result) {
                    com.example.clockappbyrohan.domain.dataclass.Event.SUCCESS -> {
                        viewModelScope.launch {
                            withContext(Dispatchers.Main) {
                                Toast.makeText(
                                    context,
                                    "Successfully updated alarm",
                                    Toast.LENGTH_SHORT
                                ).show()
                            }
                        }
                    }

                    com.example.clockappbyrohan.domain.dataclass.Event.FAILURE -> {
                        viewModelScope.launch {
                            withContext(Dispatchers.Main) {
                                Toast.makeText(
                                    context,
                                    "Error in updating alarm",
                                    Toast.LENGTH_SHORT
                                ).show()
                            }
                        }
                    }
                }
            }
        }


    }

    /**
     * deleteAlarm() is used to delete the alarm.
     * @param alarm: get it from the list of alarm. Handle it there where you are implementing the list of the alarm.
     * @return It doesnot returns anything , we have handled the toast here only to notify the user.
     */
    fun deleteAlarm(alarm:Alarms){
        val alarmName = alarm.name
        deleteAlarm.execute(alarm.id){event: com.example.clockappbyrohan.domain.dataclass.Event ->
            when(event) {
                com.example.clockappbyrohan.domain.dataclass.Event.SUCCESS -> {
                    viewModelScope.launch {
                        withContext(Dispatchers.Main){
                            Toast.makeText(context,"Successfully deleted $alarmName",Toast.LENGTH_SHORT).show()
                        }
                    }
                    CoroutineScope(Dispatchers.Default).launch {
                        getAllAlarm()
                    }
                }
                com.example.clockappbyrohan.domain.dataclass.Event.FAILURE->{
                    viewModelScope.launch {
                        withContext(Dispatchers.Main){
                            Toast.makeText(context,"Unable to delete $alarmName",Toast.LENGTH_SHORT).show()
                        }
                    }
                    CoroutineScope(Dispatchers.Default).launch {
                        getAllAlarm()
                    }
                }
            }

        }

    }



}