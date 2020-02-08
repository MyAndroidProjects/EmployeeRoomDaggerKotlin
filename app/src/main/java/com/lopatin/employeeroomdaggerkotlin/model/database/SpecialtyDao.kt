package com.lopatin.employeeroomdaggerkotlin.model.database

import androidx.room.*
import com.lopatin.employeeroomdaggerkotlin.model.data.Specialty
import com.lopatin.employeeroomdaggerkotlin.model.database.EmployeeDatabaseInfo.COLUMN_SPECIALTY_ID
import com.lopatin.employeeroomdaggerkotlin.model.database.EmployeeDatabaseInfo.COLUMN_SPECIALTY_NAME
import com.lopatin.employeeroomdaggerkotlin.model.database.EmployeeDatabaseInfo.TABLE_SPECIALTY


@Dao
interface SpecialtyDao {
    @Query("SELECT * FROM $TABLE_SPECIALTY")
    fun getAllSpecialties(): List<Specialty>


    @Query("SELECT $COLUMN_SPECIALTY_NAME FROM $TABLE_SPECIALTY WHERE $COLUMN_SPECIALTY_ID IN (:specialtyIdList) ")
    fun getSpecialtiesNames(specialtyIdList: List<Long>): List<String>


    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(specialty: Specialty): Long

    @Update
    fun update(specialty: Specialty)

    @Delete
    fun delete(specialty: Specialty)
}