package com.idapgroup.artemhuminkiy.skillincreaseapp.utils

import android.annotation.SuppressLint
import android.content.SharedPreferences
import com.idapgroup.artemhuminkiy.skillincreaseapp.Constants

@SuppressLint("ApplySharedPref")
fun SharedPreferences.firstTimeLaunched(boolean: Boolean) {
    edit()
            .putBoolean(Constants.IS_FIRST_TIME_LAUNCH, boolean)
            .commit()
}

fun SharedPreferences.isFrirstTimeLaunched(): Boolean {
    return this.getBoolean(Constants.IS_FIRST_TIME_LAUNCH, true)
}

