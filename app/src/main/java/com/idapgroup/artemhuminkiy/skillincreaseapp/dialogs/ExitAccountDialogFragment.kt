package com.idapgroup.artemhuminkiy.skillincreaseapp.dialogs

import android.content.Context
import android.support.v7.app.AlertDialog
import com.idapgroup.artemhuminkiy.skillincreaseapp.R

fun showExitAccountDialog(context: Context,
                   title: Int,
                   message: Int,
                   onOk: (() -> Unit)? = null): Unit{
    val dialog = AlertDialog.Builder(context,android.R.style.Theme_Material_Dialog_Alert)
            .setTitle(title)
            .setMessage(message)
            .setPositiveButton(android.R.string.ok){ _, _ ->
                onOk?.invoke()
            }
            .setNegativeButton(android.R.string.no){ dialog,_ ->
                dialog.cancel()
            }

    val alertDialog = dialog.create()
    alertDialog.setCanceledOnTouchOutside(true)
    alertDialog.show()
}

fun showPhotoPickerDialog(context: Context, message: Int,
                          onCamera: (() -> Unit)?, onGallery: (() -> Unit)?){

    val dialog = AlertDialog.Builder(context,android.R.style.Theme_Material_Dialog_Alert)
            .setMessage(message)
            .setPositiveButton(R.string.camera){ _, _ ->
                onCamera?.invoke()
            }
            .setNegativeButton(R.string.gallery){ _,_ ->
                onGallery?.invoke()
            }
            .setNeutralButton(android.R.string.no){dialog,_ ->
                dialog.cancel()
            }
    val alertDialog = dialog.create()
    alertDialog.setCanceledOnTouchOutside(true)
    alertDialog.show()
}