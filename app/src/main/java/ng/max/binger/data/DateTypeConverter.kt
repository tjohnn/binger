package ng.max.binger.data

import android.arch.persistence.room.TypeConverter
import java.util.*

open class DateTypeConverter {

    @TypeConverter
    fun dateToLong(date: Date) = date.time

    @TypeConverter
    fun longToDate(long: Long) = Date(long)
}