package app.worson.timewallet.module.viewex

import app.worson.timewallet.testlib.BaseTest
import com.haibin.calendarview.Calendar
import com.worson.lib.log.L
import org.junit.Assert
import org.junit.Test

import org.junit.Assert.*

/**
 * 说明:
 *
 * @author worson  10.27 2020
 */
class CalendarViewExKtTest:BaseTest() {

    val  TAG = "CalendarViewExKtTest"

    @Test
    fun differDays() {
        val calendar=Calendar().apply {
            year=2020
            month=10
            day=13
        }
        val deffer=calendar.differDays()
        L.d(TAG) { "differDays: $deffer" }
        Assert.assertEquals(18548,deffer)
    }
}