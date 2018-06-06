package com.idapgroup.artemhuminkiy.skillincreaseapp.utils

import android.Manifest
import android.annotation.TargetApi
import android.app.Activity
import android.content.Intent
import android.os.Build
import android.support.annotation.NonNull
import android.support.annotation.RequiresPermission
import android.support.v4.app.Fragment

object ImageUtils {
    val IMAGE_PATH_KEY = "image_path_key"

    @RequiresPermission(allOf = arrayOf(Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.CAMERA))
    fun takePhoto(fragment: Fragment, requestCode: Int) {
        startActivityForResult(fragment, requestCode, "take_photo")
    }

    @RequiresPermission(allOf = arrayOf(Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.CAMERA))
    fun takePhoto(activity: Activity, requestCode: Int) {
        startActivityForResult(activity, requestCode, "take_photo")
    }

    @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
    @RequiresPermission(Manifest.permission.READ_EXTERNAL_STORAGE)
    fun getImageFromGallery(fragment: Fragment, requestCode: Int) {
        startActivityForResult(fragment, requestCode, "gallery")
    }

    @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
    @RequiresPermission(Manifest.permission.READ_EXTERNAL_STORAGE)
    fun getImageFromGallery(activity: Activity, requestCode: Int) {
        startActivityForResult(activity, requestCode, "gallery")
    }

    private fun startActivityForResult(activity: Activity, requestCode: Int, action: String) {
        val intent = Intent(activity, ImageUtilsActivity::class.java)
        intent.putExtra("action", action)
        activity.startActivityForResult(intent, requestCode)
    }

    private fun startActivityForResult(fragment: Fragment, requestCode: Int, action: String) {
        val intent = Intent(fragment.activity, ImageUtilsActivity::class.java)
        intent.putExtra("action", action)
        fragment.startActivityForResult(intent, requestCode)
    }

    fun getImagePath(@NonNull data: Intent): String? {
        return if (data.extras == null) {
            null
        } else data.extras!!.getString(ImageUtils.IMAGE_PATH_KEY)
    }
}