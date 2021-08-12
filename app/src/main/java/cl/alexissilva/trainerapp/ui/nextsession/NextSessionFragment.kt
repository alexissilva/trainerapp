package cl.alexissilva.trainerapp.ui.nextsession

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.VisibleForTesting
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import cl.alexissilva.trainerapp.databinding.FragmentNextSessionBinding
import cl.alexissilva.trainerapp.domain.Session
import cl.alexissilva.trainerapp.domain.SessionStatus
import cl.alexissilva.trainerapp.ui.adapters.ExercisesAdapter
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect
import java.time.LocalDate
import java.time.format.DateTimeFormatter

@AndroidEntryPoint
class NextSessionFragment(
    private var _viewModel: NextSessionViewModel? = null
) : Fragment() {
    private val viewModel get() = _viewModel!!
    private var _binding: FragmentNextSessionBinding? = null
    private val binding get() = _binding!!
    private val exercisesAdapter by lazy { ExercisesAdapter(requireContext()) }

    companion object {
        const val DATE_FORMAT = "EEEE, d MMMM"
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        _binding = FragmentNextSessionBinding.inflate(inflater, container, false)
        _viewModel = _viewModel ?: ViewModelProvider(this).get(NextSessionViewModel::class.java)
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
            viewModel.updateSessionStatus(SessionStatus.DONE)
        }
        binding.skipButton.setOnClickListener {
            viewModel.updateSessionStatus(SessionStatus.SKIPPED)
        }
    }

    private fun setupSwipeToRefresh() {
        binding.swipeRefreshLayout.setOnRefreshListener {
            viewModel.downloadSessions()
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
            viewModel.session.collect {
                if (it != null) {
                    binding.sessionNameTextView.text = it.name
                    binding.dateTextView.text = formatDate(it.date)
                    binding.exercisesTextView.text = it.exercises.size.toString()
                    binding.setsTextView.text = countSets(it).toString()
                    binding.repsTextView.text = countReps(it).toString()
                    exercisesAdapter.setExerciseList(it.exercises)
                    binding.nextSessionLayout.visibility = View.VISIBLE
                    binding.noSessionTextView.visibility = View.INVISIBLE
                } else {
                    binding.nextSessionLayout.visibility = View.INVISIBLE
                    binding.noSessionTextView.visibility = View.VISIBLE
                }
            }
        }
    }

    private fun showSnackbar(message: String) {
        Snackbar.make(binding.swipeRefreshLayout, message, Snackbar.LENGTH_LONG).show()
    }

    private fun formatDate(date: LocalDate): String {
        return date.format(DateTimeFormatter.ofPattern(DATE_FORMAT))
    }

    private fun countSets(session: Session): Int {
        return session.exercises.fold(0, { acc, exercise ->
            val exerciseSets = exercise.groupSets.fold(0,
                { accPerExercise, groupSet -> accPerExercise + groupSet.sets }
            )
            return@fold acc + exerciseSets
        })
    }

    private fun countReps(session: Session): Int {
        return session.exercises.fold(0, { acc, exercise ->
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