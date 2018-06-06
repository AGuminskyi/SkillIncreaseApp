package com.idapgroup.artemhuminkiy.skillincreaseapp

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.support.design.widget.Snackbar
import android.support.v4.app.Fragment
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.idapgroup.artemhuminkiy.skillincreaseapp.dialogs.PhotoDialogFragment
import com.idapgroup.artemhuminkiy.skillincreaseapp.gitHub.Repository
import com.idapgroup.artemhuminkiy.skillincreaseapp.userData.DocumentFile
import com.idapgroup.artemhuminkiy.skillincreaseapp.userData.UserViewModel
import com.idapgroup.artemhuminkiy.skillincreaseapp.utils.CustomProgressDialog
import kotlinx.android.synthetic.main.documents_main_layout.*

class DocumentsFragment : Fragment() {

    companion object {
        fun newInstance() = DocumentsFragment().apply {
            arguments = Bundle()
        }
    }

    private val progressDialog by lazy { CustomProgressDialog() }

    private val userViewModel: UserViewModel by lazy {
        ViewModelProviders.of(activity!!).get(UserViewModel::class.java)
    }

    private val adapter by lazy {
        DocumentsAdapter(
                onDoneClick = {
                    documentAssigned(it)
                    showSnackBar(R.string.document_just_signed)
                },
                onOpenFile = {
                    openFile(it)
                },
                onPhotoClick = {
                    if (it != null) {
                        PhotoDialogFragment.newInstance(it).show(fragmentManager, "PhotoDialogFragment")
                    }
                })
    }

    private fun openFile(it: String?) {
        if(it != null){
            val filePath = Uri.parse(it)
            val fileExtension = it.substring(it.lastIndexOf((".")) + 1)
            val intent = Intent(Intent.ACTION_GET_CONTENT)
            intent.addCategory(Intent.CATEGORY_OPENABLE)
            intent.flags = Intent.FLAG_ACTIVITY_NO_HISTORY
            intent.type = "application/$fileExtension"
            intent.putExtra(Intent.EXTRA_MIME_TYPES, filePath)
            intent.setDataAndType(filePath, fileExtension)
//            val intent = Intent()
//            intent.action = Intent.ACTION_VIEW
//            intent.setDataAndType(filePath, "applicaton/$fileExtension")
            startActivity(Intent.createChooser(intent, "Выберите менеджер для открытия файла"))
        }
    }

    private fun documentAssigned(finished: DocumentFile) {
        userViewModel.finishedDocuments.value?.add(finished)
        userViewModel.assignedDocuments.value?.remove(finished)
    }

    private fun showSnackBar(textInSnackBar: Int) {
        Snackbar.make(
                view!!.rootView.findViewById(R.id.root),
                textInSnackBar,
                Snackbar.LENGTH_LONG).show()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (arguments != null) {
            val userName = arguments!!.getString("userName")
            userViewModel.getRepos(userName)
        }
    }


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.documents_main_layout, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        recyclerView.adapter = adapter
        progressDialog.show(activity!!.fragmentManager, "CustomProgressDialog")
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
                        adapter.addRepos(userViewModel.assignedDocuments.value!!)
                        progressDialog.dismiss()
                    }
                }
            }
        })
    }
}