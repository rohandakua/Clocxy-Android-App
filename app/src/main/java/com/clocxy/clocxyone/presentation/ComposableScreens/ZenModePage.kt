package com.clocxy.clocxyone.presentation.ComposableScreens

import android.app.NotificationManager
import android.content.Context
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.clocxy.clocxyone.domain.Functions.formatString
import com.clocxy.clocxyone.presentation.ViewModels.MainScreenViewModel
import com.clocxy.clocxyone.ui.theme.CardBackgroundBlack
import com.clocxy.clocxyone.ui.theme.MainTextColorOrange
import com.clocxy.clocxyone.ui.theme.SecondaryTextColorOrange

fun enableDoNotDisturb(context: Context) {
    val notificationManager = context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

    if (notificationManager.isNotificationPolicyAccessGranted) {
        println("DND turned on")
        notificationManager.setInterruptionFilter(NotificationManager.INTERRUPTION_FILTER_NONE)
    }
}

fun disableDoNotDisturb(context: Context) {
    val notificationManager = context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

    if (notificationManager.isNotificationPolicyAccessGranted) {
        println("DND turned off")
        notificationManager.setInterruptionFilter(NotificationManager.INTERRUPTION_FILTER_ALL)
    }
}
@Composable
fun ZenModePage(
    modifier: Modifier ,
    context: Context ,
    navController: NavHostController ,
    viewModel: MainScreenViewModel,
    cardContainerColor: Color = CardBackgroundBlack,
    backgroundColor: Color = Color.Black,
    fontColor: Color = MainTextColorOrange,
    secondaryFontColor: Color = SecondaryTextColorOrange
) {

    DisposableEffect(Unit) {
        enableDoNotDisturb(context)

        onDispose {
            disableDoNotDisturb(context)
        }
    }

    val localConfiguration = LocalConfiguration.current
    val isPortrait =
        localConfiguration.orientation == android.content.res.Configuration.ORIENTATION_PORTRAIT
    val dateText =
        viewModel.date.collectAsState().value.toString()                        //"09 November 2024"
    val dayText = viewModel.day.collectAsState().value.toString()                         //"Sunday"
    val timeHourText = formatString( viewModel.hour.collectAsState().value.toString() )                   //"55"
    val timeMinuteText = formatString( viewModel.minute.collectAsState().value.toString()  )               //"55"
    val timeSecondText = formatString( viewModel.second.collectAsState().value.toString()      )           //"55"
    if (isPortrait) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(backgroundColor)
        ) {
            Column(
                modifier = Modifier.fillMaxSize(),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.SpaceEvenly
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceEvenly,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(text = timeHourText, fontSize = 70.sp, color = fontColor)
                    Text(text = ":", fontSize = 70.sp, color = fontColor)
                    Text(text = timeMinuteText, fontSize = 70.sp, color = fontColor)
                    Text(text = ":", fontSize = 70.sp, color = fontColor)
                    Text(text = timeSecondText, fontSize = 70.sp, color = fontColor)
                }

                Column {
                    Text(text = dateText, fontSize = 30.sp, color = fontColor)
                    Text(text = dayText, fontSize = 30.sp, color = fontColor)
                }


            }

        }
    } else {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(backgroundColor)
        ) {
            Column(
                modifier = Modifier.fillMaxSize(),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.SpaceEvenly
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceEvenly,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(text = timeHourText, fontSize = 70.sp, color = fontColor)
                    Text(text = ":", fontSize = 70.sp, color = fontColor)
                    Text(text = timeMinuteText, fontSize = 70.sp, color = fontColor)
                    Text(text = ":", fontSize = 70.sp, color = fontColor)
                    Text(text = timeSecondText, fontSize = 70.sp, color = fontColor)
                }

                Row {
                    Text(text = dateText, fontSize = 30.sp, color = fontColor)
                    Spacer(modifier = Modifier.size(20.dp))
                    Text(text = dayText, fontSize = 30.sp, color = fontColor)
                }


            }

        }

    }


}

