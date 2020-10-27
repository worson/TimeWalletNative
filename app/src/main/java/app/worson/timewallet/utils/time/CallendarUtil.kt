package app.worson.timewallet.utils.time

import com.haibin.calendarview.Calendar

/**
 * @author worson  10.27 2020
 */
object CallendarUtil {

    val startCalendar= Calendar().apply {
        year=1970
        month=1
        day=1
    }

    private fun calendar(timeMs:Long):Calendar=Calendar().apply {
        val calendar=java.util.Calendar.getInstance()
        calendar.setTimeInMillis(timeMs)
        year=calendar.get(java.util.Calendar.YEAR)
        month=calendar.get(java.util.Calendar.MONTH)+1
        day=calendar.get(java.util.Calendar.DAY_OF_MONTH)
    }

    /**
     * differDay from  @see startCalendar
     */
    fun differDay(timeMs:Long)=calendar(timeMs).differ(startCalendar).toLong()

    fun dayFormatString(timeMs:Long)=calendar(timeMs).run {
        "${year}-${month}-${day}"
    }




}