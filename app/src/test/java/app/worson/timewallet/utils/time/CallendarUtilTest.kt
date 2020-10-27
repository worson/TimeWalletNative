package app.worson.timewallet.utils.time

import app.worson.timewallet.testlib.BaseTest
import com.worson.lib.log.L
import org.junit.Assert
import org.junit.Test

/**
 *
 * @author worson  10.27 2020
 */
class CallendarUtilTest:BaseTest(){

    val  TAG = "CallendarUtilTest"

    val currentTimeMillis=1603795111644


    @Test
    fun differDay() {
        val differ=CallendarUtil.differDay(currentTimeMillis)
        L.i(TAG, "differDay: ${currentTimeMillis},differ=${differ}")
        Assert.assertEquals(18532,differ)
    }

    @Test
    fun dayFormatString() {
        val dayFormatString=CallendarUtil.dayFormatString(currentTimeMillis)
        L.i(TAG, "differDay: ${currentTimeMillis},differ=${dayFormatString}")
        Assert.assertEquals("2020-10-27",dayFormatString)
    }
}