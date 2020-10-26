package app.worson.timewallet.test.page.notta

import android.graphics.Point
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.*
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import androidx.activity.addCallback
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.databinding.ViewDataBinding
import androidx.databinding.library.baseAdapters.BR
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.observe
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.LinearLayoutManager
import app.worson.timewallet.R
import app.worson.timewallet.databinding.TestItemSmartnoteBinding
import app.worson.timewallet.db.entity.TimeEventEntity
import app.worson.timewallet.page.eventtype.TimeEventViewModel
import app.worson.timewallet.page.eventtype.TimeEventViewState
import app.worson.timewallet.test.page.base.TestFragment
import app.worson.timewallet.utils.view.disableEdit
import app.worson.timewallet.utils.view.enableEdit
import app.worson.timewallet.view.rvhelper.DataBindingListAdapter
import app.worson.timewallet.view.rvhelper.DataBindingViewHolder
import app.worson.timewallet.view.rvhelper.DefaultItemDiff
import com.blankj.utilcode.util.FragmentUtils
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.worson.lib.log.L
import kotlinx.android.synthetic.main.fragment_event_list_select_dialog.*

data class SmartNoteItem (
    val id: String,
    val showTimeStamp: Boolean = true,
    val timeStamp: String = "",
    var original: String = "",
    var isEdit: Boolean = false,
)

class BindingListAdapterListFragment : TestFragment() {



    class TestDataBindingListAdapter<T, DB : ViewDataBinding>(private val layoutId: Int,
                                                              private val variableId: Int,
                                                              diffCallback: DiffUtil.ItemCallback<T>,
                                                              private val onBind: ((DB, T, Int, List<Any>) -> Unit)? = null)
        :DataBindingListAdapter<T, DB>
        (layoutId,variableId,diffCallback,onBind){

        
        override fun onBindViewHolder(
            holder: DataBindingViewHolder<DB>,
            position: Int,
            payloads: MutableList<Any>
        ) {
            super.onBindViewHolder(holder, position, payloads)
            L.d(TAG) { "onBindViewHolder: with payloads ${payloads}" }
        }

        override fun onBindViewHolder(holder: DataBindingViewHolder<DB>, position: Int) {
            super.onBindViewHolder(holder, position)
            L.i(TAG, "onBindViewHolder: ")
        }
    }

    lateinit var mAdapter: TestDataBindingListAdapter<SmartNoteItem,TestItemSmartnoteBinding>
    var mData:MutableList<SmartNoteItem> = mutableListOf()

    class SmartNoteItemDiff:DiffUtil.ItemCallback<SmartNoteItem>() {

        val keyIsEdit = "keyIsEdit"
        val keyName = "keyName"
        val keyTimeStamp = "keyTimeStamp"

        override fun areItemsTheSame(oldItem: SmartNoteItem, newItem: SmartNoteItem): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(
            oldItem: SmartNoteItem,
            newItem: SmartNoteItem
        ): Boolean {
            return oldItem==newItem
        }

        override fun getChangePayload(oldItem: SmartNoteItem, newItem: SmartNoteItem): Any? {
            return Bundle().apply {
                if (oldItem.isEdit != newItem.isEdit) putBoolean(keyIsEdit, newItem.isEdit)
                if (oldItem.original != newItem.original) putString(keyName, newItem.original)
                if (oldItem.timeStamp != newItem.timeStamp) putString(keyTimeStamp, newItem.timeStamp)
            }
        }

    }

    val diff=SmartNoteItemDiff()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.test_fragment_edit_text_list, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {



        mAdapter = TestDataBindingListAdapter(
            R.layout.test_item_smartnote,
            BR.item,
            diff
        ){ db, noteItem, position, payload ->
            L.d(TAG) { "bind: position=$position,item=${noteItem}" }

            if (!payload.isEmpty()) {
                val bundle = payload[0] as Bundle
                L.d(TAG) { "bind: payload got position=$position,bundle=${bundle}" }
                bundle.keySet().forEach {
                    if (it == diff.keyIsEdit){
                        val isEdit=bundle.getBoolean(diff.keyIsEdit)
                        L.d(TAG, "bind:addTextChangedListener position=${position},isEdit=${isEdit}")
                        if (isEdit) { //mSmartNoteViewModel.mEditState
                            db.tvOriginal.enableEdit()
                        }else{
                            db.tvOriginal.disableEdit()
                        }
                    }

                    if (it == diff.keyName) {
                        db.tvOriginal.setText(bundle.getString(it))
                    }

                    if (it == diff.keyTimeStamp) {
                        db.tvTimestamp.setText(bundle.getString(it))
                    }
                }
            }else{
                if (noteItem.isEdit) { //mSmartNoteViewModel.mEditState
                    db.tvOriginal.enableEdit()
                }else{
                    db.tvOriginal.disableEdit()
                }
            }

            if(noteItem.isEdit){
                db.tvOriginal.setOnLongClickListener(null)
                db.tvOriginal.setOnTouchListener(null)
                db.tvOriginal.setOnTouchListener(null)
            }else{
                db.tvOriginal.setOnTouchListener { v, event ->
                    when (event.action) {
                        MotionEvent.ACTION_DOWN -> {
                        }
                        MotionEvent.ACTION_UP, MotionEvent.ACTION_CANCEL -> {
                        }
                    }
                    false
                }
                db.tvOriginal.setOnLongClickListener{
                    L.d(TAG) { "setOnLongClickListener: " }
                    true
                }

            }
        }

        rvView.setLayoutManager(LinearLayoutManager(requireContext()))
        rvView.adapter = mAdapter

        mData?.apply {
            for (i in 0 .. 10){
                add(SmartNoteItem(id=i.toString(),original = "${i}. 这是一条测试笔记"))
            }
            mAdapter.submitList(this)

        }


    }



    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        L.d(TAG) { "onCreateOptionsMenu: " }
        menu.clear()
        inflater.inflate(R.menu.test_menu_databind_adapter,menu)
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onDestroyOptionsMenu() {
        L.d(TAG) { "onDestroyOptionsMenu: " }
        super.onDestroyOptionsMenu()
    }


    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        L.d(TAG) { "onOptionsItemSelected: ${item.title}" }
        when(item.itemId){
            R.id.edit_enable -> {
                mData.forEach {
                    it.isEdit=true
                }
                mAdapter.submitList(mData)
                mAdapter.notifyDataSetChanged()
            }

            R.id.edit_disable -> {
                mData.forEach {
                    it.isEdit=false
                }
                mAdapter.submitList(mData)
                mAdapter.notifyDataSetChanged()
            }

            R.id.fresh_list -> {
                mAdapter.submitList(mData.toMutableList())
            }

            R.id.force_fresh_list -> {
                mAdapter.notifyDataSetChanged()
            }

            R.id.fresh_list_first -> {
                val first=mData.first()
                first.original="第一条数据被更新"
                mAdapter.notifyItemChanged(0)
            }

            R.id.diff_fresh_list_first -> {
                val oldItem=mData.first().copy()
                val first=mData.first()
                first.original="第一条数据被更新,数据差分更新"
                mAdapter.notifyItemChanged(0,diff.getChangePayload(oldItem,first))
            }

            R.id.fresh_one_item -> {
                mAdapter.notifyItemChanged(2)
            }

            else -> return super.onOptionsItemSelected(item)
        }
        return true

    }

    companion object {

        val TAG = "BindingListAdapterListFragment"

        fun newInstance(): BindingListAdapterListFragment =
            BindingListAdapterListFragment().apply {
                arguments = Bundle().apply {
                }
            }

    }
}