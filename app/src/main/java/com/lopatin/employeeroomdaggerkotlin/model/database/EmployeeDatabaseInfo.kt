package com.lopatin.employeeroomdaggerkotlin.model.database

/**
 * в базе данных три таблицы,
 * "specialty", "employee" и "specialty_of_employee"
 */
object EmployeeDatabaseInfo {
    const val DATABASE_NAME = "employee_database"

    // таблица "employee"
    const val TABLE_EMPLOYEE = "employee"
    const val COLUMN_EMPLOYEE_ID = "id"
    const val COLUMN_EMPLOYEE_FIRST_NAME = "f_name"
    const val COLUMN_EMPLOYEE_LAST_NAME = "l_name"
    const val COLUMN_EMPLOYEE_BIRTHDAY = "birthday"
    const val COLUMN_EMPLOYEE_AGE = "age"
    const val COLUMN_EMPLOYEE_IMAGE_PATH = "avatar_url"

    // таблица "specialty"
    const val TABLE_SPECIALTY = "specialty"
    const val COLUMN_SPECIALTY_ID = "id"
    const val COLUMN_SPECIALTY_NAME = "name"

    // таблица "specialty_of_employee"
    const val TABLE_SPECIALTY_OF_EMPLOYEE = "specialty_of_employee"
    const val COLUMN_SPECIALTY_OF_EMPLOYEE_ID = "id"
    const val COLUMN_SPECIALTY_OF_EMPLOYEE_SPECIALTY_ID = "specialty_id"
    const val COLUMN_SPECIALTY_OF_EMPLOYEE_EMPLOYEE_ID = "employee_id"
}