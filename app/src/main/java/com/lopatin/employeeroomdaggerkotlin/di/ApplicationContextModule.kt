package com.lopatin.employeeroomdaggerkotlin.di

import android.content.Context
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class ApplicationContextModule (private val context: Context) {
    @Singleton
    @Provides
    fun provideContext(): Context {
        return context
    }
}