package com.lopatin.employeeroomdaggerkotlin.fragments.employee_list

import androidx.fragment.app.Fragment
import com.lopatin.employeeroomdaggerkotlin.activities.main.MainActivityPresenter
import com.lopatin.employeeroomdaggerkotlin.model.data.Employee

interface EmployeeListFragmentContract {
    interface View {
        fun setRecyclerAdapter(list: ArrayList<Employee>)
    }

    interface Presenter {
        fun fragmentIsCreating(specialtyId: Long)
        fun fragmentIsStarting()
        fun setViewToPresenter(view: View?)
        fun selectedEmployee(employee: Employee)
    }

    interface Navigator {
        fun createFragment(fragment: Fragment)
        fun setFragmentType(currentFragmentType: MainActivityPresenter.FragmentType)
    }

    interface Model {
       suspend fun getEmployeeListBySpecialty(specialtyId: Long): ArrayList<Employee>
    }
}