package com.example.clockappbyrohan.presentation.Navigation

import android.content.Context
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.clockappbyrohan.hilt.hiltModule
import com.example.clockappbyrohan.presentation.ComposableScreens.HomePage
import com.example.clockappbyrohan.presentation.ComposableScreens.ZenModePage
import com.example.clockappbyrohan.presentation.ViewModels.MainScreenViewModel

@Composable
fun NavControllerGraph(
    modifier: Modifier = Modifier,
    context: Context = LocalContext.current,
    navController: NavHostController = rememberNavController(),
    viewModel: MainScreenViewModel = hiltViewModel()
) {

    val sharedPreferences = context.getSharedPreferences("sharedPrefs", Context.MODE_PRIVATE)
    NavHost(
        navController = navController,
        startDestination = "${sharedPreferences.getString("page", "home")}"
    ) {      // to cache the initial screen in shared preferences
        composable("home") {
            HomePage(viewModel = viewModel, navController = navController)
        }
        composable("zenMode"){
            ZenModePage(modifier = modifier ,context=context , navController = navController,viewModel=viewModel)
        }
    }

}