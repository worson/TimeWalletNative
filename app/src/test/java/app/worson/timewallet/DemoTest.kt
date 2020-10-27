package app.worson.timewallet

import app.worson.timewallet.testlib.BaseTest
import com.worson.lib.log.L
import org.junit.Test

import org.junit.Assert.*

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
class DemoTest :BaseTest(){

    @Test
    fun addition_isCorrect() {
        L.d(TAG) { "addition_isCorrect: " }
        assertEquals(4, 2 + 2)
    }

    companion object{
       private val  TAG = "ExampleUnitTest"
    }
}