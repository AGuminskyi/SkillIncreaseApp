package com.idapgroup.artemhuminkiy.skillincreaseapp

import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.idapgroup.artemhuminkiy.skillincreaseapp.userData.UserViewModel
import kotlinx.android.synthetic.main.documents_main_layout.*


class ArchivedDocumentsFragment : Fragment() {

    private val adapter by lazy { ArchivedDocumentsAdapter() }

    private val userViewModel: UserViewModel by lazy {
        ViewModelProviders.of(activity!!).get(UserViewModel::class.java)
    }

    companion object {
        fun newInstance() = ArchivedDocumentsFragment().apply {
            arguments = Bundle()
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.documents_main_layout, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        recyclerView.adapter = adapter
        adapter.addRepos(userViewModel.finishedDocuments.value!!.toList())
    }
}