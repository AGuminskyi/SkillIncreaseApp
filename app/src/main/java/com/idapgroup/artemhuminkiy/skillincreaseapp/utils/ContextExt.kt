package com.idapgroup.artemhuminkiy.skillincreaseapp.utils

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.net.ConnectivityManager
import android.os.Bundle
import com.idapgroup.artemhuminkiy.skillincreaseapp.Constants

fun Context.startActivity(bundle: Bundle, clazz: Class<out Activity>) {
    val intent = Intent(this, clazz)
    intent.putExtras(bundle)
    startActivity(intent)
}

fun Intent.getExtrasExt() = this.extras.getString(Constants.USER_NAME)!!

fun Context.connected(): Boolean {
    val manager = this.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
    return manager.activeNetworkInfo != null && manager.activeNetworkInfo.isConnectedOrConnecting
}

