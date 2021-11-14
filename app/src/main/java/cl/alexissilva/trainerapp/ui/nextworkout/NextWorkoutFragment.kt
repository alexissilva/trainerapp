package cl.alexissilva.trainerapp.ui.nextworkout

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import cl.alexissilva.trainerapp.R
import cl.alexissilva.trainerapp.databinding.FragmentNextWorkoutBinding
import cl.alexissilva.trainerapp.core.domain.Workout
import cl.alexissilva.trainerapp.core.domain.WorkoutStatus
import cl.alexissilva.trainerapp.ui.adapters.ExercisesAdapter
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect

@AndroidEntryPoint
class NextWorkoutFragment(
    private var _viewModel: NextWorkoutViewModel? = null
) : Fragment() {
    private val viewModel get() = _viewModel!!
    private var _binding: FragmentNextWorkoutBinding? = null
    private val binding get() = _binding!!
    private val exercisesAdapter by lazy { ExercisesAdapter(requireContext()) }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        _binding = FragmentNextWorkoutBinding.inflate(inflater, container, false)
        _viewModel = _viewModel ?: ViewModelProvider(this).get(NextWorkoutViewModel::class.java)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupButtons()
        setupSwipeToRefresh()
        setupRecyclerView()
        collectState()
    }

    private fun setupButtons() {
        binding.doneButton.setOnClickListener {
            viewModel.updateWorkoutStatus(WorkoutStatus.DONE)
        }
        binding.skipButton.setOnClickListener {
            viewModel.updateWorkoutStatus(WorkoutStatus.SKIPPED)
        }
    }

    private fun setupSwipeToRefresh() {
        binding.swipeRefreshLayout.setOnRefreshListener {
            viewModel.syncWorkouts()
        }
    }

    private fun setupRecyclerView() {
        binding.exercisesRecyclerView.adapter = exercisesAdapter
        binding.exercisesRecyclerView.layoutManager = LinearLayoutManager(requireContext())
    }


    private fun collectState() {
        lifecycleScope.launchWhenCreated {
            viewModel.isLoading.collect {
                binding.swipeRefreshLayout.isRefreshing = it
            }
        }
        lifecycleScope.launchWhenCreated {
            viewModel.errorMessage.collect {
                it?.let { showSnackbar(it) }
            }
        }
        lifecycleScope.launchWhenCreated {
            viewModel.workout.collect {
                if (it != null) {
                    binding.workoutNameTextView.text = it.name
                    binding.dayTextView.text = getString(R.string.workout_day, it.day)
                    binding.exercisesTextView.text = it.exercises.size.toString()
                    binding.setsTextView.text = countSets(it).toString()
                    binding.repsTextView.text = countReps(it).toString()
                    exercisesAdapter.setExerciseList(it.exercises)
                    binding.nextWorkoutLayout.visibility = View.VISIBLE
                    binding.noWorkoutTextView.visibility = View.INVISIBLE
                } else {
                    binding.nextWorkoutLayout.visibility = View.INVISIBLE
                    binding.noWorkoutTextView.visibility = View.VISIBLE
                }
            }
        }
    }

    private fun showSnackbar(message: String) {
        Snackbar.make(binding.swipeRefreshLayout, message, Snackbar.LENGTH_LONG).show()
    }

    private fun countSets(workout: Workout): Int {
        return workout.exercises.fold(0, { acc, exercise ->
            val exerciseSets = exercise.groupSets.fold(0,
                { accPerExercise, groupSet -> accPerExercise + groupSet.sets }
            )
            return@fold acc + exerciseSets
        })
    }

    private fun countReps(workout: Workout): Int {
        return workout.exercises.fold(0, { acc, exercise ->
            val exerciseReps = exercise.groupSets.fold(0,
                { accPerExercise, groupSet -> accPerExercise + groupSet.sets * groupSet.reps }
            )
            return@fold acc + exerciseReps
        })
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}