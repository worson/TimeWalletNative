package com.worson.lib.appbasic.application

import android.app.Application


object GlobalContext {

    val instance: Application by lazy {
        ApplicationUtil.getApplicationByReflection()
    }

    @JvmStatic
    fun get():Application=
        instance


}