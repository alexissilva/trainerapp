package cl.alexissilva.trainerapp.ui.createworkoutlog

import android.view.LayoutInflater
import androidx.core.view.isVisible
import androidx.lifecycle.Lifecycle
import androidx.navigation.navArgs
import cl.alexissilva.trainerapp.core.domain.WorkoutStatus
import cl.alexissilva.trainerapp.databinding.ActivityWorkoutLogBinding
import cl.alexissilva.trainerapp.ui.adapters.exerciselogs.ExerciseLogsAdapter
import cl.alexissilva.trainerapp.ui.base.ActivityWithViewModelTesting
import cl.alexissilva.trainerapp.utils.extensions.launchOnLifecycle
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class CreateWorkoutLogActivity :
    ActivityWithViewModelTesting<CreateWorkoutLogViewModel, ActivityWorkoutLogBinding>() {

    private val args: CreateWorkoutLogActivityArgs by navArgs()
    private var viewModel: CreateWorkoutLogViewModel? = null

    override val inflateBinding: (LayoutInflater) -> ActivityWorkoutLogBinding
        get() = ActivityWorkoutLogBinding::inflate

    @Inject
    lateinit var adapter: ExerciseLogsAdapter

    override val viewModelClass: Class<CreateWorkoutLogViewModel>
        get() = CreateWorkoutLogViewModel::class.java

    override fun onViewModelCreated(viewModel: CreateWorkoutLogViewModel) {
        this.viewModel = viewModel
        setupScreen()
    }

    private fun setupScreen() {
        viewModel?.createCompleteDraftWorkoutLog(args.workoutId)
        showWorkoutLogDetails()
        setupProgressBar()
        saveButtonSetup()
    }

    private fun showWorkoutLogDetails() {
        binding.logsRecyclerView.adapter = adapter
        launchOnLifecycle(Lifecycle.State.STARTED) {
            viewModel?.workoutLog?.collect { log ->
                if (log != null) {
                    with(binding) {
                        logsRecyclerView.isVisible = log.status != WorkoutStatus.SKIPPED
                        workoutSkippedTextView.isVisible = log.status == WorkoutStatus.SKIPPED
                    }
                    if (log.status != WorkoutStatus.SKIPPED) {
                        adapter.setExerciseLogs(log.exerciseLogs)
                    }
                }
            }
        }
    }

    private fun setupProgressBar() {
        launchOnLifecycle(Lifecycle.State.STARTED) {
            viewModel?.loading?.collect {
                binding.progressBar.isVisible = it
            }
        }
    }

    private fun saveButtonSetup() {
        binding.saveButton.setOnClickListener {
            val modifiedLogs = adapter.getExerciseLogs()
            viewModel?.updateWorkoutLog(modifiedLogs)?.invokeOnCompletion {
                finish()
            }
        }
    }
}