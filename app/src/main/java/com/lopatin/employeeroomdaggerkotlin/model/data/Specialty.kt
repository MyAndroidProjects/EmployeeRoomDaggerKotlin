package com.lopatin.employeeroomdaggerkotlin.model.data

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName
import com.lopatin.employeeroomdaggerkotlin.model.database.EmployeeDatabaseInfo.COLUMN_SPECIALTY_ID
import com.lopatin.employeeroomdaggerkotlin.model.database.EmployeeDatabaseInfo.COLUMN_SPECIALTY_NAME
import com.lopatin.employeeroomdaggerkotlin.model.database.EmployeeDatabaseInfo.TABLE_SPECIALTY
import kotlinx.android.parcel.Parcelize

@Entity(tableName = TABLE_SPECIALTY)
@Parcelize
data class Specialty(
    @PrimaryKey
    @SerializedName("specialty_id")
    @ColumnInfo(name = COLUMN_SPECIALTY_ID)
    var specialtyId: Long?=null,
    @SerializedName("name")
    @ColumnInfo(name = COLUMN_SPECIALTY_NAME)
    var name: String = ""
) : Parcelable
