package com.mag.denis.game

import dagger.Component
import dagger.android.AndroidInjector
import javax.inject.Singleton

@Component(modules = [AppModule::class])
@Singleton
interface AppComponent : AndroidInjector<App> {

    @Component.Builder
    abstract class Builder : AndroidInjector.Builder<App> ()

}
