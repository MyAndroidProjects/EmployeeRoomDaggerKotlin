package com.lopatin.employeeroomdaggerkotlin.model.data

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class EmployeeList(
    @SerializedName("response")
    @Expose
    val employeeList: ArrayList<EmployeeServer>? = null
)