package com.idapgroup.artemhuminkiy.skillincreaseapp.userData

import android.app.Application
import android.arch.lifecycle.AndroidViewModel
import android.arch.lifecycle.MutableLiveData
import android.util.Log
import com.idapgroup.artemhuminkiy.skillincreaseapp.gitHub.GitHubService
import com.idapgroup.artemhuminkiy.skillincreaseapp.gitHub.Repository

class UserViewModel(application: Application) : AndroidViewModel(application) {
    val repos = MutableLiveData<ReposState>().apply {
        value = ReposState.Init
    }

    fun getRepos(userName: String) {
        val gitHubService = GitHubService()
        gitHubService.repos(userName)
                .subscribe({
                    repos.value = ReposState.Repos(it)
                }, {
                    repos.value = ReposState.Error(it.message!!)
                    Log.e("Skill", it.message)
                })
    }

    override fun onCleared() {
        super.onCleared()
    }

    sealed class ReposState{
        object Init: ReposState()
        data class Repos(val listRepository: List<Repository>) : ReposState()
        data class Error(val message: String) : ReposState()

    }
}
