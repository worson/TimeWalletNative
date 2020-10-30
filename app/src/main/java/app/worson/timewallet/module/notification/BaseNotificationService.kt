package app.worson.timewallet.module.notification

import android.content.Intent
import android.widget.RemoteViews
import androidx.lifecycle.LifecycleService

/**
 *
 * @author worson  10.29 2020
 */
open class BaseNotificationService :LifecycleService(){

    protected var mRemoteViewsEventHelper:RemoteViewsEventHelper?=null



    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        intent?.let {
            mRemoteViewsEventHelper?.onIntentEvent(it)
        }
        return super.onStartCommand(intent, flags, startId)
    }

    fun setOnclickListener(id: Int,listener: (id:Int)-> Unit){
        mRemoteViewsEventHelper?.setOnclickListener(id, listener)
    }
    fun setOnclickListener(id: Int,listener: OnclickListener?){
        mRemoteViewsEventHelper?.setOnclickListener(id, listener)
    }


}