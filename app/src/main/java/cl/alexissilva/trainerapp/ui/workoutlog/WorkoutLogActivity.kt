package cl.alexissilva.trainerapp.ui.workoutlog

import android.view.LayoutInflater
import androidx.core.view.isVisible
import androidx.lifecycle.lifecycleScope
import androidx.navigation.navArgs
import cl.alexissilva.trainerapp.core.domain.WorkoutStatus
import cl.alexissilva.trainerapp.databinding.ActivityWorkoutLogBinding
import cl.alexissilva.trainerapp.ui.adapters.exerciselogs.ExerciseLogsAdapter
import cl.alexissilva.trainerapp.ui.base.ActivityWithViewModelTesting
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect
import javax.inject.Inject

@AndroidEntryPoint
class WorkoutLogActivity :
    ActivityWithViewModelTesting<WorkoutLogViewModel, ActivityWorkoutLogBinding>() {

    private val args: WorkoutLogActivityArgs by navArgs()
    private var viewModel: WorkoutLogViewModel? = null
    private var readOnly: Boolean = false

    override val inflateBinding: (LayoutInflater) -> ActivityWorkoutLogBinding
        get() = ActivityWorkoutLogBinding::inflate

    @Inject
    lateinit var adapter: ExerciseLogsAdapter

    override val viewModelClass: Class<WorkoutLogViewModel>
        get() = WorkoutLogViewModel::class.java

    override fun onViewModelCreated(viewModel: WorkoutLogViewModel) {
        this.viewModel = viewModel
        setupScreen()
    }

    private fun setupScreen() {
        when {
            args.workoutLogId != null -> {
                readOnly = true
                viewModel?.loadWorkoutLog(args.workoutLogId!!)
            }
            args.workoutId != null -> viewModel?.createCompleteDraftWorkoutLog(args.workoutId!!)
            else -> {
                //TODO Handle else case - Show error
            }
        }
        showWorkoutLogDetails()
        setupProgressBar()
        saveButtonSetup()
    }

    private fun showWorkoutLogDetails() {
        binding.logsRecyclerView.adapter = adapter
        lifecycleScope.launchWhenStarted {
            viewModel?.workoutLog?.collect { log ->
                if (log != null) {
                    with(binding) {
                        logsRecyclerView.isVisible = log.status != WorkoutStatus.SKIPPED
                        workoutSkippedTextView.isVisible = log.status == WorkoutStatus.SKIPPED
                    }
                    if (log.status != WorkoutStatus.SKIPPED) {
                        adapter.setExerciseLogs(log.exerciseLogs, readOnly)
                    }
                }
            }
        }
    }

    private fun setupProgressBar() {
        lifecycleScope.launchWhenStarted {
            viewModel?.loading?.collect {
                binding.progressBar.isVisible = it
            }
        }
    }

    private fun saveButtonSetup() {
        binding.saveButton.isVisible = !readOnly
        binding.saveButton.setOnClickListener {
            val modifiedLogs = adapter.getExerciseLogs()
            viewModel?.updateWorkoutLog(modifiedLogs)?.invokeOnCompletion {
                finish()
            }
        }
    }
}