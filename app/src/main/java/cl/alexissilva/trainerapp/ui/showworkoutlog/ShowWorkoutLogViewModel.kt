package cl.alexissilva.trainerapp.ui.showworkoutlog

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import cl.alexissilva.trainerapp.core.domain.WorkoutLog
import cl.alexissilva.trainerapp.core.usecases.GetWorkoutLog
import cl.alexissilva.trainerapp.utils.CoroutineContextProvider
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class ShowWorkoutLogViewModel @Inject constructor(
    private val contextProvider: CoroutineContextProvider,
    private val getWorkoutLog: GetWorkoutLog,
) : ViewModel() {

    private var _workoutLog = MutableStateFlow<WorkoutLog?>(null)
    val workoutLog: StateFlow<WorkoutLog?> = _workoutLog

    fun loadWorkoutLog(workoutLogId: String) = viewModelScope.launch {
        _workoutLog.value = withContext(contextProvider.IO) {
            getWorkoutLog(workoutLogId).firstOrNull()
        }
    }
}