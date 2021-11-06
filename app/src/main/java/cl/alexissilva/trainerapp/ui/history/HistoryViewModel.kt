package cl.alexissilva.trainerapp.ui.history

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import cl.alexissilva.trainerapp.usecases.GetWorkoutLogsWithWorkout
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.stateIn
import javax.inject.Inject

@HiltViewModel
class HistoryViewModel @Inject constructor(
    private val getWorkoutLogsWithWorkout: GetWorkoutLogsWithWorkout,
) : ViewModel() {
    val workoutLogs = getWorkoutLogsWithWorkout().stateIn(
        viewModelScope, SharingStarted.Eagerly, emptyList()
    )
}