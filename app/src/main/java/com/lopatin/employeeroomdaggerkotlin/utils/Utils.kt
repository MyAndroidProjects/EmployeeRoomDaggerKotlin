package com.lopatin.employeeroomdaggerkotlin.utils

import android.util.DisplayMetrics
import com.lopatin.employeeroomdaggerkotlin.EmployeeApplication

object Utils {

    fun dpToPx(dp: Float): Int {
        EmployeeApplication.getApplicationContext().resources?.displayMetrics?.let {
            return (dp * (it.densityDpi.toFloat() / DisplayMetrics.DENSITY_DEFAULT)).toInt()
        }
        return 0
    }
}