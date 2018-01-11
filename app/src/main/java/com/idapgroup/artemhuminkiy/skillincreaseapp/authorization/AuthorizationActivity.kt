package com.idapgroup.artemhuminkiy.skillincreaseapp.authorization

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.content.Intent
import android.databinding.DataBindingUtil
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.widget.Toast
import com.idapgroup.artemhuminkiy.skillincreaseapp.Constants
import com.idapgroup.artemhuminkiy.skillincreaseapp.MainActivity
import com.idapgroup.artemhuminkiy.skillincreaseapp.R
import com.idapgroup.artemhuminkiy.skillincreaseapp.databinding.ActivityAutorizationBinding
import com.idapgroup.artemhuminkiy.skillincreaseapp.gitHub.User
import kotlinx.android.synthetic.main.activity_autorization.*

class AuthorizationActivity : AppCompatActivity() {

    private lateinit var authorizationViewModel : AuthorizationViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_autorization)

        val binding = DataBindingUtil.setContentView<ActivityAutorizationBinding>(this, R.layout.activity_autorization)

        authorizationViewModel = ViewModelProviders.of(this).get(AuthorizationViewModel::class.java)

        subscribe()

        autorizeButton.setOnClickListener {
            val user = User()
            user.login = login.text.toString()
            binding.user = user

            authorizationViewModel.getUser(binding.user)
            val intent = Intent(this, MainActivity::class.java)
            intent.putExtra(Constants.USER_NAME, binding.user.login)
            intent.putExtra(Constants.ID, binding.user.id)
            startActivity(intent)
            finish()
        }
    }

    private fun subscribe() {
        val user = Observer<User>{
            Toast.makeText(this, "" + it!!.login, Toast.LENGTH_SHORT).show()
        }

        authorizationViewModel.userInfo.observe(this, user)
    }
}
