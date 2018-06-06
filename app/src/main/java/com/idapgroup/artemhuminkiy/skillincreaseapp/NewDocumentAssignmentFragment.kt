package com.idapgroup.artemhuminkiy.skillincreaseapp

import android.annotation.SuppressLint
import android.app.Activity
import android.arch.lifecycle.ViewModelProviders
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.idapgroup.artemhuminkiy.skillincreaseapp.utils.ImageUtils
import com.codekidlabs.storagechooser.StorageChooser
import com.idapgroup.artemhuminkiy.skillincreaseapp.dialogs.DatePickerFragment
import com.idapgroup.artemhuminkiy.skillincreaseapp.dialogs.TimePickerDialogFragment
import com.idapgroup.artemhuminkiy.skillincreaseapp.dialogs.showPhotoPickerDialog
import com.idapgroup.artemhuminkiy.skillincreaseapp.userData.DOCUMENT_TYPE
import com.idapgroup.artemhuminkiy.skillincreaseapp.userData.DocumentFile
import com.idapgroup.artemhuminkiy.skillincreaseapp.userData.UserViewModel
import kotlinx.android.synthetic.main.create_file.*

const val DATE_PICKER_FRAGMENT = 1
const val TIME_PICKER_FRAGMENT = 2

const val CAMERA_REQUEST_CODE = 3
const val GALLERY_REQUEST_CODE = 4


class NewDocumentAssignmentFragment : Fragment() {

    companion object {
        fun newInstance() = NewDocumentAssignmentFragment()
    }

    private var cameraPhotoPath: String = ""
    var storageChooser: StorageChooser? = null
    var filePath: String? = null

    private val userViewModel: UserViewModel by lazy {
        ViewModelProviders.of(activity!!).get(UserViewModel::class.java)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        storageChooser = StorageChooser.Builder()
                .allowCustomPath(true)
                .filter(StorageChooser.FileType.DOCS)
                .setType(StorageChooser.FILE_PICKER)
                .withActivity(activity)
                .withFragmentManager(activity!!.fragmentManager)
                .withMemoryBar(true)
                .build()
    }


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.create_file, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        fileImage.setOnClickListener {
            showPhotoPicker()
            updateButton()
        }

        dateButton.setOnClickListener {
            val dialogFragment = DatePickerFragment()
            dialogFragment.setTargetFragment(this, DATE_PICKER_FRAGMENT)
            dialogFragment.show(this.fragmentManager, "dataPicker")
            updateButton()
        }

        timeButton.setOnClickListener {
            val dialogFragment = TimePickerDialogFragment()
            dialogFragment.setTargetFragment(this, TIME_PICKER_FRAGMENT)
            dialogFragment.show(this.fragmentManager, "timePicker")
            updateButton()
        }

        chooseFileFromStorage.setOnClickListener {
            storageChooser!!.show()
            storageChooser!!.setOnSelectListener {
                filePath = it
                val fileName = it.substring(it.lastIndexOf("/") + 1)
                chooseFileFromStorage.text = fileName
                updateButton()
            }
        }

        documentsRadioGroup.setOnCheckedChangeListener { _, checkedId ->
            when (checkedId) {
                R.id.documentRadioButton -> {
                    updateButton()
                }
                R.id.assignmentRadioButton -> {
                    updateButton()
                }
            }
        }

        resultDocument.setOnClickListener {
            val file: DocumentFile = getInformation()
            setFileInfo(file)
            fragmentManager!!.popBackStack()
        }
    }

    private fun setFileInfo(file: DocumentFile) {
        when (file.type) {
            DOCUMENT_TYPE.DOCUMENT -> {
                userViewModel.assignedDocuments.value!!.add(file)
            }
            DOCUMENT_TYPE.ASSIGNMENT -> {
                userViewModel.assignedFiles.value!!.add(file)
            }
        }
    }

    private fun getInformation(): DocumentFile {
        var fileName: String? = null
        if (filePath != null) {
            fileName = chooseFileFromStorage.text.toString()
        }
        val fileType = if (assignmentRadioButton.isChecked) {
            DOCUMENT_TYPE.ASSIGNMENT
        } else {
            DOCUMENT_TYPE.DOCUMENT
        }
        val file = DocumentFile(
                executor = chooseExecutorButton.text.toString(),
                image = cameraPhotoPath,
                filePath = filePath,
                fileName = fileName,
                data = dateTextView.text.toString(),
                time = timeTextView.text.toString(),
                type = fileType,
                shortInfo = informationInputText.text.toString())

        return file
    }

    private fun updateButton() {
        var fileExists = false
        if(filePath != null){
            fileExists = true
        }
        val infoText = isNotBlank(informationInputText)
        val executor = isNotBlank(chooseExecutorButton)
        val date = isNotBlank(dateTextView)
        if (infoText && executor && date && fileExists) {
            resultDocument.isEnabled = true
            resultDocument.text = context!!.getString(R.string.assign_execution)
        }
    }

    private fun isNotBlank(view: TextView): Boolean {
        return view.text.isNotBlank()
    }

    private fun showPhotoPicker() {
        showPhotoPickerDialog(context!!, R.string.do_photo_with,
                ::takePhotoWithCamera, ::takePhotoFromGallery)
    }

    @SuppressLint("MissingPermission")
    private fun takePhotoFromGallery() {
        ImageUtils.getPhoto(this, GALLERY_REQUEST_CODE)
    }

    private fun takePhotoWithCamera() {
        cameraPhotoPath = ImageUtils.takePhoto(this, CAMERA_REQUEST_CODE).absolutePath
    }

    @SuppressLint("SetTextI18n")
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (resultCode != Activity.RESULT_OK) {
            return
        }
        when (requestCode) {
            DATE_PICKER_FRAGMENT -> {
                val bundle = data!!.extras
                val years = bundle.getInt("year")
                var month = bundle.getInt("month").toString()
                if (month.toInt() < 10) {
                    month = "0$month"
                }
                var day = bundle.getInt("day").toString()
                if (day.toInt() < 10) {
                    day = "0$day"
                }
                dateTextView.text = "$day.$month.$years"
            }

            TIME_PICKER_FRAGMENT -> {
                val bundle = data!!.extras
                val hours = bundle.getInt("hours")
                var minutes = bundle.getInt("minutes").toString()
                if (minutes.toInt() < 10) {
                    minutes = "0$minutes"
                }
                timeTextView.text = "$hours:$minutes"

            }

            CAMERA_REQUEST_CODE -> {
                fileImage.setImageURI(Uri.parse(cameraPhotoPath))
            }
            GALLERY_REQUEST_CODE -> {
                data?.let {
                    cameraPhotoPath = data.data.toString()
                    fileImage.setImageURI(Uri.parse(cameraPhotoPath))
                }
            }
        }
    }
}