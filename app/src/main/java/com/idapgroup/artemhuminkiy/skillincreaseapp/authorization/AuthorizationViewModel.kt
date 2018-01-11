package com.idapgroup.artemhuminkiy.skillincreaseapp.authorization

import android.app.Application
import android.arch.lifecycle.AndroidViewModel
import android.arch.lifecycle.MutableLiveData
import android.util.Log
import com.idapgroup.artemhuminkiy.skillincreaseapp.gitHub.GitHubService
import com.idapgroup.artemhuminkiy.skillincreaseapp.gitHub.User

class AuthorizationViewModel(application: Application) : AndroidViewModel(application) {
    var userInfo: MutableLiveData<User> = MutableLiveData()

    fun getUser(user: User) {
        val gitHubService = GitHubService()
        gitHubService.user(user)
                .subscribe({
                    userInfo.postValue(it)
                }, {
                    Log.e("Skill", it.message)
                })
    }
}