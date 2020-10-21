package app.worson.timewallet.db.config

import app.worson.timewallet.db.entity.TimeEventEntity

/**
 * 说明:
 * @author worson  10.18 2020
 */
data class DefaultTimeWalletData(
    val default_events:List<TimeEventEntity>
) {

}