package cl.alexissilva.trainerapp.ui.workouts

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import cl.alexissilva.trainerapp.databinding.FragmentWorkoutsBinding
import cl.alexissilva.trainerapp.domain.Workout
import cl.alexissilva.trainerapp.ui.adapters.WorkoutsAdapter
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect

@AndroidEntryPoint
class WorkoutsFragment(
    private var _viewModel: WorkoutsViewModel? = null
) : Fragment() {
    private var _binding: FragmentWorkoutsBinding? = null
    private val binding get() = _binding!!
    private val viewModel get() = _viewModel!!
    private val adapter by lazy { WorkoutsAdapter(navigateToDetails) }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentWorkoutsBinding.inflate(inflater, container, false)
        _viewModel = _viewModel ?: ViewModelProvider(this).get(WorkoutsViewModel::class.java)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupRecyclerView()
        collectState()
    }

    private fun setupRecyclerView() {
        binding.workoutsRecyclerView.layoutManager = LinearLayoutManager(requireContext())
        binding.workoutsRecyclerView.adapter = adapter
    }

    private fun collectState() {
        lifecycleScope.launchWhenCreated {
            viewModel.workouts.collect {
                adapter.dataList = it
                binding.noWorkoutsTextView.visibility =
                    if (it.isEmpty()) View.VISIBLE else View.INVISIBLE
            }
        }
    }

    private val navigateToDetails = { workout: Workout ->
        val action =
            WorkoutsFragmentDirections.actionWorkoutsFragmentToWorkoutDetailsActivity(workout.id)
        findNavController().navigate(action)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}