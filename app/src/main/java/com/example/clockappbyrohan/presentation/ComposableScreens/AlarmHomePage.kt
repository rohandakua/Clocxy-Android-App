package com.example.clockappbyrohan.presentation.ComposableScreens

import android.content.Context
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.clockappbyrohan.data.offline.alarm.Alarms
import com.example.clockappbyrohan.presentation.ViewModels.AlarmViewModel
import com.example.clockappbyrohan.ui.theme.CardBackgroundBlack
import com.example.clockappbyrohan.ui.theme.MainTextColorOrange
import com.example.clockappbyrohan.ui.theme.SecondaryTextColorOrange

//
//@Preview(showBackground = true, backgroundColor = 0xffffff, widthDp = 720, heightDp = 360)
//@Composable
//fun AlarmHomePagePreviewKarlaLandscape() {
//    CustomThemeKarla() {
//        AlarmHomePage()
//    }
//}
//
//@Preview(showBackground = true, backgroundColor = 0xffffff, widthDp = 720, heightDp = 360)
//@Composable
//fun AlarmHomePagePreviewKanitLandscape() {
//    CustomThemeKanit() {
//        AlarmHomePage()
//    }
//}
//
//@Preview(showBackground = true, backgroundColor = 0xffffff, widthDp = 720, heightDp = 360)
//@Composable
//fun AlarmHomePagePreviewInterLandscape() {
//    CustomThemeInter() {
//        AlarmHomePage()
//    }
//}
//
//@Preview(showBackground = true, backgroundColor = 0xffffff, widthDp = 720, heightDp = 360)
//@Composable
//fun AlarmHomePagePreviewPacificoLandscape() {
//    CustomThemePacifico() {
//        AlarmHomePage()
//    }
//}
//
//@Preview(showBackground = true, backgroundColor = 0xffffff)
//@Composable
//fun AlarmHomePagePreviewKarlaPortrait() {
//    CustomThemeKarla() {
//        AlarmHomePage()
//    }
//}
//
//@Preview(showBackground = true, backgroundColor = 0xffffff)
//@Composable
//fun AlarmHomePagePreviewKanitPortrait() {
//    CustomThemeKanit() {
//        AlarmHomePage()
//    }
//}
//
//@Preview(showBackground = true, backgroundColor = 0xffffff)
//@Composable
//fun AlarmHomePagePreviewInterPortrait() {
//    CustomThemeInter() {
//        AlarmHomePage()
//    }
//}
//
//@Preview(showBackground = true, backgroundColor = 0xffffff)
//@Composable
//fun AlarmHomePagePreviewPacificoPortrait() {
//    CustomThemePacifico() {
//        AlarmHomePage()
//    }
//}

@Composable
fun AlarmHomePage(
    modifier: Modifier = Modifier,
    context: Context = LocalContext.current,
    navController: NavHostController = rememberNavController(),
    viewModel: AlarmViewModel,
    cardContainerColor: Color = CardBackgroundBlack,
    backgroundColor: Color = Color.Black,
    fontColor: Color = MainTextColorOrange,
    secondaryFontColor: Color = SecondaryTextColorOrange
) {

    // saving the initial screen in shared preferences
    val sharedPreferences = context.getSharedPreferences("sharedPrefs", Context.MODE_PRIVATE)
    sharedPreferences.edit().putString("page", "alarmPage").apply()

    val configuration = LocalConfiguration.current
    val isPortrait =
        configuration.orientation == android.content.res.Configuration.ORIENTATION_PORTRAIT
    var list = mutableListOf(
        Alarms(1),
        Alarms(2),
        Alarms(3),
        Alarms(4),
        Alarms(5)
    ) // viewModel.alarmList.collectAsState()

    Box(
        modifier = modifier
            .fillMaxSize()
            .background(backgroundColor)  // Use backgroundColor parameter
    ) {
        Column(Modifier.fillMaxSize()) {
            Box(
                modifier
                    .fillMaxWidth()
                    .height(10.dp)
                    .background(backgroundColor)
            )  // Use backgroundColor parameter

            if (isPortrait) {
                Column(
                    modifier = Modifier.fillMaxSize(),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.SpaceEvenly
                ) {
                    Spacer(modifier = Modifier.height(25.dp))
                    LazyColumn(
                        modifier = Modifier
                            .fillMaxWidth()
                            .weight(.8f),
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.SpaceEvenly
                    ) {
                        for (i in list) {
                            item {
                                Spacer(modifier = Modifier.height(10.dp))
                                EachAlarmObjectInList(
                                    alarm = i,
                                    viewModel = viewModel,
                                    context = context,
                                    navController = navController,
                                    fontColor = fontColor, // Pass fontColor to the EachAlarmObjectInList
                                    secondaryFontColor = secondaryFontColor, // Pass secondaryFontColor to the EachAlarmObjectInList,
                                    cardContainerColor = cardContainerColor,
                                    backgroundColor = backgroundColor
                                )
                                Spacer(modifier = Modifier.height(10.dp))
                            }
                        }
                    }
                    Spacer(modifier = Modifier.weight(.02f))
                    Card(
                        modifier = Modifier
                            .fillMaxWidth(.3f)
                            .weight(.05f)
                            .clickable {
                                // go to zen mode
                            },
                        colors = CardDefaults.cardColors(
                            containerColor = cardContainerColor,  // Use cardContainerColor parameter
                            contentColor = backgroundColor
                        ),
                        shape = RoundedCornerShape(24.dp)
                    ) {
                        Box(
                            modifier = Modifier
                                .fillMaxSize(),
                            contentAlignment = Alignment.Center
                        ) {
                            Text(
                                text = "Create +",
                                fontSize = 20.sp,
                                color = fontColor // Use fontColor parameter
                            )
                        }
                    }
                    Spacer(modifier = Modifier.weight(.02f))
                }
            } else {
                Column(
                    modifier = modifier.fillMaxSize(),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.SpaceEvenly
                ) {
                    Spacer(modifier = Modifier.height(12.dp))
                    LazyColumn(
                        modifier = Modifier
                            .fillMaxWidth()
                            .weight(.65f),
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.SpaceEvenly
                    ) {
                        for (i in list) {
                            item {
                                Spacer(modifier = Modifier.height(10.dp))
                                EachAlarmObjectInList(
                                    alarm = i,
                                    viewModel = viewModel,
                                    context = context,
                                    navController = navController,
                                    fontColor = fontColor, // Pass fontColor to the EachAlarmObjectInList
                                    secondaryFontColor = secondaryFontColor, // Pass secondaryFontColor to the EachAlarmObjectInList,
                                    cardContainerColor = cardContainerColor,
                                    backgroundColor = backgroundColor
                                )
                                Spacer(modifier = Modifier.height(10.dp))
                            }
                        }
                    }
                    Spacer(modifier = Modifier.weight(.02f))
                    Card(
                        modifier = Modifier
                            .fillMaxWidth(.3f)
                            .weight(.1f)
                            .clickable {
                                // go to zen mode
                                navController.navigate("zenMode")
                            },
                        colors = CardDefaults.cardColors(
                            containerColor = cardContainerColor,  // Use cardContainerColor parameter
                            contentColor = backgroundColor
                        ),
                        shape = RoundedCornerShape(24.dp)
                    ) {
                        Box(
                            modifier = Modifier
                                .fillMaxSize(),
                            contentAlignment = Alignment.Center
                        ) {
                            Text(
                                text = "Create +",
                                fontSize = 20.sp,
                                color = fontColor // Use fontColor parameter
                            )
                        }
                    }
                    Spacer(modifier = Modifier.weight(.02f))
                }
            }
        }
    }
}
