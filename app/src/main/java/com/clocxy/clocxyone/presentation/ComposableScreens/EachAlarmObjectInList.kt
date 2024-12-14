package com.clocxy.clocxyone.presentation.ComposableScreens

import android.content.Context
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.clocxy.clocxyone.R
//import com.example.clockappbyrohan.R
import com.clocxy.clocxyone.data.offline.alarm.Alarms
import com.clocxy.clocxyone.domain.Functions.getHours
import com.clocxy.clocxyone.domain.Functions.getMinutes
import com.clocxy.clocxyone.presentation.ViewModels.AlarmViewModel


@Composable
fun EachAlarmObjectInList(
    modifier: Modifier ,
    context: Context ,
    navController: NavHostController,
    viewModel: AlarmViewModel,
    alarm: Alarms,
    cardContainerColor: Color ,
    backgroundColor: Color ,
    fontColor: Color,
    secondaryFontColor: Color
) {
    val configuration = LocalConfiguration.current
    val isPortrait =
        configuration.orientation == android.content.res.Configuration.ORIENTATION_PORTRAIT
    val AlarmTitle = alarm.name.toString()
    val hours = getHours(alarm.timeInMs)
    val minutes = getMinutes(alarm.timeInMs)
    var AlarmTime = ""
    if (hours.toInt() > 12) {
        AlarmTime = (hours.toInt() % 12).toString().padStart(2,'0') + " : " + minutes.toString().padStart(2,'0') + "  PM"
    } else {
        AlarmTime = hours.toString().padStart(2,'0') + " : " + minutes.toString().padStart(2,'0') + " AM"
    }
    val isMonday = alarm.isMonday
    val isTuesday = alarm.isTuesday
    val isWednesday = alarm.isWednesday
    val isThursday = alarm.isThursday
    val isFriday = alarm.isFriday
    val isSaturday = alarm.isSaturday
    val isSunday = alarm.isSunday
    if (isPortrait) {
        Column(
            modifier = modifier
                .height(150.dp)
                .clickable {
                    viewModel.setAlarm(alarm)
                    viewModel.setAlarmTitle(alarm.name.toString())
                    viewModel.setHours(getHours(alarm.timeInMs))
                    viewModel.setMinutes(getMinutes(alarm.timeInMs))
                    viewModel.setIsNew(false)
                    navController.navigate("alarmDetailPage")
                }
        ) {
            Card(
                modifier = Modifier
                    .fillMaxWidth(.85f)
                    .fillMaxHeight(),
                colors = CardDefaults.cardColors(
                    containerColor = cardContainerColor,
                    contentColor = backgroundColor
                ), shape = RoundedCornerShape(24.dp)
            ) {
                Column(modifier = Modifier.fillMaxSize()) {
                    Row(
                        modifier = Modifier
                            .weight(.3f)
                            .fillMaxSize(),
                        horizontalArrangement = Arrangement.Start,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        // for AlarmTitle
                        Text(
                            modifier = Modifier.padding(
                                start = 20.dp,
                                top = 3.dp,
                                bottom = 0.dp,
                                end = 0.dp
                            ),
                            text = AlarmTitle,
                            fontSize = 20.sp,
                            maxLines = 1,
                            color = fontColor
                        )
                    }
                    Row(
                        modifier = Modifier
                            .weight(.4f)
                            .fillMaxSize(),
                        horizontalArrangement = Arrangement.SpaceEvenly,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        // for AlarmTime and delete button
                        Text(
                            text = AlarmTime,
                            fontSize = 30.sp,
                            maxLines = 1,
                            color = fontColor)
                        Spacer(modifier = Modifier.size(10.dp))
                        Icon(
                            painter = painterResource(id = R.drawable.baseline_delete_24),
                            contentDescription = "delete",
                            tint = fontColor,
                            modifier = Modifier.clickable {
                                viewModel.deleteAlarm(alarm)
                            }
                        )
                    }
                    Row(
                        modifier = Modifier
                            .weight(.3f)
                            .fillMaxSize()
                            .padding(start = 0.dp, end = 0.dp, top = 0.dp, bottom = 10.dp),
                        horizontalArrangement = Arrangement.SpaceEvenly,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        // for AlarmDays
                        Text(
                            text = "Mon",
                            fontSize = 18.sp,
                            maxLines = 1,
                            color = if (isMonday) fontColor else secondaryFontColor

                        )
                        Text(
                            text = "Tue",
                            fontSize = 18.sp,
                            maxLines = 1,
                            color = if (isTuesday) fontColor else secondaryFontColor

                        )
                        Text(
                            text = "Wed",
                            fontSize = 18.sp,
                            maxLines = 1,
                            color = if (isWednesday) fontColor else secondaryFontColor

                        )
                        Text(
                            text = "Thr",
                            fontSize = 18.sp,
                            maxLines = 1,
                            color = if (isThursday) fontColor else secondaryFontColor

                        )
                        Text(
                            text = "Fri",
                            fontSize = 18.sp,
                            maxLines = 1,
                            color = if (isFriday) fontColor else secondaryFontColor

                        )
                        Text(
                            text = "Sat",
                            fontSize = 18.sp,
                            maxLines = 1,
                            color = if (isSaturday) fontColor else secondaryFontColor
                        )
                        Text(
                            text = "Sun",
                            fontSize = 18.sp,
                            maxLines = 1,
                            color = if (isSunday) fontColor else secondaryFontColor
                        )

                    }
                }

            }
        }


    } else {
        // Landscape Mode
        Column(
            modifier = modifier
                .height(100.dp)
                .clickable {
                    viewModel.setAlarm(alarm)
                    viewModel.setAlarmTitle(alarm.name.toString())
                    viewModel.setHours(getHours(alarm.timeInMs))
                    viewModel.setMinutes(getMinutes(alarm.timeInMs))
                    viewModel.setIsNew(false)
                    navController.navigate("alarmDetailPage")
                }
        ) {
            Card(
                modifier = Modifier
                    .fillMaxWidth(.90f)
                    .fillMaxHeight(),
                colors = CardDefaults.cardColors(
                    containerColor = cardContainerColor,
                    contentColor = backgroundColor,
                    disabledContainerColor = backgroundColor,
                    disabledContentColor = backgroundColor
                ), shape = RoundedCornerShape(24.dp)
            ) {
                Column(modifier = Modifier.fillMaxSize()) {
                    Row(
                        modifier = Modifier
                            .weight(.3f)
                            .fillMaxSize(),
                        horizontalArrangement = Arrangement.Start,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        // for AlarmTitle
                        Text(
                            modifier = Modifier.padding(
                                start = 25.dp,
                                top = 3.dp,
                                bottom = 0.dp,
                                end = 0.dp
                            ),
                            text = AlarmTitle,
                            fontSize = 16.sp,
                            maxLines = 1,
                            color = fontColor
                        )
                    }
                    Row(
                        modifier = Modifier
                            .weight(.4f)
                            .fillMaxSize(),
                        horizontalArrangement = Arrangement.SpaceEvenly,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        // for AlarmTime and delete button
                        Text(
                            text = AlarmTime,
                            fontSize = 26.sp,
                            maxLines = 1,
                            color = fontColor
                        )
                        Spacer(modifier = Modifier.size(50.dp))
                        Icon(
                            painter = painterResource(id = R.drawable.baseline_delete_24),
                            contentDescription = "delete",
                            tint = fontColor,
                            modifier = Modifier.clickable {
                                viewModel.deleteAlarm(alarm)
                                navController.navigate("alarmPage")
                            }
                        )
                    }
                    Row(
                        modifier = Modifier
                            .weight(.3f)
                            .fillMaxSize()
                            .padding(start = 0.dp, end = 0.dp, top = 0.dp, bottom = 5.dp),
                        horizontalArrangement = Arrangement.SpaceEvenly,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        // for AlarmDays
                        Text(
                            text = "Mon",
                            fontSize = 18.sp,
                            maxLines = 1,
                            color = if (isMonday) fontColor else secondaryFontColor

                        )
                        Text(
                            text = "Tue",
                            fontSize = 18.sp,
                            maxLines = 1,
                            color = if (isTuesday) fontColor else secondaryFontColor

                        )
                        Text(
                            text = "Wed",
                            fontSize = 18.sp,
                            maxLines = 1,
                            color = if (isWednesday) fontColor else secondaryFontColor

                        )
                        Text(
                            text = "Thr",
                            fontSize = 18.sp,
                            maxLines = 1,
                            color = if (isThursday)fontColor else secondaryFontColor
                        )
                        Text(
                            text = "Fri",
                            fontSize = 18.sp,
                            maxLines = 1,
                            color = if (isFriday) fontColor else secondaryFontColor

                        )
                        Text(
                            text = "Sat",
                            fontSize = 18.sp,
                            maxLines = 1,
                            color = if (isSaturday) fontColor else secondaryFontColor
                        )
                        Text(
                            text = "Sun",
                            fontSize = 18.sp,
                            maxLines = 1,
                            color = if (isSunday) fontColor else secondaryFontColor
                        )

                    }
                }

            }
        }

    }

}


