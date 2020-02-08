package com.lopatin.employeeroomdaggerkotlin.model.data

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize


@Parcelize
class EmployeeServer(
    @SerializedName("f_name")
    var fName: String = "",
    @SerializedName("l_name")
    var lName: String = "",
    @SerializedName("birthday")
    var birthday: String = "",
    @SerializedName("age")
    var age: String = "",
    @SerializedName("avatar_url")
    var avatarUrl: String = "",
    @SerializedName("specialty")
    var specialty: ArrayList<Specialty>? = null
) : Parcelable
