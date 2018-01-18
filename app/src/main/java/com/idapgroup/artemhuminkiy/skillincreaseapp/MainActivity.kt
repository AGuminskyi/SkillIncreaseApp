package com.idapgroup.artemhuminkiy.skillincreaseapp

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.widget.Toast
import com.idapgroup.artemhuminkiy.skillincreaseapp.userData.UserViewModel
import com.idapgroup.artemhuminkiy.skillincreaseapp.utils.getExtrasExt
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    private val userViewModel: UserViewModel by lazy { ViewModelProviders.of(this).get(UserViewModel::class.java) }
    private val adapter by lazy {
        MyRecyclerAdapter(onDoneClick = {
            Toast.makeText(this, "onDonePressed", Toast.LENGTH_SHORT).show()
        })
    }
    private val userName by lazy { intent.getExtrasExt() }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        my_toolbar.title = getString(R.string.documents)
        setSupportActionBar(my_toolbar)
        recyclerView.adapter = adapter
        subscribe()
        userViewModel.getRepos(userName)
    }

    private fun subscribe() {
        userViewModel.repos.observe(this, Observer {
            if (it != null) {
                when (it) {
                    is UserViewModel.ReposState.Error -> {
                        Toast.makeText(this, it.message, Toast.LENGTH_SHORT).show()
                    }
                    is UserViewModel.ReposState.Repos -> {
                        adapter.addRepos(it.listRepository)
                    }
                }
            }
        })
    }
}

