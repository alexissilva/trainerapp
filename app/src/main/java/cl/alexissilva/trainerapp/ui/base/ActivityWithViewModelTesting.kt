package cl.alexissilva.trainerapp.ui.base

import android.os.Bundle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.viewbinding.ViewBinding

abstract class ActivityWithViewModelTesting<VM : ViewModel, VB : ViewBinding> :
    BindingActivity<VB>() {

    companion object {
        const val IS_BEING_TESTED_PARAM = "isBeingTested"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val isBeingTested = intent.getBooleanExtra(IS_BEING_TESTED_PARAM, false)
        if (!isBeingTested) {
            setupViewModel()
        }
    }


    internal fun setupViewModel(testingViewModel: VM? = null) {
        val viewModel = testingViewModel ?: ViewModelProvider(this)[viewModelClass]
        onViewModelCreated(viewModel)
    }

    abstract val viewModelClass: Class<VM>

    abstract fun onViewModelCreated(viewModel: VM)
}