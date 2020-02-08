package com.lopatin.employeeroomdaggerkotlin.fragments.employee_info

import com.lopatin.employeeroomdaggerkotlin.model.data.Employee

interface EmployeeInfoFragmentContract {
    interface View {
        fun setFragmentViewFields(employee: Employee)
        fun setSpecialtyViewFields(specialtyList: ArrayList<String>)
    }

    interface Presenter {
        fun setViewToPresenter(view: View?)
        fun setEmployeeToPresenter(employee: Employee)
        fun fragmentIsCreating(employeeId: Long?)
        fun fragmentIsStarting()
    }

    interface Model {
        suspend fun getSpecialtyListByDatabaseEmployee(employeeId: Long): ArrayList<String>
    }
}