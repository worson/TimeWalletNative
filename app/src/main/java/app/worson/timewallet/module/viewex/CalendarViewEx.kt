package app.worson.timewallet.module.viewex

import com.haibin.calendarview.Calendar
import com.haibin.calendarview.CalendarView
import java.util.*

/**
 * @author worson  10.24 2020
 */

val startCalendar=Calendar().apply {
    year=1970
    month=1
    day=1
}

fun currentCalendar()=Calendar().apply {
    val date=Date()
    val calendar=java.util.Calendar.getInstance()
    year=calendar.get(java.util.Calendar.YEAR)
    month=calendar.get(java.util.Calendar.MONTH)
    day=calendar.get(java.util.Calendar.DATE)
}

fun CalendarView.dayFormatString():String{
    return "${curYear}-${curMonth}-${curDay}"
}


fun CalendarView.calendar():Calendar{
    return Calendar().apply {
        year=curYear
        month=curMonth
        day=curDay
    }
}

fun CalendarView.differDays():Int{
    val current = calendar()
    return current.differ(startCalendar)
}

fun Calendar.differDays():Int{
    return differ(startCalendar)
}


