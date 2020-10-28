package app.worson.timewallet.page

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.worson.lib.log.L

class FlashActivity : AppCompatActivity() {

    companion object {
        private const val TAG = "FlashActivity"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        L.i(TAG, "onCreate: ")
        val i = Intent(this, MainActivity::class.java)
        startActivity(i)
        finish()

    }
}