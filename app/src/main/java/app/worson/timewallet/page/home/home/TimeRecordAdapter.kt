package app.worson.timewallet.page.home.home

import android.view.View
import androidx.core.widget.PopupMenuCompat
import app.worson.timewallet.R
import app.worson.timewallet.db.entity.TimeEventEntity
import app.worson.timewallet.db.entity.TimeRecordEntity
import app.worson.timewallet.utils.time.TimeFormatUtil
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.listener.OnItemLongClickListener
import com.chad.library.adapter.base.module.DraggableModule
import com.chad.library.adapter.base.viewholder.BaseViewHolder
import com.worson.lib.log.L
import java.util.*

data class TimeRecordItem(val id: Int = 0,
                          val uid: String,
                          val day: String,
                          val dayTimestamp: Long,
                          val distractCount: Int,
                          val endTime: Long,
                          val estimatedTime: Long,
                          val startTime: Long,
                          val thing: String?,
                          val typeId: Int,
                          val timeRecord:TimeRecordEntity
){



    fun durationDes():String{
        return "${TimeFormatUtil.hourAndMinus(startTime)} - ${TimeFormatUtil.hourAndMinus(endTime)}"
    }

    fun costDes():String{
        return TimeFormatUtil.offsetTimeMs(endTime-startTime)
    }

    companion object{

    }

}

fun TimeRecordEntity.toTimeRecordItem():TimeRecordItem{
    return TimeRecordItem(id, uid, day, dayTimestamp, distractCount, endTime, estimatedTime, startTime, thing, typeId,this)
}


class TimeRecordAdapter(data: MutableList<TimeRecordItem>) :
    BaseQuickAdapter<TimeRecordItem, BaseViewHolder>(R.layout.list_item_time_record, data)
    {

    init {

    }

    var timeEventMap:Map<Int, TimeEventEntity> = mapOf()
        set(value) {
            field=value
            notifyDataSetChanged()
            L.i(TAG, "update:timeEventMap ")
        }

    override fun convert(holder: BaseViewHolder, item: TimeRecordItem) {
//        L.d(TAG){"convert: ${item.id}"}
        val eventEntity=timeEventMap.get(item.typeId)
        val thing:String= if (item.thing.isNullOrBlank()){
            eventEntity?.desc ?: ""
        }else{
            item.thing
        }
        val duration=item.durationDes()
        val eventName=eventEntity?.name
        val costDes=item.costDes()

        val content=StringBuilder().apply {
            listOf(thing,duration,costDes,item.day).forEach {
                append(it)
                append("\n")
            }
        }.toString()

        holder.setText(R.id.title,eventName)
        holder.setText(R.id.subTitle, content)
        eventEntity?.let {
            holder.setBackgroundColor(R.id.vgRoot,it.color)
        }
    }



    companion object{
        val  TAG = "TimeRecordAdapter"
    }


}