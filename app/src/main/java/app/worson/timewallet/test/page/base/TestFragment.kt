package app.worson.timewallet.test.page.base

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.addCallback
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver
import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.LifecycleOwner
import app.worson.timewallet.module.page.BaseFragment
import app.worson.timewallet.page.timetask.TimeTaskFragment
import com.blankj.utilcode.util.FragmentUtils
import com.worson.lib.log.L

/**
 * 说明:
 * @author worson  10.25 2020
 */
open class TestFragment : BaseFragment() {

    private val TAG by lazy {
        "TestFragment@{${this.javaClass.simpleName}}"
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        setHasOptionsMenu(true)
        L.d(TAG, { "onActivityCreated: " })
        /*viewLifecycleOwner.lifecycle.addObserver(object : LifecycleEventObserver {
            override fun onStateChanged(source: LifecycleOwner, event: Lifecycle.Event) {
                L.d(TAG, { "onStateChanged: ${event}" })
            }
        })*/

        requireActivity().onBackPressedDispatcher.addCallback(viewLifecycleOwner) {
            L.d(TAG) { "onActivityCreated: onBackPressed ${this.javaClass.simpleName}" }
            FragmentUtils.remove(this@TestFragment)
        }
    }

    override fun onResume() {
        super.onResume()
        (requireActivity() as? AppCompatActivity)?.getSupportActionBar()?.title=this.javaClass.simpleName
//        requireActivity().getSupportActionBar()?.title=this.javaClass.simpleName
    }
}