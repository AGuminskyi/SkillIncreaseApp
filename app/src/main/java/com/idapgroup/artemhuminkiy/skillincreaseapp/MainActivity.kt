package com.idapgroup.artemhuminkiy.skillincreaseapp

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import android.support.design.widget.Snackbar
import android.support.v7.app.AppCompatActivity
import android.util.Log
import com.idapgroup.artemhuminkiy.skillincreaseapp.userData.UserViewModel
import com.idapgroup.artemhuminkiy.skillincreaseapp.utils.CustomProgressDialog
import com.idapgroup.artemhuminkiy.skillincreaseapp.utils.connected
import com.idapgroup.artemhuminkiy.skillincreaseapp.utils.getExtrasExt
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    private val userViewModel: UserViewModel by lazy { ViewModelProviders.of(this).get(UserViewModel::class.java) }
    private val adapter by lazy {
        MyRecyclerAdapter(onDoneClick = {
            Snackbar.make(findViewById(R.id.root), R.string.document_signed, Snackbar.LENGTH_LONG).show()
        })
    }
    private val userName by lazy { intent.getExtrasExt() }

    private val progressDialog by lazy { CustomProgressDialog() }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        my_toolbar.title = getString(R.string.documents)
        setSupportActionBar(my_toolbar)
        recyclerView.adapter = adapter
        subscribe()
        checkNetworkInfo()
    }

    private fun checkNetworkInfo() {
        if (this.connected()) {
            progressDialog.show(fragmentManager, "CustomProgressDialog")
            userViewModel.getRepos(userName)
        } else
            Snackbar.make(findViewById(R.id.root), R.string.no_internet_connection, Snackbar.LENGTH_LONG).show()
    }

    private fun subscribe() {
        userViewModel.repos.observe(this, Observer {
            if (it != null) {
                when (it) {
                    is UserViewModel.ReposState.Error -> {
                        progressDialog.dismiss()
                        Snackbar.make(findViewById(R.id.root), R.string.something_go_wrong, Snackbar.LENGTH_LONG).show()
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

