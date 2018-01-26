package com.idapgroup.artemhuminkiy.skillincreaseapp.app.di

import com.idapgroup.artemhuminkiy.skillincreaseapp.app.App
import dagger.Component

@Component
interface AppComponent {
    fun inject(app: App)
}