package app.worson.timewallet.testlib

import com.langogo.lib.log.internal.Platform
import com.langogo.lib.log.printer.AndroidPrinter
import com.langogo.lib.log.printer.ConsolePrinter
import com.worson.lib.log.L
import com.worson.lib.log.LogConfiguration
import com.worson.lib.log.LogLevel

/**
 * @author worson  10.27 2020
 */
open class BaseTest {

    init {
        initConsolePrint(true)
    }

    private fun initConsolePrint(debug:Boolean){
        L.init(
            LogConfiguration.Builder()
                .logLevel(if (debug) LogLevel.ALL else LogLevel.DEBUG)
                .threadInfo(debug)
                .traceInfo(debug, 6)
                .addPrinter(ConsolePrinter())
                .build()
        )
    }

}