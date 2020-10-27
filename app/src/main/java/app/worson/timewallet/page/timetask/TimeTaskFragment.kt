package app.worson.timewallet.page.timetask

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.addCallback
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.observe
import app.worson.timewallet.R
import app.worson.timewallet.page.eventtype.TimeEventSelectDialogFragment
import app.worson.timewallet.page.home.MainViewModel
import app.worson.timewallet.utils.time.TimeFormatUtil
import com.blankj.utilcode.constant.TimeConstants
import com.blankj.utilcode.util.FragmentUtils
import com.blankj.utilcode.util.TimeUtils
import com.worson.lib.log.L
import kotlinx.android.synthetic.main.time_task_fragment.*

class TimeTaskFragment : Fragment() {

    private val mTaskViewModel by activityViewModels<TimeTaskViewModel>()
    private val mMainViewModel by activityViewModels<MainViewModel>()

    companion object {
        val  TAG = "TimeTaskFragment"
        fun newInstance() = TimeTaskFragment()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.time_task_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        requireActivity().onBackPressedDispatcher.addCallback(viewLifecycleOwner) {
            L.i(TAG, "OnBackPressedCallback: ")
            FragmentUtils.hide(this@TimeTaskFragment)
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initClick()
        initInitData()

    }

    private fun initInitData() {
        mTaskViewModel.liveData.observe(viewLifecycleOwner){
            observeTaskViewState(it)
        }
    }

    private fun observeTaskViewState(state: TimeTaskViewState) {
        L.d(TAG) { "observeTaskViewState: " }
        state.timeEvent?.handleIfNotHandled {
            L.d(TAG) { "observeTaskViewState: timeEvent ${it}" }
            tvTaskEvent.setText("事件名字:"+it.descDisplay())
        }
        state.record?.handleIfNotHandled {
            L.d(TAG) { "observeTaskViewState: record ${it}" }
            etTaskThing.setText(it.thing)
            if (it.isTasking()){
                btStart.text="结束"
            }else{
                btStart.text="开始"
            }
            val orgin=etTaskThing.text.toString()
            if (it.thing!=orgin){
                etTaskThing.setText(it.thing)
            }
        }
        state.leftTimeMs?.handleIfNotHandled {
            planTime.setText(TimeFormatUtil.offsetTimeMs(it))
        }

    }


    private fun initClick() {
        vgRoot.setOnClickListener {  }
        btStart.setOnClickListener {
            val record=mTaskViewModel.getRecord()
            record?.let {
                if (it.isTasking()){
                    mTaskViewModel.stopTask()
                }else{
                    mTaskViewModel.startTask()
                }
            } ?: mTaskViewModel.startTask()
        }
        tvTaskEvent.setOnClickListener {
            showTimeEventSelect()
        }
        etTaskThing.addTextChangedListener {
            val content=it.toString()
            L.d(TAG) { "addTextChangedListener: ${content} " }
            mTaskViewModel.getRecord()?.let {
                if (content!=it.thing){
                    mTaskViewModel.updateRecord(it.copy(thing = content ))
                }
            }
        }

        planTime.setOnClickListener {
            mTaskViewModel.setEstimatedTime(TimeConstants.HOUR/2L)
        }
    }

    private fun showTimeEventSelect() {
        L.i(TAG, "showTimeEventSelect: ")
        val fragment= TimeEventSelectDialogFragment.newInstance()
        FragmentUtils.add(requireActivity().supportFragmentManager,fragment,R.id.fragment_containner)
        FragmentUtils.show(fragment)
        fragment.setSelectListener {
            L.i(TAG, "setSelectListener: ${it}")
            mTaskViewModel.setTaskEvent(it)
        }
    }

    override fun onHiddenChanged(hidden: Boolean) {
        super.onHiddenChanged(hidden)
        L.i(TAG, "onHiddenChanged: hidden=${hidden}")
        mMainViewModel.fullScreen(!hidden)
    }
}