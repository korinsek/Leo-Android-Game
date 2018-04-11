package com.mag.denis.game;

import android.content.Context;

import dagger.Binds;
import dagger.Module;

@Module
public interface AppBindModule {

    @Binds Context bindAppContext(App app);
}
