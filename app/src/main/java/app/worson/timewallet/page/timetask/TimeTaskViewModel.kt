package app.worson.timewallet.page.timetask

import androidx.lifecycle.MutableLiveData
import com.worson.lib.appbasic.architecture.comm.Event
import androidx.lifecycle.liveData
import app.worson.timewallet.comm.BaseViewModel
import app.worson.timewallet.db.entity.TimeEventEntity
import app.worson.timewallet.db.entity.TimeRecordEntity
import app.worson.timewallet.module.storage.AccountSettings
import app.worson.timewallet.module.storage.TimeRecordSource
import app.worson.timewallet.module.storage.TimeWalletRepository
import com.blankj.utilcode.constant.TimeConstants
import com.worson.lib.log.L
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.collect

/**
 * @author worson  10.19 2020
 */
class TimeTaskViewModel : BaseViewModel() {

    private val TAG = "TimeTaskViewModel"

    var viewState: TimeTaskViewState = TimeTaskViewState()
        private set

    var currentRecordObserveJob:Job?=null
    var taskTimeCountJob:Job?=null

    val dao by lazy { TimeWalletRepository.recordDao }
    val uid by lazy { AccountSettings.uid }

    private val mLiveData = MutableLiveData<TimeTaskViewState>()
    val liveData = liveData { emitSource(mLiveData) }


    init {
        L.d(TAG) { "init: " }
        launch(Dispatchers.IO) {

            dao.queryNotEnd(AccountSettings.uid).firstOrNull()?.let {
                notifyViewState(viewState.copy(record = Event(it)))
                startObserveCurrentRecord(it)
            }

            val record = getRecord()
            val timeEvent = if (record == null) {
                TimeWalletRepository.eventDao.queryEvents(AccountSettings.uid).apply {
                    sortBy { it.id }
                }.firstOrNull()
            } else {
                TimeWalletRepository.eventDao.queryById(AccountSettings.uid, record.id)
            }

            timeEvent?.let {
                notifyViewState(
                    viewState.copy(
                        timeEvent = Event(
                            it
                        )
                    )
                )
            }

            record?.apply {
                if (estimatedTime>0){
                    observeEstimateTime(this)
                }
            }

        }
    }

    fun setTaskEvent(tasEventEntity: TimeEventEntity) {
        getRecord()?.let {
            updateRecord(it.copy(typeId = tasEventEntity.id))
        }
        notifyViewState(
            viewState.copy(
                timeEvent = Event(
                    tasEventEntity
                )
            )
        )
    }

    fun startTask(startTime:Long=System.currentTimeMillis(),typeId:Int=1) {
        L.i(TAG, "startTask: ")
        stopTask()
        launch() {
            TimeRecordSource.startTimeRecord()?.let {
                //block
                L.i(TAG, "startTask: ${it}")
                startObserveCurrentRecord(it)
            }
        }
    }

    fun stopTask() {
        resetObserveEstimateTime()
        getRecord()?.apply {
            if (isTasking()){
                L.d(TAG) { "stopTask: " }
                updateRecord(this.copy(endTime = System.currentTimeMillis()))
            }else{
                L.d(TAG) { "stopTask: task has end" }
            }

        }

    }

    fun setEstimatedTime(offsetTimeMs:Long){
        getRecord()?.apply {
            val record=copy(estimatedTime = startTime+offsetTimeMs)
            updateRecord(record)
            observeEstimateTime(record)
        }
    }

    private fun resetObserveEstimateTime(){
        taskTimeCountJob?.let {
            taskTimeCountJob=null
            it.cancel()
        }
    }

    private fun observeEstimateTime(record: TimeRecordEntity) {
        L.i(TAG, "observeEstimateTime: ${record} ")
        if (record.estimatedTime<System.currentTimeMillis()){
            L.w(TAG, "observeEstimateTime: error estimatedTime=${record.estimatedTime}")
            return
        }
        resetObserveEstimateTime()
        launch {
            delay(TimeConstants.SEC*1L)
            notifyViewState(
                viewState.copy(
                    leftTimeMs = Event(record.estimatedTime-System.currentTimeMillis())
                )
            )
        }
    }

    private fun resetStartObserveCurrentRecord(){
        currentRecordObserveJob?.let {
            L.i(TAG, "startObserveCurrentRecord: cancel last task")
            currentRecordObserveJob=null
            it.cancel()
        }
    }

    private fun startObserveCurrentRecord(it: TimeRecordEntity) {
        L.i(TAG, "startObserveCurrentRecord: ${it}")
        resetStartObserveCurrentRecord()
        currentRecordObserveJob=launch(Dispatchers.IO) {
            TimeRecordSource.dao.queryFlowById(uid,it.id).collect {
                // FIXME: 2020/10/27 how to cancel task
                notifyViewState(viewState.copy(record = Event(it)))
            }
        }
    }

    fun updateRecord(record:TimeRecordEntity){
        launch(Dispatchers.IO) {
            TimeRecordSource.dao.addOrReplace(record)
        }
    }

    fun getRecord(): TimeRecordEntity? {
        return viewState.record?.peekContent()
    }

    fun getTimeEvent(): TimeEventEntity? {
        return viewState.timeEvent?.peekContent()
    }

    private fun notifyViewState(viewState: TimeTaskViewState) {
        this.viewState = viewState
        mLiveData.postValue(viewState)
    }



    companion object {
        private val TAG = "TimeTaskViewModel"
    }


}

data class TimeTaskViewState(
    val timeEvent: Event<TimeEventEntity>? = null,
    val record: Event<TimeRecordEntity>? = null,
    val leftTimeMs: Event<Long>? = null,
) {

}