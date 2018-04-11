package com.mag.denis.game

import android.content.Context
import android.content.SharedPreferences
import android.content.pm.PackageManager
import android.content.res.Resources
import android.preference.PreferenceManager
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class AndroidModule {

    @Provides @Singleton fun provideSharedPreferences(context: Context): SharedPreferences {
        return PreferenceManager.getDefaultSharedPreferences(context)
    }

    @Provides @Singleton fun provideResources(context: Context): Resources {
        return context.resources
    }

    @Provides @Singleton fun providePackageManager(context: Context): PackageManager {
        return context.packageManager
    }

}
