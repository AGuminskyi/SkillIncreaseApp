package com.idapgroup.artemhuminkiy.skillincreaseapp

import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

class ReasignedFragment: Fragment() {

    companion object {
        fun newInstance(): Fragment = ReasignedFragment()
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return View.inflate(context!!, R.layout.empty_document_list, null)
    }
}