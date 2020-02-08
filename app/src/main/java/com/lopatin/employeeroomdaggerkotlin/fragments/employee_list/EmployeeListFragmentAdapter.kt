package com.lopatin.employeeroomdaggerkotlin.fragments.employee_list

import androidx.recyclerview.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import kotlinx.android.synthetic.main.item_employee_list.view.*
import com.lopatin.employeeroomdaggerkotlin.R
import com.lopatin.employeeroomdaggerkotlin.model.data.Employee

class EmployeeListFragmentAdapter(
    private val list: ArrayList<Employee>,
    private val clickListener: EmployeeListItemClickListener
) :
    RecyclerView.Adapter<EmployeeListFragmentAdapter.ViewHolder>() {

    override fun onCreateViewHolder(viewGroup: ViewGroup, i: Int): ViewHolder {
        val view = LayoutInflater.from(viewGroup.context)
            .inflate(R.layout.item_employee_list, viewGroup, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return list.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = list[position]
        holder.employeeItemView.employeeLastName.text=item.lName
        holder.employeeItemView.employeeFirstName.text=item.fName
        holder.employeeItemView.age.text=item.age

        holder.itemView.setOnClickListener {
            clickListener.onEmployeeItemClick(item)
        }
    }

    class ViewHolder(val employeeItemView: View) : RecyclerView.ViewHolder(employeeItemView)

    interface EmployeeListItemClickListener {
        fun onEmployeeItemClick(employee: Employee)
    }
}