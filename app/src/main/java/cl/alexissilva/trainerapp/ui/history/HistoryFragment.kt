package cl.alexissilva.trainerapp.ui.history

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import cl.alexissilva.trainerapp.databinding.FragmentHistoryBinding
import cl.alexissilva.trainerapp.domain.WorkoutStatus
import cl.alexissilva.trainerapp.ui.adapters.LogsWithWorkoutAdapter
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect

@AndroidEntryPoint
class HistoryFragment(
    private var _viewModel: HistoryViewModel? = null
) : Fragment() {

    private var _binding: FragmentHistoryBinding? = null
    private val binding get() = _binding!!
    private val viewModel get() = _viewModel!!
    private val adapter by lazy { LogsWithWorkoutAdapter() }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentHistoryBinding.inflate(inflater, container, false)
        _viewModel = _viewModel ?: ViewModelProvider(this).get(HistoryViewModel::class.java)
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
            viewModel.workoutLogs.collect { logs ->

                binding.doneTextView.text =
                    logs.count { it.status == WorkoutStatus.DONE }.toString()
                binding.skippedTextView.text =
                    logs.count { it.status == WorkoutStatus.SKIPPED }.toString()


                binding.noPastWorkoutsTextView.visibility =
                    if (logs.isEmpty()) View.VISIBLE else View.INVISIBLE

                adapter.dataList = logs
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}