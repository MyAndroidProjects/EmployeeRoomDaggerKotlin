package com.lopatin.employeeroomdaggerkotlin.model.database

import androidx.room.*
import com.lopatin.employeeroomdaggerkotlin.model.data.SpecialtyOfEmployee
import com.lopatin.employeeroomdaggerkotlin.model.database.EmployeeDatabaseInfo.COLUMN_SPECIALTY_OF_EMPLOYEE_EMPLOYEE_ID
import com.lopatin.employeeroomdaggerkotlin.model.database.EmployeeDatabaseInfo.COLUMN_SPECIALTY_OF_EMPLOYEE_SPECIALTY_ID
import com.lopatin.employeeroomdaggerkotlin.model.database.EmployeeDatabaseInfo.TABLE_SPECIALTY_OF_EMPLOYEE


@Dao
interface DatabaseSpecialtyOfEmployeeDao {

    @Insert(onConflict = OnConflictStrategy.ABORT)
    fun insert(specialtyOfEmployee: SpecialtyOfEmployee): Long

    @Update
    fun update(specialtyOfEmployee: SpecialtyOfEmployee)

    @Delete
    fun delete(specialtyOfEmployee: SpecialtyOfEmployee)

    @Query("SELECT $COLUMN_SPECIALTY_OF_EMPLOYEE_EMPLOYEE_ID FROM $TABLE_SPECIALTY_OF_EMPLOYEE WHERE $COLUMN_SPECIALTY_OF_EMPLOYEE_SPECIALTY_ID LIKE (:specialtyId)")
    fun getEmployeeIdListBySpecialtyId(specialtyId: Long): List<Long>

    @Query("SELECT $COLUMN_SPECIALTY_OF_EMPLOYEE_SPECIALTY_ID FROM $TABLE_SPECIALTY_OF_EMPLOYEE WHERE $COLUMN_SPECIALTY_OF_EMPLOYEE_EMPLOYEE_ID LIKE :employeeId ")
    fun getSpecialtyIdListByEmployeeId(employeeId: Long): List<Long>


}