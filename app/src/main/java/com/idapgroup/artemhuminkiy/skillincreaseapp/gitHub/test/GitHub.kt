package com.idapgroup.artemhuminkiy.skillincreaseapp.gitHub.test

import com.idapgroup.artemhuminkiy.skillincreaseapp.gitHub.Repository
import com.idapgroup.artemhuminkiy.skillincreaseapp.gitHub.User
import io.reactivex.Single


interface GitHub {
    fun repos(userName: String): Single<List<Repository>>

    fun userInfo(userName: String) : Single<User>
}