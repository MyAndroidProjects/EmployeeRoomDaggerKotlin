package com.lopatin.employeeroomdaggerkotlin.fragments.employee_list

import com.lopatin.employeeroomdaggerkotlin.EmployeeApplication
import com.lopatin.employeeroomdaggerkotlin.activities.main.MainActivityPresenter
import com.lopatin.employeeroomdaggerkotlin.fragments.employee_info.EmployeeInfoFragment
import com.lopatin.employeeroomdaggerkotlin.model.data.Employee
import kotlinx.coroutines.*

object EmployeeListFragmentPresenter : EmployeeListFragmentContract.Presenter {
    private val navigator = EmployeeListFragmentNavigator as EmployeeListFragmentContract.Navigator
    private var employeeList = ArrayList<Employee>()
    private var view: EmployeeListFragmentContract.View? = null
    private var specialtyId: Long = 0

    override fun fragmentIsCreating(specialtyId: Long) {
        this.specialtyId = specialtyId
    }

    override fun fragmentIsStarting() {
        CoroutineScope(Dispatchers.Main + Job()).launch {
            withContext(Dispatchers.Default) {
                val model: EmployeeListFragmentContract.Model =
                    EmployeeApplication.instance.appComponent.getEmployeeDatabaseManager()
                employeeList = model.getEmployeeListBySpecialty(specialtyId)
            }

            view?.setRecyclerAdapter(employeeList)
        }

    }

    override fun setViewToPresenter(view: EmployeeListFragmentContract.View?) {
        EmployeeListFragmentPresenter.view = view
    }

    override fun selectedEmployee(employee: Employee) {
        navigator.setFragmentType(MainActivityPresenter.FragmentType.EMPLOYEE_INFO)
        navigator.createFragment(EmployeeInfoFragment.getInstance(employee))
    }
}