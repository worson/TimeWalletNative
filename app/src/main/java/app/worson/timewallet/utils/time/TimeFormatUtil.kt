package app.worson.timewallet.utils.time

import androidx.core.graphics.drawable.toDrawable
import app.worson.timewallet.R
import com.worson.lib.appbasic.view.string
import java.text.SimpleDateFormat
import java.util.*

/**
 * @author wangshengxing  06.05 2020
 */
object TimeFormatUtil {

    val TIME_FORMAT_HOUR by lazy { R.string.time_format_hour.string() }
    val TIME_FORMAT_MINUTE by lazy { R.string.time_format_minute.string() }
    val TIME_FORMAT_SECOND by lazy { R.string.time_format_second.string() }

    val HOUR_MINUS = "HH${TIME_FORMAT_HOUR}mm${TIME_FORMAT_MINUTE}"
    val HOUR_MINUS_SECOND = "HH${TIME_FORMAT_HOUR}mm${TIME_FORMAT_MINUTE}ss${TIME_FORMAT_SECOND}"


    fun offsetTimeMs(millisecond:Long):String{
        val seconds = millisecond / 1000
        return if (seconds >= 60 * 60) {
            String.format(
                Locale.getDefault(),
                "%d${TIME_FORMAT_HOUR}%d${TIME_FORMAT_MINUTE}%02d${TIME_FORMAT_SECOND}",
                seconds / 60 / 60,
                seconds / 60 % 60,
                seconds % 60
            )
        } else {
            String.format(Locale.getDefault(), "%d${TIME_FORMAT_HOUR}%02d${TIME_FORMAT_SECOND}", seconds / 60, seconds % 60)
        }
    }

    fun timeFormat(millisecond:Long,format:String):String{
        val timeFormat= SimpleDateFormat(format)
        return timeFormat.format(millisecond)
    }

    fun hourAndMinus(millisecond:Long):String=timeFormat(millisecond,HOUR_MINUS)
}