package cl.alexissilva.trainerapp.ui.history

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import cl.alexissilva.trainerapp.core.domain.WorkoutLog
import cl.alexissilva.trainerapp.core.domain.WorkoutStatus
import cl.alexissilva.trainerapp.databinding.FragmentHistoryBinding
import cl.alexissilva.trainerapp.ui.adapters.LogsWithWorkoutAdapter
import cl.alexissilva.trainerapp.ui.base.BindingFragment
import cl.alexissilva.trainerapp.utils.extensions.launchOnLifecycle
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HistoryFragment(
    private var _viewModel: HistoryViewModel? = null
) : BindingFragment<FragmentHistoryBinding>() {

    private val viewModel get() = _viewModel!!
    private val adapter by lazy { LogsWithWorkoutAdapter(::onItemClick) }

    override val inflateBinding: (LayoutInflater, ViewGroup?, Boolean) -> FragmentHistoryBinding
        get() = FragmentHistoryBinding::inflate

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _viewModel = _viewModel ?: ViewModelProvider(this).get(HistoryViewModel::class.java)
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
            viewModel.workoutLogs.collect { logs ->
                binding.doneTextView.text =
                    logs.count { it.status == WorkoutStatus.DONE }.toString()
                binding.skippedTextView.text =
                    logs.count { it.status == WorkoutStatus.SKIPPED }.toString()

                binding.noPastWorkoutsTextView.visibility =
                    if (logs.isEmpty()) View.VISIBLE else View.INVISIBLE

                adapter.submitList(logs)
            }
        }
    }

    private fun onItemClick(log: WorkoutLog) {
        val toWorkoutLogActivity = HistoryFragmentDirections.actionHistoryFragmentToShowWorkoutLogActivity(log.id)
        findNavController().navigate(toWorkoutLogActivity)
    }


}