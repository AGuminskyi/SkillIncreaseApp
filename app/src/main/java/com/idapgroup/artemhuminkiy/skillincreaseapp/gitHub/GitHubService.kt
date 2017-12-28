package com.idapgroup.artemhuminkiy.skillincreaseapp.gitHub

import com.fasterxml.jackson.annotation.JsonInclude
import com.fasterxml.jackson.databind.DeserializationFeature
import com.fasterxml.jackson.databind.ObjectMapper
import com.idapgroup.artemhuminkiy.skillincreaseapp.Constants
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.jackson.JacksonConverterFactory

class GitHubService() {
    private var gitHubApi: GitHubApi

    init {
        val retrofit = Retrofit.Builder()
                .baseUrl(Constants.GITHUB_BASE_URL)
                .addConverterFactory(JacksonConverterFactory.create(objectMapper()))
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build()
        gitHubApi = retrofit.create(GitHubApi::class.java)
    }

    private fun objectMapper() : ObjectMapper{
        val objectMapper = ObjectMapper()
        objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false)
        objectMapper.setSerializationInclusion(JsonInclude.Include.NON_NULL)
        return objectMapper
    }

    fun repos(userName: String): Single<List<Repository>> =
            gitHubApi.listRepos(userName)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())

}