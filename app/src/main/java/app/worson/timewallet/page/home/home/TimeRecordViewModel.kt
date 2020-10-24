package app.worson.timewallet.page.home.home

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import app.worson.timewallet.comm.Event
import androidx.lifecycle.liveData
import androidx.lifecycle.viewModelScope
import app.worson.timewallet.db.entity.TimeRecordEntity
import app.worson.timewallet.module.storage.AccountSettings
import app.worson.timewallet.module.storage.TimeWalletRepository
import com.worson.lib.log.L
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

/**
 * @author worson  10.19 2020
 */
class TimeRecordViewModel : ViewModel(){

    val TAG = "TimeRecordViewModel"

    var viewState:TimeRecordViewState=TimeRecordViewState()
        private set

    private val mLiveData = MutableLiveData<TimeRecordViewState>()
    val liveData = liveData { emitSource(mLiveData) }

    private val mRecordsLive = MutableLiveData<MutableList<TimeRecordEntity>>()
    val recordsLive = liveData { emitSource(mRecordsLive) }



    init {

    }

    fun startObserveDayRecord(day:String){
        viewModelScope.launch(Dispatchers.IO) {
            L.i(TAG,"startObserveDayRecord ${day}")
            TimeWalletRepository.recordDao.queryEventsFlow(AccountSettings.uid,day)
                .collect {
                    notifyList(it)
                }
        }
    }

    fun freshTimeRecords(day:String){
        viewModelScope.launch(Dispatchers.IO) {
            TimeWalletRepository.recordDao.queryEvents(AccountSettings.uid,day)?.let {
                notifyList(it)
            }
        }
    }

    private fun notifyList(list:MutableList<TimeRecordEntity>){
        mRecordsLive.postValue(list)
    }

    companion object {
        private const val TAG = "TimeRecordViewModel"
    }



}

data class TimeRecordViewState(
    val timeEvents: Event<MutableList<TimeRecordEntity>>? = null,
){

}