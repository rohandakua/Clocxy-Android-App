package com.example.clockappbyrohan.presentation.ViewModels

import android.content.Context
import android.widget.Toast
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.Dispatchers
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
        MutableStateFlow<String>("Orange") // default is Orange
    var choosedColor: MutableStateFlow<String> = _choosedColor
        private set

    fun setChoosedColor(color: String) {
        _choosedColor.value = color
        sharedPreferences.edit().putString("color", color).apply()
    }

    private val _choosedBackgroundColor: MutableStateFlow<String> =
        MutableStateFlow<String>("Black") // default is Black
    var choosedBackgroundColor: MutableStateFlow<String> = _choosedBackgroundColor
        private set

    fun setChoosedBackgroundColor(color: String) {

        _choosedBackgroundColor.value = color
        sharedPreferences.edit().putString("backgroundColor", color).apply()
    }

    private val _choosedFont: MutableStateFlow<String> =
        MutableStateFlow<String>("Karla") // default is Karla
    var choosedFont: MutableStateFlow<String> = _choosedFont
        private set

    fun setChoosedFont(font: String) {
        _choosedFont.value = font
        sharedPreferences.edit().putString("font", font).apply()
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
                withContext(Dispatchers.Main) {
                    Toast.makeText(context, "Saved Successfully", Toast.LENGTH_SHORT).show()
                }
            }
        }

    }

}