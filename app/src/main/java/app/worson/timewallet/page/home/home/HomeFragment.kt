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
import app.worson.timewallet.module.viewex.calendar
import app.worson.timewallet.module.viewex.currentCalendar
import app.worson.timewallet.module.viewex.dayFormatString
import app.worson.timewallet.module.viewex.differDays
import app.worson.timewallet.page.eventtype.TimeEventSelectDialogFragment
import app.worson.timewallet.page.eventtype.TimeEventViewModel
import app.worson.timewallet.page.eventtype.TimeEventViewState
import app.worson.timewallet.view.rvhelper.DefaultItemDiff
import com.blankj.utilcode.util.FragmentUtils
import com.haibin.calendarview.Calendar
import com.haibin.calendarview.CalendarView
import com.worson.lib.log.L
import kotlinx.android.synthetic.main.fragment_home.*
import kotlinx.android.synthetic.main.fragment_home.rvView
import kotlin.collections.ArrayList

class HomeFragment : Fragment() {
    val  TAG = "HomeFragment"

    private lateinit var homeViewModel: HomeViewModel

    private val mTimeRecordViewModel by activityViewModels<TimeRecordViewModel>()
    private val mTimeEventViewModel by activityViewModels<TimeEventViewModel>()

    private val mAdapter: TimeRecordAdapter = TimeRecordAdapter(mutableListOf())

//    var currentDifferDay=18549
    var currentDifferDay=currentCalendar().differDays()

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
        initTimeEvents()
        initCanlender()
    }

    private fun initCanlender() {
        L.i(TAG, "initCanlender: date ${calendarView.dayFormatString()}")
        calendarView.setOnCalendarSelectListener(object : CalendarView.OnCalendarSelectListener{
            override fun onCalendarSelect(calendar: Calendar, isClick: Boolean) {
                currentDifferDay=calendar.differDays()
                L.i(TAG, "onCalendarSelect: differDays ${currentDifferDay},date ${calendar}")
                mTimeRecordViewModel.startObserveDayRecord(currentDifferDay)
            }

            override fun onCalendarOutOfRange(calendar: Calendar?) {
            }
        })
    }

    private fun initTimeEvents() {
        mTimeEventViewModel.freshTimeEvents()
        mTimeEventViewModel.liveData.observe(viewLifecycleOwner){
            observeTimeEventViewState(it)
        }
    }

    private fun observeTimeEventViewState(viewState: TimeEventViewState) {

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
        mTimeRecordViewModel.liveData.observe(viewLifecycleOwner){
            observeTimeRecordsViewState(it)
        }
        mTimeRecordViewModel.recordsLive.observe(viewLifecycleOwner){
            observeTimeRecords(it ?: mutableListOf<TimeRecordEntity>())
        }
        rvView.setLayoutManager(LinearLayoutManager(requireContext()))
        rvView.adapter=mAdapter
        mAdapter.setDiffCallback(DefaultItemDiff())

        mTimeRecordViewModel.startObserveDayRecord(currentDifferDay)

    }

    private fun observeTimeRecords(list: MutableList<TimeRecordEntity>) {
        mAdapter.setDiffNewData(ArrayList(list.map { it.toTimeRecordItem() }))
    }


    private fun observeTimeRecordsViewState(viewState: TimeRecordViewState?) {
        L.d(TAG) { "observeViewState:" }


    }

}