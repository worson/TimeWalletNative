package app.worson.timewallet.page.home

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.worson.lib.appbasic.architecture.comm.Event
import androidx.lifecycle.liveData

/**
 * @author worson  10.19 2020
 */
class MainViewModel : ViewModel(){

    private  val TAG = "MainViewModel"

    var viewState:MainViewState=MainViewState()
        private set

    private val mLiveData = MutableLiveData<MainViewState>()
    val liveData = liveData { emitSource(mLiveData) }



    init {

    }

    fun showTimeTask(isShow:Boolean){
        notifyViewState(viewState.copy(showTimeTask = Event(
            isShow
        )
        ))
    }

    fun fullScreen(yes:Boolean){
        notifyViewState(viewState.copy(fullScreen = Event(
            yes
        )
        ))
    }

    private fun notifyViewState(viewState:MainViewState){
        this.viewState=viewState
        mLiveData.postValue(viewState)
    }

    companion object {
        private  val TAG = "MainViewModel"
    }



}

data class MainViewState(
    val showTimeTask: Event<Boolean>? = null,
    val fullScreen: Event<Boolean>? = null,
){

}