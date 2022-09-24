package com.example.easy_money.others

import android.content.SharedPreferences
import com.example.easy_money.others.Constants.FIRST_TIME_KEY
import javax.inject.Inject

class SharedPreferenceHelper
@Inject
constructor(
    private val sharedPref: SharedPreferences,
) {

    private fun getBooleanValue(): Boolean {
        return sharedPref.getBoolean(FIRST_TIME_KEY, true)
    }

    private fun changeBooleanValueToFalse() {
        val editor = sharedPref.edit()
        editor.putBoolean(FIRST_TIME_KEY, false).apply()
    }

    fun isFirstTime(): Boolean {
        val isFirstLogApp = getBooleanValue()
        if (isFirstLogApp)
            changeBooleanValueToFalse()
        return isFirstLogApp
    }
}