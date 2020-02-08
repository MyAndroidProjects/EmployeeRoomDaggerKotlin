package com.lopatin.employeeroomdaggerkotlin.fragments.employee_info

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import kotlinx.android.synthetic.main.fragment_employee_info.*
import com.lopatin.employeeroomdaggerkotlin.R
import com.lopatin.employeeroomdaggerkotlin.fragments.BaseFragment
import com.lopatin.employeeroomdaggerkotlin.model.data.Employee
import com.lopatin.employeeroomdaggerkotlin.utils.Utils
import com.squareup.picasso.Callback
import com.squareup.picasso.Picasso



class EmployeeInfoFragment : BaseFragment(), EmployeeInfoFragmentContract.View {

    companion object {
        const val employeeKey: String = "employee"
        @Synchronized
        fun getInstance(employee: Employee): EmployeeInfoFragment {
            val fragment = EmployeeInfoFragment()
            val args = Bundle()
            args.putParcelable(employeeKey, employee)
            fragment.arguments = args
            return fragment
        }
    }

    private lateinit var employee: Employee
    private var presenter: EmployeeInfoFragmentContract.Presenter? = null
    override fun getFragmentLayout(): Int {
        return R.layout.fragment_employee_info
    }

    override fun getValuesFromArguments() {
        employee = arguments?.getParcelable(employeeKey) ?: return
    }

    override fun setPresenter() {
        presenter = EmployeeInfoFragmentPresenter
        presenter?.setViewToPresenter(this)
        presenter?.setEmployeeToPresenter(employee)
    }

    override fun nullifyPresenter() {
        presenter?.setViewToPresenter(null)
        presenter = null
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val createdView = super.onCreateView(inflater, container, savedInstanceState)
        presenter?.fragmentIsCreating(employee.id)
        return createdView
    }

    override fun onStart() {
        super.onStart()
        presenter?.fragmentIsStarting()
    }

    override fun setFragmentViewFields(employee: Employee) {
        if (employee.avatarUrl.isNotEmpty()) {
            val imageSizePix = Utils.dpToPx(resources.getDimension(R.dimen.employee_info_avatar_size))
            try {
                Picasso.get()
                    .load(employee.avatarUrl)
                    .resize(imageSizePix, imageSizePix)
                    .centerInside()
                    .priority(Picasso.Priority.NORMAL)
                    .into(employeeInfoAvatar, object :Callback{
                        override fun onSuccess() {
                            Log.d("logException", "setFragmentViewFields onSuccess")
                        }

                        override fun onError(e: java.lang.Exception?) {
                            Log.d("logException", "setFragmentViewFields onError: $e")
                            employeeInfoAvatar.visibility = View.GONE
                        }
                    })

            }catch (ex : Exception){
                Log.d("logException", "setFragmentViewFields Exception: $ex")
                employeeInfoAvatar.visibility = View.GONE
            }

        } else {
            employeeInfoAvatar.visibility = View.GONE
        }
        employeeInfoLastName.text = employee.lName
        employeeInfoFirstName.text = employee.fName
        val birthdayString = getString(R.string.employee_info_birthday, employee.birthday)
        birthday.text = birthdayString
        age.text = employee.age
    }

    override fun setSpecialtyViewFields(specialtyList: ArrayList<String>) {
        var i = 0
        for (specialty in specialtyList.iterator()) {
            if (i == 0) {
                specialtyFirst.text = specialty
            } else {
                specialtyLabel.text = getString(R.string.specialties_label)
                specialtySecond.visibility = View.VISIBLE
                specialtySecond.text = specialty
            }
            i++
        }
    }
}