package com.idapgroup.artemhuminkiy.skillincreaseapp.introScreen

import android.support.v4.view.ViewPager

open class PageChangeListener(
        private val pageStateChanged: ((Int) -> Unit)? = null,
        private val pageScrolled: (Int, Float, Int) -> Unit = { _, _ ,_ -> },
        private val pageSelected: (Int) -> Unit = {}
): ViewPager.OnPageChangeListener {

    override fun onPageScrollStateChanged(state: Int) {
        pageStateChanged?.invoke(state)
    }

    override fun onPageScrolled(position: Int, positionOffset: Float, positionOffsetPixels: Int) {
        pageScrolled.invoke(position, positionOffset, positionOffsetPixels)
    }

    override fun onPageSelected(position: Int) {
        pageSelected.invoke(position)
    }
}

class PageSelectedListener(myPageSelected : (Int) -> Unit) : PageChangeListener(pageSelected = myPageSelected)