package com.idapgroup.artemhuminkiy.skillincreaseapp

import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

private val adapter: AssignedDocumentsAdapter

class AssignedDocumentsFragment : Fragment() {
    companion object {
        fun newInstance() = AssignedDocumentsFragment().apply {
            arguments = Bundle()
        }
    }

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater!!.inflate(R.layout.asigned_documents, container, false)
    }


}