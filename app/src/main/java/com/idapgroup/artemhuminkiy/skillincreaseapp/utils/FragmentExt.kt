package com.idapgroup.artemhuminkiy.skillincreaseapp.utils

import android.support.v4.app.Fragment
import android.support.v7.app.AppCompatActivity
import com.idapgroup.artemhuminkiy.skillincreaseapp.R

fun AppCompatActivity.showFragment(fragmentToShow: Fragment){
    val transaction = supportFragmentManager.beginTransaction()
    transaction.setCustomAnimations(R.anim.slide_fragment_left, R.anim.slide_fragment_right,
                                    R.anim.slide_fragment_left, R.anim.slide_fragment_right)
    val tag = fragmentToShow.javaClass.name
    val fragment : Fragment = supportFragmentManager.findFragmentByTag(tag) ?: fragmentToShow
//    transaction.remove(supportFragmentManager.findFragmentById(R.id.fragment_container))
    transaction.replace(R.id.fragment_container, fragmentToShow, tag)
    transaction.addToBackStack(tag)
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
