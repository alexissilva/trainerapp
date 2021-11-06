package cl.alexissilva.trainerapp.ui.base

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import androidx.viewbinding.ViewBinding

abstract class RecyclerAdapterBinding<VB : ViewBinding> :
    RecyclerView.Adapter<RecyclerAdapterBinding.ViewHolderBinding<VB>>() {

    private lateinit var _context: Context
    val context get() = _context

    abstract val inflateBinding: (LayoutInflater, ViewGroup, Boolean) -> VB

    class ViewHolderBinding<VB : ViewBinding>(val binding: VB) :
        RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolderBinding<VB> {
        _context = parent.context
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding = inflateBinding(layoutInflater, parent, false)
        return ViewHolderBinding(binding)
    }
}