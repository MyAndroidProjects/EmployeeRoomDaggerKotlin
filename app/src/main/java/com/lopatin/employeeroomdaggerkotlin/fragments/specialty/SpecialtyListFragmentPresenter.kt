package com.lopatin.employeeroomdaggerkotlin.fragments.specialty

import android.util.Log
import com.lopatin.employeeroomdaggerkotlin.EmployeeApplication
import com.lopatin.employeeroomdaggerkotlin.activities.main.MainActivityPresenter
import com.lopatin.employeeroomdaggerkotlin.fragments.employee_list.EmployeeListFragment
import com.lopatin.employeeroomdaggerkotlin.model.data.Specialty
import kotlinx.coroutines.*
import java.util.*


object SpecialtyListFragmentPresenter : SpecialtyListFragmentContract.Presenter {
    private val navigator = SpecialtyListFragmentNavigator as SpecialtyListFragmentContract.Navigator

    private var specialtyList = ArrayList<Specialty>()
    private var view: SpecialtyListFragmentContract.View? = null

    override fun fragmentIsStarting() {
        CoroutineScope(Dispatchers.Main + Job()).launch {
            // при ошибке получения данныx выбрасывает ошибку
            try {
                withContext(Dispatchers.Default) {
                    specialtyList = getSpecialtyList()
                    withContext(Dispatchers.Main) {
                        for (sp in specialtyList){
                            Log.d("logBD", "specialty : ${sp.specialtyId} ${sp.name}")
                        }
                        view?.setRecyclerAdapter(specialtyList)
                    }
                }
            } catch (ex: Exception) {
                Log.d("logException", "SpecialtyListFragmentPresenter Exception: $ex")
            }
        }
    }

    override fun selectedSpecialty(specialty: Specialty) {
        specialty.specialtyId ?: return
        navigator.setFragmentType(MainActivityPresenter.FragmentType.EMPLOYEE_LIST)
        navigator.createFragment(
            EmployeeListFragment.getInstance(specialty.specialtyId)
        )
    }

    private fun getSpecialtyList(): ArrayList<Specialty> {
        val model: SpecialtyListFragmentContract.Model =  EmployeeApplication.instance.appComponent.getEmployeeDatabaseManager()
        return model.getSpecialtyListFromSpecialtyTable()
    }

    override fun setViewToPresenter(view: SpecialtyListFragmentContract.View?) {
        SpecialtyListFragmentPresenter.view = view
    }
}