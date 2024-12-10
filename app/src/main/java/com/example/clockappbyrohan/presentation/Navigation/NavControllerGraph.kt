package com.example.clockappbyrohan.presentation.Navigation

import android.content.Context
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.clockappbyrohan.hilt.hiltModule
import com.example.clockappbyrohan.presentation.ComposableScreens.AlarmDetailPage
import com.example.clockappbyrohan.presentation.ComposableScreens.AlarmHomePage
import com.example.clockappbyrohan.presentation.ComposableScreens.HomePage
import com.example.clockappbyrohan.presentation.ComposableScreens.StopWatchPage
import com.example.clockappbyrohan.presentation.ComposableScreens.ZenModePage
import com.example.clockappbyrohan.presentation.ViewModels.AlarmViewModel
import com.example.clockappbyrohan.presentation.ViewModels.MainScreenViewModel
import com.example.clockappbyrohan.presentation.ViewModels.StopWatchViewModel
import com.example.clockappbyrohan.ui.theme.CardBackgroundBlack
import com.example.clockappbyrohan.ui.theme.MainTextColorOrange
import com.example.clockappbyrohan.ui.theme.SecondaryTextColorOrange

@Composable
fun NavControllerGraph(
    modifier: Modifier = Modifier,
    context: Context = LocalContext.current,
    navController: NavHostController = rememberNavController(),
    viewModel: MainScreenViewModel = hiltViewModel(),
    stopwatchViewModel: StopWatchViewModel = hiltViewModel(),
    alarmViewModel: AlarmViewModel = hiltViewModel(),
    cardContainerColor: Color = CardBackgroundBlack,
    backgroundColor: Color = Color.Black,
    fontColor: Color = MainTextColorOrange,
    secondaryFontColor: Color = SecondaryTextColorOrange

) {

    val sharedPreferences = context.getSharedPreferences("sharedPrefs", Context.MODE_PRIVATE)
    NavHost(
        navController = navController,
        startDestination = "${sharedPreferences.getString("page", "home")}"
    ) {      // to cache the initial screen in shared preferences
        composable("home") {
            HomePage(
                viewModel = viewModel, navController = navController,
                cardContainerColor = cardContainerColor,
                backgroundColor = backgroundColor,
                fontColor = fontColor,
                secondaryFontColor = secondaryFontColor
            )
        }
        composable("zenMode") {
            ZenModePage(
                modifier = Modifier,
                context = context,
                navController = navController,
                viewModel = viewModel,
                cardContainerColor = cardContainerColor,
                backgroundColor = backgroundColor,
                fontColor = fontColor,
                secondaryFontColor = secondaryFontColor
            )
        }
        composable("stopWatch") {
            StopWatchPage(
                modifier = Modifier,
                context = context,
                navController = navController,
                viewModel = stopwatchViewModel,
                cardContainerColor = cardContainerColor,
                backgroundColor = backgroundColor,
                fontColor = fontColor,
                secondaryFontColor = secondaryFontColor
            )
        }
        composable("alarmPage") {
            AlarmHomePage(
                viewModel = alarmViewModel,
                context = context,
                navController = navController,
                modifier = Modifier,
                cardContainerColor = cardContainerColor,
                backgroundColor = backgroundColor,
                fontColor = fontColor,
                secondaryFontColor = secondaryFontColor
            )
        }
        composable("alarmDetailPage") {
            AlarmDetailPage(
                viewModel = alarmViewModel,
                context = context,
                navController = navController,
                modifier = Modifier,
                cardContainerColor = cardContainerColor,
                backgroundColor = backgroundColor,
                fontColor = fontColor,
                secondaryFontColor = secondaryFontColor
            )
        }


    }

}