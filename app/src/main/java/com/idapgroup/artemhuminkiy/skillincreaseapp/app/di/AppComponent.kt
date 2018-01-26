package com.idapgroup.artemhuminkiy.skillincreaseapp.app.di

import com.idapgroup.artemhuminkiy.skillincreaseapp.app.App
import com.idapgroup.artemhuminkiy.skillincreaseapp.gitHub.test.GitHub
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = arrayOf(GitHubModule::class))
interface AppComponent {
    fun inject(app: App)

    fun provideGitHub():GitHub
}