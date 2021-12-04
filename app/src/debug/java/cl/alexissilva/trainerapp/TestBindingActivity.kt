package cl.alexissilva.trainerapp

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import androidx.viewbinding.ViewBinding
import cl.alexissilva.trainerapp.ui.base.BindingActivity

class TestBindingActivity : BindingActivity<TestActivityViewBinding>() {

    override val inflateBinding: (LayoutInflater) -> TestActivityViewBinding
        get() = TestActivityViewBinding.inflate

    val bindingPublic
        get() = try {
            binding
        } catch (e: NullPointerException) {
            null
        }

}

class TestActivityViewBinding(val context: Context) : ViewBinding {
    override fun getRoot() = View(context)

    companion object {
        private fun inflateViewBinding(inflater: LayoutInflater) =
            TestActivityViewBinding(inflater.context)

        val inflate = ::inflateViewBinding
    }
}