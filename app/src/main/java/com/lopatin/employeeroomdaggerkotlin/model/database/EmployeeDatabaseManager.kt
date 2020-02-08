package com.lopatin.employeeroomdaggerkotlin.model.database

import android.util.Log
import com.lopatin.employeeroomdaggerkotlin.EmployeeApplication
import com.lopatin.employeeroomdaggerkotlin.activities.main.MainActivityContract
import com.lopatin.employeeroomdaggerkotlin.fragments.employee_info.EmployeeInfoFragmentContract
import com.lopatin.employeeroomdaggerkotlin.fragments.employee_list.EmployeeListFragmentContract
import com.lopatin.employeeroomdaggerkotlin.fragments.specialty.SpecialtyListFragmentContract
import com.lopatin.employeeroomdaggerkotlin.model.data.Employee
import com.lopatin.employeeroomdaggerkotlin.model.data.EmployeeServer
import com.lopatin.employeeroomdaggerkotlin.model.data.Specialty
import com.lopatin.employeeroomdaggerkotlin.model.data.SpecialtyOfEmployee
import kotlinx.coroutines.*
import kotlin.Exception

/**
 *  В EmployeeDatabaseManager будем использовать методы моделей
 *  тут из dagger брать базу данных
 */
class EmployeeDatabaseManager : MainActivityContract.Model, SpecialtyListFragmentContract.Model,
    EmployeeListFragmentContract.Model,
    EmployeeInfoFragmentContract.Model {

    override fun putEmployeeToEmployeeTable(employee: EmployeeServer): Long {
        var id: Long = 1
        val database: EmployeeDatabase =
            EmployeeApplication.instance.appComponent.getEmployeeDatabase()
        try {
            val emp = Employee(
                null,
                employee.fName,
                employee.lName,
                employee.birthday,
                employee.age,
                employee.avatarUrl
            )
            id = database.databaseEmployeeDao().insert(emp)
        } catch (ex: Exception) {
            Log.d("logException", "putEmployeeToEmployeeTable Exception: $ex")
        }

        return id
    }

    override fun putEmployeeAndSpecialtyToSpecialtyOfEmployeeTable(specialtyOfEmployee: SpecialtyOfEmployee): Long {
        var id: Long = 1
        val database: EmployeeDatabase =
            EmployeeApplication.instance.appComponent.getEmployeeDatabase()
        try {
            id = database.databaseSpecialtyOfEmployeeDao().insert(specialtyOfEmployee)
        } catch (ex: Exception) {
            Log.d(
                "logException",
                "putEmployeeAndSpecialtyToSpecialtyOfEmployeeTable Exception: $ex"
            )
        }

        return id
    }

    override fun putSpecialtyToSpecialtyTable(specialty: Specialty): Long {

        var id: Long = 1
        val database: EmployeeDatabase =
            EmployeeApplication.instance.appComponent.getEmployeeDatabase()
        try {
            id = database.databaseSpecialtyDao().insert(specialty)
        } catch (ex: Exception) {
            Log.d("logException", "putSpecialtyToSpecialtyTable Exception: $ex")
        }

        return id
    }

    override fun getSpecialtyListFromSpecialtyTable(): ArrayList<Specialty> {
        val database: EmployeeDatabase =
            EmployeeApplication.instance.appComponent.getEmployeeDatabase()
        return ArrayList(database.databaseSpecialtyDao().getAllSpecialties())
    }

    override suspend fun  getEmployeeListBySpecialty(specialtyId: Long): ArrayList<Employee> {
        var list = ArrayList<Employee>()

            // при ошибке получения данныx выбрасывает ошибку

                withContext(Dispatchers.Default) {
                    val database: EmployeeDatabase =
                        EmployeeApplication.instance.appComponent.getEmployeeDatabase()

                    val employeeIdList: List<Long> =
                        database.databaseSpecialtyOfEmployeeDao()
                            .getEmployeeIdListBySpecialtyId(specialtyId)
                  val  list2 = ArrayList(
                        database.databaseEmployeeDao().getEmployeeListByIdList(employeeIdList)
                    )
                    withContext(Dispatchers.Main) {
                        list = list2

                        for (emp in list) {
                            Log.d( "logBD",
                                "employee111 : ${emp.id} ${emp.fName} ${emp.lName} ${emp.specialty}"
                            )
                        }
                    }
                }

        for (emp in list) {
            Log.d( "logBD",
                "employee 222 : ${emp.id} ${emp.fName} ${emp.lName} ${emp.specialty}"
            )
        }
        return list
    }

    override suspend fun  getSpecialtyListByDatabaseEmployee(employeeId: Long): ArrayList<String> {
        val database: EmployeeDatabase =
            EmployeeApplication.instance.appComponent.getEmployeeDatabase()
        val specialtyIdList =
            database.databaseSpecialtyOfEmployeeDao().getSpecialtyIdListByEmployeeId(employeeId)

        return ArrayList(database.databaseSpecialtyDao().getSpecialtiesNames(specialtyIdList))
    }


}