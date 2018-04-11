package com.mag.denis.game

import com.mag.denis.game.ui.InjectorModule
import dagger.Module
import dagger.android.support.AndroidSupportInjectionModule

@Module(includes = [AppBindModule::class, AndroidModule::class, AndroidSupportInjectionModule::class, InjectorModule::class])
class AppModule
