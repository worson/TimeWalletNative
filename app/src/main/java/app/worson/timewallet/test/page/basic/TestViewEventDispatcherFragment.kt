package app.worson.timewallet.test.page.basic

import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import app.worson.timewallet.R
import app.worson.timewallet.test.page.base.TestFragment
import app.worson.timewallet.test.page.notta.BindingListAdapterListFragment
import com.worson.lib.log.L
import kotlinx.android.synthetic.main.test_fragment_view_event_dispatcher.*


/**
 * A simple [Fragment] subclass.
 * Use the [TestViewEventDispatcherFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class TestViewEventDispatcherFragment : TestFragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.test_fragment_view_event_dispatcher, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        btButton.setOnClickListener {
            L.i(TAG, "click: btButton ")
        }

        vgMidle.setOnClickListener {
            L.i(TAG, "click: vgMidle ")
        }

        vgRoot.setOnClickListener {
            L.i(TAG, "click: vgRoot ")
        }

        
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        L.d(TAG) { "onCreateOptionsMenu: " }
        menu.clear()
        inflater.inflate(R.menu.test_menu_view_event_dispatch,menu)
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        L.d(TAG) { "onOptionsItemSelected: ${item.title}" }
        when(item.itemId){
            R.id.root_click_enable -> {
                vgRoot.isClickable=true
            }
            R.id.root_click_disable -> {
                vgRoot.isClickable=false
            }
            R.id.root_view_disable -> {
                vgRoot.isEnabled=false
            }

            R.id.root_view_intercept -> {
                vgRoot.requestDisallowInterceptTouchEvent(true)
            }
            else -> return super.onOptionsItemSelected(item)
        }
        return true
    }

    companion object {

        private  val TAG = "TestViewEventDispatcherFragment"

        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment TestViewEventDispatcherFragment.
         */
        @JvmStatic
        fun newInstance() =
            TestViewEventDispatcherFragment().apply {

            }
    }
}