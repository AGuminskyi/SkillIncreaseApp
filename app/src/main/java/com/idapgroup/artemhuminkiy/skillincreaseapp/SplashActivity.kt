package com.idapgroup.artemhuminkiy.skillincreaseapp

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.support.v7.app.AppCompatActivity
import android.view.animation.RotateAnimation
import com.idapgroup.artemhuminkiy.skillincreaseapp.authorization.AuthorizationActivity
import com.idapgroup.artemhuminkiy.skillincreaseapp.introScreen.WelcomeActivity
import com.idapgroup.artemhuminkiy.skillincreaseapp.utils.startActivity
import kotlinx.android.synthetic.main.activity_splash.*

class SplashActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)

        Handler().postDelayed({ flipLogo() }, 400)
        Handler().postDelayed({ checkFirstScreen() }, 1900)
    }

    private fun flipLogo() {
        val rotate = RotateAnimation(0f, 360f, RotateAnimation.RELATIVE_TO_SELF, 0.5f, RotateAnimation.RELATIVE_TO_SELF, 0.5f)
        rotate.duration = 1500
        progress.startAnimation(rotate)
    }

    private fun checkFirstScreen() {
        val prefs = getSharedPreferences(Constants.PREF_NAME,Context.MODE_PRIVATE)
        val isUser = prefs.getString(Constants.USER_NAME, null)
        val isWelcome = prefs.getBoolean(Constants.IS_FIRST_TIME_LAUNCH, false)

        if (isUser != null && isWelcome) {
            val bundle = Bundle()
            bundle.putString(Constants.USER_NAME, isUser)
            startActivity(bundle, MainActivity::class.java)
            finish()
        } else
            if (isWelcome && isUser == null) {
                startActivity(Intent(this, AuthorizationActivity::class.java))
                finish()
            } else {
                startActivity(Intent(this, WelcomeActivity::class.java))
                finish()
            }
    }
}
