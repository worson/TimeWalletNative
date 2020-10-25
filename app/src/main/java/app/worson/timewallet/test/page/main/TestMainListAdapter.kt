package app.worson.timewallet.test.page.main

import androidx.fragment.app.Fragment
import app.worson.timewallet.R
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.viewholder.BaseViewHolder

data class FragmentItem(val fragment: Fragment,val title:String=fragment.javaClass.simpleName){

}

class TestMainListAdapter(data: MutableList<FragmentItem>) :

    BaseQuickAdapter<FragmentItem, BaseViewHolder>(R.layout.test_item_main, data){

    override fun convert(holder: BaseViewHolder, item: FragmentItem) {
        holder.setText(R.id.title,item.title)
//        holder.setText(R.id.subTitle,item.desc)
    }
}