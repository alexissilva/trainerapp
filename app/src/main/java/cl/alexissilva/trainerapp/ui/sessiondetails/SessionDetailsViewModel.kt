package cl.alexissilva.trainerapp.ui.sessiondetails

import android.util.Log
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import cl.alexissilva.trainerapp.domain.Session
import cl.alexissilva.trainerapp.usecases.GetLocalSession
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import javax.inject.Inject

@HiltViewModel
class SessionDetailsViewModel @Inject constructor(
    private val getLocalSession: GetLocalSession,
    private val state: SavedStateHandle
) : ViewModel() {

    //FIXME handle null case (?)
    val session = getLocalSession(state.get<String>("sessionId")!!)
        .stateIn(viewModelScope, SharingStarted.Eagerly, null)

}