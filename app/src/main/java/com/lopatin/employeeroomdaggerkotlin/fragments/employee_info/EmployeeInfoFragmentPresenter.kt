package com.lopatin.employeeroomdaggerkotlin.fragments.employee_info

import com.lopatin.employeeroomdaggerkotlin.EmployeeApplication
import com.lopatin.employeeroomdaggerkotlin.model.data.Employee
import kotlinx.coroutines.*

object EmployeeInfoFragmentPresenter : EmployeeInfoFragmentContract.Presenter {
    private var view: EmployeeInfoFragmentContract.View? = null
    private lateinit var specialtyList: ArrayList<String>
    private lateinit var employee: Employee
    private var employeeId: Long? = 0

    override fun setViewToPresenter(view: EmployeeInfoFragmentContract.View?) {
        EmployeeInfoFragmentPresenter.view = view
    }

    override fun setEmployeeToPresenter(employee: Employee) {
        this.employee = employee
    }

    override fun fragmentIsCreating(employeeId: Long?) {
        employeeId ?: return
        this.employeeId = employeeId

    }

    override fun fragmentIsStarting() {
        val eId = employeeId
        eId ?: return
        CoroutineScope(Dispatchers.Main + Job()).launch {
            withContext(Dispatchers.Default){
                val model: EmployeeInfoFragmentContract.Model =
                    EmployeeApplication.instance.appComponent.getEmployeeDatabaseManager()
                specialtyList = model.getSpecialtyListByDatabaseEmployee(eId)
            }
            view?.setSpecialtyViewFields(specialtyList)
            view?.setFragmentViewFields(employee)
        }
    }
}