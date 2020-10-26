package app.worson.timewallet.page.eventtype

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import app.worson.timewallet.comm.Event
import androidx.lifecycle.liveData
import androidx.lifecycle.viewModelScope
import app.worson.timewallet.db.entity.TimeEventEntity
import app.worson.timewallet.module.storage.AccountSettings
import app.worson.timewallet.module.storage.TimeWalletRepository
import com.worson.lib.log.L
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

/**
 * 说明:
 * @author worson  10.19 2020
 */
class TimeEventViewModel : ViewModel(){

    private  val TAG = "TimeEventViewModel"

    var viewState:TimeEventViewState=TimeEventViewState()
        private set

    private val mLiveData = MutableLiveData<TimeEventViewState>()
    val liveData = liveData { emitSource(mLiveData) }

    private val mEventsLive = MutableLiveData<MutableList<TimeEventEntity>>()
    val eventsLive = liveData { emitSource(mEventsLive) }





    init {
        viewModelScope.launch(Dispatchers.IO) {
            L.i(TAG,"init")
            TimeWalletRepository.eventDao.queryEventsFlow(AccountSettings.uid)
                .collect {
                    notifyList(it)
                }
        }
    }

    fun freshTimeEvents(){
        viewModelScope.launch(Dispatchers.IO) {
            TimeWalletRepository.eventDao.queryEvents(AccountSettings.uid)?.let {
                notifyList(it)
            }
        }
    }

    private fun notifyList(list:MutableList<TimeEventEntity>){
        mEventsLive.postValue(list)
    }

    companion object {
        private  val TAG = "TimeEventViewModel"
    }



}

data class TimeEventViewState(
    val timeEvents: Event<MutableList<TimeEventEntity>>? = null,
    val menuState: Event<Int>? = null,
){

}