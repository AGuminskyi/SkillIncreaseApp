package com.idapgroup.artemhuminkiy.skillincreaseapp.dialogs

import android.app.Dialog
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.net.Uri
import android.os.Bundle
import android.support.v7.app.AppCompatDialogFragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.idapgroup.artemhuminkiy.skillincreaseapp.R
import kotlinx.android.synthetic.main.file_photo_popup.*

class PhotoDialogFragment : AppCompatDialogFragment() {

    val PHOTO_URL = "photo_url"

    companion object {
        fun newInstance(photoUrl: String) =
            PhotoDialogFragment().apply {
                arguments = Bundle()
                arguments!!.putString(PHOTO_URL, photoUrl)
            }
    }

    var photoUrl: String = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        photoUrl = arguments!!.getString(PHOTO_URL)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.file_photo_popup, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        photoPreview.setImageURI(Uri.parse(photoUrl))
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val dialog = super.onCreateDialog(savedInstanceState)
        dialog.window.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        dialog.setCancelable(true)
        dialog.setCanceledOnTouchOutside(true)
        return dialog
    }
}