package app.worson.timewallet.module.viewex

import app.worson.timewallet.utils.time.CallendarUtil.startCalendar
import com.haibin.calendarview.Calendar
import com.haibin.calendarview.CalendarView
import java.util.*

fun currentCalendar()=Calendar().apply {
    val date=Date()
    val calendar=java.util.Calendar.getInstance()
    year=calendar.get(java.util.Calendar.YEAR)
    month=calendar.get(java.util.Calendar.MONTH)+1
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


