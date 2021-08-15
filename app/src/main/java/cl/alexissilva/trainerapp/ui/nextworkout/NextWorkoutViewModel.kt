package cl.alexissilva.trainerapp.ui.nextworkout

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import cl.alexissilva.trainerapp.data.RemoteResult
import cl.alexissilva.trainerapp.domain.Workout
import cl.alexissilva.trainerapp.domain.WorkoutStatus
import cl.alexissilva.trainerapp.usecases.DownloadWorkouts
import cl.alexissilva.trainerapp.usecases.GetNextWorkout
import cl.alexissilva.trainerapp.usecases.UpdateWorkoutStatus
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
    private val getNextWorkout: GetNextWorkout,
    private val updateWorkoutStatusUC: UpdateWorkoutStatus,
    private val contextProvider: CoroutineContextProvider,
) : ViewModel() {

    val workout: StateFlow<Workout?> = getNextWorkout()
        .stateIn(viewModelScope, SharingStarted.Eagerly, null)

    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading

    private val _errorMessage = MutableStateFlow<String?>(null)
    val errorMessage: StateFlow<String?> = _errorMessage


    fun downloadWorkouts() = viewModelScope.launch {
        _isLoading.value = true
        _errorMessage.value = null
        val remoteResult = withContext(contextProvider.IO) { downloadWorkoutsUC() }
        if (remoteResult is RemoteResult.Error) {
            _errorMessage.value = remoteResult.message
        }
        _isLoading.value = false
    }

    fun updateWorkoutStatus(status: WorkoutStatus) = viewModelScope.launch {
        workout.value?.let {
            withContext(contextProvider.IO) {
                updateWorkoutStatusUC(it, status)
            }
        }
    }

}