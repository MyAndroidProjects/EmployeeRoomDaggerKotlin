package com.lopatin.employeeroomdaggerkotlin.model.database

import androidx.room.*
import com.lopatin.employeeroomdaggerkotlin.model.data.Employee
import com.lopatin.employeeroomdaggerkotlin.model.database.EmployeeDatabaseInfo.TABLE_EMPLOYEE


@Dao
interface EmployeeDao {
    @Query("SELECT * FROM $TABLE_EMPLOYEE")
    fun getAllEmployees(): List<Employee>

    @Query("SELECT * FROM $TABLE_EMPLOYEE WHERE ${EmployeeDatabaseInfo.COLUMN_EMPLOYEE_ID} = :id")
    fun getEmployeeById(id: Int): Employee

    @Query("SELECT * FROM $TABLE_EMPLOYEE WHERE ${EmployeeDatabaseInfo.COLUMN_EMPLOYEE_ID} IN (:idList) ORDER BY ${EmployeeDatabaseInfo.COLUMN_EMPLOYEE_LAST_NAME}, ${EmployeeDatabaseInfo.COLUMN_EMPLOYEE_FIRST_NAME}")
    fun getEmployeeListByIdList(idList: List<Long>): List<Employee>

    @Insert
    fun insert(employee: Employee): Long

    @Insert
    fun insert(employees: List<Employee>)

    @Update
    fun update(employee: Employee)

    @Delete
    fun delete(employee: Employee)

}