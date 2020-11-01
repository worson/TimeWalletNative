package app.worson.timewallet.page.home.setting

import android.Manifest
import android.os.Bundle
import android.view.*
import android.widget.EditText
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import app.worson.timewallet.R
import app.worson.timewallet.db.import.TimeLogImport
import app.worson.timewallet.test.page.TestMainFragment
import app.worson.timewallet.utils.T
import com.permissionx.guolindev.PermissionX
import com.worson.lib.appbasic.view.resName
import com.worson.lib.log.L
import kotlinx.android.synthetic.main.fragment_setting.*
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import java.io.File

class SettingFragment : Fragment() {

    val TAG="SettingFragment"

    private lateinit var settingViewModel: SettingViewModel

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        setHasOptionsMenu(true)
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
                timelog.importAssetFile("timelog/demo.json")
            }

        }
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        L.d(TAG) { "onCreateOptionsMenu: " }
        menu.clear()
        inflater.inflate(R.menu.menu_home_setting,menu)
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        L.d(TAG) { "onContextItemSelected: ${item.itemId.resName()}" }
        var result=true
        when(item.itemId){
            R.id.it_import_default -> {
                GlobalScope.launch {
                    val timelog = TimeLogImport()
                    timelog.importAssetFile("timelog/demo.json")
                }
            }

            R.id.it_import_timelog -> {
                PermissionX.init(activity)
                    .permissions(
                        Manifest.permission.READ_EXTERNAL_STORAGE,
                        Manifest.permission.WRITE_EXTERNAL_STORAGE)
                    .request { allGranted, grantedList, deniedList ->
                        if (allGranted) {
                            val editText= EditText(requireContext())
                            editText.hint="输入需要添加的文件或文件夹位置"
                            editText.setText("/storage/emulated/0/TimeLogger/backup/backup_json.json")
                            AlertDialog.Builder(requireContext())
                                .setTitle("添加文件目录")
                                .setView(editText)
                                .setPositiveButton("确定"){
                                        a,b ->
                                    if (editText.text.toString().isNullOrBlank()){
                                        T.normal("请输入文件夹目录").show()
                                        return@setPositiveButton
                                    }
                                    val path= File(editText.text.toString())
                                    if (!path.exists()){
                                        T.normal("路径不存在").show()
                                        return@setPositiveButton
                                    }
                                    GlobalScope.launch {
                                        val timelog = TimeLogImport()
                                        timelog.importFile(path)
                                    }

                                }.show()
                        } else {
                            T.normal("请授予相关权限").show()
                        }
                    }
            }

            else -> result=false
        }
        if (result){
            return true
        }
        return super.onContextItemSelected(item)
    }
}