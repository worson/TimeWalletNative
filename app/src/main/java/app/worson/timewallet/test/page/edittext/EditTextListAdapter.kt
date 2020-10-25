package app.worson.timewallet.test.page.edittext

import app.worson.timewallet.R
import app.worson.timewallet.db.entity.TimeEventEntity
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.module.DraggableModule
import com.chad.library.adapter.base.viewholder.BaseViewHolder

class EditTextListAdapter(data: MutableList<TimeEventEntity>) :
    BaseQuickAdapter<TimeEventEntity, BaseViewHolder>(R.layout.test_item_edit_text, data),
    DraggableModule {

    override fun convert(holder: BaseViewHolder, item: TimeEventEntity) {
        holder.setText(R.id.title,item.name)
        holder.setText(R.id.subTitle,item.desc)
        holder.setBackgroundColor(R.id.vgRoot,item.color)
    }


}