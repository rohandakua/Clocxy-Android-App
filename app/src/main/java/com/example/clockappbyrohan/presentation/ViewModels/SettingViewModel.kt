package com.example.clockappbyrohan.presentation.ViewModels

import android.content.Context
import android.content.Intent
import android.widget.Toast
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.clockappbyrohan.presentation.Activity.MainActivity
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class SettingViewModel @Inject constructor(
    @ApplicationContext private val context: Context
) : ViewModel() {
    val sharedPreferences = context.getSharedPreferences("sharedPrefs", Context.MODE_PRIVATE)

    /**
     * This viewModel is for managing the setting page of the app
     * It has to store three components in the shared preferences
     *      1. color =     three options { Orange(Default) , White , Black }
     *      2. backgroundColor =        two options { White , Black(Default) }
     *      3. font =           four options { Karla(Default) , Kanit , Pacifico , Inter }
     */
    private val _choosedColor: MutableStateFlow<String> =
        MutableStateFlow<String>(sharedPreferences.getString("color", "Orange").toString()) // default is Orange
    var choosedColor: MutableStateFlow<String> = _choosedColor
        private set

    fun setChoosedColor(color: String) {
        sharedPreferences.edit().putString("color", color).apply()
        _choosedColor.value = color
    }

    private val _choosedBackgroundColor: MutableStateFlow<String> =
        MutableStateFlow<String>(sharedPreferences.getString("backgroundColor", "Black").toString()) // default is Black
    var choosedBackgroundColor: MutableStateFlow<String> = _choosedBackgroundColor
        private set

    fun setChoosedBackgroundColor(color: String) {

        sharedPreferences.edit().putString("backgroundColor", color).apply()
        _choosedBackgroundColor.value = color
    }

    private val _choosedFont: MutableStateFlow<String> =
        MutableStateFlow<String>(sharedPreferences.getString("font", "Karla").toString()) // default is Karla
    var choosedFont: MutableStateFlow<String> = _choosedFont
        private set

    fun setChoosedFont(font: String) {
        sharedPreferences.edit().putString("font", font).apply()
        _choosedFont.value = font
    }

    fun savedClicked(
        fontToSet: String,
        colorToSet: String,
        backgroundColorToSet: String
    ) {
        if (colorToSet == backgroundColorToSet) {
            viewModelScope.launch {
                withContext(Dispatchers.Main) {
                    Toast.makeText(
                        context,
                        "Color and Background Color cannot be same",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }
        } else {
            setChoosedFont(fontToSet)
            setChoosedColor(colorToSet)
            setChoosedBackgroundColor(backgroundColorToSet)
            viewModelScope.launch {
                delay(200)
                withContext(Dispatchers.Main) {
                    Toast.makeText(context, "Saved Successfully", Toast.LENGTH_SHORT).show()
                    val intent = context.packageManager.getLaunchIntentForPackage(context.packageName)
                    intent?.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
                    context.startActivity(intent)
                    Runtime.getRuntime().exit(0)
                }
            }

        }

    }

}