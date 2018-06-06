package com.idapgroup.artemhuminkiy.skillincreaseapp.dialogs

import android.os.Bundle
import android.support.v4.app.DialogFragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.ListAdapter
import com.idapgroup.artemhuminkiy.skillincreaseapp.R
import kotlinx.android.synthetic.main.simple_list.*

val USERS_LIST = "users"

class SimpleListDialogFragment() : DialogFragment() {

    companion object {
        fun newInstance(list: Int): DialogFragment {
            return SimpleListDialogFragment().apply {
                arguments?.putInt(USERS_LIST, list)
            }
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.simple_list, container, false)
        val usersList = arguments!!.getInt(USERS_LIST)
//        val listAdapter = ListA<String>(context, android.R.layout.simple_list_item_1, usersList)
//        simpleListView.adapter = listAdapter
        return view
    }
}