package ru.skillbranch.devintensive.extensions

import java.text.SimpleDateFormat
import java.util.*

const val SECOND = 1000L
const val MINUTE = 60*SECOND
const val HOUR = 60* MINUTE
const val DAY = 24* HOUR

fun Date.format(pattern:String="HH:mm:ss dd.MM.yy"): String{
    val dateFormat = SimpleDateFormat(pattern, Locale("RU"))
    return dateFormat.format(this)
}
fun Date.add(value:Int, units: TimeUnits):Date {
    var time = this.time
    time+=when(units){
        TimeUnits.SECOND -> value* SECOND
        TimeUnits.MINUTE -> value* MINUTE
        TimeUnits.HOUR -> value* HOUR
        TimeUnits.DAY -> value* DAY
    }

    this.time = time
    return this
}
enum class TimeUnits(){

    SECOND, MINUTE, HOUR, DAY;

    fun plural(value: Int):String {
        val lastNum = value.toString().takeLast(1).toInt()
        val map1 = mapOf<TimeUnits,String>(SECOND to "секунду", MINUTE to "минуту", HOUR to "час", DAY to "день")
        val map24 = mapOf<TimeUnits,String>(SECOND to "секунды", MINUTE to "минуты", HOUR to "часа", DAY to "дня")
        val mapDefault = mapOf<TimeUnits,String>(SECOND to "секунд", MINUTE to "минут", HOUR to "часов", DAY to "дней")
        val pronounNumName = when (lastNum)
        {
            1 -> map1[this]
            in 2..4 -> map24[this]
            else -> mapDefault[this]
        }
        return "$value $pronounNumName"
    }
}

fun Date.humanizeDiff(date: Date = Date()): String{

    var dateDiff = (date.time - this.time)// time difference in seconds

    val postfix = if (dateDiff > 0) " назад" else ""
    val prefix = if (dateDiff < 0) "через " else ""

    if (dateDiff<0) {
        dateDiff *= -1
    }

    return when (dateDiff){
        in 0 .. 1*SECOND -> "только что"
        in 1*SECOND .. 45* SECOND -> "${prefix}несколько секунд$postfix"
        in 45* SECOND .. 75* SECOND -> "${prefix}минуту$postfix"
        in 75* SECOND .. 45* MINUTE -> "${prefix}${TimeUnits.MINUTE.plural((dateDiff/ MINUTE).toInt())}$postfix"
        in 45*MINUTE  .. 75* MINUTE -> "${prefix}час $postfix"
        in 75* MINUTE  .. 22* HOUR -> "${prefix}${TimeUnits.HOUR.plural((dateDiff/ HOUR).toInt())}$postfix"
        in 22* HOUR  .. 26* HOUR -> "${prefix}день$postfix"
        in 26* HOUR  .. 360* DAY -> "${prefix}${TimeUnits.DAY.plural((dateDiff/ DAY).toInt())}$postfix"
        else -> if (prefix.isNotBlank()) "более чем через год" else "более года назад"
    }

}

fun Date.shortFormat():String {
    return ""
}


