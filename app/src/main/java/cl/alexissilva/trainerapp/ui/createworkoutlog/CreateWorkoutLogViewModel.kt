package cl.alexissilva.trainerapp.ui.createworkoutlog

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import cl.alexissilva.trainerapp.core.domain.ExerciseLog
import cl.alexissilva.trainerapp.core.domain.WorkoutLog
import cl.alexissilva.trainerapp.core.usecases.CreateDraftWorkoutLog
import cl.alexissilva.trainerapp.core.usecases.GetWorkoutLog
import cl.alexissilva.trainerapp.core.usecases.SaveWorkoutLog
import cl.alexissilva.trainerapp.utils.CoroutineContextProvider
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class CreateWorkoutLogViewModel @Inject constructor(
    private val contextProvider: CoroutineContextProvider,
    private val createDraftWorkoutLogUC: CreateDraftWorkoutLog,
    private val saveWorkoutLogUC: SaveWorkoutLog,
) : ViewModel() {

    private val _loading = MutableStateFlow(false)
    val loading: StateFlow<Boolean> = _loading

    private var _workoutLog = MutableStateFlow<WorkoutLog?>(null)
    val workoutLog: StateFlow<WorkoutLog?> = _workoutLog

    fun createCompleteDraftWorkoutLog(workoutId: String) = viewModelScope.launch {
        _loading.value = true
        _workoutLog.value = withContext(contextProvider.IO) {
            createDraftWorkoutLogUC(workoutId)
        }
        _loading.value = false
    }

    fun updateWorkoutLog(exerciseLogs: List<ExerciseLog>) = viewModelScope.launch {
        _loading.value = true
        withContext(contextProvider.IO) {
            workoutLog.value?.let {
                it.exerciseLogs = exerciseLogs
                saveWorkoutLogUC(it)
            }
        }
        _loading.value = false
    }
}