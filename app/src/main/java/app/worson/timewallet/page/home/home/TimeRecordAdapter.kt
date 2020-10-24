package app.worson.timewallet.page.home.home

import app.worson.timewallet.R
import app.worson.timewallet.db.entity.TimeRecordEntity
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.module.DraggableModule
import com.chad.library.adapter.base.viewholder.BaseViewHolder

class TimeRecordAdapter(data: MutableList<TimeRecordEntity>) :
    BaseQuickAdapter<TimeRecordEntity, BaseViewHolder>(R.layout.event_type_list_select_item, data),
    DraggableModule {

    override fun convert(holder: BaseViewHolder, item: TimeRecordEntity) {
        holder.setText(R.id.title,item.day)
        holder.setText(R.id.subTitle,item.thing ?: "")
//        holder.setBackgroundColor(R.id.vgRoot,item.color)
    }


}