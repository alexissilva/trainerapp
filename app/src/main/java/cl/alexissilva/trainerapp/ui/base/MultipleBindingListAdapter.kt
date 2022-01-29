package cl.alexissilva.trainerapp.ui.base

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import androidx.viewbinding.ViewBinding

/**
 * List adapter able to handle multiples view bindings.
 * [E]: View types enum.
 * [T]: Data type.
 */
abstract class MultipleBindingListAdapter<E : Enum<E>, T> :
    ListAdapter<T, MultipleBindingViewHolder<T, *>>(DefaultDiffItemCallback<T>()) {

    private lateinit var _context: Context
    val context get() = _context

    abstract fun onCreateViewHolderByType(
        inflater: LayoutInflater,
        parent: ViewGroup,
        attachToParent: Boolean,
        viewType: E
    ): MultipleBindingViewHolder<T, *>

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): MultipleBindingViewHolder<T, *> {
        _context = parent.context
        val layoutInflater = LayoutInflater.from(parent.context)
        return onCreateViewHolderByType(layoutInflater, parent, false, enumValues[viewType])
    }

    /**
     * Array containing the constants of the enum, in the order they're declared: Enum<E>.values()
     */
    abstract val enumValues: Array<E>

    abstract fun getItemViewType(item: T, position: Int): E
    override fun getItemViewType(position: Int): Int {
        return getItemViewType(getItem(position), position).ordinal
    }


    override fun onBindViewHolder(holder: MultipleBindingViewHolder<T, *>, position: Int) {
        holder.onBind(getItem(position), position)
    }

    override fun onViewAttachedToWindow(holder: MultipleBindingViewHolder<T, *>) {
        holder.onAttachedToWindow()
    }

    override fun onViewDetachedFromWindow(holder: MultipleBindingViewHolder<T, *>) {
        holder.onDetachedFromWindow()
    }
}

abstract class MultipleBindingViewHolder<T, VB : ViewBinding>(val binding: VB) :
    RecyclerView.ViewHolder(binding.root) {
    abstract fun VB.onBind(item: T, position: Int)
    fun onBind(item: T, position: Int) {
        binding.onBind(item, position)
    }

    open fun VB.onAttachedToWindow() {}
    fun onAttachedToWindow() {
        binding.onAttachedToWindow()
    }

    open fun VB.onDetachedFromWindow() {}
    fun onDetachedFromWindow() {
        binding.onDetachedFromWindow()
    }
}

