package app.worson.timewallet.page.timetask

import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.addCallback
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.observe
import androidx.lifecycle.viewModelScope
import app.worson.timewallet.R
import app.worson.timewallet.module.storage.AccountSettings
import app.worson.timewallet.module.storage.TimeWalletRepository
import app.worson.timewallet.page.eventtype.TimeEventSelectDialogFragment
import app.worson.timewallet.page.home.MainViewModel
import com.blankj.utilcode.util.BarUtils
import com.blankj.utilcode.util.FragmentUtils
import com.blankj.utilcode.util.GsonUtils
import com.worson.lib.log.L
import kotlinx.android.synthetic.main.time_task_fragment.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

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
        state.timeEvent?.handleIfNotHandled {
            tvTaskEvent.setText(it.name)
        }

    }


    private fun initClick() {
        vgRoot.setOnClickListener {  }
        btStart.setOnClickListener {
            mTaskViewModel.startTask()
        }
        tvTaskEvent.setOnClickListener {
            showTimeEventSelect()
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

    override fun onStart() {
        super.onStart()
        L.i(TAG, "onStart: ")

    }

    override fun onStop() {
        super.onStop()
        L.i(TAG, "onStop: ")
    }

    override fun onHiddenChanged(hidden: Boolean) {
        super.onHiddenChanged(hidden)
        L.i(TAG, "onHiddenChanged: hidden=${hidden}")
        mMainViewModel.fullScreen(!hidden)
    }
}