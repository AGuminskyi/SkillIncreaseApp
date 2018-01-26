package com.idapgroup.artemhuminkiy.skillincreaseapp.introScreen

import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.os.Build
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.View
import android.view.WindowManager
import com.idapgroup.artemhuminkiy.skillincreaseapp.Constants
import com.idapgroup.artemhuminkiy.skillincreaseapp.R
import com.idapgroup.artemhuminkiy.skillincreaseapp.authorization.AuthorizationActivity
import com.idapgroup.artemhuminkiy.skillincreaseapp.utils.firstTimeLaunched
import kotlinx.android.synthetic.main.activity_welcome.*

class WelcomeActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        if (Build.VERSION.SDK_INT >= 21) {
            window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_FULLSCREEN
            window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
        }

        setContentView(R.layout.activity_welcome)

        changeStatusBarColor()

        val viewPagerAdapter = ViewPagerAdapter(supportFragmentManager)
        viewPager.adapter = viewPagerAdapter
        indicator.setViewPager(viewPager)

        nextButton.setOnClickListener {
            val position = viewPager.currentItem
            if (position != viewPager.adapter.count - 1) {
                viewPager.currentItem = position + 1
            } else
                launchHomeScreen()
        }

        skipButton.setOnClickListener {
            launchHomeScreen()
        }

        viewPager.addOnPageChangeListener(PageSelectedListener{
            if (it == viewPager.adapter.count - 1) {
                nextButton.text = getString(R.string.go_it)
                skipButton.visibility = View.GONE
            } else {
                nextButton.text = getString(R.string.next)
                skipButton.visibility = View.VISIBLE
            }
        })


    }

    private fun changeStatusBarColor() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            val window = window
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
            window.statusBarColor = Color.TRANSPARENT
        }
    }

    private fun launchHomeScreen() {
        val prefs = getSharedPreferences(Constants.PREF_NAME, Context.MODE_PRIVATE)
        prefs.firstTimeLaunched(true)
        startActivity(Intent(this, AuthorizationActivity::class.java))
        finish()
    }
}

