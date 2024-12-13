package com.example.clockappbyrohan.presentation.Activity

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.core.app.ActivityCompat
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.clockappbyrohan.presentation.ComposableScreens.HomePage
import com.example.clockappbyrohan.presentation.ComposableScreens.SwipeableScreens
import com.example.clockappbyrohan.presentation.ComposableScreens.ZenModePage
import com.example.clockappbyrohan.presentation.Navigation.NavControllerGraph
import com.example.clockappbyrohan.presentation.ViewModels.MainScreenViewModel
import com.example.clockappbyrohan.ui.theme.CardBackgroundBlack
import com.example.clockappbyrohan.ui.theme.CardBackgroundWhite
import com.example.clockappbyrohan.ui.theme.ClockAppByRohanTheme
import com.example.clockappbyrohan.ui.theme.CustomThemeInter
import com.example.clockappbyrohan.ui.theme.CustomThemeKanit
import com.example.clockappbyrohan.ui.theme.CustomThemeKarla
import com.example.clockappbyrohan.ui.theme.CustomThemePacifico
import com.example.clockappbyrohan.ui.theme.MainTextColorBlack
import com.example.clockappbyrohan.ui.theme.MainTextColorOrange
import com.example.clockappbyrohan.ui.theme.MainTextColorWhite
import com.example.clockappbyrohan.ui.theme.SecondaryTextColorBlack
import com.example.clockappbyrohan.ui.theme.SecondaryTextColorOrange
import com.example.clockappbyrohan.ui.theme.SecondaryTextColorWhite
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
//    private val viewModel: MainScreenViewModel by viewModels()
    override fun onRestart() {
        super.onRestart()
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        // Request Permissions

//        // viewModel.dosth()
        val LOCATION_PERMISSION_REQUEST_CODE = 100
        // Request permissions
        if (ActivityCompat.checkSelfPermission(
                this,
                Manifest.permission.ACCESS_FINE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
                this,
                Manifest.permission.ACCESS_COARSE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
                this,
                Manifest.permission.SCHEDULE_EXACT_ALARM
            ) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
                this,
                Manifest.permission.READ_MEDIA_AUDIO
            ) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
                this,
                Manifest.permission.POST_NOTIFICATIONS
            ) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
                this,
                Manifest.permission.MANAGE_EXTERNAL_STORAGE
            ) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
                this,
                Manifest.permission.ACCESS_NOTIFICATION_POLICY
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            ActivityCompat.requestPermissions(
                this,
                arrayOf(
                    Manifest.permission.ACCESS_FINE_LOCATION,
                    Manifest.permission.ACCESS_COARSE_LOCATION,
                    Manifest.permission.SCHEDULE_EXACT_ALARM,
                    Manifest.permission.POST_NOTIFICATIONS,
                    Manifest.permission.READ_MEDIA_AUDIO,
                    Manifest.permission.MANAGE_EXTERNAL_STORAGE,
                    Manifest.permission.ACCESS_NOTIFICATION_POLICY
                ),
                LOCATION_PERMISSION_REQUEST_CODE
            )
        }
////        viewModel.triedTimes=0
//


        val sharedPreferences = this.getSharedPreferences("sharedPrefs", MODE_PRIVATE)
        val context = this


        setContent {
            val navController: NavHostController = rememberNavController()
            enableEdgeToEdge()


            if(sharedPreferences.getString("font", "Karla") =="Inter"){
                val fontColor = if( sharedPreferences.getString("color", "Orange") == "Black") MainTextColorBlack else if (sharedPreferences.getString("color", "Orange") == "Orange") MainTextColorOrange else MainTextColorWhite
                val secondaryFontColor = if( sharedPreferences.getString("color", "Orange") == "Black") SecondaryTextColorBlack else if (sharedPreferences.getString("color", "Orange") == "Orange") SecondaryTextColorOrange else SecondaryTextColorWhite


                val backgroundColor = if (sharedPreferences.getString("backgroundColor","Black") == "Black") Color.Black else Color.White
                val cardBackgrounColor = if(sharedPreferences.getString("backgroundColor","Black")=="Black") CardBackgroundBlack else CardBackgroundWhite

                CustomThemeInter {
                    Scaffold(
                        modifier = Modifier.fillMaxSize()
                    ) {
                        NavControllerGraph(
                            modifier = Modifier.padding(it),
                            context = context,
                            navController = navController,
                            cardContainerColor = cardBackgrounColor,
                            backgroundColor = backgroundColor,
                            fontColor = fontColor,
                            secondaryFontColor = secondaryFontColor
                        )

                    }
                }

            }else if(sharedPreferences.getString("font", "Karla")=="Kanit"){
                val fontColor = if( sharedPreferences.getString("color", "Orange") == "Black") MainTextColorBlack else if (sharedPreferences.getString("color", "Orange") == "Orange") MainTextColorOrange else MainTextColorWhite
                val secondaryFontColor = if( sharedPreferences.getString("color", "Orange") == "Black") SecondaryTextColorBlack else if (sharedPreferences.getString("color", "Orange") == "Orange") SecondaryTextColorOrange else SecondaryTextColorWhite


                val backgroundColor = if (sharedPreferences.getString("backgroundColor","Black") == "Black") Color.Black else Color.White
                val cardBackgrounColor = if(sharedPreferences.getString("backgroundColor","Black")=="Black") CardBackgroundBlack else CardBackgroundWhite

                CustomThemeKanit {
                    Scaffold(
                        modifier = Modifier.fillMaxSize()
                    ) {
                        NavControllerGraph(
                            modifier = Modifier.padding(it),
                            context = context,
                            navController = navController,
                            cardContainerColor = cardBackgrounColor,
                            backgroundColor = backgroundColor,
                            fontColor = fontColor,
                            secondaryFontColor = secondaryFontColor
                        )

                    }
                }

            }else if(sharedPreferences.getString("font", "Karla")=="Pacifico"){
                val fontColor = if( sharedPreferences.getString("color", "Orange") == "Black") MainTextColorBlack else if (sharedPreferences.getString("color", "Orange") == "Orange") MainTextColorOrange else MainTextColorWhite
                val secondaryFontColor = if( sharedPreferences.getString("color", "Orange") == "Black") SecondaryTextColorBlack else if (sharedPreferences.getString("color", "Orange") == "Orange") SecondaryTextColorOrange else SecondaryTextColorWhite


                val backgroundColor = if (sharedPreferences.getString("backgroundColor","Black") == "Black") Color.Black else Color.White
                val cardBackgrounColor = if(sharedPreferences.getString("backgroundColor","Black")=="Black") CardBackgroundBlack else CardBackgroundWhite

                CustomThemePacifico {
                    Scaffold(
                        modifier = Modifier.fillMaxSize()
                    ) {
                        NavControllerGraph(
                            modifier = Modifier.padding(it),
                            context = context,
                            navController = navController,
                            cardContainerColor = cardBackgrounColor,
                            backgroundColor = backgroundColor,
                            fontColor = fontColor,
                            secondaryFontColor = secondaryFontColor
                        )

                    }
                }

            }else{
                CustomThemeKarla {
                    val fontColor = if( sharedPreferences.getString("color", "Orange") == "Black") MainTextColorBlack else if (sharedPreferences.getString("color", "Orange") == "Orange") MainTextColorOrange else MainTextColorWhite
                    val secondaryFontColor = if( sharedPreferences.getString("color", "Orange") == "Black") SecondaryTextColorBlack else if (sharedPreferences.getString("color", "Orange") == "Orange") SecondaryTextColorOrange else SecondaryTextColorWhite


                    val backgroundColor = if (sharedPreferences.getString("backgroundColor","Black") == "Black") Color.Black else Color.White
                    val cardBackgrounColor = if(sharedPreferences.getString("backgroundColor","Black")=="Black") CardBackgroundBlack else CardBackgroundWhite

                    Scaffold(
                        modifier = Modifier.fillMaxSize()
                    ) {
                        NavControllerGraph(
                            modifier = Modifier.padding(it),
                            context = context,
                            navController = navController,
                            cardContainerColor = cardBackgrounColor,
                            backgroundColor = backgroundColor,
                            fontColor = fontColor,
                            secondaryFontColor = secondaryFontColor
                        )

                    }
                }

            }





           // permissionHandler.requestPermissions()






        }
    }
}



