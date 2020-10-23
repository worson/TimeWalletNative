package app.worson.timewallet.page.home.setting

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import app.worson.timewallet.R
import app.worson.timewallet.db.import.TimeLogImport
import kotlinx.android.synthetic.main.fragment_setting.*
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class SettingFragment : Fragment() {

    private lateinit var settingViewModel: SettingViewModel

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        settingViewModel =
                ViewModelProviders.of(this).get(SettingViewModel::class.java)
        val root = inflater.inflate(R.layout.fragment_setting, container, false)
        settingViewModel.text.observe(viewLifecycleOwner, Observer {

        })
        return root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        taskImport.setOnClickListener {
            GlobalScope.launch {
                val timelog = TimeLogImport()
                timelog.importFile("timelog/demo.json")
            }

        }
    }
}