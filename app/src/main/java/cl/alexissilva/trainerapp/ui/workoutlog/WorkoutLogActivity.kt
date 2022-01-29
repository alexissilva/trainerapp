package cl.alexissilva.trainerapp.ui.workoutlog

import android.os.Bundle
import android.view.LayoutInflater
import androidx.core.view.isVisible
import androidx.lifecycle.lifecycleScope
import androidx.navigation.navArgs
import cl.alexissilva.trainerapp.core.domain.WorkoutLog
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

    override val inflateBinding: (LayoutInflater) -> ActivityWorkoutLogBinding
        get() = ActivityWorkoutLogBinding::inflate

    //TODO move to VM
    private lateinit var workoutLog: WorkoutLog

    @Inject
    lateinit var adapter: ExerciseLogsAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        saveButtonSetup()
    }

    override val viewModelClass: Class<WorkoutLogViewModel>
        get() = WorkoutLogViewModel::class.java

    override fun onViewModelCreated(viewModel: WorkoutLogViewModel) {
        this.viewModel = viewModel
        setupAdapter()
        setupProgressBar()
    }

    private fun setupAdapter() {
        binding.logsRecyclerView.adapter = adapter
        //TODO replace for repeatOn
        lifecycleScope.launchWhenStarted {
            // TODO add a loading while get data from database
            val logTemp = when {
                args.workoutLogId != null -> viewModel?.getWorkoutLog(args.workoutLogId!!)
                args.workoutId != null -> viewModel?.createCompleteDraftWorkoutLog(args.workoutId!!)
                else -> null
            }
            //TODO handle null case
            workoutLog = requireNotNull(logTemp)
            adapter.setExerciseLogs(workoutLog.exerciseLogs)
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
        binding.saveButton.setOnClickListener {
            val modifiedLogs = adapter.getExerciseLogs()
            workoutLog.exerciseLogs = modifiedLogs
            viewModel?.saveWorkoutLog(workoutLog)?.invokeOnCompletion {
                finish()
            }
        }
    }
}