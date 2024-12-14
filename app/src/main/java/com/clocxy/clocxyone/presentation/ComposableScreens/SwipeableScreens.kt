package com.clocxy.clocxyone.presentation.ComposableScreens

import android.content.Context
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.clocxy.clocxyone.presentation.ViewModels.AlarmViewModel
import com.clocxy.clocxyone.presentation.ViewModels.MainScreenViewModel
import com.clocxy.clocxyone.presentation.ViewModels.SettingViewModel
import com.clocxy.clocxyone.presentation.ViewModels.StopWatchViewModel

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun SwipeableScreens(
    modifier: Modifier, context: Context,
    cardContainerColor: Color,
    backgroundColor: Color,
    fontColor: Color ,
    secondaryFontColor: Color ,
    navController: NavHostController,
    viewModel: MainScreenViewModel,
    stopwatchViewModel: StopWatchViewModel,
    alarmViewModel: AlarmViewModel,
    settingViewModel: SettingViewModel
) {
    val pagerState = rememberPagerState(initialPage = 1, pageCount = { 4 })

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(backgroundColor),
        verticalArrangement = Arrangement.SpaceBetween
    ) {
        // Pager
        HorizontalPager(
            state = pagerState,
            modifier = Modifier.weight(1f)
        ) { page ->
            when (page) {
                0 -> StopWatchPage(
                    modifier = Modifier,
                    context = context,
                    navController = navController,
                    viewModel = stopwatchViewModel,
                    cardContainerColor = cardContainerColor,
                    backgroundColor = backgroundColor,
                    fontColor = fontColor,
                    secondaryFontColor = secondaryFontColor
                )

                1 -> HomePage(
                    viewModel = viewModel, navController = navController,
                    cardContainerColor = cardContainerColor,
                    backgroundColor = backgroundColor,
                    fontColor = fontColor,
                    secondaryFontColor = secondaryFontColor, modifier = Modifier, context = context
                )

                2 -> AlarmHomePage(
                    viewModel = alarmViewModel,
                    context = context,
                    navController = navController,
                    modifier = Modifier,
                    cardContainerColor = cardContainerColor,
                    backgroundColor = backgroundColor,
                    fontColor = fontColor,
                    secondaryFontColor = secondaryFontColor
                )

                3 -> SettingScreenPage(
                    viewModel = settingViewModel,
                    context = context, modifier = Modifier, navController = navController,
                    cardContainerColor = cardContainerColor,
                    backgroundColor = backgroundColor,
                    fontColor = fontColor,
                    secondaryFontColor = secondaryFontColor
                )
            }

        }
    }
    Row(
        horizontalArrangement = Arrangement.Center, verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 40.dp)
    ) {
        repeat(pagerState.pageCount) { page ->
            val color = if (pagerState.currentPage == page) fontColor else secondaryFontColor
            Box(
                modifier = Modifier
                    .size(if (pagerState.currentPage == page) 16.dp else 13.dp)
                    .padding(4.dp)
                    .background(
                        color = color,
                        shape = if (pagerState.currentPage == page) RoundedCornerShape(
                            corner = CornerSize(3.dp)
                        ) else CircleShape
                    )
            )
        }
    }
}

