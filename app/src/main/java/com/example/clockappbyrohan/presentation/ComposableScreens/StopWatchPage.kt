package com.example.clockappbyrohan.presentation.ComposableScreens

import android.content.Context
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.substring
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.clockappbyrohan.presentation.ViewModels.StopWatchViewModel
import com.example.clockappbyrohan.ui.theme.CardBackgroundBlack
import com.example.clockappbyrohan.ui.theme.MainTextColorOrange
import com.example.clockappbyrohan.ui.theme.SecondaryTextColorOrange



@Composable
fun StopWatchPage(
    modifier: Modifier ,
    context: Context ,
    navController: NavHostController ,
    viewModel: StopWatchViewModel,
    cardContainerColor: Color,
    backgroundColor: Color ,
    fontColor: Color ,
    secondaryFontColor: Color
) {
    // Defining text variables
    var time=  viewModel.time.collectAsState()
    val resetText = "Reset"
    var btnText =  if(viewModel.isRunning.collectAsState().value) "Stop" else "Start"

    // saving the initial screen in shared preferences
    val sharedPreferences = context.getSharedPreferences("sharedPrefs", Context.MODE_PRIVATE)
    sharedPreferences.edit().putString("page", "stopWatch").apply()


    val configuration = LocalConfiguration.current
    val isPortrait =
        configuration.orientation == android.content.res.Configuration.ORIENTATION_PORTRAIT
    Column(Modifier.fillMaxSize()) {
        Box(
            modifier
                .fillMaxWidth()
                .height(10.dp)
                .background(backgroundColor)
        )

        if (isPortrait) {
            Box(
                modifier = modifier
                    .fillMaxSize()
                    .background(backgroundColor)
            ) {
                Column(
                    Modifier.fillMaxSize(),
                    verticalArrangement = Arrangement.SpaceEvenly,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Spacer(modifier = Modifier.weight(.4f))

                    Card(
                        modifier = Modifier
                            .fillMaxWidth(.8f)
                            .weight(.4f), colors = CardDefaults.cardColors(
                            containerColor = cardContainerColor,
                            contentColor = backgroundColor
                        ), shape = RoundedCornerShape(24.dp)
                    ) {
                        Box(
                            modifier = Modifier
                                .padding(8.dp)
                                .fillMaxSize(),
                            contentAlignment = Alignment.Center
                        ) {
                            Row(
                                Modifier.fillMaxSize(), horizontalArrangement = Arrangement.SpaceEvenly, verticalAlignment = Alignment.CenterVertically

                            ) {
                                Text(
                                    text = time.value, fontSize = 31.sp, color = fontColor
                                )

                            }
                        }
                    }

                    Spacer(modifier = Modifier.weight(.4f))

                    Card(
                        modifier = Modifier
                            .fillMaxWidth(.8f)
                            .weight(.2f), colors = CardDefaults.cardColors(
                            containerColor = cardContainerColor,
                            contentColor = backgroundColor
                        ), shape = RoundedCornerShape(24.dp)
                    ) {
                        Box(
                            modifier = Modifier
                                .padding(8.dp)
                                .fillMaxSize(),
                            contentAlignment = Alignment.Center
                        ) {
                            Row(
                                modifier = Modifier.fillMaxSize(),
                                horizontalArrangement = Arrangement.Center,
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Card(
                                    modifier = Modifier
                                        .fillMaxWidth(.8f)
                                        .weight(.5f)
                                        .clickable {
                                            // reset the stopwatch
                                            viewModel.resetStopwatch()
                                        },
                                    colors = CardDefaults.cardColors(
                                        containerColor = cardContainerColor,
                                        contentColor = backgroundColor
                                    ),
                                    shape = RoundedCornerShape(24.dp)
                                ) {
                                    Box(
                                        modifier = Modifier
                                            .padding(8.dp)
                                            .fillMaxSize(),
                                        contentAlignment = Alignment.Center
                                    ) {
                                        Text(
                                            text = resetText, fontSize = 24.sp, color = fontColor
                                        )
                                    }
                                }
                                Card(
                                    modifier = Modifier
                                        .fillMaxWidth(.8f)
                                        .weight(.5f)
                                        .clickable {
                                            // start The stopwatch
                                            viewModel.startStop()

                                        },
                                    colors = CardDefaults.cardColors(
                                        containerColor = cardContainerColor,
                                        contentColor = backgroundColor
                                    ),
                                    shape = RoundedCornerShape(24.dp)
                                ) {
                                    Box(
                                        modifier = Modifier
                                            .padding(8.dp)
                                            .fillMaxSize(),
                                        contentAlignment = Alignment.Center
                                    ) {
                                        Text(
                                            text = btnText, fontSize = 24.sp, color = fontColor
                                        )
                                    }
                                }
                            }
                        }
                    }
                    Spacer(modifier = Modifier.weight(.3f))
                }
            }
        } else {
            Box(
                modifier = modifier
                    .fillMaxSize()
                    .background(backgroundColor)
            ) {
                Column(
                    Modifier.fillMaxSize(),
                    verticalArrangement = Arrangement.SpaceEvenly,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Spacer(modifier = Modifier.weight(.2f))

                    Card(
                        modifier = Modifier
                            .fillMaxWidth(.8f)
                            .weight(.30f), colors = CardDefaults.cardColors(
                            containerColor = cardContainerColor,
                            contentColor = backgroundColor
                        ), shape = RoundedCornerShape(24.dp)
                    ) {
                        Box(
                            modifier = Modifier
                                .padding(8.dp)
                                .fillMaxSize(),
                            contentAlignment = Alignment.Center
                        ) {
                            Column {
                                Text(
                                    text = time.value, fontSize = 31.sp, color = fontColor
                                )
                            }
                        }
                    }

                    Spacer(modifier = Modifier.weight(.15f))

                    Card(
                        modifier = Modifier
                            .fillMaxWidth(.8f)
                            .weight(.2f), colors = CardDefaults.cardColors(
                            containerColor = cardContainerColor,
                            contentColor = backgroundColor
                        ), shape = RoundedCornerShape(24.dp)
                    ) {
                        Box(
                            modifier = Modifier
                                .padding(8.dp)
                                .fillMaxSize(),
                            contentAlignment = Alignment.Center
                        ) {
                            Row(
                                modifier = Modifier.fillMaxSize(),
                                horizontalArrangement = Arrangement.Center,
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Card(
                                    modifier = Modifier
                                        .fillMaxWidth(.8f)
                                        .weight(.5f)
                                        .clickable {
                                            // reset the stopwatch
                                            viewModel.resetStopwatch()
                                        },
                                    colors = CardDefaults.cardColors(
                                        containerColor = cardContainerColor,
                                        contentColor = backgroundColor
                                    ),
                                    shape = RoundedCornerShape(24.dp)
                                ) {
                                    Box(
                                        modifier = Modifier
                                            .padding(8.dp)
                                            .fillMaxSize(),
                                        contentAlignment = Alignment.Center
                                    ) {
                                        Text(
                                            text = resetText, fontSize = 24.sp, color = fontColor
                                        )
                                    }
                                }
                                Card(
                                    modifier = Modifier
                                        .fillMaxWidth(.8f)
                                        .weight(.5f)
                                        .clickable {
                                            // start The stopwatch
                                            viewModel.startStop()

                                        },
                                    colors = CardDefaults.cardColors(
                                        containerColor = cardContainerColor,
                                        contentColor = backgroundColor
                                    ),
                                    shape = RoundedCornerShape(24.dp)
                                ) {
                                    Box(
                                        modifier = Modifier
                                            .padding(8.dp)
                                            .fillMaxSize(),
                                        contentAlignment = Alignment.Center
                                    ) {
                                        Text(
                                            text = btnText, fontSize = 24.sp, color = fontColor
                                        )
                                    }
                                }
                            }
                        }
                    }
                    Spacer(modifier = Modifier.weight(.15f))
                }
            }
        }
    }
}