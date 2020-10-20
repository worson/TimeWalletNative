package app.worson.timewallet.page.eventtype

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import app.worson.timewallet.comm.Event
import app.worson.timewallet.db.entity.TaskEntity
import androidx.lifecycle.liveData
import androidx.lifecycle.viewModelScope
import app.worson.timewallet.db.TimeWalletDb
import app.worson.timewallet.db.entity.EventTypeEntity
import app.worson.timewallet.module.storage.AccountSettings
import app.worson.timewallet.module.storage.TimeWalletRepository
import com.worson.lib.log.L
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

/**
 * 说明:
 * @author worson  10.19 2020
 */
class TimeEventViewModel : ViewModel(){

    val TAG = "TimeEventViewModel"

    var viewState:TimeEventViewState=TimeEventViewState()
        private set
    private val mLiveData = MutableLiveData<TimeEventViewState>()
    val liveData = liveData { emitSource(mLiveData) }



    init {
        viewModelScope.launch(Dispatchers.IO) {
            L.i(TAG,"init")
            TimeWalletRepository.eventDao.queryEventsFlow(AccountSettings.uid)
                .collect {
                    viewState=viewState.copy(timeEvents=Event(it))
                    mLiveData.postValue(viewState)
                }
        }
    }

    companion object {
        private const val TAG = "TimeEventViewModel"
    }



}

data class TimeEventViewState(
    val timeEvents: Event<List<EventTypeEntity>>? = null,
    val menuState: Event<Int>? = null,
){

}