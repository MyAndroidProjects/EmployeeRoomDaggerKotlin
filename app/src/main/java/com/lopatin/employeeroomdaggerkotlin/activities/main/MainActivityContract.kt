package com.lopatin.employeeroomdaggerkotlin.activities.main

import android.content.Context
import androidx.fragment.app.Fragment
import com.lopatin.employeeroomdaggerkotlin.model.data.EmployeeServer
import com.lopatin.employeeroomdaggerkotlin.model.data.Specialty
import com.lopatin.employeeroomdaggerkotlin.model.data.SpecialtyOfEmployee

interface MainActivityContract {
    interface View {
        fun showBackButton(isShow: Boolean)
        fun callSuperBackPressed()
        fun showProgressBar(isShow: Boolean)
        fun setToolbarTitle(titleId: Int)
    }

    interface Presenter {
        fun setViewToPresenter(view: View?)
        fun activityIsCreating(context: Context)
        fun buttonBackPressed()
    }

    interface Navigator {
        fun createFragment(fragment: Fragment)
    }

    interface Model {
        fun putEmployeeToEmployeeTable(employee: EmployeeServer): Long
        fun putEmployeeAndSpecialtyToSpecialtyOfEmployeeTable(
            specialtyOfEmployee: SpecialtyOfEmployee
        ): Long

        fun putSpecialtyToSpecialtyTable(
            specialty : Specialty
        ): Long
    }
}