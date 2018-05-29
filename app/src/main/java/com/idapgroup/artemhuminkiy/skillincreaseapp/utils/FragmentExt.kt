package com.idapgroup.artemhuminkiy.skillincreaseapp.utils

import android.support.v4.app.Fragment
import android.support.v7.app.AppCompatActivity
import com.idapgroup.artemhuminkiy.skillincreaseapp.R

fun AppCompatActivity.startFragment(fragment: Fragment, tag: String? = null){
    val fragmentManager = supportFragmentManager
    val fragmentTransaction = fragmentManager.beginTransaction()
    fragmentTransaction.replace(R.id.fragment_container, fragment, tag)
//    fragmentTransaction.add(R.id.fragment_container, fragment)
    fragmentTransaction.commit()
}

fun AppCompatActivity.startFirstFragment(fragment: Fragment, tag: String? = null){
    val fragmentTransaction = supportFragmentManager.beginTransaction()
    fragmentTransaction.add(R.id.fragment_container, fragment, tag)
    fragmentTransaction.commit()
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