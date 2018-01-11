package com.idapgroup.artemhuminkiy.skillincreaseapp

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import com.idapgroup.artemhuminkiy.skillincreaseapp.gitHub.Repository
import com.idapgroup.artemhuminkiy.skillincreaseapp.userData.UserViewModel
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    private lateinit var layoutManager: RecyclerView.LayoutManager
    private lateinit var userViewModel : UserViewModel

    private val adapter by lazy {
        MyRecyclerAdapter()
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        my_toolbar.title = getString(R.string.documents)
        setSupportActionBar(my_toolbar)

        layoutManager = LinearLayoutManager(this)
        recyclerView.layoutManager = layoutManager
        recyclerView.adapter = adapter

        userViewModel = ViewModelProviders.of(this).get(UserViewModel::class.java)


        subscribe()
        val intent = intent
        val userName = intent.getStringExtra(Constants.USER_NAME)
        userViewModel.getRepos(userName)
    }

    private fun subscribe() {

//        val users = Observer<List<User>> {
//            adapter.addItems(it!!)
//        }

        val repos = Observer<List<Repository>>{
           adapter.addRepos(it!!)
        }

//        userViewModel.users.observe(this, users)
        userViewModel.repos.observe(this, repos)
    }
}

