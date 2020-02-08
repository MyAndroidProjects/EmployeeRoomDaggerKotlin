package com.lopatin.employeeroomdaggerkotlin.model.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.lopatin.employeeroomdaggerkotlin.model.data.Employee
import com.lopatin.employeeroomdaggerkotlin.model.data.Specialty
import com.lopatin.employeeroomdaggerkotlin.model.data.SpecialtyOfEmployee


@Database(
    entities = [Employee::class, Specialty::class, SpecialtyOfEmployee::class],
    version = 1,
    exportSchema = false
)
abstract class EmployeeDatabase : RoomDatabase() {
abstract fun databaseEmployeeDao() : EmployeeDao
    abstract fun databaseSpecialtyDao(): SpecialtyDao
    abstract fun databaseSpecialtyOfEmployeeDao(): DatabaseSpecialtyOfEmployeeDao
}