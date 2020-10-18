package app.sen.musics.utils

import android.app.Application
import app.worson.timewallet.page.utils.ApplicationUtil


object GlobalContext {

    val instance: Application by lazy {
        ApplicationUtil.getApplicationByReflection()
    }

    @JvmStatic
    fun get():Application=instance


}