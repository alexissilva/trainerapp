package cl.alexissilva.trainerapp.ui.workouts

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import cl.alexissilva.trainerapp.usecases.GetUpcomingWorkouts
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.stateIn
import javax.inject.Inject

@HiltViewModel
class WorkoutsViewModel @Inject constructor(
    private val getUpcomingWorkouts: GetUpcomingWorkouts
) : ViewModel() {

    val workouts =
        getUpcomingWorkouts().stateIn(viewModelScope, SharingStarted.Eagerly, emptyList())
}
