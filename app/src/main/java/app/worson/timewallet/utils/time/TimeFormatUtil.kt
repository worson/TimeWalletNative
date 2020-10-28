package app.worson.timewallet.utils.time

import java.text.SimpleDateFormat
import java.util.*

/**
 * @author wangshengxing  06.05 2020
 */
object TimeFormatUtil {

    val HOUR_MINUS = "HH:mm"
    val HOUR_MINUS_SECOND = "HH:mm:ss"


    fun offsetTimeMs(millisecond:Long):String{
        val seconds = millisecond / 1000
        return if (seconds >= 60 * 60) {
            String.format(
                Locale.getDefault(),
                "%d:%d:%02d",
                seconds / 60 / 60,
                seconds / 60 % 60,
                seconds % 60
            )
        } else {
            String.format(Locale.getDefault(), "%d:%02d", seconds / 60, seconds % 60)
        }
    }

    fun timeFormat(millisecond:Long,format:String):String{
        val timeFormat= SimpleDateFormat(format)
        return timeFormat.format(millisecond)
    }

    fun hourAndMinus(millisecond:Long):String=timeFormat(millisecond,HOUR_MINUS)
}