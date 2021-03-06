package app.worson.timewallet.page.home.home

import android.content.DialogInterface
import android.os.Bundle
import android.view.*
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.widget.PopupMenu
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.*
import androidx.recyclerview.widget.LinearLayoutManager
import app.worson.timewallet.R
import app.worson.timewallet.db.entity.TimeEventEntity
import app.worson.timewallet.db.entity.TimeRecordEntity
import app.worson.timewallet.module.viewex.currentCalendar
import app.worson.timewallet.module.viewex.dayFormatString
import app.worson.timewallet.module.viewex.differDays
import app.worson.timewallet.page.MainActivity
import app.worson.timewallet.page.eventtype.TimeEventSelectDialogFragment
import app.worson.timewallet.page.eventtype.TimeEventViewModel
import app.worson.timewallet.page.eventtype.TimeEventViewState
import app.worson.timewallet.page.home.MainViewModel
import app.worson.timewallet.test.page.TestMainFragment
import com.worson.lib.appbasic.view.rvhelper.DefaultItemDiff
import com.blankj.utilcode.util.FragmentUtils
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.listener.OnItemClickListener
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.haibin.calendarview.Calendar
import com.haibin.calendarview.CalendarView
import com.worson.lib.log.L
import kotlinx.android.synthetic.main.fragment_home.*


class HomeFragment : Fragment() {
    val  TAG = "HomeFragment"

    private lateinit var homeViewModel: HomeViewModel

    private val mTimeRecordViewModel by activityViewModels<TimeRecordViewModel>()
    private val mTimeEventViewModel by activityViewModels<TimeEventViewModel>()
    private val mMainViewModel by activityViewModels<MainViewModel>()

    private var mTimeEventMap:MutableMap<Int,TimeEventEntity> = mutableMapOf()
    private val mAdapter: TimeRecordAdapter = TimeRecordAdapter(mutableListOf())

//    var currentDifferDay=18549
    var currentDifferDay=currentCalendar().differDays()

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        setHasOptionsMenu(true)
        lifecycle.addObserver(object : LifecycleEventObserver {
            override fun onStateChanged(source: LifecycleOwner, event: Lifecycle.Event) {
                L.d(TAG) { "onStateChanged: event ${event} " }
            }
        })
        homeViewModel =
                ViewModelProviders.of(this).get(HomeViewModel::class.java)
        val root = inflater.inflate(R.layout.fragment_home, container, false)
        homeViewModel.text.observe(viewLifecycleOwner, Observer {

        })
        return root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        L.i(TAG, "onViewCreated: ")
        btTask.setOnClickListener{
//            showTimeEventSelect()
            L.i(TAG, "notifyDataSetChanged: ")
//            mAdapter.notifyDataSetChanged()
//            mAdapter.timeEventMap=mTimeEventMap
            mMainViewModel.showTimeTask(true)
        }
        initTimeRecordList()
        initTimeEvents()
        initCanlender()
        initTest()
        mMainViewModel.showTimeTask(true)
    }

    private fun initTest() {

        mAdapter.setOnItemLongClickListener{
                a,view,p ->
            L.i(TimeRecordAdapter.TAG, "convert: setOnClickListener")
            // 这里的view代表popupMenu需要依附的view
            val popupMenu = PopupMenu(requireActivity(), view, Gravity.CENTER_HORIZONTAL)
            // 获取布局文件
            popupMenu.getMenuInflater().inflate(R.menu.menu_time_record_list_item, popupMenu.getMenu())
            popupMenu.show()
            // 通过上面这几行代码，就可以把控件显示出来了
            popupMenu.setOnMenuItemClickListener{
                item ->
                when(item.itemId){
                    R.id.it_delete -> mTimeRecordViewModel.delete((a.getItem(p) as TimeRecordItem).timeRecord)
                }
                true
            }
            /*popupMenu.setOnDismissListener(object : PopupMenu.OnDismissListener {
                fun onDismiss(menu: PopupMenu?) {
                    // 控件消失时的事件
                }
            })*/
            true
        }

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
        mTimeEventViewModel.eventsLive.observe(viewLifecycleOwner){
            it.forEach {
                mTimeEventMap.put(it.id,it)
                mAdapter.notifyDataSetChanged()
            }
        }
    }

    private fun observeTimeEventViewState(viewState: TimeEventViewState) {
        viewState.timeEvents?.handleIfNotHandled {
            L.i(TAG, "observeTimeEventViewState: timeEvents changed")
            it.forEach {
                mTimeEventMap.put(it.id,it)
                mAdapter.notifyDataSetChanged()
            }
        }
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
        rvView.setLayoutManager(LinearLayoutManager(requireContext()))
        rvView.adapter=mAdapter
        mAdapter.setDiffCallback(DefaultItemDiff())
        mAdapter.timeEventMap=mTimeEventMap

        //view
        mAdapter.setOnItemClickListener(object : OnItemClickListener {
            override fun onItemClick(adapter: BaseQuickAdapter<*, *>, view: View, position: Int) {
                L.i(TAG, "onItemClick: ")
                editTimeRecordThing(adapter.getItem(position) as TimeRecordItem)
            }
        })

        //data
        mTimeRecordViewModel.liveData.observe(viewLifecycleOwner){
            observeTimeRecordsViewState(it)
        }
        mTimeRecordViewModel.recordsLive.observe(viewLifecycleOwner){
            observeTimeRecords(it ?: mutableListOf<TimeRecordEntity>())
        }
        mTimeRecordViewModel.startObserveDayRecord(currentDifferDay)

    }

    private fun observeTimeRecords(list: MutableList<TimeRecordEntity>) {
        mAdapter.setDiffNewData(ArrayList(list.map { it.toTimeRecordItem() }))
    }


    private fun observeTimeRecordsViewState(viewState: TimeRecordViewState?) {
        L.d(TAG) { "observeViewState:" }


    }

    fun editTimeRecordThing(timeRecordItem: TimeRecordItem){
        val alertDialogBuilder=MaterialAlertDialogBuilder(requireContext())
            .setTitle("修改内容")
            .setView(R.layout.dialog_edit_text)
            .setPositiveButton(
                "修改",
                DialogInterface.OnClickListener { dialog, which ->
                    val input =
                        (dialog as AlertDialog).findViewById<TextView>(
                            android.R.id.text1
                        )
                    input?.let {
                        val text=it.text.toString()
                        val item=timeRecordItem.timeRecord
                        L.i(TAG, "editTimeRecordThing: modify ${item.id} , ${text}")
                        mTimeRecordViewModel.modify(item.copy(thing = text ))
                    }
                })
            .setNegativeButton("取消", null)
        alertDialogBuilder.show()
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        L.d(TAG) { "onCreateOptionsMenu: " }
        menu.clear()
        inflater.inflate(R.menu.menu_home_home,menu)
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return super.onOptionsItemSelected(item)
    }
}