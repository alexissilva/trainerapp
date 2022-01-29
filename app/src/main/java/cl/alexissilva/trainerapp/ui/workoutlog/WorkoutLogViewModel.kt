package cl.alexissilva.trainerapp.ui.workoutlog

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import cl.alexissilva.trainerapp.core.domain.WorkoutLog
import cl.alexissilva.trainerapp.core.usecases.CreateDraftWorkoutLog
import cl.alexissilva.trainerapp.core.usecases.SaveWorkoutLog
import cl.alexissilva.trainerapp.utils.CoroutineContextProvider
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class WorkoutLogViewModel @Inject constructor(
    private val contextProvider: CoroutineContextProvider,
    private val createDraftWorkoutLogUC: CreateDraftWorkoutLog,
    private val saveWorkoutLogUC: SaveWorkoutLog,
) : ViewModel() {

    private val _loading = MutableStateFlow(false)
    val loading: StateFlow<Boolean> = _loading

    //TODO get workoutlog from repository
    fun getWorkoutLog(workoutLogId: String): WorkoutLog = WorkoutLog(workoutLogId, "")

    suspend fun createCompleteDraftWorkoutLog(workoutId: String): WorkoutLog? {
        _loading.value = true
        val draft = withContext(contextProvider.IO) {
            createDraftWorkoutLogUC(workoutId)
        }
        _loading.value = false
        return draft
    }

    fun saveWorkoutLog(workoutLog: WorkoutLog) = viewModelScope.launch {
        _loading.value = true
        withContext(contextProvider.IO) {
            saveWorkoutLogUC(workoutLog)
        }
        _loading.value = false
    }

}