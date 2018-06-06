package com.idapgroup.artemhuminkiy.skillincreaseapp.dialogs

import android.app.Activity
import android.widget.DatePicker
import android.app.DatePickerDialog
import android.app.Dialog
import android.content.Intent
import android.os.Bundle
import android.support.v4.app.DialogFragment
import java.util.*


class DatePickerFragment : DialogFragment(), DatePickerDialog.OnDateSetListener {

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        // Use the current date as the default date in the picker
        val c = Calendar.getInstance()
        val year = c.get(Calendar.YEAR)
        val month = c.get(Calendar.MONTH)
        val day = c.get(Calendar.DAY_OF_MONTH)

        // Create a new instance of DatePickerDialog and return it
        return DatePickerDialog(activity, this, year, month, day)
    }

    override fun onDateSet(view: DatePicker, year: Int, month: Int, day: Int) {
        val intent = Intent()
        with(intent){
            putExtra("year", year)
            putExtra("month", month)
            putExtra("day", day)
        }
        targetFragment?.onActivityResult(targetRequestCode, Activity.RESULT_OK, intent)

    }
}