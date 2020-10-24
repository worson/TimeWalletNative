package app.worson.timewallet.page.home.home

import app.worson.timewallet.R
import app.worson.timewallet.db.entity.TimeRecordEntity
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.module.DraggableModule
import com.chad.library.adapter.base.viewholder.BaseViewHolder

data class TimeRecordItem(val id: Int = 0,
                          val uid: String,
                          val day: String,
                          val dayTimestamp: Long,
                          val distractCount: Int,
                          val endTime: Long,
                          val estimatedTime: Long,
                          val startTime: Long,
                          val thing: String?,
                          val typeId: Int){

    companion object{

    }

}

fun TimeRecordEntity.toTimeRecordItem():TimeRecordItem{
    return TimeRecordItem(id, uid, day, dayTimestamp, distractCount, endTime, estimatedTime, startTime, thing, typeId)
}


class TimeRecordAdapter(data: MutableList<TimeRecordItem>) :
    BaseQuickAdapter<TimeRecordItem, BaseViewHolder>(R.layout.list_item_time_record, data),
    DraggableModule {

    override fun convert(holder: BaseViewHolder, item: TimeRecordItem) {
        holder.setText(R.id.title,item.day)
        holder.setText(R.id.subTitle,item.thing ?: "")
//        holder.setBackgroundColor(R.id.vgRoot,item.color)
    }


}