package com.mag.denis.game.service

import dagger.Binds
import dagger.Module

@Module
interface MusicModule {

    @Binds fun bindView(service: MusicService): MusicView
}
