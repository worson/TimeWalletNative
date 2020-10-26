package app.worson.timewallet.test.page.edittext

import android.widget.EditText
import app.worson.timewallet.R
import app.worson.timewallet.db.entity.TimeEventEntity
import com.worson.lib.appbasic.view.extend.disableEdit
import com.worson.lib.appbasic.view.extend.enableEdit
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.module.DraggableModule
import com.chad.library.adapter.base.viewholder.BaseViewHolder
import com.worson.lib.log.L

class EditTextListAdapter(data: MutableList<TimeEventEntity>) :
    BaseQuickAdapter<TimeEventEntity, BaseViewHolder>(R.layout.test_item_edit_text, data),
    DraggableModule {

    private var setEditable=false

    fun setEditable(yes:Boolean){
        setEditable=yes
        notifyDataSetChanged()

    }
    override fun convert(holder: BaseViewHolder, item: TimeEventEntity) {
        L.i(TAG, "convert: ${holder.adapterPosition}")
        holder.setText(R.id.title,item.name)
        holder.setText(R.id.subTitle,item.desc)
//        holder.setBackgroundColor(R.id.vgRoot,item.color)

        val editText=holder.getView<EditText>(R.id.subTitle)

        val isEdit=setEditable

        editText.setBackgroundColor(item.color)
        

        if (isEdit){
            editText.enableEdit()
        }else{
            editText.disableEdit()
            editText.setOnLongClickListener {
                L.i(TAG, "convert: setOnLongClickListener")
                true
            }
        }


    }

    companion object{
        val  TAG = "EditTextListAdapter"

    }


}