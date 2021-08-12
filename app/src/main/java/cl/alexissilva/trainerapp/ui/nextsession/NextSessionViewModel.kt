package cl.alexissilva.trainerapp.ui.nextsession

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import cl.alexissilva.trainerapp.data.RemoteResult
import cl.alexissilva.trainerapp.domain.Session
import cl.alexissilva.trainerapp.domain.SessionStatus
import cl.alexissilva.trainerapp.usecases.DownloadSessions
import cl.alexissilva.trainerapp.usecases.GetNextSession
import cl.alexissilva.trainerapp.usecases.UpdateSessionStatus
import cl.alexissilva.trainerapp.utils.CoroutineContextProvider
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject


@HiltViewModel
class NextSessionViewModel @Inject constructor(
    private val downloadSessionsUC: DownloadSessions,
    private val getNextSession: GetNextSession,
    private val updateSessionStatusUC: UpdateSessionStatus,
    private val contextProvider: CoroutineContextProvider,
) : ViewModel() {

    val session: StateFlow<Session?> = getNextSession()
        .stateIn(viewModelScope, SharingStarted.Eagerly, null)

    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading

    private val _errorMessage = MutableStateFlow<String?>(null)
    val errorMessage: StateFlow<String?> = _errorMessage


    fun downloadSessions() = viewModelScope.launch {
        _isLoading.value = true
        _errorMessage.value = null
        val remoteResult = withContext(contextProvider.IO) { downloadSessionsUC() }
        if (remoteResult is RemoteResult.Error) {
            _errorMessage.value = remoteResult.message
        }
        _isLoading.value = false
    }

    fun updateSessionStatus(status: SessionStatus) = viewModelScope.launch {
        session.value?.let {
            withContext(contextProvider.IO) {
                updateSessionStatusUC(it, status)
            }
        }
    }

}