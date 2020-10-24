package app.worson.timewallet.page.timetask

import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import app.worson.timewallet.R
import com.blankj.utilcode.util.BarUtils
import com.blankj.utilcode.util.GsonUtils

class TimeTaskFragment : Fragment() {

    private val viewModel by activityViewModels<TimeTaskViewModel>()

    companion object {
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
    }

    override fun onResume() {
        super.onResume()
//        BarUtils.setStatusBarVisibility(requireActivity(),false)
    }

    override fun onPause() {
        super.onPause()
//        BarUtils.setStatusBarVisibility(requireActivity(),true)
    }
}