package com.lopatin.employeeroomdaggerkotlin.fragments.employee_list

import android.os.Bundle
import android.os.Handler
import android.util.Log
import androidx.recyclerview.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import kotlinx.android.synthetic.main.fragment_employee_list.*
import com.lopatin.employeeroomdaggerkotlin.R
import com.lopatin.employeeroomdaggerkotlin.fragments.BaseFragment
import com.lopatin.employeeroomdaggerkotlin.model.data.Employee

class EmployeeListFragment : BaseFragment(), EmployeeListFragmentContract.View,
    EmployeeListFragmentAdapter.EmployeeListItemClickListener {

    companion object {
        const val specialtyIdKey: String = "specialtyId"
        @Synchronized
        fun getInstance(specialtyId: Long?): EmployeeListFragment {
            val fragment = EmployeeListFragment()
            if (specialtyId != null) {
                val args = Bundle()
                args.putLong(specialtyIdKey, specialtyId)
                fragment.arguments = args
            }
            return fragment
        }
    }

    private var presenter: EmployeeListFragmentContract.Presenter? = null
    private var specialtyId: Long = 0

    override fun getFragmentLayout(): Int {
        return R.layout.fragment_employee_list
    }

    override fun getValuesFromArguments() {
        specialtyId = arguments?.getLong(specialtyIdKey) ?: return
    }

    override fun setPresenter() {
        presenter = EmployeeListFragmentPresenter
        presenter?.setViewToPresenter(this)
    }

    override fun nullifyPresenter() {
        presenter?.setViewToPresenter(null)
        presenter = null
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val createdView = super.onCreateView(inflater, container, savedInstanceState)
        presenter = EmployeeListFragmentPresenter
        presenter?.fragmentIsCreating(specialtyId)
        return createdView
    }

    override fun onStart() {
        super.onStart()
        val handler = Handler()
        handler.postDelayed({
            presenter?.fragmentIsStarting()
        }, 1000)

    }

    override fun setRecyclerAdapter(list: ArrayList<Employee>) {
        Log.d("logBD", "setRecyclerAdapter : ${list.size} ")
        val adapter = EmployeeListFragmentAdapter(list, this)
        val layoutManager = LinearLayoutManager(context)
        recyclerViewEmployeeList.layoutManager = layoutManager
        recyclerViewEmployeeList.adapter = adapter
    }

    override fun onEmployeeItemClick(employee: Employee) {
        presenter?.selectedEmployee(employee)
    }
}