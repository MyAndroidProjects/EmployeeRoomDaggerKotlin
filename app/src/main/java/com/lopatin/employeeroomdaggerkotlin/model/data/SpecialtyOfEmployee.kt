package com.lopatin.employeeroomdaggerkotlin.model.data

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.lopatin.employeeroomdaggerkotlin.model.database.EmployeeDatabaseInfo.COLUMN_SPECIALTY_OF_EMPLOYEE_EMPLOYEE_ID
import com.lopatin.employeeroomdaggerkotlin.model.database.EmployeeDatabaseInfo.COLUMN_SPECIALTY_OF_EMPLOYEE_ID
import com.lopatin.employeeroomdaggerkotlin.model.database.EmployeeDatabaseInfo.COLUMN_SPECIALTY_OF_EMPLOYEE_SPECIALTY_ID
import com.lopatin.employeeroomdaggerkotlin.model.database.EmployeeDatabaseInfo.TABLE_SPECIALTY_OF_EMPLOYEE
import kotlinx.android.parcel.Parcelize

@Entity(tableName = TABLE_SPECIALTY_OF_EMPLOYEE)
@Parcelize
class SpecialtyOfEmployee(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = COLUMN_SPECIALTY_OF_EMPLOYEE_ID)
    var id: Long?=null,
    @ColumnInfo(name = COLUMN_SPECIALTY_OF_EMPLOYEE_SPECIALTY_ID)
    var specialtyId: Long?,
    @ColumnInfo(name = COLUMN_SPECIALTY_OF_EMPLOYEE_EMPLOYEE_ID)
    var employeeId: Long?
) : Parcelable