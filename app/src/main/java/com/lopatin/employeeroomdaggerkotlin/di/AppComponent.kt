package com.lopatin.employeeroomdaggerkotlin.di

import com.lopatin.employeeroomdaggerkotlin.model.database.EmployeeDatabase
import com.lopatin.employeeroomdaggerkotlin.model.database.EmployeeDatabaseManager
import dagger.Component
import javax.inject.Singleton


@Singleton
@Component(modules = [EmployeeDatabaseModule::class, ApplicationContextModule::class, EmployeeDatabaseManagerModule::class]) // массив модулей, к которым будет доступ
interface AppComponent {
    /**
     * методы, указывающие на то, какой класс предоставить get-методы
     */
       fun getEmployeeDatabase() : EmployeeDatabase
       fun getEmployeeDatabaseManager() : EmployeeDatabaseManager
}