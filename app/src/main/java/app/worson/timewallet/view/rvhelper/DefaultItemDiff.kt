package app.worson.timewallet.view.rvhelper

import androidx.recyclerview.widget.DiffUtil

/**
 * @author wangshengxing  10.21 2020
 */
class DefaultItemDiff<T>: DiffUtil.ItemCallback<T>() {
    override fun areItemsTheSame(oldItem: T, newItem: T): Boolean = oldItem==newItem

    override fun areContentsTheSame(oldItem: T, newItem: T): Boolean =true
}