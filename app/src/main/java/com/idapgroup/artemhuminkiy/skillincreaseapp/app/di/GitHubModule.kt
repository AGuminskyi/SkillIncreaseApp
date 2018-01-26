package com.idapgroup.artemhuminkiy.skillincreaseapp.app.di

import com.fasterxml.jackson.annotation.JsonInclude
import com.fasterxml.jackson.databind.DeserializationFeature
import com.fasterxml.jackson.databind.ObjectMapper
import com.idapgroup.artemhuminkiy.skillincreaseapp.Constants
import com.idapgroup.artemhuminkiy.skillincreaseapp.gitHub.test.GitHub
import com.idapgroup.artemhuminkiy.skillincreaseapp.gitHub.test.GitHubImpl
import dagger.Module
import dagger.Provides
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.jackson.JacksonConverterFactory.create
import javax.inject.Singleton

@Module
class GitHubModule {

//    @Singleton
//    @Provides
//    fun gitHubApi(retrofit: Retrofit): GitHubApi {
//        return retrofit.create(GitHubApi::class.java)
//    }

    @Singleton
    @Provides
    fun provideGitHub(impl : GitHubImpl) : GitHub = impl


    @Singleton
    @Provides
    fun retrofit(objectMapper: ObjectMapper, interceptor: Interceptor): Retrofit {
        return Retrofit.Builder()
                .baseUrl(Constants.GITHUB_BASE_URL)
                .addConverterFactory(create(objectMapper))
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .client(OkHttpClient.Builder().addInterceptor(interceptor).build())
                .build()
    }
//
    @Singleton
    @Provides
    fun interceptor(): Interceptor {
        return HttpLoggingInterceptor().apply { level = HttpLoggingInterceptor.Level.BODY }
    }

    @Singleton
    @Provides
    fun objectMapper(): ObjectMapper {
        val objectMapper = ObjectMapper()
        objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false)
        objectMapper.setSerializationInclusion(JsonInclude.Include.NON_NULL)
        return objectMapper
    }
}