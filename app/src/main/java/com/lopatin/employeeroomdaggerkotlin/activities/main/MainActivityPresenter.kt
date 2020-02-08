package com.lopatin.employeeroomdaggerkotlin.activities.main

import android.content.Context
import android.util.Log
import com.google.gson.Gson
import com.lopatin.employeeroomdaggerkotlin.EmployeeApplication
import io.reactivex.Observer
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import com.lopatin.employeeroomdaggerkotlin.fragments.specialty.SpecialtyListFragment
import com.lopatin.employeeroomdaggerkotlin.network.JsonConverterRetrofit
import com.lopatin.employeeroomdaggerkotlin.R
import com.lopatin.employeeroomdaggerkotlin.model.data.*
import com.lopatin.employeeroomdaggerkotlin.navigation.NavigationContract
import kotlinx.coroutines.*
import java.text.SimpleDateFormat
import java.util.*
import java.io.BufferedReader
import java.io.InputStreamReader
import java.io.StringWriter


object MainActivityPresenter : MainActivityContract.Presenter,
    NavigationContract.MainActivityPresenter {

    enum class FragmentType {
        SPECIALTY,
        EMPLOYEE_LIST,
        EMPLOYEE_INFO
    }

    private val navigator: MainActivityContract.Navigator = MainActivityNavigator
    private var view: MainActivityContract.View? = null

    private var lastFragmentType: FragmentType = FragmentType.SPECIALTY
    private var currentFragmentType: FragmentType = FragmentType.SPECIALTY
        set(value) {
            lastFragmentType = field
            field = value
            when (value) {
                FragmentType.SPECIALTY -> {
                    view?.showBackButton(false)
                    view?.setToolbarTitle(R.string.toolbar_title_specialty)
                }
                FragmentType.EMPLOYEE_LIST -> {
                    view?.showBackButton(true)
                    view?.setToolbarTitle(R.string.toolbar_title_employees)
                }
                FragmentType.EMPLOYEE_INFO -> {
                    view?.showBackButton(true)
                    view?.setToolbarTitle(R.string.toolbar_title_employee_info)
                }
            }
        }
    private var isFirstStartActivity: Boolean = true
    private const val URL: String = "testTask.json"
    private const val PLACE_HOLDER = """««"""

    override fun setViewToPresenter(view: MainActivityContract.View?) {
        MainActivityPresenter.view = view
    }

    override fun buttonBackPressed() {
        if (currentFragmentType == FragmentType.SPECIALTY) {
            view?.callSuperBackPressed()
            view?.callSuperBackPressed()
        } else {
            currentFragmentType = lastFragmentType
            view?.callSuperBackPressed()
        }
    }

    override fun activityIsCreating(context: Context) {
        if (isFirstStartActivity) {
            getDataFromRawAndSetToDatabase(context)
            isFirstStartActivity = false
        } else {
            createSpecialtyFragment()
        }
    }

    /**
     * getting data from server replaced getting data from raw/all_employees.json
     */
    private fun getDataFromRaw(context: Context): EmployeeList {
        val inputStream = context.resources.openRawResource(R.raw.all_employees)
        val writer = StringWriter()
        val buffer = CharArray(1024)
        try {
            val reader = BufferedReader(InputStreamReader(inputStream, "UTF-8"))
            var n: Int = reader.read(buffer)
            while (n != -1) {
                writer.write(buffer, 0, n)
                n = reader.read(buffer)
            }
        } finally {
            inputStream.close()
        }

        val jsonString = writer.toString()
        val gson = Gson()
        return gson.fromJson(jsonString, EmployeeList::class.java)
    }

    /**
     * getting data from server replaced getting data from raw/all_employees.json with coroutines
     */
    private fun getDataFromRawAndSetToDatabase(context: Context) {
        view?.showProgressBar(true)
        CoroutineScope(Dispatchers.Main + Job()).launch {
            // при ошибке получения данныx выбрасывает ошибку
            try {
                withContext(Dispatchers.Default) {
                    /**
                     * иммитация задержки ответа с сервера
                     */
                    delay(400)
                    val employeeListResponse = getDataFromRaw(context)
                    val employeeList = employeeListResponse.employeeList
                    employeeList ?: return@withContext
                    var i = 0
                    for (employee: EmployeeServer in employeeList.iterator()) {
                        withContext(Dispatchers.Main) {
                            Log.d("logBD", "EmployeeServer: ${employee.fName} ${employee.lName} ${employee.age}")
                        }
                        val databaseEmployee = convertEmployeeToDatabaseEmployee(employee)
                        withContext(Dispatchers.Main) {
                            Log.d("logBD", "EmployeeServer: ${databaseEmployee?.fName} ${databaseEmployee?.lName} ${databaseEmployee?.age}")
                        }
                        databaseEmployee ?: continue
                        fillDatabase( databaseEmployee)
                        i++
                    }
                    withContext(Dispatchers.Main) {
                        view?.showProgressBar(false)
                        createSpecialtyFragment()
                    }
                }
            } catch (ex: Exception) {
                Log.d("logException", "Exception: $ex")
            }
        }
    }

    /**
     * getting data from server replaced getting data from raw/all_employees.json
     *  get data from url
     */
    private fun getDataFromNetAndSetToDatabase(context: Context) {
        val jsonApi = JsonConverterRetrofit.retrofitApiJson
        val observer = object : Observer<EmployeeList> {
            override fun onSubscribe(d: Disposable) {}

            override fun onNext(employeeList: EmployeeList) {
                view?.showProgressBar(true)
                val list = employeeList.employeeList
                list ?: return
                var i = 0
                for (employee: EmployeeServer in list.iterator()) {
                    val databaseEmployee = convertEmployeeToDatabaseEmployee(employee)
                    databaseEmployee ?: continue
                    fillDatabase( employee)
                    i++
                }
            }

            override fun onError(e: Throwable) {
                Log.d("logError", "getDataFromNet onError $e")
            }

            override fun onComplete() {
                view?.showProgressBar(false)
                createSpecialtyFragment()
            }
        }

        jsonApi.getObservableData(URL)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(observer)
    }

    private fun fillDatabase(employeeServer: EmployeeServer) {
        try {
        val model: MainActivityContract.Model =
            EmployeeApplication.instance.appComponent.getEmployeeDatabaseManager()
        val employeeId = model.putEmployeeToEmployeeTable(employeeServer)
        val specialtyList = employeeServer.specialty
        if (specialtyList != null) {
            for (specialty in specialtyList.iterator()) {
                specialty.specialtyId ?: continue
                val specialtyIdLong = specialty.specialtyId
                specialty.name
                val specialty: Specialty = Specialty(specialtyIdLong, specialty.name)
                model.putSpecialtyToSpecialtyTable(specialty)
                val specialtyOfEmployee = SpecialtyOfEmployee(null,specialtyIdLong, employeeId)
                model.putEmployeeAndSpecialtyToSpecialtyOfEmployeeTable(specialtyOfEmployee)
            }
        }
        }catch (ex : Exception){
            Log.d("logException", "fillDatabase Exception: $ex")
        }
    }

    private fun createSpecialtyFragment() {
        currentFragmentType = FragmentType.SPECIALTY
        navigator.createFragment(
            SpecialtyListFragment.getInstance()
        )
    }

    private fun convertEmployeeToDatabaseEmployee(employee: EmployeeServer): EmployeeServer? {
        var firstName = ""
        var lastName = ""
        var birthdayDate = PLACE_HOLDER
        var age = ""
        var avatarUrl = ""

        val fName = employee.fName
        if (fName != null) {
            firstName = convertStringToNameTemplate(fName)
        }
        val lName = employee.lName
        if (lName != null) {
            lastName = convertStringToNameTemplate(lName)
        }
        val birthday = employee.birthday
        if (birthday != null) {
            birthdayDate = convertBirthdayData(birthday)
        }
        age = calculateAge(birthdayDate)
        val avatarPath = employee.avatarUrl
        if (avatarPath != null) {
            avatarUrl = avatarPath
        }

        return EmployeeServer(
            firstName,
            lastName,
            birthdayDate,
            age,
            avatarUrl,
            employee.specialty
        )
    }

    private fun convertStringToNameTemplate(string: String): String {
        val stringStringBuilder = StringBuilder(string)
        var i = 0
        for (c in stringStringBuilder) {
            if (i == 0) {
                stringStringBuilder.setCharAt(i, c.toUpperCase())
            } else {
                stringStringBuilder.setCharAt(i, c.toLowerCase())
                c.toLowerCase()
            }
            i++
        }
        return String(stringStringBuilder)
    }

    private fun convertBirthdayData(date: String): String {
        val dateBirthday: Date?
        val regexYMD = Regex(pattern = """\d{4}-\d{2}-\d{2}""")
        val regexDMY = Regex(pattern = """\d{2}-\d{2}-\d{4}""")

        dateBirthday = when {
            regexYMD.matches(input = date) -> {
                val dateFormat = "yyyy-MM-dd"
                val formatDate = SimpleDateFormat(dateFormat, Locale.getDefault())
                formatDate.parse(date)
            }
            regexDMY.matches(input = date) -> {
                val dateFormat = "dd-MM-yyyy"
                val formatDate = SimpleDateFormat(dateFormat, Locale.getDefault())
                formatDate.parse(date)
            }
            else -> return PLACE_HOLDER
        }

        val dateFormat = "dd.MM.yyyy"
        val formatDate = SimpleDateFormat(dateFormat, Locale.getDefault())
        return formatDate.format(dateBirthday)
    }

    private fun calculateAge(date: String): String {

        if (date == PLACE_HOLDER) {
            return PLACE_HOLDER
        }
        val year: Int
        val month: Int
        val day: Int
        val dateParts = date.split(".")
        year = dateParts[2].toInt()
        month = dateParts[1].toInt()
        day = dateParts[0].toInt()

        val currentTime: Date = Calendar.getInstance().time
        val dateFormat = "dd.MM.yyyy"
        val formatDate = SimpleDateFormat(dateFormat, Locale.getDefault())
        val currentDate = formatDate.format(currentTime)
        val currentYear: Int
        val currentMonth: Int
        val currentDay: Int
        val currentDateParts = currentDate.split(".")
        currentYear = currentDateParts[2].toInt()
        currentMonth = currentDateParts[1].toInt()
        currentDay = currentDateParts[0].toInt()

        val ageYear = currentYear - year
        if (currentMonth < month) {
            ageYear - 1
        } else if (currentMonth == month) {
            if (currentDay < day) {
                ageYear - 1
            }
        }
        return ageYear.toString()
    }

    // NavigationContract.MainActivityPresenter
    override fun setFragmentType(currentFragmentType: FragmentType) {
        MainActivityPresenter.currentFragmentType = currentFragmentType
    }
}