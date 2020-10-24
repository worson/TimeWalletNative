package app.worson.timewallet.page

import android.os.Bundle
import com.google.android.material.bottomnavigation.BottomNavigationView
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import androidx.lifecycle.observe
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import app.worson.timewallet.R
import app.worson.timewallet.page.home.MainViewModel
import app.worson.timewallet.page.home.MainViewState
import app.worson.timewallet.page.home.dashboard.DashboardViewModel
import app.worson.timewallet.page.timetask.TimeTaskFragment
import com.blankj.utilcode.util.BarUtils
import com.blankj.utilcode.util.FragmentUtils
import com.worson.lib.log.L

class MainActivity : AppCompatActivity() {

    private lateinit var mainViewModel:MainViewModel

    lateinit var taskTimeTaskFragment: TimeTaskFragment

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val navView: BottomNavigationView = findViewById(R.id.nav_view)

        val navController = findNavController(R.id.nav_host_fragment)
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        val appBarConfiguration = AppBarConfiguration(setOf(
            R.id.navigation_home, R.id.navigation_dashboard, R.id.navigation_notifications
        ))
        setupActionBarWithNavController(navController, appBarConfiguration)
        navView.setupWithNavController(navController)
        initTimeTask()
        initViewModel()
//        navView.invisible()


    }

    private fun initViewModel() {
        mainViewModel=ViewModelProvider(this).get(MainViewModel::class.java)
        mainViewModel.liveData.observe(this) {
            observeMainViewState(it)
        }
    }

    private fun observeMainViewState(viewState: MainViewState) {
        viewState.showTimeTask?.handleIfNotHandled {
            showHideTimeTaskFragment(it)
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
        FragmentUtils.add(supportFragmentManager,taskTimeTaskFragment,R.id.task_containner)
        showHideTimeTaskFragment(false)

    }

    private fun showHideTimeTaskFragment(isShow: Boolean) {
        if (isShow){
            FragmentUtils.show(taskTimeTaskFragment)
        }else{
            FragmentUtils.hide(taskTimeTaskFragment)
        }

    }

    companion object{
        val  TAG = "MainActivity"
    }
}