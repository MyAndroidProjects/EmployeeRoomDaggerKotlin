package com.lopatin.employeeroomdaggerkotlin.di

import android.content.Context
import dagger.Module
import dagger.Provides
import androidx.room.Room
import com.lopatin.employeeroomdaggerkotlin.model.database.EmployeeDatabase
import com.lopatin.employeeroomdaggerkotlin.model.database.EmployeeDatabaseInfo.DATABASE_NAME
import javax.inject.Singleton

@Module
class EmployeeDatabaseModule {
    @Singleton
    @Provides
    fun providesAppDatabase(context: Context): EmployeeDatabase {
      return  Room.databaseBuilder(context, EmployeeDatabase::class.java, DATABASE_NAME)
            .build()
    }
}