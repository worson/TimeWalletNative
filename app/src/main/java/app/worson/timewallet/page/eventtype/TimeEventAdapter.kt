package app.worson.timewallet.page.eventtype

import app.worson.timewallet.R
import app.worson.timewallet.db.entity.TimeEventEntity
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.module.DraggableModule
import com.chad.library.adapter.base.viewholder.BaseViewHolder

class TimeEventAdapter(data: MutableList<TimeEventEntity>) :
    BaseQuickAdapter<TimeEventEntity, BaseViewHolder>(R.layout.event_type_list_select_item, data),
    DraggableModule {

    override fun convert(holder: BaseViewHolder, item: TimeEventEntity) {
        holder.setText(R.id.title,item.name)
        holder.setText(R.id.subTitle,item.desc)
        holder.setBackgroundColor(R.id.vgRoot,item.color)
    }


}