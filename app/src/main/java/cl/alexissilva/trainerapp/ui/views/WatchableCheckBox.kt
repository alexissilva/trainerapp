package cl.alexissilva.trainerapp.ui.views

import android.content.Context
import android.util.AttributeSet
import android.widget.CompoundButton
import android.widget.CompoundButton.OnCheckedChangeListener
import androidx.appcompat.widget.AppCompatCheckBox

/**
 * Checkbox with a customize listener. Useful in RecyclerView.
 * - Call [startWatching]/[stopWatching] on onViewAttachedToWindow/onViewDetachedFromWindow.
 * - Change onCheckedChange in onBindViewHolder.
 */
class WatchableCheckBox @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null
) : AppCompatCheckBox(context, attrs) {

    var onCheckedChange: ((checkbox: CompoundButton?, isChecked: Boolean) -> Unit)? = null

    private val listener = OnCheckedChangeListener { checkbox, isChecked ->
        onCheckedChange?.invoke(checkbox, isChecked)
    }

    fun startWatching() {
        setOnCheckedChangeListener(listener)
    }

    fun stopWatching() {
        removeTextChangedListener(null)

    }
}