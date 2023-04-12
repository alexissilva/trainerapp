package cl.alexissilva.trainerapp.ui.workouts

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import cl.alexissilva.trainerapp.core.domain.Workout
import cl.alexissilva.trainerapp.databinding.FragmentWorkoutsBinding
import cl.alexissilva.trainerapp.ui.adapters.WorkoutsAdapter
import cl.alexissilva.trainerapp.ui.base.BindingFragment
import cl.alexissilva.trainerapp.utils.extensions.launchOnLifecycle
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class WorkoutsFragment(
    private var _viewModel: WorkoutsViewModel? = null
) : BindingFragment<FragmentWorkoutsBinding>() {

    private val viewModel get() = _viewModel!!
    private val adapter by lazy { WorkoutsAdapter(navigateToDetails) }

    override val inflateBinding: (LayoutInflater, ViewGroup?, Boolean) -> FragmentWorkoutsBinding
        get() = FragmentWorkoutsBinding::inflate

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _viewModel = _viewModel ?: ViewModelProvider(this).get(WorkoutsViewModel::class.java)
        return super.onCreateView(inflater, container, savedInstanceState)
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
        launchOnLifecycle(Lifecycle.State.CREATED) {
            viewModel.workouts.collect {
                adapter.submitList(it)
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

}