package cl.alexissilva.trainerapp.testutils

import android.content.Context
import android.view.View
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import androidx.recyclerview.widget.RecyclerView

object AdapterTestUtils {

    fun <RA : RecyclerView.Adapter<VH>, VH : RecyclerView.ViewHolder> getBoundViewHolder(
        context: Context,
        adapter: RA,
        @LayoutRes layoutRes: Int,
        index: Int
    ): VH {
        val rowView = View.inflate(context, layoutRes, null) as ViewGroup
        val viewHolder = adapter.createViewHolder(rowView, 0)
        adapter.bindViewHolder(viewHolder, index)
        return viewHolder
    }
}