package com.idapgroup.artemhuminkiy.skillincreaseapp.gitHub

import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Path

interface GitHubApi {
    @GET("users/{user}/repos")
    fun listRepos(@Path("user") user : String) : Single<List<Repository>>

}