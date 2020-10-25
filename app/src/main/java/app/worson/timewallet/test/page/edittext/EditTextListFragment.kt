package app.worson.timewallet.test.page.edittext

import android.os.Bundle
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.addCallback
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.observe
import androidx.recyclerview.widget.LinearLayoutManager
import app.worson.timewallet.R
import app.worson.timewallet.db.entity.TimeEventEntity
import app.worson.timewallet.page.eventtype.TimeEventViewModel
import app.worson.timewallet.page.eventtype.TimeEventViewState
import app.worson.timewallet.test.page.base.TestFragment
import app.worson.timewallet.view.rvhelper.DefaultItemDiff
import com.blankj.utilcode.util.FragmentUtils
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.worson.lib.log.L
import kotlinx.android.synthetic.main.fragment_event_list_select_dialog.*


class EditTextListFragment : TestFragment() {

    private val mTimeEventViewModel by activityViewModels<TimeEventViewModel>()
    private val mAdapter: EditTextListAdapter =
        EditTextListAdapter(
            mutableListOf()
        )

    private var listener: ((timeType: TimeEventEntity) -> Unit?)? = null

    fun setSelectListener(listener: ((timeType: TimeEventEntity) -> Unit?)?) {
        this.listener = listener
    }



    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.test_fragment_edit_text_list, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        mTimeEventViewModel.freshTimeEvents()
        mTimeEventViewModel.eventsLive.observe(viewLifecycleOwner) {
            mAdapter.setDiffNewData(it)
        }
        rvView.setLayoutManager(LinearLayoutManager(requireContext()))
        rvView.adapter = mAdapter
        mAdapter.setDiffCallback(DefaultItemDiff())
        mAdapter.setOnItemClickListener() { a, _, p ->
            mAdapter.getItem(p)?.let {
                this.listener?.invoke(it)
            }
            FragmentUtils.remove(this)
        }

    }




    companion object {

        val TAG = "TimeEventSelectDialogFragment"

        fun newInstance(): EditTextListFragment =
            EditTextListFragment().apply {
                arguments = Bundle().apply {
                }
            }

    }
}