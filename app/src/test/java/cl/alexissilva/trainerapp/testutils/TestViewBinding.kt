package cl.alexissilva.trainerapp.testutils

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.viewbinding.ViewBinding

class TestViewBinding(val context: Context) : ViewBinding {
    override fun getRoot() = View(context)

    companion object {
        private fun inflateViewBinding(
            inflater: LayoutInflater, parent: ViewGroup?, attachToParent: Boolean
        ) = TestViewBinding(parent?.context ?: inflater.context)

        val inflate = ::inflateViewBinding
    }
}
