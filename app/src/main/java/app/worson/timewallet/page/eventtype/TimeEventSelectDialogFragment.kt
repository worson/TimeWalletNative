package app.worson.timewallet.page.eventtype

import android.os.Bundle
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.LinearLayoutManager
import app.worson.timewallet.R
import app.worson.timewallet.db.entity.TimeEventEntity
import app.worson.timewallet.view.rvhelper.DefaultItemDiff
import com.blankj.utilcode.util.FragmentUtils
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.worson.lib.log.L
import kotlinx.android.synthetic.main.fragment_event_list_select_dialog.*

// TODO: Customize parameter argument names
const val ARG_ITEM_COUNT = "item_count"

/**
 *
 *
 */
class TimeEventSelectDialogFragment : BottomSheetDialogFragment() {

    private lateinit var bottomSheetBehavior: BottomSheetBehavior<ConstraintLayout>
    private val mTimeEventViewModel by activityViewModels<TimeEventViewModel>()
    private val mAdapter:TimeEventAdapter=TimeEventAdapter(mutableListOf())

    private var listener:((timeType:TimeEventEntity) -> Unit?)?=null

    fun setSelectListener(listener:((timeType:TimeEventEntity) -> Unit?)?){
        this.listener=listener
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_event_list_select_dialog, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        bottomSheetBehavior = BottomSheetBehavior.from(bottomLayout)
        mTimeEventViewModel.freshTimeEvents()
        mTimeEventViewModel.liveData.observe(this){
            observeViewState(it)
        }
        rvView.setLayoutManager(LinearLayoutManager(requireContext()))
        rvView.adapter=mAdapter
        mAdapter.setDiffCallback(DefaultItemDiff())
        mAdapter.setOnItemClickListener(){
            a,_,p ->
            mAdapter.getItem(p)?.let {
                this.listener?.invoke(it)
            }
            FragmentUtils.hide(this)
        }

    }

    private fun observeViewState(viewState: TimeEventViewState?) {
        L.d(TAG) { "observeViewState: ${viewState}" }
        viewState?.timeEvents?.handleIfNotHandled {
            mAdapter.setDiffNewData(it)
//            mAdapter.setList(it)
        }

    }


    companion object {

        val TAG="TimeEventSelectDialogFragment"

        fun newInstance(): TimeEventSelectDialogFragment =
            TimeEventSelectDialogFragment().apply {
                val itemCount: Int=1
                arguments = Bundle().apply {
                    putInt(ARG_ITEM_COUNT, itemCount)
                }
            }

    }
}