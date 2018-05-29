package com.idapgroup.artemhuminkiy.skillincreaseapp.utils

import android.support.v4.app.Fragment
import android.support.v7.app.AppCompatActivity
import com.idapgroup.artemhuminkiy.skillincreaseapp.R

fun AppCompatActivity.showFragment(fragmentToShow: Fragment){
    val transaction = supportFragmentManager.beginTransaction()
    val tag = fragmentToShow.javaClass.name
    val fragment : Fragment = supportFragmentManager.findFragmentByTag(tag) ?: fragmentToShow
    transaction.replace(R.id.fragment_container, fragmentToShow, tag)
    transaction.addToBackStack(null)
    transaction.commit()
}

fun AppCompatActivity.checkIsCurrentFragmentEquals(fragmentTag: String): Boolean{
    val fragment = supportFragmentManager.findFragmentByTag(fragmentTag)
    return fragment != null && fragment.isVisible
}

fun AppCompatActivity.getCurrentFragment(fragmentContainerId: Int): Fragment?{
    val fragment = supportFragmentManager.findFragmentById(fragmentContainerId)
    return if(fragment != null && fragment.isVisible) {
        fragment
    } else {
        logd("No fragments found")
        null
    }
}

fun Fragment.getCurrentFragmentTag(): String{
    return javaClass.simpleName
}