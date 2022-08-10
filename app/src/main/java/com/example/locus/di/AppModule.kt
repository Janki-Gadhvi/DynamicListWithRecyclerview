package com.example.locus.di

import android.content.Context
import com.example.locus.utils.LoadJson
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Named
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Singleton
    @Provides
    fun provideLoadJson(@ApplicationContext context: Context): LoadJson {
        return LoadJson(context)
    }

    @Singleton
    @Provides
    @Named("filename")
    fun provideFileName() = "list.json"
}