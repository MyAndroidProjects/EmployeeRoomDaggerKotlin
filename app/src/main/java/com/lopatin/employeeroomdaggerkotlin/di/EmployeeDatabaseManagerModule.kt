package com.lopatin.employeeroomdaggerkotlin.di

import com.lopatin.employeeroomdaggerkotlin.model.database.EmployeeDatabaseManager
import dagger.Module
import dagger.Provides

@Module
class EmployeeDatabaseManagerModule {
    @Provides
    fun providesEmployeeDatabaseManager(): EmployeeDatabaseManager {
      return  EmployeeDatabaseManager()
    }
}