package cl.alexissilva.trainerapp.ui.history

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import cl.alexissilva.trainerapp.usecases.GetPastSessions
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.stateIn
import javax.inject.Inject

@HiltViewModel
class HistoryViewModel @Inject constructor(
    private val getPastSessions: GetPastSessions,
) : ViewModel() {
    val pastSessions = getPastSessions().stateIn(
        viewModelScope, SharingStarted.Eagerly, emptyList()
    )
}