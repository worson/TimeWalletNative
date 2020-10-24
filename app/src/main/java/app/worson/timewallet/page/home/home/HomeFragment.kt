package app.worson.timewallet.page.home.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.lifecycle.observe
import androidx.recyclerview.widget.LinearLayoutManager
import app.worson.timewallet.R
import app.worson.timewallet.db.entity.TimeRecordEntity
import app.worson.timewallet.page.eventtype.TimeEventAdapter
import app.worson.timewallet.page.eventtype.TimeEventSelectDialogFragment
import app.worson.timewallet.page.eventtype.TimeEventViewState
import app.worson.timewallet.page.timetask.TimeTaskViewModel
import app.worson.timewallet.view.rvhelper.DefaultItemDiff
import com.blankj.utilcode.util.FragmentUtils
import com.worson.lib.log.L
import kotlinx.android.synthetic.main.fragment_event_list_select_dialog.*
import kotlinx.android.synthetic.main.fragment_home.*
import kotlinx.android.synthetic.main.fragment_home.rvView

class HomeFragment : Fragment() {
    val  TAG = "HomeFragment"

    private lateinit var homeViewModel: HomeViewModel

    private val mTimeRecordViewModel by activityViewModels<TimeRecordViewModel>()
    private val mAdapter: TimeRecordAdapter = TimeRecordAdapter(mutableListOf())

    var currentDay="2020-10-14"

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        homeViewModel =
                ViewModelProviders.of(this).get(HomeViewModel::class.java)
        val root = inflater.inflate(R.layout.fragment_home, container, false)
        homeViewModel.text.observe(viewLifecycleOwner, Observer {

        })
        return root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        btTask.setOnClickListener{
//            showTimeEventSelect()
            L.i(TAG, "notifyDataSetChanged: ")
            mAdapter.notifyDataSetChanged()

        }

        initTimeRecordList()
    }

    private fun showTimeEventSelect() {
        val fragment= TimeEventSelectDialogFragment.newInstance()
        FragmentUtils.add(requireActivity().supportFragmentManager,fragment,R.id.fragment_containner)
        FragmentUtils.show(fragment)
        fragment.setSelectListener {
            L.i(TAG, "setSelectListener: ${it}")
        }
    }

    private fun initTimeRecordList() {
        mTimeRecordViewModel.freshTimeRecords(currentDay)
        mTimeRecordViewModel.liveData.observe(viewLifecycleOwner){
            observeViewState(it)
        }
        mTimeRecordViewModel.recordsLive.observe(viewLifecycleOwner){
            observeTimeRecords(it ?: mutableListOf<TimeRecordEntity>())
        }
        rvView.setLayoutManager(LinearLayoutManager(requireContext()))
        rvView.adapter=mAdapter
        mAdapter.setDiffCallback(DefaultItemDiff())

        mTimeRecordViewModel.startObserveDayRecord(currentDay)

    }

    private fun observeTimeRecords(list: MutableList<TimeRecordEntity>) {
        mAdapter.setDiffNewData(list)
    }


    private fun observeViewState(viewState: TimeRecordViewState?) {
        L.d(TAG) { "observeViewState:" }
        viewState?.timeEvents?.handleIfNotHandled {
            mAdapter.setDiffNewData(it)
        }

    }

}