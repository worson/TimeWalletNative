package app.worson.timewallet.page

import android.Manifest
import android.content.Context
import android.content.Intent
import android.hardware.Sensor
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver
import androidx.lifecycle.LifecycleOwner
import com.google.android.material.bottomnavigation.BottomNavigationView
import androidx.lifecycle.observe
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import app.worson.timewallet.R
import app.worson.timewallet.module.page.BaseActivity
import app.worson.timewallet.page.home.MainViewModel
import app.worson.timewallet.page.home.MainViewState
import app.worson.timewallet.page.service.TaskRecordServiceManager
import app.worson.timewallet.page.timetask.TimeTaskFragment
import app.worson.timewallet.test.page.TestMainFragment
import com.blankj.utilcode.util.BarUtils
import com.blankj.utilcode.util.FragmentUtils
import com.permissionx.guolindev.PermissionX
import com.permissionx.guolindev.callback.RequestCallback
import com.permissionx.guolindev.request.PermissionBuilder
import com.worson.lib.appbasic.android.data.bundleStrInfo
import com.worson.lib.log.L

class MainActivity : BaseActivity() {

    private lateinit var mMainViewModel:MainViewModel

    lateinit var taskTimeTaskFragment: TimeTaskFragment


    override fun onNewIntent(intent: Intent) {
        super.onNewIntent(intent)
        L.i(TAG, "onNewIntent: ${intent.bundleStrInfo()} ")
        intent.extras?.getString(KEY_SOURCE)?.takeIf {
            it.isNotBlank() && VALUE_SOURCE_TASKRECORD==it
        }?.let {
            showHideTimeTaskFragment(true)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        L.i(TAG, "onCreate: ")
        val navView: BottomNavigationView = findViewById(R.id.nav_view)

        val navController = findNavController(R.id.nav_host_fragment)
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        val appBarConfiguration = AppBarConfiguration(
            setOf(
                R.id.navigation_home, R.id.navigation_dashboard, R.id.navigation_notifications
            )
        )
        setupActionBarWithNavController(navController, appBarConfiguration)
        navView.setupWithNavController(navController)
        initTimeTask()
        initViewModel()

        lifecycle.addObserver(object : LifecycleEventObserver {
            override fun onStateChanged(source: LifecycleOwner, event: Lifecycle.Event) {
                L.d(TAG) { "onStateChanged: event ${event} " }
            }
        })
//        navView.invisible()
        PermissionX.init(this).permissions(Manifest.permission.ACTIVITY_RECOGNITION)
            .request(object :
                RequestCallback {
                override fun onResult(
                    allGranted: Boolean,
                    grantedList: MutableList<String>?,
                    deniedList: MutableList<String>?
                ) {
                    L.i(TAG, "onResult: allGranted ${allGranted} ")
                }
            })
    }

    val mTestMainFragment by lazy {
        TestMainFragment.newInstance()
    }

    private fun showTestFragments() {
        val fragment=mTestMainFragment
        val tag=fragment.javaClass.simpleName
        FragmentUtils.findFragment(supportFragmentManager,tag)
            ?: FragmentUtils.add(supportFragmentManager,fragment,R.id.task_containner,tag)
        FragmentUtils.show(fragment)
    }

    private fun initViewModel() {
        mMainViewModel=viewModel(MainViewModel::class.java)
        mMainViewModel.liveData.observe(this) {
            observeMainViewState(it)
        }
    }

    private fun observeMainViewState(viewState: MainViewState) {
        viewState.showTimeTask?.handleIfNotHandled {
            showTestFragments()
//            showHideTimeTaskFragment(it)
        }
        viewState.fullScreen?.handleIfNotHandled {
            showFullScreen(it)
        }
    }

    private fun showFullScreen(yes: Boolean) {
        L.i(TAG, "showFullScreen: full=${yes}")
        BarUtils.setStatusBarVisibility(this,!yes)
        getSupportActionBar()?.apply {
            if (yes){
                hide()
            }else{
                show()
            }
        }
    }


    private fun initTimeTask() {
        taskTimeTaskFragment=TimeTaskFragment()
        checkAddFragment(taskTimeTaskFragment,R.id.task_containner)
        showHideTimeTaskFragment(false)

    }

    private fun showHideTimeTaskFragment(isShow: Boolean) {
        L.i(TAG, "showHideTimeTaskFragment: ")
        showHideFragment(taskTimeTaskFragment,isShow,R.id.task_containner)
    }

    /*override fun onCreateOptionsMenu(menu: Menu): Boolean {
        L.d(TAG, { "onCreateOptionsMenu: " })
        getMenuInflater().inflate(R.menu.bottom_nav_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }*/

    override fun onOptionsMenuClosed(menu: Menu) {
        L.d(TAG, { "onOptionsMenuClosed: " })
        super.onOptionsMenuClosed(menu)
    }

    override fun onBackPressed() {
        super.onBackPressed()
        L.d(TAG) { "onBackPressed: " }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        L.d(TAG, { "onOptionsItemSelected: ${item.title}" })
        return super.onOptionsItemSelected(item)
    }

    companion object{
        val  TAG = "MainActivity"
        const val KEY_SOURCE = "key_source"

        const val VALUE_SOURCE_TASKRECORD = "value_source_taskrecord"

        fun newTaskRecordIntent(context: Context): Intent {
            val intent = Intent(context, MainActivity::class.java)
            intent.putExtra(KEY_SOURCE, VALUE_SOURCE_TASKRECORD)
            return intent
        }
    }
}