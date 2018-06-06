package com.idapgroup.artemhuminkiy.skillincreaseapp

 import android.annotation.SuppressLint
import android.app.Activity
 import android.content.Intent
 import android.net.Uri
 import android.os.Bundle
 import android.provider.MediaStore
 import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
 import com.idapgroup.artemhuminkiy.skillincreaseapp.dialogs.DatePickerFragment
 import com.idapgroup.artemhuminkiy.skillincreaseapp.dialogs.TimePickerDialogFragment
 import com.idapgroup.artemhuminkiy.skillincreaseapp.dialogs.showPhotoPickerDialog
 import com.idapgroup.artemhuminkiy.skillincreaseapp.utils.ImageUtils
 import kotlinx.android.synthetic.main.create_file.*

const val DATE_PICKER_FRAGMENT = 1
const val TIME_PICKER_FRAGMENT = 2

const val CAMERA_REQUEST_CODE = 3
const val GALLERY_REQUEST_CODE = 4


class NewDocumentAssignmentFragment : Fragment() {

    companion object {
        fun newInstance() = NewDocumentAssignmentFragment()
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.create_file, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        fileImage.setOnClickListener{
            showPhotoPicker()
        }

        dateButton.setOnClickListener {
            val dialogFragment = DatePickerFragment()
            dialogFragment.setTargetFragment(this, DATE_PICKER_FRAGMENT)
            dialogFragment.show(this.fragmentManager, "dataPicker")
        }

        timeButton.setOnClickListener{
            val dialogFragment = TimePickerDialogFragment()
            dialogFragment.setTargetFragment(this, TIME_PICKER_FRAGMENT)
            dialogFragment.show(this.fragmentManager, "timePicker")
        }
    }

    private fun showPhotoPicker(){
        showPhotoPickerDialog(context!!, R.string.do_photo_with,
                ::takePhotoWithCamera, ::takePhotoFromGallery)
    }

    @SuppressLint("MissingPermission")
    private fun takePhotoFromGallery() {
        ImageUtils.getImageFromGallery(this, GALLERY_REQUEST_CODE)
    }


    private fun takePhotoWithCamera() {
        ImageUtils.takePhoto(this, CAMERA_REQUEST_CODE)
    }

    @SuppressLint("SetTextI18n")
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        when(requestCode){
            DATE_PICKER_FRAGMENT -> {
                if(resultCode == Activity.RESULT_OK){
                    val bundle = data!!.extras
                    val years = bundle.getInt("year")
                    var month = bundle.getInt("month").toString()
                    if(month.toInt() < 10){
                       month = "0$month"
                    }
                    var day = bundle.getInt("day").toString()
                    if(day.toInt() < 10){
                        day = "0$day"
                    }
                    dateTextView.text = "$day.$month.$years"
                }
                else if(resultCode == Activity.RESULT_CANCELED){
                    Toast.makeText(context, "Дата не указана", Toast.LENGTH_SHORT).show()
                }
            }

            TIME_PICKER_FRAGMENT -> {
                if(resultCode == Activity.RESULT_OK){
                    val bundle = data!!.extras
                    val hours = bundle.getInt("hours")
                    var minutes = bundle.getInt("minutes").toString()
                    if(minutes.toInt() < 10){
                        minutes = "0$minutes"
                    }
                        timeTextView.text = "$hours:$minutes"
                }
                else if(resultCode == Activity.RESULT_CANCELED){
                    Toast.makeText(context, "Время не указано", Toast.LENGTH_SHORT).show()
                }
            }

            CAMERA_REQUEST_CODE -> {
                if(resultCode == Activity.RESULT_OK){
                    val imagePath = data?.extras?.getString(ImageUtils.IMAGE_PATH_KEY)
                    fileImage.setImageURI(Uri.parse(imagePath))
                    Toast.makeText(context, "$imagePath", Toast.LENGTH_SHORT).show()
                }
            }
            GALLERY_REQUEST_CODE  -> {
                if(resultCode == Activity.RESULT_OK){
                    val imagePath = data?.extras?.getString(ImageUtils.IMAGE_PATH_KEY)
                    fileImage.setImageURI(Uri.parse(imagePath))
                    Toast.makeText(context, "$imagePath", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }
}