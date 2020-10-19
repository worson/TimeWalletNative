package app.worson.timewallet.page.home.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import app.worson.timewallet.R
import app.worson.timewallet.db.TimeWalletDb
import app.worson.timewallet.page.eventtype.EventListSelectDialogFragment
import com.blankj.utilcode.util.FragmentUtils
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import kotlinx.android.synthetic.main.fragment_home.*

class HomeFragment : Fragment() {

    private lateinit var homeViewModel: HomeViewModel

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
            val fragment= EventListSelectDialogFragment.newInstance()
            FragmentUtils.add(requireActivity().supportFragmentManager,fragment,R.id.fragment_containner)
            FragmentUtils.show(fragment)
        }
    }

}