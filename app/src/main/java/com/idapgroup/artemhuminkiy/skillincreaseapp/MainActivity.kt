package com.idapgroup.artemhuminkiy.skillincreaseapp

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import android.support.design.widget.Snackbar
import android.support.v4.view.GravityCompat
import android.support.v7.app.ActionBar
import android.support.v7.app.AppCompatActivity
import android.util.Log
import android.view.MenuItem
import com.idapgroup.artemhuminkiy.skillincreaseapp.userData.UserViewModel
import com.idapgroup.artemhuminkiy.skillincreaseapp.utils.CustomProgressDialog
import com.idapgroup.artemhuminkiy.skillincreaseapp.utils.connected
import com.idapgroup.artemhuminkiy.skillincreaseapp.utils.getExtrasExt
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.activity_toolbar_content_layout.*
import kotlinx.android.synthetic.main.documents_main_layout.*

class MainActivity : AppCompatActivity() {

    private val userViewModel: UserViewModel by lazy { ViewModelProviders.of(this).get(UserViewModel::class.java) }
    private val adapter by lazy {
        MyRecyclerAdapter(onDoneClick = {
            Snackbar.make(findViewById(R.id.root), R.string.document_just_signed, Snackbar.LENGTH_LONG).show()
        })
    }
    private val userName by lazy { intent.getExtrasExt() }

    private val progressDialog by lazy { CustomProgressDialog() }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        my_toolbar.title = getString(R.string.documents)
        setSupportActionBar(my_toolbar)
        val actionBar: ActionBar? = supportActionBar
        actionBar?.apply {
            setDisplayHomeAsUpEnabled(true)
            setHomeAsUpIndicator(R.drawable.ic_menu)
        }
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

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            android.R.id.home -> {
                drawerLayout.openDrawer(GravityCompat.START)
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }
}

