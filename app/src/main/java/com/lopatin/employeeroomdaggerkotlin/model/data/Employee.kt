package com.lopatin.employeeroomdaggerkotlin.model.data

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Ignore
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName
import com.lopatin.employeeroomdaggerkotlin.model.database.EmployeeDatabaseInfo.COLUMN_EMPLOYEE_AGE
import com.lopatin.employeeroomdaggerkotlin.model.database.EmployeeDatabaseInfo.COLUMN_EMPLOYEE_BIRTHDAY
import com.lopatin.employeeroomdaggerkotlin.model.database.EmployeeDatabaseInfo.COLUMN_EMPLOYEE_FIRST_NAME
import com.lopatin.employeeroomdaggerkotlin.model.database.EmployeeDatabaseInfo.COLUMN_EMPLOYEE_ID
import com.lopatin.employeeroomdaggerkotlin.model.database.EmployeeDatabaseInfo.COLUMN_EMPLOYEE_IMAGE_PATH
import com.lopatin.employeeroomdaggerkotlin.model.database.EmployeeDatabaseInfo.COLUMN_EMPLOYEE_LAST_NAME
import com.lopatin.employeeroomdaggerkotlin.model.database.EmployeeDatabaseInfo.TABLE_EMPLOYEE
import kotlinx.android.parcel.Parcelize

/**
 *  @ColumnInfo(index = true) - проверка на уникальность в колонке
 *  @Entity(indices = {@Index(value = {"f_name", "l_name"}, unique = true)}) - проверка на уникальность пары значений в таблице
 *  если не указыватьт название колонки то оно будет названо так же как и поле класса
 *  если не указывать название таблицы, то таблица будет названа как класс
 *   @ColumnInfo(typeAffinity = TEXT) - явное присвоекние типа записи (по умолчанию Room сам определит тип)
 *   PrimaryKey -  параметр autoGenerate включает для поля режим autoincrement
 *   Внешние ключи ForeignKey
 *   @Entity(foreignKeys = @ForeignKey(entity = SomeClass1.class, parentColumns = "real_name", childColumns = "f_name"))
 *   дополнительным параметром можно указать поведение при удалении внешнего (родительского) ключа, например  onDelete = CASCADE
 *   параметр аннотации ForeignKey deferred, имеет по умолчанию значение false, если задать true, то внешний ключ станет отложенным и будет проверяться только в конце транзакции
 *   Если в Entity использовать как поле другой класс, то его помечаем аннотацией @Embedded и с таблицу бцдет добавлены поля (как колонки) из этого класса
 *   Embedded объекты могут включать в себя другие Embedded объекты
 *   Аннотация @Ignore позволяет подсказать Room, что это поле не должно записываться в базу или читаться из нее, например  @Ignore val employeeGender: String
 */
/**
 * Чтобы скомпилировалось надо чтобы поля были изменяемыми и имели значение по умолчанию
 */
@Entity(tableName = TABLE_EMPLOYEE)
@Parcelize
data class Employee(
    @SerializedName("id")
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = COLUMN_EMPLOYEE_ID)
    var id: Long?=null,
    @SerializedName("f_name")
    @ColumnInfo(name = COLUMN_EMPLOYEE_FIRST_NAME)
    var fName: String = "",
    @SerializedName("l_name")
    @ColumnInfo(name = COLUMN_EMPLOYEE_LAST_NAME)
    var lName: String = "",
    @SerializedName("birthday")
    @ColumnInfo(name = COLUMN_EMPLOYEE_BIRTHDAY)
    var birthday: String = "",
    @SerializedName("age")
    @ColumnInfo(name = COLUMN_EMPLOYEE_AGE)
    var age: String = "",
    @SerializedName("avatar_url")
    @ColumnInfo(name = COLUMN_EMPLOYEE_IMAGE_PATH)
    var avatarUrl: String = "",
    @SerializedName("specialty")
    @Ignore
    var specialty: ArrayList<Specialty>? = null
) : Parcelable
