package cl.alexissilva.trainerapp.ui.base

import androidx.recyclerview.widget.DiffUtil
import androidx.viewbinding.ViewBinding
import cl.alexissilva.trainerapp.ui.DefaultDiffUtil

abstract class RecyclerAdapterBindingList<VB : ViewBinding, T> : RecyclerAdapterBinding<VB>() {

    private val _dataList = mutableListOf<T>()

    var dataList: List<T>
        get() = _dataList
        set(newDataList) {
            updateDataList(newDataList)
        }


    override fun getItemCount(): Int {
        return _dataList.size
    }

    private fun updateDataList(newDataList: List<T>) {
        val diffCallback = DefaultDiffUtil(_dataList, newDataList)
        val diffResult = DiffUtil.calculateDiff(diffCallback)
        _dataList.clear()
        _dataList.addAll(newDataList)
        diffResult.dispatchUpdatesTo(this)
    }

}