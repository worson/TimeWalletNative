package app.worson.timewallet.page.eventtype

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import app.worson.timewallet.comm.Event
import app.worson.timewallet.db.entity.TaskEntity
import androidx.lifecycle.liveData
import androidx.lifecycle.viewModelScope
import app.worson.timewallet.db.TimeWalletDb
import app.worson.timewallet.module.storage.AccountSettings
import app.worson.timewallet.module.storage.TimeWalletRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

/**
 * 说明:
 * @author worson  10.19 2020
 */
class TimeEventViewModel : ViewModel(){
    private val mLiveData = MutableLiveData<TimeEventViewState>()
    val liveData = liveData { emitSource(mLiveData) }



    init {
        /*TimeWalletRepository.eventDao.queryLiveEvents(AccountSettings.uid).observe(){

        }*/
        viewModelScope.launch(Dispatchers.IO) {

        }
    }

    companion object {
        private const val TAG = "TimeEventViewModel"
    }



}

data class TimeEventViewState(
    val editMode: Event<List<TaskEntity>>? = null,
    val menuState: Event<Int>? = null,
){

}