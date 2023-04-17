package cl.alexissilva.trainerapp.ui.workoutdetails

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import cl.alexissilva.trainerapp.core.domain.Workout
import cl.alexissilva.trainerapp.core.usecases.GetLocalWorkout
import cl.alexissilva.trainerapp.utils.CoroutineContextProvider
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class WorkoutDetailsViewModel @Inject constructor(
    private val contextProvider: CoroutineContextProvider,
    private val getLocalWorkout: GetLocalWorkout,
) : ViewModel() {

    private var _workout = MutableStateFlow<Workout?>(null)
    val workout : StateFlow<Workout?> = _workout

    fun loadWorkout(workoutId: String){
        viewModelScope.launch {
            _workout.value = withContext(contextProvider.IO){
                getLocalWorkout(workoutId).firstOrNull()
            }
        }
    }
}