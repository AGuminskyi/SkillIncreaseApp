package com.idapgroup.artemhuminkiy.skillincreaseapp.userData

import android.app.Application
import android.arch.lifecycle.AndroidViewModel
import android.arch.lifecycle.MutableLiveData
import android.util.Log
import com.idapgroup.artemhuminkiy.skillincreaseapp.gitHub.GitHubService
import com.idapgroup.artemhuminkiy.skillincreaseapp.gitHub.Repository

class UserViewModel(application: Application) : AndroidViewModel(application) {
    val repos: MutableLiveData<List<Repository>> = MutableLiveData()

    fun getRepos(userName: String) {
        val gitHubService = GitHubService()
        gitHubService.repos(userName)
                .subscribe({
                    repos.postValue(it)
                }, {
                    Log.e("Skill", it.message)
                })
    }
}
