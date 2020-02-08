package com.lopatin.employeeroomdaggerkotlin.fragments.employee_list

import androidx.fragment.app.Fragment
import com.lopatin.employeeroomdaggerkotlin.activities.main.MainActivityPresenter
import com.lopatin.employeeroomdaggerkotlin.navigation.NavigationContract
import com.lopatin.employeeroomdaggerkotlin.navigation.NavigationManager

/**
 * Navigator set commands to Navigation Manager
 */

object EmployeeListFragmentNavigator : EmployeeListFragmentContract.Navigator {
    private val manager = NavigationManager as NavigationContract.Manager
    override fun createFragment(fragment: Fragment) {
        manager.createFragment(fragment)
    }

    override fun setFragmentType(currentFragmentType: MainActivityPresenter.FragmentType) {
        manager.setFragmentType(currentFragmentType)
    }
}