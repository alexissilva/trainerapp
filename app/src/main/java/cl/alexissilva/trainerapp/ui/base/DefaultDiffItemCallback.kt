package cl.alexissilva.trainerapp.ui.base

import android.annotation.SuppressLint
import androidx.recyclerview.widget.DiffUtil


class DefaultDiffItemCallback<T> : DiffUtil.ItemCallback<T>() {
    override fun areItemsTheSame(oldItem: T, newItem: T): Boolean {
        return oldItem === newItem
    }

    @SuppressLint("DiffUtilEquals")
    override fun areContentsTheSame(oldItem: T, newItem: T): Boolean {
        return oldItem === newItem
    }
}
