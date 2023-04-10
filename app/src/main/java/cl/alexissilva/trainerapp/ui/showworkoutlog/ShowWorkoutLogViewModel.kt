package cl.alexissilva.trainerapp.ui.showworkoutlog

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import cl.alexissilva.trainerapp.core.usecases.GetWorkoutLogAsStringPairs
import cl.alexissilva.trainerapp.utils.CoroutineContextProvider
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class ShowWorkoutLogViewModel @Inject constructor(
    private val contextProvider: CoroutineContextProvider,
    private val getWorkoutLogAsStringPairs: GetWorkoutLogAsStringPairs,
) : ViewModel() {

    private var _workoutLogAsStringPairs = MutableStateFlow<List<Pair<String, List<String>>>?>(null)
    val workoutLogAsStringPairs: StateFlow<List<Pair<String, List<String>>>?> = _workoutLogAsStringPairs

    fun loadWorkoutLog(workoutLogId: String) = viewModelScope.launch {
        _workoutLogAsStringPairs.value = withContext(contextProvider.IO) {
            getWorkoutLogAsStringPairs.invoke(workoutLogId)
        }
    }
}