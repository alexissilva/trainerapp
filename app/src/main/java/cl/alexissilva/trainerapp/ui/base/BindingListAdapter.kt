package cl.alexissilva.trainerapp.ui.base

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import androidx.viewbinding.ViewBinding

abstract class BindingListAdapter<T, VB : ViewBinding>
    : ListAdapter<T, ViewHolderBinding<VB>>(DefaultDiffItemCallback<T>()) {

    private lateinit var _context: Context
    val context get() = _context

    abstract val inflateBinding: (LayoutInflater, ViewGroup, Boolean) -> VB

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolderBinding<VB> {
        _context = parent.context
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding = inflateBinding(layoutInflater, parent, false)
        return ViewHolderBinding(binding)
    }

    override fun onBindViewHolder(holder: ViewHolderBinding<VB>, position: Int) {
        holder.binding.onBind(getItem(position))
    }

    abstract fun VB.onBind(item: T)

}

class ViewHolderBinding<VB : ViewBinding>(val binding: VB) : RecyclerView.ViewHolder(binding.root)


class DefaultDiffItemCallback<T> : DiffUtil.ItemCallback<T>() {
    override fun areItemsTheSame(oldItem: T, newItem: T): Boolean {
        return oldItem === newItem
    }

    @SuppressLint("DiffUtilEquals")
    override fun areContentsTheSame(oldItem: T, newItem: T): Boolean {
        return oldItem === newItem
    }
}
