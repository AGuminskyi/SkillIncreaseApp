
package com.idapgroup.artemhuminkiy.skillincreaseapp.utils

import android.Manifest
import android.annotation.TargetApi
import android.app.Activity
import android.content.Context
import android.content.Intent
import android.database.Cursor
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Matrix
import android.media.ExifInterface
import android.net.Uri
import android.os.Build
import android.provider.MediaStore
import android.support.annotation.RequiresPermission
import android.support.v4.app.Fragment
import android.util.Log
import java.io.File
import java.io.FileOutputStream
import java.io.IOException
import java.text.SimpleDateFormat
import java.util.*

/**
 * Created by eugene.kotsogub on 4/18/16.
 * Utils for images operations (add image, rotate e/t/c)
 */
object ImageUtils {
    private val TAG = "ImageUtils"
    private val TMP_DIR = "tmp"

    private fun generateFilename(ext: String = "jpg"): String {
        return SimpleDateFormat("yyyyMMdd_HHmmssSSS", Locale.US).format(Date()) + "." + ext
    }

    fun getTempDirectory(context: Context?): File {
//        val externalDir = context!!.cacheDir
        val externalDir = context!!.getExternalFilesDir(null) ?: throw IllegalStateException("Media storage isn't available")
        return File(externalDir, TMP_DIR)
    }

    /**
     * get path to local photo
     * @param context - context
     * @param uri - photo uri
     * @return path to local photo on device
     */
    fun getPath(context: Context, uri: Uri?): String? {
        if (uri == null) {
            return null
        }
        val projection = arrayOf(MediaStore.Images.Media.DATA)
        val cursor = context.contentResolver.query(uri, projection, null, null, null)
        if (cursor != null) {
            val columnIndex = cursor
                    .getColumnIndexOrThrow(MediaStore.Images.Media.DATA)
            cursor.moveToFirst()
            val string = cursor.getString(columnIndex)
            cursor.close()
            return string
        }
        return uri.path
    }

    fun getRealPathFromURI(context: Context, contentUri: Uri): String {
        var cursor: Cursor? = null
        try {
            val proj = arrayOf(MediaStore.Images.Media.DATA)
            cursor = context.contentResolver.query(contentUri, proj, null, null, null)
            val columnIndex = cursor!!.getColumnIndexOrThrow(MediaStore.Images.Media.DATA)
            cursor.moveToFirst()
            return cursor.getString(columnIndex)
        } finally {
            if (cursor != null) {
                cursor.close()
            }
        }
    }

    /**
     * @param imageUri scale -> rotate -> copy from content provider to app local path
     * @return - return copied image path
     */
    @Throws(IOException::class)
    fun getImageFile(context: Context, imageUri: Uri): String {
        val inputStream = context.contentResolver.openInputStream(imageUri) ?: throw IOException(imageUri.toString() + " - input stream is null")
        var bitmap: Bitmap? = inputStream.use {
            BitmapFactory.decodeStream(it)
        }
        bitmap ?: throw IOException(imageUri.toString() + " - bitmap is null")

        val destFile = createImageFile(context)
        val path = getPath(context, imageUri)
        bitmap = if (path != null) {
            fixImageOrientation(path, bitmap)
        } else {
            getScaledBitmap(bitmap, 1024, 768)
        }
        FileOutputStream(destFile).use { outputStream ->
            bitmap!!.compress(Bitmap.CompressFormat.JPEG, 80, outputStream)
        }
        return destFile.path
    }

    fun createImageFile(context: Context?): File {
        val pictureFileName = generateFilename()
        val dstDirectory = getTempDirectory(context)

        dstDirectory.mkdirs()
        return File(dstDirectory, pictureFileName)
    }

    /**
     * @param fragment - result receiver
     * @return - file for the destination taken photo
     */
    @RequiresPermission(allOf = [
        Manifest.permission.WRITE_EXTERNAL_STORAGE,
        Manifest.permission.CAMERA
    ])
    fun takePhoto(fragment: Fragment, requestCode: Int): File {
        return takePhoto(fragment.context!!, requestCode, fragment::startActivityForResult)
    }

    /**
     * @param activity - result receiver
     * @return - file for the destination taken photo
     */
    @RequiresPermission(allOf = [
        Manifest.permission.WRITE_EXTERNAL_STORAGE,
        Manifest.permission.CAMERA
    ])
    fun takePhoto(activity: Activity, requestCode: Int): File {
        return takePhoto(activity, requestCode, activity::startActivityForResult)
    }

    private fun takePhoto(context: Context, requestCode: Int,
                          startActivity: (Intent, requestCode: Int) -> Unit): File {
        val file = createImageFile(context)
        val intent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
        intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(file))
        startActivity(Intent.createChooser(intent, ""), requestCode)
        return file
    }

    /**
     * @param fragment -result receiver
     * @param galleryRequest - gallery request code
     */
    //todo посмотреть, почему READ_EXTERNAL_STORAGE с 16 апи и как решить
    @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
    @RequiresPermission(Manifest.permission.READ_EXTERNAL_STORAGE)
    fun getPhoto(fragment: Fragment, galleryRequest: Int) {
        val intent = Intent(
                Intent.ACTION_PICK)
        intent.type = "image/*"
        fragment.startActivityForResult(
                Intent.createChooser(intent, ""),
                galleryRequest)

    }

    @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
    @RequiresPermission(Manifest.permission.READ_EXTERNAL_STORAGE)
    fun getPhoto(activity: Activity, galleryRequest: Int) {
        val intent = Intent(
                Intent.ACTION_PICK)
        intent.type = "image/*"
        activity.startActivityForResult(
                Intent.createChooser(intent, ""),
                galleryRequest)

    }

    fun fixImageOrientation(photoPath: String, b: Bitmap?): Bitmap? {
        var exifInterface: ExifInterface? = null
        val bitmap: Bitmap
        try {
            Log.d(TAG, "path to exif - $photoPath")
            exifInterface = ExifInterface(photoPath)
        } catch (e: IOException) {
            e.printStackTrace()
        }

        val bmOptions = BitmapFactory.Options()
        bitmap = b ?: BitmapFactory.decodeFile(photoPath, bmOptions)

        var photoBitmap: Bitmap? = bitmap
        photoBitmap = getScaledBitmap(photoBitmap, 1024, 768)
        if (exifInterface != null) {
            val orientation = exifInterface.getAttributeInt(ExifInterface.TAG_ORIENTATION, ExifInterface.ORIENTATION_UNDEFINED)
            Log.d(TAG, "orientation - $orientation")
            when (orientation) {
                ExifInterface.ORIENTATION_ROTATE_90 -> photoBitmap = rotateImage(photoBitmap, 90f)
                ExifInterface.ORIENTATION_ROTATE_180 -> photoBitmap = rotateImage(photoBitmap, 180f)
                ExifInterface.ORIENTATION_ROTATE_270 -> photoBitmap = rotateImage(photoBitmap, 270f)
            }
        }
        return photoBitmap
    }

    fun rotateImage(bitmap: Bitmap?, angle: Float): Bitmap? {
        if (bitmap == null) return null

        val matrix = Matrix()
        matrix.postRotate(angle)
        return Bitmap.createBitmap(bitmap, 0, 0, bitmap.width, bitmap.height, matrix, true)
    }


    fun getScaledBitmap(bitmap: Bitmap?, maxWidth: Int, maxHeight: Int): Bitmap? {
        bitmap ?: return null

        var width = bitmap.width
        var height = bitmap.height
        val ratio = height.toDouble() / width
        if (width > maxWidth) {
            width = maxWidth
        }
        height = (ratio * width).toInt()
        if (height > maxHeight) {
            height = maxHeight
            width = (height / ratio).toInt()
        }
        val scaledBitmap = Bitmap.createScaledBitmap(bitmap, width, height, false)
        return getCroppedBitmap(scaledBitmap)
    }

    fun getCroppedBitmap(srcBmp: Bitmap?): Bitmap? {
        if (srcBmp == null) return null

        val dstBmp: Bitmap
        if (srcBmp.width >= srcBmp.height) {

            dstBmp = Bitmap.createBitmap(
                    srcBmp,
                    srcBmp.width / 2 - srcBmp.height / 2,
                    0,
                    srcBmp.height,
                    srcBmp.height
            )

        } else {

            dstBmp = Bitmap.createBitmap(
                    srcBmp,
                    0,
                    srcBmp.height / 2 - srcBmp.width / 2,
                    srcBmp.width,
                    srcBmp.width
            )
        }
        return dstBmp
    }
}
