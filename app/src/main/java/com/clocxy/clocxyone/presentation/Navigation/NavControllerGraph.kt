package com.clocxy.clocxyone.presentation.Navigation

import android.content.Context
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.clocxy.clocxyone.presentation.ComposableScreens.AlarmDetailPage
import com.clocxy.clocxyone.presentation.ComposableScreens.AlarmHomePage
import com.clocxy.clocxyone.presentation.ComposableScreens.HomePage
import com.clocxy.clocxyone.presentation.ComposableScreens.StopWatchPage
import com.clocxy.clocxyone.presentation.ComposableScreens.SwipeableScreens
import com.clocxy.clocxyone.presentation.ComposableScreens.ZenModePage
import com.clocxy.clocxyone.presentation.ViewModels.AlarmViewModel
import com.clocxy.clocxyone.presentation.ViewModels.MainScreenViewModel
import com.clocxy.clocxyone.presentation.ViewModels.SettingViewModel
import com.clocxy.clocxyone.presentation.ViewModels.StopWatchViewModel

@Composable
fun NavControllerGraph(
    modifier: Modifier = Modifier,
    context: Context = LocalContext.current,
    navController: NavHostController = rememberNavController(),
    viewModel: MainScreenViewModel = hiltViewModel(),
    stopwatchViewModel: StopWatchViewModel = hiltViewModel(),
    alarmViewModel: AlarmViewModel = hiltViewModel(),
    cardContainerColor: Color,
    backgroundColor: Color ,
    fontColor: Color ,
    secondaryFontColor: Color ,
    settingViewModel: SettingViewModel = hiltViewModel()

) {

    val sharedPreferences = context.getSharedPreferences("sharedPrefs", Context.MODE_PRIVATE)
    NavHost(
        navController = navController,
        startDestination = "swipeScreen"//"${sharedPreferences.getString("page", "home")}"
    ) {      // to cache the initial screen in shared preferences
        composable("swipeScreen") {
            SwipeableScreens(
                modifier = Modifier, navController = navController,
                cardContainerColor = cardContainerColor,
                backgroundColor = backgroundColor,
                fontColor = fontColor,
                secondaryFontColor = secondaryFontColor,
                viewModel = viewModel,
                stopwatchViewModel = stopwatchViewModel,
                alarmViewModel = alarmViewModel,
                settingViewModel = settingViewModel, context = context
            )
        }
        composable("home") {
            HomePage(
                viewModel = viewModel, navController = navController,
                cardContainerColor = cardContainerColor,
                backgroundColor = backgroundColor,
                fontColor = fontColor,
                secondaryFontColor = secondaryFontColor, context = context, modifier = Modifier
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