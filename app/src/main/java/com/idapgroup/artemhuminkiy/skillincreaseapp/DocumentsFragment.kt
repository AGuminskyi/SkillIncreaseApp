package com.idapgroup.artemhuminkiy.skillincreaseapp

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import android.support.design.widget.Snackbar
import android.support.v4.app.Fragment
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.idapgroup.artemhuminkiy.skillincreaseapp.userData.UserViewModel
import com.idapgroup.artemhuminkiy.skillincreaseapp.utils.CustomProgressDialog
import kotlinx.android.synthetic.main.documents_main_layout.*

class DocumentsFragment : Fragment(){

    companion object {
        fun newInstance() = DocumentsFragment().apply {
                arguments = Bundle()
            }
        }

    private val progressDialog by lazy { CustomProgressDialog() }

    private val userViewModel: UserViewModel by lazy {
        ViewModelProviders.of(activity).get(UserViewModel::class.java)
    }

    private val adapter by lazy {
        DocumentsAdapter(onDoneClick = {
            Snackbar.make(
                    view!!.rootView.findViewById(R.id.root),
                    R.string.document_just_signed,
                    Snackbar.LENGTH_LONG).show()
        })
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if(arguments != null){
            val userName = arguments.getString("userName")
            progressDialog.show(activity.fragmentManager, "CustomProgressDialog")
            userViewModel.getRepos(userName)
        }
    }

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater!!.inflate(R.layout.documents_main_layout, container, false)
    }

    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        recyclerView.adapter = adapter
        subscribe()
    }

    private fun subscribe() {
        userViewModel.repos.observe(this, Observer {
            if (it != null) {
                when (it) {
                    is UserViewModel.ReposState.Error -> {
                        progressDialog.dismiss()
                        Snackbar.make(view!!.rootView.findViewById(R.id.root), R.string.something_go_wrong, Snackbar.LENGTH_LONG).show()
                        Log.d(Constants.MAIN_ACTIVITY, it.message)
                    }
                    is UserViewModel.ReposState.Repos -> {
                        adapter.addRepos(it.listRepository)
                        progressDialog.dismiss()
                    }
                }
            }
        })
    }
}