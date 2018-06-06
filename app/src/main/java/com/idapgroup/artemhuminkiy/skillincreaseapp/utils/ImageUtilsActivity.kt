//package com.idapgroup.artemhuminkiy.skillincreaseapp.utils
//
//import android.app.Activity
//import android.graphics.Bitmap
//import android.media.ExifInterface
//import android.graphics.BitmapFactory
//import android.provider.MediaStore
//import android.content.Intent
//import android.graphics.Matrix
//import android.net.Uri
//import android.os.Bundle
//import android.support.annotation.NonNull
//import android.support.annotation.Nullable
//import android.text.TextUtils
//import android.support.v7.app.AppCompatActivity
//import android.util.Log
//import java.io.File
//import java.io.FileNotFoundException
//import java.io.FileOutputStream
//import java.io.IOException
//import java.text.SimpleDateFormat
//import java.util.*
//
//
//class ImageUtilsActivity : AppCompatActivity() {
//    private var photoPath: String? = null
//
//    private val tempDirectory: File
//        @NonNull
//        get() {
//            val externalDir = getExternalFilesDir(null)
//                    ?: throw IllegalStateException("Media storage isn't available")
//
//            return File(externalDir, TMP_DIR)
//        }
//
//    override fun onCreate(@Nullable savedInstanceState: Bundle?) {
//        super.onCreate(savedInstanceState)
//        //        getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
//        //        setTheme(R.style.Theme_Transparent);
//        if (savedInstanceState != null) {
//            photoPath = savedInstanceState.getString(PHOTO_PATH)
//        } else {
//            val extras = intent.extras
//            val action = extras!!.getString("action")
//            if (!TextUtils.isEmpty(action)) {
//                if (action == "take_photo") {
//                    takePhoto()
//                } else if (action == "gallery") {
//                    getImageFromDevice()
//                }
//            }
//        }
//    }
//
//    override fun onSaveInstanceState(outState: Bundle) {
//        outState.putString(PHOTO_PATH, photoPath)
//        super.onSaveInstanceState(outState)
//    }
//
//    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent) {
//        super.onActivityResult(requestCode, resultCode, data)
//        if (resultCode != Activity.RESULT_OK) {
//            setResult(RESULT_CANCELED)
//            return
//        }
//        val path: String?
//        if (requestCode == REQUEST_CAMERA) {
//                        path = getImageFile( Uri.fromFile(File(photoPath)))
//                        proceedResult(path)
////            proceedResult(photoPath)
//        } else if (requestCode == REQUEST_GALLERY) {
//            path = getImageFile(data.data)
//            proceedResult(path)
//        }
//        setResult(RESULT_CANCELED)
//
//    }
//
//    private fun proceedResult(path: String?) {
//        val intent = Intent()
//        intent.putExtra(ImageUtils.IMAGE_PATH_KEY, path)
//        setResult(Activity.RESULT_OK, intent)
//        finish()
//    }
//
//    private fun takePhoto() {
//        val photoFile = createImageFile()
//        photoPath = photoFile.getAbsolutePath()
//        val intent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
//        intent.putExtra(MediaStore.EXTRA_OUTPUT,
//                Uri.fromFile(photoFile))
//        startActivityForResult(Intent.createChooser(intent, ""), REQUEST_CAMERA)
//    }
//
//    private fun getImageFromDevice() {
//        val intent = Intent(
//                Intent.ACTION_PICK)
//        intent.type = "image/*"
//        startActivityForResult(
//                Intent.createChooser(intent, ""),
//                REQUEST_GALLERY)
//
//    }
//
//    private fun generateFilename(): String {
//        return SimpleDateFormat("yyyyMMdd_HHmmssSSS", Locale.US).format(Date()) + ".jpg"
//    }
//
//    private fun getPath(uri: Uri?): String? {
//        if (uri == null) {
//            return null
//        }
//        val projection = arrayOf(MediaStore.Images.Media.DATA)
//        val cursor = contentResolver.query(uri!!, projection, null, null, null)
//        if (cursor != null) {
//            val column_index = cursor
//                    .getColumnIndexOrThrow(MediaStore.Images.Media.DATA)
//            cursor.moveToFirst()
//            val string = cursor.getString(column_index)
//            cursor.close()
//            return string
//        }
//        return uri!!.getPath()
//    }
//
//    private fun getImageFile(selectedImageUri: Uri?): String? {
//        var bitmap: Bitmap? = null
//        try {
//            val `is` = contentResolver.openInputStream(selectedImageUri!!)
//            if (`is` != null) {
//                bitmap = BitmapFactory.decodeStream(`is`)
//                `is`.close()
//            }
//        } catch (e: IOException) {
//            e.printStackTrace()
//        }
//
//        if (bitmap == null) {
//            return null
//        }
//        val destFile = createImageFile()
//        val path = getPath(selectedImageUri)
//        //todo next feature
//        //        if (path != null) {
//        //            bitmap = fixImageOrientation(path, bitmap);
//        //        }
//        try {
//            val fOut = FileOutputStream(destFile)
//            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, fOut)
//            fOut.close()
//            return destFile.getPath()
//        } catch (e: FileNotFoundException) {
//            e.printStackTrace()
//            return null
//        } catch (e: IOException) {
//            e.printStackTrace()
//            return null
//        }
//
//    }
//
//    private fun createImageFile(): File {
//        val pictureFileName = generateFilename()
//        val dstDirectory = tempDirectory
//
//        dstDirectory.mkdirs()
//        return File(dstDirectory, pictureFileName)
//    }
//
//    private fun fixImageOrientation(photoPath: String, @NonNull bitmap: Bitmap?): Bitmap? {
//        var bitmap = bitmap
//        var exifInterface: ExifInterface? = null
//        //        Bitmap bitmap;
//        try {
//            Log.d("image__", "path to exif - $photoPath")
//            exifInterface = ExifInterface(photoPath)
//        } catch (e: IOException) {
//            e.printStackTrace()
//        }
//
//        //        BitmapFactory.Options bmOptions = new BitmapFactory.Options();
//        //        if (b == null) {
//        //            bitmap = BitmapFactory.decodeFile(photoPath, bmOptions);
//        //        } else {
//        //            bitmap = b;
//        //        }
//        //        Bitmap photoBitmap = bitmap;
//        if (exifInterface != null) {
//            val orientation = exifInterface.getAttributeInt(ExifInterface.TAG_ORIENTATION, ExifInterface.ORIENTATION_UNDEFINED)
//            Log.d("image__", "orientation - $orientation")
//            when (orientation) {
//                ExifInterface.ORIENTATION_ROTATE_90 -> bitmap = rotateImage(bitmap, 90f)
//                ExifInterface.ORIENTATION_ROTATE_180 -> bitmap = rotateImage(bitmap, 180f)
//                ExifInterface.ORIENTATION_ROTATE_270 -> bitmap = rotateImage(bitmap, 270f)
//            }
//        }
//        return bitmap
//    }
//
//    private fun rotateImage(bitmap: Bitmap?, angle: Float): Bitmap? {
//        if (bitmap == null) return null
//
//        val matrix = Matrix()
//        matrix.postRotate(angle)
//        return Bitmap.createBitmap(bitmap, 0, 0, bitmap.width, bitmap.height, matrix, true)
//    }
//
//    companion object {
//
//        private val TMP_DIR = "tmp"
//        val REQUEST_CAMERA = 101
//        val REQUEST_GALLERY = 102
//        val PHOTO_PATH = "photo_path"
//    }
//
//}