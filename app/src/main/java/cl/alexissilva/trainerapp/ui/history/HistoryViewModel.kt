package cl.alexissilva.trainerapp.ui.history

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import cl.alexissilva.trainerapp.usecases.GetPastWorkouts
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.stateIn
import javax.inject.Inject

@HiltViewModel
class HistoryViewModel @Inject constructor(
    private val getPastWorkouts: GetPastWorkouts,
) : ViewModel() {
    val pastWorkouts = getPastWorkouts().stateIn(
        viewModelScope, SharingStarted.Eagerly, emptyList()
    )
}