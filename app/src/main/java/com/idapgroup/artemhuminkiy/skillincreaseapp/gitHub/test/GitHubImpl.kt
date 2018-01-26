package com.idapgroup.artemhuminkiy.skillincreaseapp.gitHub.test

import com.idapgroup.artemhuminkiy.skillincreaseapp.gitHub.Repository
import com.idapgroup.artemhuminkiy.skillincreaseapp.gitHub.User
import io.reactivex.Single
import retrofit2.Retrofit
import retrofit2.http.GET
import retrofit2.http.Path
import javax.inject.Inject

class GitHubImpl @Inject constructor(private val retrofit: Retrofit) : GitHub {

    override fun userInfo(userName: String): Single<User> {
        return retrofit.create(GitHubApi::class.java).userInfo(userName)
    }

    override fun repos(userName: String): Single<List<Repository>> {
        return retrofit.create(GitHubApi::class.java).listRepos(userName)
    }

}

interface GitHubApi {
    @GET("users/{user}/repos")
    fun listRepos(@Path("user") user : String) : Single<List<Repository>>

    @GET("users/{user}")
    fun userInfo(@Path("user") user : String) : Single<User>
}