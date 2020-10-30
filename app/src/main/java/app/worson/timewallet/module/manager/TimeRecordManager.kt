package app.worson.timewallet.module.manager

import app.worson.timewallet.db.entity.TimeEventEntity
import app.worson.timewallet.db.entity.TimeRecordEntity

/**
 * @author wangshengxing  10.30 2020
 */

interface ITimeRecordManager{
    fun startRecord(){}

    fun stopRecord(){}

    fun updateRecord(){}

    fun observeResult(list: TimeRecordManager.TimeRecordListener){

    }
}

object TimeRecordManager:ITimeRecordManager {

    interface TimeRecordListener{
        fun onChange(record: TimeRecordEntity)
        fun onChange(record: TimeEventEntity)
    }

    fun init(){

    }



}