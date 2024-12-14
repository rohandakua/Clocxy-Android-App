package com.clocxy.clocxyone.presentation.ComposableScreens

import android.content.Context
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.LocalTextStyle
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.clocxy.clocxyone.data.offline.alarm.Alarms
import com.clocxy.clocxyone.domain.Functions.getMsFromHoursAndMinutes
import com.clocxy.clocxyone.presentation.ViewModels.AlarmViewModel
import com.clocxy.clocxyone.ui.theme.CardBackgroundBlack
import com.clocxy.clocxyone.ui.theme.MainTextColorOrange
import com.clocxy.clocxyone.ui.theme.SecondaryTextColorOrange
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.launch

@Composable
fun AlarmDetailPage(
    modifier: Modifier,
    context: Context ,
    navController: NavHostController,
    viewModel: AlarmViewModel,
    cardContainerColor: Color,
    backgroundColor: Color,
    fontColor: Color ,
    secondaryFontColor: Color
) {
    var alarm = viewModel.data.collectAsState()
    var alarmTitle = viewModel.alarmTitle.collectAsState() // alarm.title
    var hours = viewModel.hours.collectAsState()

    var minutes = viewModel.minutes.collectAsState()

    var isMonday by remember {
        mutableStateOf(alarm.value.isMonday)
    }//alarm.isMonday
    var isTuesday by remember {
        mutableStateOf(alarm.value.isTuesday)
    }//alarm.isTuesday
    var isWednesday by remember {
        mutableStateOf(alarm.value.isWednesday)
    }  //alarm.isWednesday
    var isThursday by remember {
        mutableStateOf(alarm.value.isThursday)
    }//alarm.isThursday
    var isFriday by remember {
        mutableStateOf(alarm.value.isFriday)
    } //alarm.isFriday
    var isSaturday by remember {
        mutableStateOf(alarm.value.isSaturday)
    } //alarm.isSaturday
    var isSunday by remember {
        mutableStateOf(alarm.value.isSunday)
    } //alarm.isSunday
    val configuration = LocalConfiguration.current
    val isPortrait =
        configuration.orientation == android.content.res.Configuration.ORIENTATION_PORTRAIT
    var timePair: Pair<String, String> = Pair(hours.value, minutes.value)
    Box(
        modifier = modifier
            .fillMaxSize()
            .background(backgroundColor)
    ) {
        Column(Modifier.fillMaxSize()) {
            Box(
                modifier
                    .fillMaxWidth()
                    .height(30.dp)
                    .background(Color.Black)
            )
            if (isPortrait) {
                // for portrait
                Column(
                    modifier = Modifier.fillMaxSize(),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.SpaceEvenly
                ) {
                    Spacer(modifier = Modifier.weight(.1f))
                    Card(
                        modifier = Modifier
                            .fillMaxWidth(.9f),
                        shape = RoundedCornerShape(20.dp),
                        colors = CardDefaults.cardColors(
                            containerColor = cardContainerColor,
                            disabledContainerColor = cardContainerColor,
                            disabledContentColor = cardContainerColor

                        )

                    ) {
                        TextField(
                            value = alarmTitle.value,
                            onValueChange = { it ->
                                viewModel.setAlarmTitle(it)
                                //alarm.title = it
                            }, placeholder = ({
                                Text(
                                    text = "Alarm Name",
                                    fontSize = 30.sp,
                                    modifier = Modifier.fillMaxWidth(.9f),
                                    textAlign = TextAlign.Left,
                                    lineHeight = 10.sp, color = fontColor
                                )
                            }),
                            textStyle = LocalTextStyle.current.copy(
                                fontSize = 30.sp, lineHeight = 10.sp, color = fontColor
                            ),
                            modifier = Modifier
                                .fillMaxWidth()
                                .background(Color.Transparent),
                            maxLines = 1, shape = RoundedCornerShape(6.dp),
                            colors = TextFieldDefaults.colors(
                                focusedTextColor = fontColor,
                                unfocusedTextColor = fontColor,
                                focusedContainerColor = cardContainerColor,
                                unfocusedContainerColor = cardContainerColor,
                                focusedIndicatorColor = Color.Transparent,
                                unfocusedIndicatorColor = Color.Transparent,
                                cursorColor = fontColor
                            ), singleLine = true
                        )
                    }

                    Box(
                        modifier = Modifier
                            .weight(.4f)
                            .fillMaxSize(), contentAlignment = Alignment.Center
                    ) {
                        PreviewTimePicker(
                            timePair,
                            cardContainerColor = cardContainerColor,
                            backgroundColor = backgroundColor,
                            fontColor = fontColor,
                            secondaryFontColor = secondaryFontColor
                        ) { hour, minute, amP ->
                            if (amP == "PM") {
                                viewModel.setHours((hour + 12).toString())
                                viewModel.setMinutes(minute.toString())
                            } else {
                                viewModel.setHours(hour.toString())
                                viewModel.setMinutes(minute.toString())
                            }
                        }
                    }

                    Row(
                        modifier = Modifier
                            .weight(.2f)
                            .fillMaxSize(),
                        horizontalArrangement = Arrangement.SpaceEvenly,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        // for AlarmDays
                        Text(
                            text = "Mon",
                            fontSize = 18.sp,
                            maxLines = 1,
                            color = if (isMonday) fontColor else secondaryFontColor,
                            modifier = Modifier.clickable {
                                isMonday = !isMonday
                            }

                        )
                        Text(
                            text = "Tue",
                            fontSize = 18.sp,
                            maxLines = 1,
                            color = if (isTuesday) fontColor else secondaryFontColor,
                            modifier = Modifier.clickable {
                                isTuesday = !isTuesday
                            }

                        )
                        Text(
                            text = "Wed",
                            fontSize = 18.sp,
                            maxLines = 1,
                            color = if (isWednesday) fontColor else secondaryFontColor,
                            modifier = Modifier.clickable {
                                isWednesday = !isWednesday
                            }

                        )
                        Text(
                            text = "Thr",
                            fontSize = 18.sp,
                            maxLines = 1,
                            color = if (isThursday) fontColor else secondaryFontColor,
                            modifier = Modifier.clickable {
                                isThursday = !isThursday
                            }

                        )
                        Text(
                            text = "Fri",
                            fontSize = 18.sp,
                            maxLines = 1,
                            color = if (isFriday) fontColor else secondaryFontColor,
                            modifier = Modifier.clickable {
                                isFriday = !isFriday
                            }

                        )
                        Text(
                            text = "Sat",
                            fontSize = 18.sp,
                            maxLines = 1,
                            color = if (isSaturday) fontColor else secondaryFontColor,
                            modifier = Modifier.clickable {
                                isSaturday = !isSaturday
                            }
                        )
                        Text(
                            text = "Sun",
                            fontSize = 18.sp,
                            maxLines = 1,
                            color = if (isSunday) fontColor else secondaryFontColor,
                            modifier = Modifier.clickable {
                                isSunday = !isSunday
                            }

                        )

                    }
                    Spacer(modifier = Modifier.weight(.02f))

                    Button(
                        onClick = {
                            CoroutineScope(Dispatchers.IO).launch {
                                viewModel.saveClicked(
                                    Alarms(
                                        id = alarm.value.id,
                                        name = alarmTitle.value,
                                        timeInMs = getMsFromHoursAndMinutes(
                                            hours.value,
                                            minutes.value
                                        ),
                                        isMonday,
                                        isTuesday,
                                        isWednesday,
                                        isThursday,
                                        isFriday,
                                        isSaturday,
                                        isSunday
                                    )

                                )


                            }
                            navController.popBackStack()
                        }, colors = ButtonDefaults.buttonColors(
                            containerColor = cardContainerColor
                        ), shape = RoundedCornerShape(16.dp)
                    ) {
                        Text(
                            text = "Save",
                            fontSize = 24.sp,
                            color = fontColor
                        )
                    }
                    Spacer(modifier = Modifier.weight(.05f))

                }

            } else {
                // for landscape
                Column(
                    modifier = Modifier.fillMaxSize(),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.spacedBy(10.dp)
                ) {
//                    Card(
//                        modifier = Modifier
//                            .fillMaxWidth(.9f)
//                            .height(60.dp),
//                        colors = CardDefaults.cardColors(
//                            containerColor = CardBackground,
//                            disabledContainerColor = CardBackground,
//                            disabledContentColor = CardBackground
//
//                        )
//                    ) {
//                        Column(
//                            modifier = Modifier.fillMaxSize(),
//                            horizontalAlignment = Alignment.Start,
//                            verticalArrangement = Arrangement.Top
//                        ) {

                    TextField(
                        value = alarmTitle.value,
                        onValueChange = { it ->
                            viewModel.setAlarmTitle(it)
                            //alarm.title = it
                        }, placeholder = ({
                            Text(
                                modifier = Modifier
                                    .fillMaxWidth(.9f)
                                    .background(Color.Transparent),
                                text = "Alarm Name",
                                fontSize = 20.sp,
                                textAlign = TextAlign.Left
                            )
                        }),
                        textStyle = LocalTextStyle.current.copy(
                            fontSize = 20.sp, color = fontColor
                        ),
                        modifier = Modifier
                            .fillMaxWidth(.9f)
                            .background(Color.Transparent),
                        maxLines = 1,
                        colors = TextFieldDefaults.colors(
                            focusedTextColor = fontColor,
                            unfocusedTextColor = fontColor,
                            focusedContainerColor = cardContainerColor,
                            unfocusedContainerColor = cardContainerColor,
                            focusedIndicatorColor = Color.Transparent,
                            unfocusedIndicatorColor = Color.Transparent,
                            cursorColor = fontColor
                        ), singleLine = true, shape = (RoundedCornerShape(12.dp))
                    )
//                        }
//
//                    }
                    Box(
                        modifier = Modifier
                            .weight(.2f), contentAlignment = Alignment.Center
                    ) {
                        Column(
                            modifier = Modifier.height(200.dp),
                            verticalArrangement = Arrangement.Center,
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            PreviewTimePicker(
                                timePair,
                                textSize = 20,
                                numSize = 40,
                                cardContainerColor = cardContainerColor,
                                backgroundColor = backgroundColor,
                                fontColor = fontColor,
                                secondaryFontColor = secondaryFontColor
                            ) { hour, minute, amP ->
                                if (amP == "PM") {
                                    viewModel.setHours((hour + 12).toString())
                                    viewModel.setMinutes(minute.toString())
                                } else {
                                    viewModel.setHours(hour.toString())
                                    viewModel.setMinutes(minute.toString())
                                }
                            }
                        }

                    }
                    Row(
                        modifier = Modifier
                            .weight(.1f)
                            .fillMaxSize(),
                        horizontalArrangement = Arrangement.SpaceEvenly,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        // for AlarmDays
                        Text(
                            text = "Mon",
                            fontSize = 18.sp,
                            maxLines = 1,
                            color = if (isMonday) fontColor else secondaryFontColor,
                            modifier = Modifier.clickable {
                                isMonday = !isMonday
                            }

                        )
                        Text(
                            text = "Tue",
                            fontSize = 18.sp,
                            maxLines = 1,
                            color = if (isTuesday) fontColor else secondaryFontColor,
                            modifier = Modifier.clickable {
                                isTuesday = !isTuesday
                            }

                        )
                        Text(
                            text = "Wed",
                            fontSize = 18.sp,
                            maxLines = 1,
                            color = if (isWednesday) fontColor else secondaryFontColor,
                            modifier = Modifier.clickable {
                                isWednesday = !isWednesday
                            }

                        )
                        Text(
                            text = "Thr",
                            fontSize = 18.sp,
                            maxLines = 1,
                            color = if (isThursday) fontColor else secondaryFontColor,
                            modifier = Modifier.clickable {
                                isThursday = !isThursday
                            }

                        )
                        Text(
                            text = "Fri",
                            fontSize = 18.sp,
                            maxLines = 1,
                            color = if (isFriday) fontColor else secondaryFontColor,
                            modifier = Modifier.clickable {
                                isFriday = !isFriday
                            }

                        )
                        Text(
                            text = "Sat",
                            fontSize = 18.sp,
                            maxLines = 1,
                            color = if (isSaturday) fontColor else secondaryFontColor,
                            modifier = Modifier.clickable {
                                isSaturday = !isSaturday
                            }
                        )
                        Text(
                            text = "Sun",
                            fontSize = 18.sp,
                            maxLines = 1,
                            color = if (isSunday) fontColor else secondaryFontColor,
                            modifier = Modifier.clickable {
                                isSunday = !isSunday
                            }

                        )

                    }
                    Button(
                        onClick = {
                            CoroutineScope(Dispatchers.IO).launch {
                                viewModel.saveClicked(
                                    Alarms(
                                        id = alarm.value.id,
                                        name = alarmTitle.value,
                                        timeInMs = getMsFromHoursAndMinutes(
                                            hours.value,
                                            minutes.value
                                        ),
                                        isMonday,
                                        isTuesday,
                                        isWednesday,
                                        isThursday,
                                        isFriday,
                                        isSaturday,
                                        isSunday
                                    )

                                )


                            }
                            navController.popBackStack()
                        }, colors = ButtonDefaults.buttonColors(
                            containerColor = cardContainerColor
                        ), shape = RoundedCornerShape(12.dp)
                    ) {
                        Text(
                            text = "Save",
                            fontSize = 24.sp,
                            color = fontColor
                        )
                    }

                    Spacer(modifier = Modifier.height(10.dp))

                }

            }
        }
    }


}

@Composable
fun PreviewTimePicker(
    time: Pair<String, String>,
    secondaryFontColor: Color = SecondaryTextColorOrange,
    cardContainerColor: Color = CardBackgroundBlack,
    backgroundColor: Color = Color.Black,
    fontColor: Color = MainTextColorOrange,
    textSize: Int = 30,
    numSize: Int = 60,
    function: (Int, Int, String) -> Unit = { selectedHour, selectedMinute, selectedString ->
        println("Selected Time: $selectedHour:$selectedMinute $selectedString")
    }
) { //selectedHour, selectedMinute, selectedString
    TimePicker(
        hour = time.first,
        minute = time.second,
        textSize = textSize,
        numSize = numSize,
        onTimeSelected = function,
        cardContainerColor = cardContainerColor,
        backgroundColor = backgroundColor,
        fontColor = fontColor,
        secondaryFontColor = secondaryFontColor)
}

@Composable
fun TimePicker(
    hour: String,
    cardContainerColor: Color = CardBackgroundBlack,
    backgroundColor: Color = Color.Black,
    fontColor: Color = MainTextColorOrange,secondaryFontColor: Color = SecondaryTextColorOrange,
    textSize: Int = 30,
    numSize: Int = 60,
    minute: String,
    onTimeSelected: (Int, Int, String) -> Unit
) {
    val hours = listOf(0,1,2,3,4,5,6,7,8,9,10,11)  // for 00:00 hrs to 24:00 hrs
    val minutes = (0..59).toList()  // List of minutes
    val ampm = listOf("AM", "PM")
    val sep = listOf(":")

    val ah = hour.toInt()
    val am = minute.toInt()
    val aampm = if (hour.toInt() >= 12) 1 else 0
    val hourState = rememberLazyListState(initialFirstVisibleItemIndex = ah + (12 * 20))
    val minuteState = rememberLazyListState(initialFirstVisibleItemIndex = am + (60 * 20))
    val ampmState = rememberLazyListState(initialFirstVisibleItemIndex = aampm + (2 * 200))
    val sepState = rememberLazyListState(initialFirstVisibleItemIndex = 0)

    val configuration = LocalConfiguration.current
    val isPortrait =
        configuration.orientation == android.content.res.Configuration.ORIENTATION_PORTRAIT

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(if (isPortrait) 200.dp else 130.dp),
        horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.CenterVertically
    ) {
        TimeColumnPicker(
            items = hours,
            listState = hourState,
            onItemSelected = { selectedHour ->
                onTimeSelected(
                    selectedHour%12,
                    minuteState.firstVisibleItemIndex % 60,
                    ampm[ampmState.firstVisibleItemIndex % 2]
                )
            }, textSize = textSize, numSize = numSize,
            cardContainerColor = cardContainerColor,
            backgroundColor = backgroundColor,
            fontColor = fontColor,
            secondaryFontColor = secondaryFontColor
        )

        TimeColumnPickerss(                 // for :
            items = sep,
            listState = sepState,
            onItemSelected = {}, textSize = textSize, numSize = numSize,
            cardContainerColor = cardContainerColor,
            backgroundColor = backgroundColor,
            fontColor = fontColor,
            secondaryFontColor = secondaryFontColor
        )

        TimeColumnPicker(                     // for AM PM text
            items = minutes,
            listState = minuteState,
            onItemSelected = { selectedMinute ->
                onTimeSelected(
                    hourState.firstVisibleItemIndex % 12,
                    selectedMinute,
                    ampm[ampmState.firstVisibleItemIndex % 2]
                )
            }, textSize = textSize, numSize = numSize,
            cardContainerColor = cardContainerColor,
            backgroundColor = backgroundColor,
            fontColor = fontColor,
            secondaryFontColor = secondaryFontColor
        )

        TimeColumnPickers(
            items = ampm,
            listState = ampmState,
            onItemSelected = { selectedAmPm ->
                onTimeSelected(
                    hourState.firstVisibleItemIndex % 12,
                    minuteState.firstVisibleItemIndex % 60,
                    ampm[selectedAmPm]
                )
            }, textSize = textSize, numSize = numSize,
            cardContainerColor = cardContainerColor,
            backgroundColor = backgroundColor,
            fontColor = fontColor,
            secondaryFontColor = secondaryFontColor
        )
    }
}

@Composable
fun TimeColumnPicker(
    items: List<Int>,
    cardContainerColor: Color = CardBackgroundBlack,
    backgroundColor: Color = Color.Black,
    fontColor: Color = MainTextColorOrange,secondaryFontColor: Color= SecondaryTextColorOrange,
    listState: LazyListState,
    onItemSelected: (Int) -> Unit, textSize: Int = 30, numSize: Int = 60
) {
    val configuration = LocalConfiguration.current
    val isPortrait =
        configuration.orientation == android.content.res.Configuration.ORIENTATION_PORTRAIT
    val infiniteItems = List(items.size * 100) { index -> items[index % items.size] }
    Box(
        modifier = Modifier
            .fillMaxHeight()
            .width(80.dp)
    ) {

        Box(
            modifier = Modifier
                .fillMaxSize()
                .align(Alignment.Center),
            contentAlignment = Alignment.Center
        ) {
            LazyColumn(
                state = listState,
                modifier = Modifier
                    .height(200.dp)
                    .width(80.dp),
                contentPadding = PaddingValues(vertical = if (isPortrait) 40.dp else 40.dp) // Center selected item
            ) {
                itemsIndexed(infiniteItems) { index, item ->
                    val isSelected = index == listState.firstVisibleItemIndex
                    Text(
                        text = item.toString().padStart(2, '0'),
                        fontSize = if (isSelected) numSize.sp else (numSize * .66).toInt().sp,
                        color = if (isSelected) fontColor else secondaryFontColor,
                        modifier = Modifier.fillParentMaxWidth(),
                        textAlign = TextAlign.Center
                    )
                }
            }

            LaunchedEffect(listState.firstVisibleItemIndex) {
                snapshotFlow { listState.isScrollInProgress }
                    .distinctUntilChanged()
                    .filter { !it }
                    .collect {
                        val selectedItemIndex = listState.firstVisibleItemIndex
                        listState.animateScrollToItem(selectedItemIndex)
                        onItemSelected(selectedItemIndex % items.size)
                    }


            }

        }
    }

}

@Composable
fun TimeColumnPickers(
    items: List<String>,
    listState: LazyListState,
    onItemSelected: (Int) -> Unit, textSize: Int = 30, numSize: Int = 60,
    cardContainerColor: Color = CardBackgroundBlack,
    backgroundColor: Color = Color.Black,
    fontColor: Color = MainTextColorOrange,secondaryFontColor: Color = SecondaryTextColorOrange,
) {
    val configuration = LocalConfiguration.current
    val isPortrait =
        configuration.orientation == android.content.res.Configuration.ORIENTATION_PORTRAIT
    val infiniteItems = List(items.size * 1000) { index -> items[index % items.size] }
    Box(
        modifier = Modifier
            .fillMaxHeight()
            .width(80.dp)
    ) {

        Box(
            modifier = Modifier
                .fillMaxSize()
                .align(Alignment.Center),
            contentAlignment = Alignment.Center
        ) {
            LazyColumn(
                state = listState,
                modifier = Modifier
                    .height(180.dp)
                    .width(80.dp),
                contentPadding = PaddingValues(vertical = if (isPortrait) 37.dp else 35.dp)
            ) {
                itemsIndexed(infiniteItems) { index, item ->
                    val isSelected = index == listState.firstVisibleItemIndex
                    Column(
                        modifier = Modifier.fillMaxSize(),
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.Center
                    ) {

                        Spacer(modifier = Modifier.height(15.dp))
                        Text(
                            text = item,
                            fontSize = if (isSelected) textSize.sp else (textSize * .66).toInt().sp,
                            color = if (isSelected) fontColor else secondaryFontColor,
                            modifier = Modifier.fillParentMaxWidth(),
                            textAlign = TextAlign.Center
                        )
                        Spacer(modifier = Modifier.height(15.dp))
                    }
                }
            }

            LaunchedEffect(listState.firstVisibleItemIndex) {
                snapshotFlow { listState.isScrollInProgress }
                    .distinctUntilChanged()
                    .filter { !it }
                    .collect {
                        val selectedItemIndex = listState.firstVisibleItemIndex
                        listState.animateScrollToItem(selectedItemIndex)
                        onItemSelected(selectedItemIndex % items.size)
                    }
            }

        }
    }

}

@Composable
fun TimeColumnPickerss( // use num size
    items: List<String>,
    cardContainerColor: Color = CardBackgroundBlack,
    backgroundColor: Color = Color.Black,
    fontColor: Color = MainTextColorOrange,secondaryFontColor: Color = SecondaryTextColorOrange,
    listState: LazyListState,
    onItemSelected: (Int) -> Unit, textSize: Int = 30, numSize: Int = 60
) {
    val configuration = LocalConfiguration.current
    val isPortrait =
        configuration.orientation == android.content.res.Configuration.ORIENTATION_PORTRAIT
    Box(
        modifier = Modifier
            .fillMaxHeight()
            .width(80.dp)
    ) {

        Box(
            modifier = Modifier
                .fillMaxSize()
                .align(Alignment.Center),
            contentAlignment = Alignment.Center
        ) {
            LazyColumn(
                state = listState,
                modifier = Modifier
                    .height(135.dp)
                    .width(80.dp)
                    .padding(top = if (!isPortrait) 35.dp else 0.dp),
                contentPadding = PaddingValues(vertical = if (isPortrait) 5.dp else 5.dp)
            ) {
                itemsIndexed(items) { index, item ->
                    val isSelected = index == listState.firstVisibleItemIndex
                    Text(
                        text = item,
                        fontSize = if (isSelected) numSize.sp else (numSize * .66).toInt().sp,
                        color =if (isSelected) fontColor else secondaryFontColor,
                        modifier = Modifier.fillParentMaxWidth(),
                        textAlign = TextAlign.Center
                    )
                }
            }

            LaunchedEffect(listState) {
                snapshotFlow { listState.isScrollInProgress }
                    .distinctUntilChanged()
                    .filter { !it }
                    .collect {
                        val selectedItemIndex = listState.firstVisibleItemIndex
                        listState.animateScrollToItem(selectedItemIndex)
                        onItemSelected(selectedItemIndex % items.size)
                    }
            }
        }
    }

}
