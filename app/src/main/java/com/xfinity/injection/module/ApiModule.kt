package com.xfinity.injection.module

import com.xfinity.data.remote.CharactersService

import javax.inject.Singleton

import dagger.Module
import dagger.Provides
import retrofit2.Retrofit

@Module(includes = arrayOf(NetworkModule::class))
class ApiModule {

    @Provides
    @Singleton
    internal fun provideAngelApi(retrofit: Retrofit): CharactersService {
        return retrofit.create(CharactersService::class.java)
    }

}