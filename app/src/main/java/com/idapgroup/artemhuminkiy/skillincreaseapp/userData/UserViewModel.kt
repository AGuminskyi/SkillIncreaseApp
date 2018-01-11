package com.idapgroup.artemhuminkiy.skillincreaseapp.userData

import android.app.Application
import android.arch.lifecycle.AndroidViewModel
import android.arch.lifecycle.MutableLiveData
import android.util.Log
import com.idapgroup.artemhuminkiy.skillincreaseapp.gitHub.GitHubService
import com.idapgroup.artemhuminkiy.skillincreaseapp.gitHub.Repository

class UserViewModel(application: Application) : AndroidViewModel(application) {
//    val users: MutableLiveData<List<User>> = MutableLiveData()
    val repos: MutableLiveData<List<Repository>> = MutableLiveData()

/*    fun getUsers() {
        val userModel = UserManager()
        userModel.getUsersId()
                .flattenAsObservable { it }
                .flatMapSingle { userModel.getUser(it) }
                .toList()
                .subscribe({
                    //                    it.sortBy { it.id }
                    users.postValue(it)
                }, {
                    print("Error")
                })

    }

*/

    fun getRepos(userName: String) {
        val gitHubService = GitHubService()
        gitHubService.repos(userName)
                .subscribe({
                    repos.postValue(it)
                }, {
                    Log.e("Skill", it.message)
                })
    }

/*    fun method() {
//        Observable.fromCallable {
//            90
//        }
//
//        var callable = Callable<Int> {
//            90
//        }
//        var obj = Observable.create<Int>(object : ObservableOnSubscribe<Int> {
//            override fun subscribe(emitter: ObservableEmitter<Int>) {
//
////                Schedulers.io().createWorker().schedule {
////                    Schedulers.computation().createWorker().schedule {
//                        try {
//                            emitter.onNext(callable.call())
//                            emitter.onComplete()
//                        } catch (e: Exception) {
//                            emitter.onError(e)
//                        }
////                    }
////                }
//            }
//        })
//                .subscribeOn(Schedulers.computation())
//                .subscribeOn(Schedulers.io())
//
//
//
//                .observeOn(AndroidSchedulers.mainThread())
//                .observeOn(Schedulers.newThread())
//
//        obj.subscribe({
//            Handler(Looper.getMainLooper()).post {
//            }
//        })
//        obj.subscribe(object : Observer<Int> {
//            override fun onSubscribe(d: Disposable) {
//                TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
//            }
//
//            override fun onNext(t: Int) {
//                TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
//            }
//
//            override fun onError(e: Throwable) {
//                TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
//            }
//
//            override fun onComplete() {
//                TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
//            }
//
//        })
    } */
}

/*        userModel.getUsersId()
//                .toObservable()
//                .flatMapIterable{it}
//                .flatMap { userModel.getUser(it).toObservable() }
//                .toList()
//                .subscribeOn(Schedulers.io())
//                .observeOn(AndroidSchedulers.mainThread())
//                .subscribe({ it.sortBy { it.id }
//                    adapter.addItems(it)
//                }, {
//                    print("Error")
                }) */