package app.worson.timewallet.page.eventtype

import android.os.Bundle
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import androidx.recyclerview.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.LinearLayoutManager
import app.worson.timewallet.R
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


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_event_list_select_dialog, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        bottomSheetBehavior = BottomSheetBehavior.from(bottomLayout)

        mTimeEventViewModel.liveData.observe(this){
            observeViewState(it)
        }
        rvView.setLayoutManager(LinearLayoutManager(requireContext()))
        rvView.adapter=mAdapter

    }

    private fun observeViewState(viewState: TimeEventViewState?) {
        L.d(TAG) { "observeViewState: ${viewState}" }
        viewState?.timeEvents?.handleIfNotHandled {
            mAdapter.setList(it)
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