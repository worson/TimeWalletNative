package app.worson.timewallet.module.notification

import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.widget.RemoteViews
import app.worson.timewallet.R
import app.worson.timewallet.page.service.TaskRecordService
import com.worson.lib.appbasic.android.data.bundleStrInfo
import com.worson.lib.log.L

/**
 *
 * @author worson  10.29 2020
 */

interface OnclickListener{

    fun onClick(id:Int):Unit
}

class RemoteViewsEventHelper (val context:Context, val remoteViews: RemoteViews, val intentCreater: ()-> Intent ){

    val CODE_REMOTEVIEW_EVENT=5

    val ACTION_REMOTEVIEW_EVENT="action_remoteview_event"

    val KEY_REMOTEVIEW_EVENT="remoteview_event"
    val KEY_EVENT_VIEW_ID="event_view_id"


    val REMOTEVIEW_EVENT_CLICK="remoteview_event_click"

    val map:MutableMap<Int,OnclickListener?> = HashMap()

    fun setOnclickListener(id: Int,listener: (id:Int)-> Unit){
        map.put(id,object :OnclickListener{
            override fun onClick(id: Int) {
                listener(id)
            }
        })
        remoteViews.setOnClickPendingIntent(id,createIntent(id))
    }


    fun setOnclickListener(id: Int,listener: OnclickListener?){
        map.put(id,listener)
        remoteViews.setOnClickPendingIntent(id,createIntent(id))
    }

    fun onIntentEvent(intent:Intent){
        L.i(TAG, "onIntentEvent action=${intent.action}, intent=${intent.bundleStrInfo()}")
        if (intent.action==ACTION_REMOTEVIEW_EVENT) {
            when(intent.getStringExtra(KEY_REMOTEVIEW_EVENT)){
                KEY_EVENT_VIEW_ID -> {
                    intent.getIntExtra(KEY_EVENT_VIEW_ID,-1).let {
                        id ->
                        map.get(id)?.onClick(id)
                    }
                }
            }
        }
    }

    private fun createIntent(clickId:Int): PendingIntent {
        return intentCreater().apply {
            action=ACTION_REMOTEVIEW_EVENT
            putExtra(KEY_REMOTEVIEW_EVENT,REMOTEVIEW_EVENT_CLICK)
            putExtra(KEY_EVENT_VIEW_ID,clickId)
        }.let {
            PendingIntent.getService(context,CODE_REMOTEVIEW_EVENT,it,PendingIntent.FLAG_UPDATE_CURRENT)
        }
    }

    companion object{
        val  TAG = "RemoteViewsEventHelper"
    }

}