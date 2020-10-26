package app.worson.timewallet.module.storage

import com.worson.lib.appbasic.application.GlobalContext
import app.worson.timewallet.utils.Pref


/**
 */
object AccountSettings {

    var account: String by Pref(
        GlobalContext.instance,
        key = "account",
        default = ""
    )

    var uid: String by Pref(
        GlobalContext.instance,
        key = "account_uid",
        default = ""
    )

    var token: String by Pref(
        GlobalContext.instance,
        key = "token",
        default = ""
    )

    var license: String by Pref(
        GlobalContext.instance,
        key = "license",
        default = ""
    )

}