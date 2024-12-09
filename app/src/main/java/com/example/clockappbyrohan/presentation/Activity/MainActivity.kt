package com.example.clockappbyrohan.presentation.Activity

import android.Manifest
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
import androidx.compose.ui.tooling.preview.Preview
import androidx.core.app.ActivityCompat
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.clockappbyrohan.presentation.ComposableScreens.HomePage
import com.example.clockappbyrohan.presentation.ComposableScreens.ZenModePage
import com.example.clockappbyrohan.presentation.Navigation.NavControllerGraph
import com.example.clockappbyrohan.presentation.ViewModels.MainScreenViewModel
import com.example.clockappbyrohan.ui.theme.ClockAppByRohanTheme
import com.example.clockappbyrohan.ui.theme.CustomThemeInter
import com.example.clockappbyrohan.ui.theme.CustomThemeKanit
import com.example.clockappbyrohan.ui.theme.CustomThemeKarla
import com.example.clockappbyrohan.ui.theme.CustomThemePacifico
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
//    private val viewModel: MainScreenViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        // viewModel.dosth()
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
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            ActivityCompat.requestPermissions(
                this,
                arrayOf(
                    Manifest.permission.ACCESS_FINE_LOCATION,
                    Manifest.permission.ACCESS_COARSE_LOCATION,
                    Manifest.permission.SCHEDULE_EXACT_ALARM
                ),
                LOCATION_PERMISSION_REQUEST_CODE
            )
        }
//        viewModel.triedTimes=0



        val sharedPreferences = this.getSharedPreferences("sharedPrefs", MODE_PRIVATE)
        val context = this


        setContent {
            val navController: NavHostController = rememberNavController()
            enableEdgeToEdge()
//            CustomThemeInter {
//                Scaffold(
//                    modifier = Modifier.fillMaxSize()
//                ) {
//                    NavControllerGraph(
//                        modifier = Modifier.padding(it),
//                        context = context,
//                        navController = navController
//                    )
//
//                }
//            }
//            CustomThemeKarla {
//                Scaffold(
//                    modifier = Modifier.fillMaxSize()
//                ) {
//                    NavControllerGraph(
//                        modifier = Modifier.padding(it),
//                        context = context,
//                        navController = navController
//                    )
//
//                }
//            }
            CustomThemeKanit {
//                Scaffold(
//                    modifier = Modifier.fillMaxSize()
//                ) {
//                    NavControllerGraph(
//                        modifier = Modifier.padding(it),
//                        context = context,
//                        navController = navController
//                    )
//
//                }
//            }
//            CustomThemePacifico {
                Scaffold(
                    modifier = Modifier.fillMaxSize()
                ) {
                    NavControllerGraph(
                        modifier = Modifier.padding(it),
                        context = context,
                        navController = navController
                    )

                }
            }


        }
    }
}



