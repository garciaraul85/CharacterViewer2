package com.xfinity.injection.module

import android.app.Application
import android.content.Context
import android.content.SharedPreferences
import com.xfinity.features.masterdetail.fragment.ItemListFragment
import com.xfinity.injection.ApplicationContext

import com.xfinity.util.PreferenceHelper

import dagger.Module
import dagger.Provides

import javax.inject.Singleton

import com.xfinity.util.PreferenceHelper.Companion.PREF_FILE_NAME

@Module(includes = arrayOf(ApiModule::class))
class AppModule(private val application: Application) {

    @Provides
    internal fun provideApplication(): Application {
        return application
    }

    @Provides
    @ApplicationContext
    internal fun provideContext(): Context {
        return application
    }

    @Provides
    @ApplicationContext
    internal fun provideSharedPreference(@ApplicationContext context: Context): SharedPreferences {
        return context.getSharedPreferences(PREF_FILE_NAME, Context.MODE_PRIVATE)
    }

    @Provides
    @Singleton
    internal fun providePreferencesHelper(@ApplicationContext sharedPreferences: SharedPreferences): PreferenceHelper {
        return PreferenceHelper(sharedPreferences)
    }

}