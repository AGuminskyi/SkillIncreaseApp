package com.idapgroup.artemhuminkiy.skillincreaseapp.gitHub

import com.idapgroup.artemhuminkiy.skillincreaseapp.gitHub.test.GitHub
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

class GitHubService {

    @Inject lateinit var gitHub: GitHub

/*    private val daggerAppComponent = DaggerAppComponent.builder().build().inject()
//    private var gitHubApi by lazy {  }

//    init {
//        val retrofit = Retrofit.Builder()
//                .baseUrl(Constants.GITHUB_BASE_URL)
//                .addConverterFactory(JacksonConverterFactory.create(objectMapper()))
//                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
//                .client(OkHttpClient.Builder().addInterceptor(getLoggingInterceptor()).build())
//                .build()
//        gitHubApi = retrofit.create(GitHubApi::class.java)
//    }
//
//    private fun getLoggingInterceptor(): Interceptor {
//        val logging = HttpLoggingInterceptor()
//        logging.level = HttpLoggingInterceptor.Level.BODY
//        return logging
//    }
//
//    private fun objectMapper(): ObjectMapper {
//        val objectMapper = ObjectMapper()
//        objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false)
//        objectMapper.setSerializationInclusion(JsonInclude.Include.NON_NULL)
//        return objectMapper
    } */
//
//    fun repos(userName: String): Single<List<Repository>> =
//            gitHubApi.listRepos(userName)
//                    .subscribeOn(Schedulers.io())
//                    .observeOn(AndroidSchedulers.mainThread())

    fun user(user: User): Single<User> =
            gitHub.userInfo(user.login)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())


    fun repos(user: User) : Single<List<Repository>> =
        gitHub.repos(user.login)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())

}