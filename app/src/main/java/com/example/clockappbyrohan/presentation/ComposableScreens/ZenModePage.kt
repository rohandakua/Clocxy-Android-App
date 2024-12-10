package com.example.clockappbyrohan.presentation.ComposableScreens

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
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.clockappbyrohan.domain.Functions.formatString
import com.example.clockappbyrohan.presentation.ViewModels.MainScreenViewModel
import com.example.clockappbyrohan.ui.theme.CardBackgroundBlack
import com.example.clockappbyrohan.ui.theme.CustomThemeInter
import com.example.clockappbyrohan.ui.theme.CustomThemeKanit
import com.example.clockappbyrohan.ui.theme.CustomThemeKarla
import com.example.clockappbyrohan.ui.theme.CustomThemePacifico
import com.example.clockappbyrohan.ui.theme.MainTextColorOrange
import com.example.clockappbyrohan.ui.theme.SecondaryTextColorOrange

//@Preview(showBackground = true, backgroundColor = 0xffffff, widthDp = 720, heightDp = 360)
//@Composable
//fun ZenModePreviewPacificoLandscape() {
//    CustomThemePacifico() {
//        ZenModePage()
//    }
//}
//
//@Preview(showBackground = true, backgroundColor = 0xffffff, widthDp = 720, heightDp = 360)
//@Composable
//fun ZenModePreviewInterLandscape() {
//    CustomThemeInter() {
//        ZenModePage()
//    }
//}
//
//@Preview(showBackground = true, backgroundColor = 0xffffff, widthDp = 720, heightDp = 360)
//@Composable
//fun ZenModePreviewKarlaLandscape() {
//    CustomThemeKarla() {
//        ZenModePage()
//    }
//}
//
//@Preview(showBackground = true, backgroundColor = 0xffffff, widthDp = 720, heightDp = 360)
//@Composable
//fun ZenModePreviewKanitLandscape() {
//    CustomThemeKanit() {
//        ZenModePage()
//    }
//}
//
//@Preview(showBackground = true, backgroundColor = 0xffffff)
//@Composable
//fun ZenModePreviewKanitPortrait() {
//    CustomThemeKanit() {
//        ZenModePage()
//    }
//}
//
//@Preview(showBackground = true, backgroundColor = 0xffffff)
//@Composable
//fun ZenModePreviewPacificoPortrait() {
//    CustomThemePacifico() {
//        ZenModePage()
//    }
//}
//
//@Preview(showBackground = true, backgroundColor = 0xffffff)
//@Composable
//fun ZenModePreviewInterPortrait() {
//    CustomThemeInter() {
//        ZenModePage()
//    }
//}
//
//@Preview(showBackground = true, backgroundColor = 0xffffff)
//@Composable
//fun ZenModePreviewKarlaPortrait() {
//    CustomThemeKarla() {
//        ZenModePage()
//    }
//}


@Composable
fun ZenModePage(
    modifier: Modifier = Modifier,
    context: Context = LocalContext.current,
    navController: NavHostController = rememberNavController(),
    viewModel: MainScreenViewModel,
    cardContainerColor: Color = CardBackgroundBlack,
    backgroundColor: Color = Color.Black,
    fontColor: Color = MainTextColorOrange,
    secondaryFontColor: Color = SecondaryTextColorOrange
) {
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
                    Text(text = timeHourText, fontSize = 70.sp)
                    Text(text = ":", fontSize = 70.sp)
                    Text(text = timeMinuteText, fontSize = 70.sp)
                    Text(text = ":", fontSize = 70.sp)
                    Text(text = timeSecondText, fontSize = 70.sp)
                }

                Column {
                    Text(text = dateText, fontSize = 30.sp)
                    Text(text = dayText, fontSize = 30.sp)
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
                    Text(text = timeHourText, fontSize = 70.sp)
                    Text(text = ":", fontSize = 70.sp)
                    Text(text = timeMinuteText, fontSize = 70.sp)
                    Text(text = ":", fontSize = 70.sp)
                    Text(text = timeSecondText, fontSize = 70.sp)
                }

                Row {
                    Text(text = dateText, fontSize = 30.sp)
                    Spacer(modifier = Modifier.size(20.dp))
                    Text(text = dayText, fontSize = 30.sp)
                }


            }

        }

    }


}