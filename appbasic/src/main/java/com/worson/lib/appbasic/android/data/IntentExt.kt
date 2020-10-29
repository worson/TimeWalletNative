package com.worson.lib.appbasic.android.data

import android.content.Intent
import org.json.JSONObject

/**
 * @author worson  10.28 2020
 */

fun Intent.bundleStrInfo(): String {
    return JSONObject().let { jo ->
        extras?.keySet()?.forEach {
            jo.put(it, extras?.get(it))
        }
        jo
    }.toString()
}