package app.worson.timewallet.module.page

import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import app.worson.timewallet.page.home.MainViewModel
import com.blankj.utilcode.util.FragmentUtils
import com.worson.lib.appbasic.kotlinex.simpleName

/**
 * @author worson  10.28 2020
 */
open class BaseActivity : AppCompatActivity(){

    fun checkAddFragment(fragment:Fragment,layoutContainer:Int){
        val tag=fragment.simpleName()
        FragmentUtils.findFragment(supportFragmentManager,tag)
            ?: FragmentUtils.add(supportFragmentManager,fragment,layoutContainer,tag)
    }

    fun showHideFragment(fragment:Fragment,isShow:Boolean,layoutContainer:Int ){
        if (isShow){
            checkAddFragment(fragment,layoutContainer)
            FragmentUtils.show(fragment)
        }else{
            FragmentUtils.hide(fragment)
        }

    }

    fun <T : ViewModel> viewModel (viewmodel: Class<T>): T {
        return ViewModelProvider(this).get(viewmodel)
    }


}