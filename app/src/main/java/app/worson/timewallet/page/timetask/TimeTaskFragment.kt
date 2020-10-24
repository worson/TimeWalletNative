package app.worson.timewallet.page.timetask

import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.addCallback
import androidx.fragment.app.activityViewModels
import app.worson.timewallet.R
import app.worson.timewallet.page.home.MainViewModel
import com.blankj.utilcode.util.BarUtils
import com.blankj.utilcode.util.FragmentUtils
import com.blankj.utilcode.util.GsonUtils
import com.worson.lib.log.L

class TimeTaskFragment : Fragment() {

    private val viewModel by activityViewModels<TimeTaskViewModel>()
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