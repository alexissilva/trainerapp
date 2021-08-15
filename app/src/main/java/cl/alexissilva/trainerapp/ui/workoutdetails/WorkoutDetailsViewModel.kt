package cl.alexissilva.trainerapp.ui.workoutdetails

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import cl.alexissilva.trainerapp.usecases.GetLocalWorkout
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.stateIn
import javax.inject.Inject

@HiltViewModel
class WorkoutDetailsViewModel @Inject constructor(
    private val getLocalWorkout: GetLocalWorkout,
    private val state: SavedStateHandle
) : ViewModel() {

    //FIXME handle null case (?)
    val workout = getLocalWorkout(state.get<String>("workoutId")!!)
        .stateIn(viewModelScope, SharingStarted.Eagerly, null)

}