package cl.alexissilva.trainerapp.ui.nextworkout

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import cl.alexissilva.trainerapp.core.data.RemoteResult
import cl.alexissilva.trainerapp.core.domain.Workout
import cl.alexissilva.trainerapp.core.usecases.DeleteWorkoutLogs
import cl.alexissilva.trainerapp.core.usecases.DownloadWorkouts
import cl.alexissilva.trainerapp.core.usecases.GetNextWorkout
import cl.alexissilva.trainerapp.core.usecases.SkipWorkout
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
class NextWorkoutViewModel @Inject constructor(
    private val downloadWorkoutsUC: DownloadWorkouts,
    private val deleteWorkoutLogs: DeleteWorkoutLogs,
    private val getNextWorkout: GetNextWorkout,
    private val skipWorkoutUC: SkipWorkout,
    private val contextProvider: CoroutineContextProvider,
) : ViewModel() {

    val workout: StateFlow<Workout?> = getNextWorkout()
        .stateIn(viewModelScope, SharingStarted.Eagerly, null)

    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading

    private val _errorMessage = MutableStateFlow<String?>(null)
    val errorMessage: StateFlow<String?> = _errorMessage


    fun syncWorkouts() = viewModelScope.launch {
        _isLoading.value = true
        _errorMessage.value = null
        val remoteResult = withContext(contextProvider.IO) {
            //TODO remove deleteWorkoutLogs
            deleteWorkoutLogs()
            downloadWorkoutsUC()
        }
        if (remoteResult is RemoteResult.Error) {
            _errorMessage.value = remoteResult.message
        }
        _isLoading.value = false
    }

    fun skipWorkout() = viewModelScope.launch {
        workout.value?.let {
            withContext(contextProvider.IO) {
                skipWorkoutUC(it)
            }
        }
    }

}