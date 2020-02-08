package com.lopatin.employeeroomdaggerkotlin.fragments.specialty

import androidx.fragment.app.Fragment
import com.lopatin.employeeroomdaggerkotlin.activities.main.MainActivityPresenter
import com.lopatin.employeeroomdaggerkotlin.model.data.Specialty

interface SpecialtyListFragmentContract {
    interface View {
        fun setRecyclerAdapter(list: ArrayList<Specialty>)
    }

    interface Presenter {
        fun setViewToPresenter(view: View?)
        fun selectedSpecialty(specialty: Specialty)
        fun fragmentIsStarting()
    }

    interface Navigator {
        fun createFragment(fragment: Fragment)
        fun setFragmentType(currentFragmentType: MainActivityPresenter.FragmentType)
    }

    interface Model {
        fun getSpecialtyListFromSpecialtyTable(): ArrayList<Specialty>
    }
}