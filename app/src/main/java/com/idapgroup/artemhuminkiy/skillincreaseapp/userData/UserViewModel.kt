package com.idapgroup.artemhuminkiy.skillincreaseapp.userData

import android.app.Application
import android.arch.lifecycle.AndroidViewModel
import android.arch.lifecycle.MutableLiveData
import android.util.Log
import com.idapgroup.artemhuminkiy.skillincreaseapp.gitHub.GitHubService
import com.idapgroup.artemhuminkiy.skillincreaseapp.gitHub.Repository
import io.reactivex.disposables.Disposable
import java.util.*

class UserViewModel(application: Application) : AndroidViewModel(application) {
    val repos = MutableLiveData<ReposState>().apply {
        value = ReposState.Init
    }

    val assignedDocumentsNew = MutableLiveData<MutableList<DocumentFile>>().apply {
        value = mutableListOf()
    }

    val assignedDocuments = MutableLiveData<MutableList<DocumentFile>>().apply {
        value = mutableListOf()
    }

    val finishedDocuments = MutableLiveData<MutableList<DocumentFile>>().apply {
        value = mutableListOf()
    }


    val assignedFiles = MutableLiveData<MutableList<DocumentFile>>().apply {
        value = mutableListOf()
    }

    private var disposableObject : Disposable? = null

    fun getRepos(userName: String) {
        val gitHubService = GitHubService()
        disposableObject = gitHubService.repos(userName)
                .subscribe({
                    repos.value = ReposState.Repos(it)
                }, {
                    repos.value = ReposState.Error(it.message!!)
                    Log.e("Skill", it.message)
                })
    }

    override fun onCleared() {
        super.onCleared()
        disposableObject?.dispose()
    }

    sealed class ReposState{
        object Init: ReposState()
        data class Repos(val listRepository: List<Repository>) : ReposState()
        data class Error(val message: String) : ReposState()

    }
}

class DocumentFile(
        var id: UUID = UUID.randomUUID(),
        var executor: String = "",
        var image: String? = null,
        var filePath: String? = null,
        var fileName: String? = null,
        var data: String = "",
        var time: String? = null,
        var type: DOCUMENT_TYPE = DOCUMENT_TYPE.DOCUMENT,
        var shortInfo: String? = null,
        var executionState: PROCESSING_STATE = PROCESSING_STATE.NOT_SEEN
        )

enum class DOCUMENT_TYPE {
    DOCUMENT, ASSIGNMENT
}

enum class PROCESSING_STATE{
    IN_PROGRESS, DONE, NOT_SEEN
}
