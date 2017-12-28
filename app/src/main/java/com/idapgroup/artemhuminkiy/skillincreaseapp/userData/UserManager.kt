package com.idapgroup.artemhuminkiy.skillincreaseapp.userData

import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import java.util.concurrent.TimeUnit

class UserManager {

    private var users = initialize()

    private fun initialize(): List<User> {
        return listOf(User("1", "Artem", 20, "Male"),
                User("2", "Jeketos", 25, "Male"),
                User("3", "Aliona", 25, "Female"))
    }

    fun getUsersId(): Single<List<String>> {
        val usersIds = mutableListOf<String>()
        users.mapTo(usersIds) { it.id }
        return Single.just(usersIds.toList()).delay(1, TimeUnit.SECONDS).observeOn(AndroidSchedulers.mainThread())
    }

    fun getUser(id: String): Single<User> = Single.just(users.find { it.id == id }!!).observeOn(AndroidSchedulers.mainThread())

}