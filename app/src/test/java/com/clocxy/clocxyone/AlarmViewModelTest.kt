package com.clocxy.clocxyone
//
//import android.content.Context
//import android.widget.Toast
//import com.example.clockappbyrohan.data.offline.alarm.Alarms
//import com.example.clockappbyrohan.domain.usecase.alarmUsecase.CancelAlarm
//import com.example.clockappbyrohan.domain.usecase.alarmUsecase.DeleteAlarm
//import com.example.clockappbyrohan.domain.usecase.alarmUsecase.GetAllAlarm
//import com.example.clockappbyrohan.domain.usecase.alarmUsecase.ScheduleAlarm
//import com.example.clockappbyrohan.domain.usecase.alarmUsecase.UpdateAlarm
//import com.example.clockappbyrohan.presentation.ViewModels.AlarmViewModel
//import junit.framework.TestCase.assertEquals
//import kotlinx.coroutines.Dispatchers
//import kotlinx.coroutines.ExperimentalCoroutinesApi
//import kotlinx.coroutines.test.TestCoroutineDispatcher
//import kotlinx.coroutines.test.resetMain
//import kotlinx.coroutines.test.setMain
//import org.junit.After
//import org.junit.Before
//import org.junit.Test
//import org.mockito.ArgumentMatchers.any
//import org.mockito.ArgumentMatchers.eq
//import org.mockito.Mockito.doAnswer
//import org.mockito.Mockito.mock
//import org.mockito.Mockito.verify
//import org.mockito.Mockito.`when`
//
//@ExperimentalCoroutinesApi
//class AlarmViewModelTest {
//
//    // Mock dependencies
//    private lateinit var cancelAlarm: CancelAlarm
//    private lateinit var deleteAlarm: DeleteAlarm
//    private lateinit var scheduleAlarm: ScheduleAlarm
//    private lateinit var updateAlarm: UpdateAlarm
//    private lateinit var getAllAlarm: GetAllAlarm
//    private lateinit var context: Context
//
//    // ViewModel under test
//    private lateinit var alarmViewModel: AlarmViewModel
//
//    // Coroutines Test Dispatcher
//    private val testDispatcher = TestCoroutineDispatcher()
//
//    @Before
//    fun setUp() {
//        cancelAlarm = mock(CancelAlarm::class.java)
//        deleteAlarm = mock(DeleteAlarm::class.java)
//        scheduleAlarm = mock(ScheduleAlarm::class.java)
//        updateAlarm = mock(UpdateAlarm::class.java)
//        getAllAlarm = mock(GetAllAlarm::class.java)
//        context = mock(Context::class.java)
//
//        Dispatchers.setMain(testDispatcher)
//
//        alarmViewModel = AlarmViewModel(
//            context = context,
//            cancelAlarm = cancelAlarm,
//            deleteAlarm = deleteAlarm,
//            scheduleAlarm = scheduleAlarm,
//            updateAlarm = updateAlarm,
//            getAllAlarm = getAllAlarm
//        )
//    }
//
//    @After
//    fun tearDown() {
//        Dispatchers.resetMain()
//        testDispatcher.cleanupTestCoroutines()
//    }
//
//    @Test
//    fun `getAllAlarm updates alarmList`() {
//        // Arrange
//        val mockAlarms = listOf(
//            Alarms(1, "Alarm 1", 1000L, false, false, false, false, false, false, false),
//            Alarms(2, "Alarm 2", 2000L, false, false, false, false, false, false, false)
//        )
//        `when`(getAllAlarm.execute()).thenReturn(mockAlarms)
//
//        // Act
//        alarmViewModel.getAllAlarm()
//
//        // Assert
//        assertEquals(mockAlarms.size, alarmViewModel.alarmList.size)
//        assertEquals(mockAlarms[0].name, alarmViewModel.alarmList[0].name)
//    }
//
//    @Test
//    fun `alarmClicked updates data and time fields`() {
//        // Arrange
//        val alarm = Alarms(1, "Alarm Test", 3600000L, false, false, false, false, false, false, false)
//
//        // Act
//        alarmViewModel.alarmClicked(alarm)
//
//        // Assert
//        assertEquals(alarm, alarmViewModel.data.value)
//        assertEquals("01", alarmViewModel.hours.value)
//        assertEquals("00", alarmViewModel.minutes.value)
//    }
//
//    @Test
//    fun `createClicked resets fields to default`() {
//        // Act
//        alarmViewModel.createClicked()
//
//        // Assert
//        assertEquals("Alarm", alarmViewModel.alarmTitle.value)
//        assertEquals("00", alarmViewModel.hours.value)
//        assertEquals("00", alarmViewModel.minutes.value)
//    }
//
//    @Test
//    fun `saveClicked schedules new alarm`() {
//        // Arrange
//        val alarm = Alarms(0, "New Alarm", 1000L, false, false, false, false, false, false, false)
//        `when`(scheduleAlarm.execute(alarm)).thenReturn(com.example.clockappbyrohan.domain.dataclass.Event.SUCCESS)
//
//        // Act
//        val result = alarmViewModel.saveClicked(alarm, isNew = true)
//
//        // Assert
//        assertEquals(com.example.clockappbyrohan.domain.dataclass.Event.SUCCESS, result)
//    }
//
//    @Test
//    fun `saveClicked updates existing alarm`() {
//        // Arrange
//        val alarm = Alarms(1, "Updated Alarm", 1000L, false, false, false, false, false, false, false)
//
//        doAnswer { invocation ->
//            val callback = invocation.arguments[1] as (com.example.clockappbyrohan.domain.dataclass.Event) -> Unit
//            callback(com.example.clockappbyrohan.domain.dataclass.Event.SUCCESS)
//        }.`when`(updateAlarm).execute(eq(alarm), any())
//
//        // Act
//        alarmViewModel.saveClicked(alarm, isNew = false)
//
//        // Verify Toast is shown
//        verify(context).let {
//            Toast.makeText(it, "Successfully updated alarm", Toast.LENGTH_SHORT).show()
//        }
//    }
//
//
//    @Test
//    fun `deleteAlarm removes alarm`() {
//        // Arrange
//        val alarm = Alarms(1, "Alarm to delete", 1000L, false, false, false, false, false, false, false)
//
//        doAnswer { invocation ->
//            val callback = invocation.arguments[1] as (com.example.clockappbyrohan.domain.dataclass.Event) -> Unit
//            callback(com.example.clockappbyrohan.domain.dataclass.Event.SUCCESS)
//        }.`when`(deleteAlarm).execute(eq(alarm.id), any())
//
//        // Act
//        alarmViewModel.deleteAlarm(alarm)
//
//        // Verify Toast is shown
//        verify(context).let {
//            Toast.makeText(it, "Successfully deleted Alarm to delete", Toast.LENGTH_SHORT).show()
//        }
//    }
//
//}
