package com.idapgroup.artemhuminkiy.skillincreaseapp.authorization

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.databinding.DataBindingUtil
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.widget.Toast
import com.idapgroup.artemhuminkiy.skillincreaseapp.Constants
import com.idapgroup.artemhuminkiy.skillincreaseapp.MainActivity
import com.idapgroup.artemhuminkiy.skillincreaseapp.R
import com.idapgroup.artemhuminkiy.skillincreaseapp.databinding.ActivityAutorizationBinding
import com.idapgroup.artemhuminkiy.skillincreaseapp.gitHub.User
import com.idapgroup.artemhuminkiy.skillincreaseapp.utils.startActivity
import kotlinx.android.synthetic.main.activity_autorization.*

class AuthorizationActivity : AppCompatActivity() {

    private lateinit var authorizationViewModel: AuthorizationViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
//        setContentView(R.layout.activity_autorization)

        authorizationViewModel = ViewModelProviders.of(this).get(AuthorizationViewModel::class.java)

        val binding = DataBindingUtil.setContentView<ActivityAutorizationBinding>(this, R.layout.activity_autorization)
        subscribe()

        authorizeButton.setOnClickListener {
            getUser(binding)
        }

    }

    private fun subscribe() {
        authorizationViewModel.userInfo.observe(this, Observer {
            if (it != null) {
                when (it) {
                    is AuthorizationViewModel.AuthorizationState.Error -> {
                        Toast.makeText(this, it.message, Toast.LENGTH_SHORT).show()
                    }
                    is AuthorizationViewModel.AuthorizationState.Data -> {
                        startActivity(putInBundle(it.user), MainActivity::class.java)
                    }
                }
            }
        })
    }

    private fun getUser(binding: ActivityAutorizationBinding) {
        val user = User()
        user.login = login.text.toString()
        binding.user = user
        authorizationViewModel.getUser(binding.user)
    }

    private fun putInBundle(user: User): Bundle {
        val bundle = Bundle()
        with(bundle, {
            putString(Constants.USER_NAME, user.login)
            putString(Constants.ID, user.id)
        })
        return bundle
    }

}
