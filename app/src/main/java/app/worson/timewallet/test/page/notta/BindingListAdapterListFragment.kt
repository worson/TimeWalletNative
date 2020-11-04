package app.worson.timewallet.test.page.notta

import android.os.Bundle
import android.text.Editable
import android.view.*
import android.widget.EditText
import androidx.databinding.ViewDataBinding
import androidx.databinding.library.baseAdapters.BR
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.LinearLayoutManager
import app.worson.timewallet.R
import app.worson.timewallet.databinding.TestItemSmartnoteBinding
import app.worson.timewallet.test.page.base.TestFragment
import app.worson.timewallet.view.custom.DefaultInputHandler
import com.worson.lib.appbasic.view.extend.disableEdit
import com.worson.lib.appbasic.view.extend.enableEdit
import com.worson.lib.appbasic.view.rvhelper.DataBindingListAdapter
import com.worson.lib.appbasic.view.rvhelper.DataBindingViewHolder
import com.worson.lib.log.L
import kotlinx.android.synthetic.main.fragment_event_list_select_dialog.*
import java.lang.Math.random
import kotlin.math.min

data class SmartNoteItem (
    val id: String,
    var original: String = "",
    var isEdit: Boolean = false,
)

class BindingListAdapterListFragment : TestFragment() {



    class TestDataBindingListAdapter<T, DB : ViewDataBinding>(private val layoutId: Int,
                                                              private val variableId: Int,
                                                              diffCallback: DiffUtil.ItemCallback<T>,
                                                              private val onBind: ((DB, T, Int, List<Any>) -> Unit)? = null)
        : DataBindingListAdapter<T, DB>
        (layoutId,variableId,diffCallback,onBind){

        
        override fun onBindViewHolder(
            holder: DataBindingViewHolder<DB>,
            position: Int,
            payloads: MutableList<Any>
        ) {
            super.onBindViewHolder(holder, position, payloads)
//            L.d(TAG) { "onBindViewHolder: with payloads ${payloads}" }
        }

        override fun onBindViewHolder(holder: DataBindingViewHolder<DB>, position: Int) {
            super.onBindViewHolder(holder, position)
//            L.i(TAG, "onBindViewHolder: ")
        }
    }

    lateinit var mAdapter: TestDataBindingListAdapter<SmartNoteItem,TestItemSmartnoteBinding>
    var mData:MutableList<SmartNoteItem> = mutableListOf()

    class SmartNoteItemDiff:DiffUtil.ItemCallback<SmartNoteItem>() {

        val keyIsEdit = "keyIsEdit"
        val keyName = "keyName"

        override fun areItemsTheSame(oldItem: SmartNoteItem, newItem: SmartNoteItem): Boolean {
            return (oldItem.id == newItem.id)
        }

        override fun areContentsTheSame(
            oldItem: SmartNoteItem,
            newItem: SmartNoteItem
        ): Boolean {
            return (oldItem==newItem)
        }

        override fun getChangePayload(oldItem: SmartNoteItem, newItem: SmartNoteItem): Any? {
            return Bundle().apply {
                if (oldItem.isEdit != newItem.isEdit) putBoolean(keyIsEdit, newItem.isEdit)
                if (oldItem.original != newItem.original) putString(keyName, newItem.original)
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
        ){ db, noteItem, position, payloads ->
            L.d(TAG) { "bind: position=$position,item=${noteItem}" }

            fun removeHandler(){
                db.tvOriginal.tag?.let {
                    it as InputHandler
                    db.tvOriginal.removeTextChangedListener(it)
                }
                db.tvOriginal.tag=null
            }

            fun resetHandler(){
                removeHandler()
                val inputHandler =
                    InputHandler(db.tvOriginal, position, noteItem.id,noteItem)

                db.tvOriginal.addTextChangedListener(inputHandler)
                db.tvOriginal.inputConnectionHandler = inputHandler
                db.tvOriginal.tag = inputHandler
            }

            if (!payloads.isEmpty()) {
                val bundle = if (payloads.isNotEmpty()) {
                    payloads.find { it is Bundle } as? Bundle
                } else null

                if (bundle!=null){
                    L.d(TAG) { "bind: payloads got position=$position,bundle=${bundle}" }
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

                    }
                }

                val focusPosition = if (payloads.isNotEmpty()) {
                    payloads.find { it is FocusPosition } as? FocusPosition
                } else null
                if (focusPosition!=null){
                    if (focusPosition.position == position) {
                        resetHandler()
                        db.tvOriginal.requestFocus()
                        db.tvOriginal.setSelection(
                            db.tvOriginal.text?.length ?:0
                        )
                        L.i(TAG, "handle focus position:$focusPosition,${db.tvOriginal.text}")
                    }
                }

                val updatePosition = if (payloads.isNotEmpty()) {
                    payloads.find { it is UpdatePosition } as? UpdatePosition
                } else null
                if (updatePosition!=null){
                    if (updatePosition.line == position) {
                        removeHandler()
                        L.i(TAG, "handle updatePosition:$updatePosition")
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

                resetHandler()

            }else{
                removeHandler()

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

        mData?.clear()
        mData?.apply {
            for (i in 0 .. 30){
                add(SmartNoteItem(id=i.toString(),original = "${i}. 这是一条测试笔记",isEdit = true))
            }
            mAdapter.submitList(this)

        }


    }

    data class FocusPosition(
        val position: Int,
    )

    data class UpdatePosition(
        val line: Int,
    )

    inner class InputHandler(
        private val editText: EditText,
        private val position: Int,
        val id: String,
        val item:SmartNoteItem
    ) : DefaultInputHandler() {



        fun findPositon():Int{
            return mData.indexOf(item)
        }

        override fun sendKeyEvent(event: KeyEvent): Boolean {
            if (event.keyCode == KeyEvent.KEYCODE_ENTER) {
                if (event.action == KeyEvent.ACTION_UP) {
                    L.i(TAG, "sendKeyEvent: KEYCODE_ENTER ,position=${position}")
                }
            }

            if (event.keyCode == KeyEvent.KEYCODE_DEL && event.action == KeyEvent.ACTION_UP) {
                L.i(TAG, "sendKeyEvent: KEYCODE_DEL ,position=${position},selectionStart=${editText.selectionStart}")
                if (editText.selectionStart == 0 && position != 0) {
                    L.i(TAG, "sendKeyEvent: KEYCODE_DEL ,position=${position} , selectionStart==0")


//                    mAdapter.notifyItemRemoved(position)
//                    mAdapter.notifyItemChanged(position)
                    /*mAdapter.submitList(mData.map { it.copy() }){
                        rvView.smoothScrollToPosition(position)
                    }*/

                    L.i(TAG, "sendKeyEvent: removeAt ${mAdapter.currentList.removeAt(position)}")

                    mAdapter.notifyDataSetChanged()

                    rvView.postDelayed({
                        mAdapter.notifyItemChanged(position-1,FocusPosition(position = position-1))
                        rvView.smoothScrollToPosition(min(rvView.childCount,position))
                    },10)

                    /*val dataIndex=mData.indexOf(item)
                    if (dataIndex>0){
                        L.i(TAG, "sendKeyEvent: dataIndex ${dataIndex}")
                        mData.removeAt(dataIndex)
                        mAdapter.notifyItemRemoved(dataIndex)
                        mAdapter.submitList(mData.map { it.copy() }){
                            val preIndex=dataIndex-1
                            if (preIndex>=0){
                                rvView.postDelayed({
                                    mAdapter.notifyItemChanged(preIndex,FocusPosition(line = preIndex))
                                    rvView.smoothScrollToPosition(preIndex)
                                },1)


                            }
                        }
                    }*/




                    return true
                }
            }

            return false
        }

        override fun setSelection(start: Int, end: Int): Boolean {
            return false
        }

        var beforeText = ""
        var before = 0
        var start = 0
        var count = 0

        override fun afterTextChanged(s: Editable?) {
            if (s == null) {
                return
            }
            val afterText = s.toString()
            val deleteIndex = start + before
            val insertIndex = start + count
            if (before != 0) {
                L.d(TAG) { "delete position:$position, before: $beforeText, after: $afterText, startIndex:$start, stopIndex:$deleteIndex" }
//                onUserEditCallback?.onTextChanged(position, null, start, deleteIndex)
            }
            if (count != 0) {
                val subText = afterText.substring(start, insertIndex)
                L.d(TAG ) { "insert position:$position, before: $beforeText, after: $afterText, subText:$subText, startIndex:$start, stopIndex:$insertIndex" }
//                onUserEditCallback?.onTextChanged(position, subText, start, insertIndex)
            }
        }

        override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            if (s == null) {
                return
            }
            beforeText = s.toString()
        }

        override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
            if (s == null) {
                return
            }
            this.start = start
            this.before = before
            this.count = count
            L.d(TAG) {
                "!@# onTextChanged s:$s, start:$start, before:$before, count:$count"
            }
            mData.getOrNull(findPositon())?.original=s.toString()
        }

    }



    override fun onDestroyOptionsMenu() {
        L.d(TAG) { "onDestroyOptionsMenu: " }
        super.onDestroyOptionsMenu()
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        L.d(TAG) { "onCreateOptionsMenu: " }
        menu.clear()
        inflater.inflate(R.menu.test_menu_databind_adapter,menu)
        super.onCreateOptionsMenu(menu, inflater)
    }



    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        L.d(TAG) { "onOptionsItemSelected: ${item.title}" }
        when(item.itemId){
            R.id.edit_enable -> {
                mAdapter.submitList(mData.map { it.copy(isEdit = true) })
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
                L.d(TAG) { "onOptionsItemSelected: fresh_list_first" }
                val newList=mData.toMutableList()
                val first=newList.first().copy()
                first.original="第一条数据被更新:${random()}"
                newList.removeAt(0)
                newList.add(0,first)
                mAdapter.submitList(newList)
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

        private  val TAG = "BindingListAdapterListFragment"

        fun newInstance(): BindingListAdapterListFragment =
            BindingListAdapterListFragment().apply {
                arguments = Bundle().apply {
                }
            }

    }
}