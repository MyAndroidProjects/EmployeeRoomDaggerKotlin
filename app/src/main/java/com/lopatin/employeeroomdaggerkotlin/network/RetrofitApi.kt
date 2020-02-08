package com.lopatin.employeeroomdaggerkotlin.network

import com.lopatin.employeeroomdaggerkotlin.model.data.EmployeeList
import io.reactivex.Observable
import retrofit2.http.GET
import retrofit2.http.Url

/**
 * getting data from server replaced getting data from raw/all_employees.json
 */

interface RetrofitApi {
    @GET
    fun getObservableData(@Url url: String): Observable<EmployeeList>
}