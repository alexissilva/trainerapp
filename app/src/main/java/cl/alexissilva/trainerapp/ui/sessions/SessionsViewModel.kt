package cl.alexissilva.trainerapp.ui.sessions

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import cl.alexissilva.trainerapp.data.SessionRepository
import cl.alexissilva.trainerapp.usecases.GetUpcomingSessions
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.stateIn
import javax.inject.Inject

@HiltViewModel
class SessionsViewModel @Inject constructor(
    private val getUpcomingSessions: GetUpcomingSessions
) : ViewModel() {

    val sessions = getUpcomingSessions().stateIn(viewModelScope, SharingStarted.Eagerly, emptyList())
}