package com.idapgroup.artemhuminkiy.skillincreaseapp.introScreen

import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentPagerAdapter
import com.idapgroup.artemhuminkiy.skillincreaseapp.R

class ViewPagerAdapter(fm : FragmentManager) : FragmentPagerAdapter(fm) {
    private var layouts = listOf(R.layout.welcome_slide1,R.layout.welcome_slide2,R.layout.welcome_slide3,R.layout.welcome_slide4)

    override fun getItem(position: Int): Fragment =
            WelcomeSlideFragment.newInstance(layouts[position])

    override fun getCount(): Int = layouts.size

    }
