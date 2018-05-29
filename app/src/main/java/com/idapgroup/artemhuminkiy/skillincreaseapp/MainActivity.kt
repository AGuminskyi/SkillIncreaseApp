package com.idapgroup.artemhuminkiy.skillincreaseapp

import android.os.Bundle
import android.support.design.widget.Snackbar
import android.support.v4.app.Fragment
import android.support.v4.view.GravityCompat
import android.support.v7.app.ActionBar
import android.support.v7.app.AppCompatActivity
import android.view.MenuItem
import com.idapgroup.artemhuminkiy.skillincreaseapp.R.id.*
import com.idapgroup.artemhuminkiy.skillincreaseapp.utils.*
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.activity_toolbar.*

class MainActivity : AppCompatActivity() {

    private val userName by lazy { intent.getExtrasExt() }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(my_toolbar)
        val actionBar: ActionBar? = supportActionBar
        actionBar?.apply {
            setDisplayHomeAsUpEnabled(true)
            setHomeAsUpIndicator(R.drawable.ic_menu)
            title = this@MainActivity.getString(R.string.documents_on_asign)
        }
        handleNavigationViewClick()
        val isNetworkConnected = checkNetworkInfo()
        if (isNetworkConnected) {
            addFragment(savedInstanceState)
        }
    }

    private fun handleNavigationViewClick() {
        design_navigation_view.setNavigationItemSelectedListener {
            val currentFragment = getCurrentFragment(R.id.fragment_container)
            when(it.itemId){
                nav_document_on_asign -> {
                    if (currentFragment !is DocumentsFragment){
                        val fragment = DocumentsFragment.newInstance()
                        startFragment(fragment, fragment.getCurrentFragmentTag())
                    }
                }
                nav_documents_asigned -> {
                    if (currentFragment !is AssignedDocumentsFragment){
                        val fragment = AssignedDocumentsFragment.newInstance()
                        startFragment(fragment, fragment.getCurrentFragmentTag())
                    }
                }
                nav_documents_that_you_asign -> {
                    if(currentFragment !is DocumentsFragment){

                    }
                }
                nav_settings_menu -> {

                }
            }
            true
        }
    }

//    private fun checkFragmentsFreaquency(fragmentToShow:Fragment, currentFragment: Fragment){
//        if(currentFragment !is DocumentsFragment)
//    }

    private fun addFragment(savedInstanceState: Bundle?) {
        if (savedInstanceState == null) {
            val fragment = DocumentsFragment.newInstance()
            val bundle = Bundle()
            bundle.putString("userName", userName)
            fragment.arguments = bundle
//            setToolbarTitle("Документы")
            startFirstFragment(fragment, fragment.getCurrentFragmentTag())
        }
    }



    private fun checkNetworkInfo(): Boolean {
        return if (this.connected()) {
            true
        } else {
            Snackbar.make(findViewById(R.id.root), R.string.no_internet_connection, Snackbar.LENGTH_LONG).show()
            false
        }
    }

    fun setToolbarTitle(toolbarTitle: String) {
        actionBar?.apply { title = toolbarTitle }
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

