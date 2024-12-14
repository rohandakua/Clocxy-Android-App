package com.clocxy.clocxyone.presentation.Activity

import android.Manifest
import android.content.pm.PackageManager
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.core.app.ActivityCompat
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.clocxy.clocxyone.presentation.Navigation.NavControllerGraph
import com.clocxy.clocxyone.ui.theme.CardBackgroundBlack
import com.clocxy.clocxyone.ui.theme.CardBackgroundWhite
import com.clocxy.clocxyone.ui.theme.CustomThemeInter
import com.clocxy.clocxyone.ui.theme.CustomThemeKanit
import com.clocxy.clocxyone.ui.theme.CustomThemeKarla
import com.clocxy.clocxyone.ui.theme.CustomThemePacifico
import com.clocxy.clocxyone.ui.theme.MainTextColorBlack
import com.clocxy.clocxyone.ui.theme.MainTextColorOrange
import com.clocxy.clocxyone.ui.theme.MainTextColorWhite
import com.clocxy.clocxyone.ui.theme.SecondaryTextColorBlack
import com.clocxy.clocxyone.ui.theme.SecondaryTextColorOrange
import com.clocxy.clocxyone.ui.theme.SecondaryTextColorWhite
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    fun requestPermission(){
        val LOCATION_PERMISSION_REQUEST_CODE = 100
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
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        val sharedPreferences = this.getSharedPreferences("sharedPrefs", MODE_PRIVATE)
        val context = this
        setContent {
            val navController: NavHostController = rememberNavController()

            if(sharedPreferences.getString("font", "Karla") =="Inter"){
                val fontColor = if( sharedPreferences.getString("color", "Orange") == "Black") MainTextColorBlack else if (sharedPreferences.getString("color", "Orange") == "Orange") MainTextColorOrange else MainTextColorWhite
                val secondaryFontColor = if( sharedPreferences.getString("color", "Orange") == "Black") SecondaryTextColorBlack else if (sharedPreferences.getString("color", "Orange") == "Orange") SecondaryTextColorOrange else SecondaryTextColorWhite
                val backgroundColor = if (sharedPreferences.getString("backgroundColor","Black") == "Black") Color.Black else Color.White
                val cardBackgrounColor = if(sharedPreferences.getString("backgroundColor","Black")=="Black") CardBackgroundBlack else CardBackgroundWhite

                CustomThemeInter {
                    Scaffold(
                        modifier = Modifier.fillMaxSize()
                    ) {
                        requestPermission()
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
                        requestPermission()
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
                        requestPermission()
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
                        requestPermission()
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
        }
    }
}



