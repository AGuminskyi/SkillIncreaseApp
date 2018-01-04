package com.idapgroup.artemhuminkiy.skillincreaseapp.utils

import android.annotation.SuppressLint
import android.content.Context
import android.content.SharedPreferences
import com.idapgroup.artemhuminkiy.skillincreaseapp.Constants

@SuppressLint("ApplySharedPref")
fun SharedPreferences.firstTimeLaunched(boolean: Boolean, context : Context){
    val prefs = context.getSharedPreferences(Constants.PREF_NAME, Context.MODE_PRIVATE)
    val editor =  prefs.edit()
    editor.putBoolean(Constants.IS_FIRST_TIME_LAUNCH, boolean)
    editor.commit()
}

fun SharedPreferences.isFrirstTimeLaunched(context: Context) : Boolean{
        val prefs = context.getSharedPreferences(Constants.PREF_NAME, Context.MODE_PRIVATE)
    return prefs.getBoolean(Constants.IS_FIRST_TIME_LAUNCH, true)
}
