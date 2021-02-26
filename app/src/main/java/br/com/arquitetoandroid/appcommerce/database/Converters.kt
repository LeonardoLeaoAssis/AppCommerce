package br.com.arquitetoandroid.appcommerce.database

import androidx.room.TypeConverter
import br.com.arquitetoandroid.appcommerce.model.Method
import br.com.arquitetoandroid.appcommerce.model.Status

class Converters {

    @TypeConverter
    fun fromStatus(status: Status): String = status.toString()

    @TypeConverter
    fun toStatus(value: String): Status = Status.valueOf(value)

    @TypeConverter
    fun fromMethod(method: Method): String {
        return method.toString()
    }

    @TypeConverter
    fun toMethod(value: String): Method{
        return Method.valueOf(value)
    }

}