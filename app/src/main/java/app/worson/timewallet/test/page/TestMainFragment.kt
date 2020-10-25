package app.worson.timewallet.test.page

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.addCallback
import androidx.lifecycle.observe
import androidx.recyclerview.widget.LinearLayoutManager
import app.worson.timewallet.R
import app.worson.timewallet.page.timetask.TimeTaskFragment
import app.worson.timewallet.test.page.base.TestFragment
import app.worson.timewallet.test.page.edittext.EditTextListFragment
import app.worson.timewallet.test.page.main.FragmentItem
import app.worson.timewallet.test.page.main.TestMainListAdapter
import app.worson.timewallet.view.rvhelper.DefaultItemDiff
import com.blankj.utilcode.util.FragmentUtils
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.worson.lib.log.L
import kotlinx.android.synthetic.main.fragment_event_list_select_dialog.*


/**
 * A simple [Fragment] subclass.
 * Use the [TestMainFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class TestMainFragment : TestFragment() {

    private val mAdapter: TestMainListAdapter =
        TestMainListAdapter(
            mutableListOf()
        )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_test_main, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        rvView.setLayoutManager(LinearLayoutManager(requireContext()))
        rvView.adapter = mAdapter
        mAdapter.setDiffCallback(DefaultItemDiff())
        mAdapter.setOnItemClickListener() { a, _, p ->
            L.i(TAG, "setOnItemClickListener: ")
            val item=a.getItem(p)
            when(item){
                is FragmentItem -> {
                    showTestFragments(item.fragment)
                }
            }

        }

        mAdapter.setDiffNewData(mutableListOf(
            FragmentItem(EditTextListFragment.newInstance())
        ))

    }

    private fun showTestFragments(fragment:Fragment) {
        val tag=fragment.javaClass.simpleName
        FragmentUtils.findFragment(requireActivity().supportFragmentManager,tag)
            ?: FragmentUtils.add(requireActivity().supportFragmentManager,fragment,R.id.test_fragment_containner,tag)
        FragmentUtils.show(fragment)
    }

    companion object {

        val  TAG = "TestMainFragment"
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment TestMainFragment.
         */
        @JvmStatic
        fun newInstance() =
            TestMainFragment().apply {
                arguments = Bundle().apply {
                }
            }
    }
}