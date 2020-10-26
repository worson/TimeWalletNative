package app.worson.timewallet.page.timetask

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.worson.lib.appbasic.architecture.comm.Event
import androidx.lifecycle.liveData
import androidx.lifecycle.viewModelScope
import app.worson.timewallet.db.entity.TimeEventEntity
import app.worson.timewallet.module.storage.AccountSettings
import app.worson.timewallet.module.storage.TimeWalletRepository
import com.worson.lib.log.L
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

/**
 * @author worson  10.19 2020
 */
class TimeTaskViewModel : ViewModel(){

    private  val TAG = "TimeTaskViewModel"

    var viewState:TimeTaskViewState=TimeTaskViewState()
        private set

    private val mLiveData = MutableLiveData<TimeTaskViewState>()
    val liveData = liveData { emitSource(mLiveData) }

    init {
        viewModelScope.launch(Dispatchers.IO) {
            TimeWalletRepository.eventDao.queryEvents(AccountSettings.uid).apply {
                sortBy { it.id }
            }.firstOrNull()?.let {
                notifyViewState(viewState.copy(timeEvent = Event(
                    it
                )
                ))
            }
        }
    }

    fun setTaskEvent(tasEventEntity: TimeEventEntity){

        notifyViewState(viewState.copy(timeEvent = Event(
            tasEventEntity
        )
        ))
    }

    fun startTask(){
        L.i(TAG, "startTask: ")
    }


    private fun notifyViewState(viewState:TimeTaskViewState){
        this.viewState=viewState
        mLiveData.postValue(viewState)
    }

    companion object {
        private  val TAG = "TimeTaskViewModel"
    }



}

data class TimeTaskViewState(
    val timeEvent: Event<TimeEventEntity>? = null,
){

}