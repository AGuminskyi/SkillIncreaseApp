package com.idapgroup.artemhuminkiy.skillincreaseapp.authorization

import android.app.Application
import android.arch.lifecycle.AndroidViewModel
import android.arch.lifecycle.MutableLiveData
import android.util.Log
import com.idapgroup.artemhuminkiy.skillincreaseapp.gitHub.GitHubService
import com.idapgroup.artemhuminkiy.skillincreaseapp.gitHub.User
import io.reactivex.disposables.Disposable

class AuthorizationViewModel(application: Application) : AndroidViewModel(application) {
    var userInfo = MutableLiveData<AuthorizationState>().apply {
        value = AuthorizationState.Init
    }
    lateinit var disposeObject : Disposable

    fun getUser(user: User) {
        val gitHubService = GitHubService()
        disposeObject = gitHubService.user(user)
                .subscribe({
                    userInfo.value = AuthorizationState.Data(it)
                }, {
                    userInfo.value = AuthorizationState.Error(it.message!!)
                    Log.e("Skill", it.message)
                })
    }

    override fun onCleared() {
        super.onCleared()
        disposeObject.dispose()
    }

    sealed class AuthorizationState {
        object Init : AuthorizationState()
        class Error(val message: String) : AuthorizationState()
        class Data(val user : User) : AuthorizationState()
    }
}