package com.idapgroup.artemhuminkiy.skillincreaseapp

import android.app.ProgressDialog
import android.content.Context
import android.os.Bundle
import android.view.animation.Animation
import android.view.animation.RotateAnimation
import kotlinx.android.synthetic.main.custom_dialog.*

@Suppress("DEPRECATION")
class CustomProgressDialog(context: Context) : ProgressDialog(context) {

    private val rotate by lazy { RotateAnimation(0f, 360f, RotateAnimation.RELATIVE_TO_SELF, 0.5f, RotateAnimation.RELATIVE_TO_SELF, 0.5f) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.custom_dialog)
    }

    override fun show() {
        super.show()
        with(rotate, {
            repeatMode = 1
            repeatCount = Animation.INFINITE
            duration = 1500
        })
        logoImage.startAnimation(rotate)
    }

    override fun dismiss() {
        super.dismiss()
        logoImage.clearAnimation()
    }
}