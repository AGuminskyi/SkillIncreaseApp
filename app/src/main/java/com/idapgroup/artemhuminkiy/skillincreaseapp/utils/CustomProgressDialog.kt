package com.idapgroup.artemhuminkiy.skillincreaseapp.utils

import android.app.AlertDialog
import android.app.Dialog
import android.app.DialogFragment
import android.os.Bundle
import android.view.animation.Animation
import android.view.animation.RotateAnimation
import android.widget.ImageView
import com.idapgroup.artemhuminkiy.skillincreaseapp.R

class CustomProgressDialog : DialogFragment() {

    private val rotate by lazy { RotateAnimation(0f, 360f, RotateAnimation.RELATIVE_TO_SELF, 0.5f, RotateAnimation.RELATIVE_TO_SELF, 0.5f) }
    private val builder by lazy { AlertDialog.Builder(activity) }
    private lateinit var image: ImageView

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val view = activity.layoutInflater.inflate(R.layout.custom_dialog, null, false)
        builder.setView(view)
        image = view.findViewById(R.id.logoImage)
        animate()
        val alertDialog = builder.create()
        isCancelable = false
        alertDialog.setCanceledOnTouchOutside(false)
        return alertDialog
    }

    private fun animate() {
        with(rotate, {
            repeatMode = 1
            repeatCount = Animation.INFINITE
            duration = 1500
        })
        image.startAnimation(rotate)
    }

    override fun dismiss() {
        super.dismiss()
        if(::image.isInitialized) {
            image.clearAnimation()
        }
    }
}