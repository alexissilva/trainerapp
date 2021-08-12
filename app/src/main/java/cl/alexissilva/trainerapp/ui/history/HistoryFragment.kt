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
import cl.alexissilva.trainerapp.domain.SessionStatus
import cl.alexissilva.trainerapp.ui.adapters.SessionsAdapter
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect

@AndroidEntryPoint
class HistoryFragment(
    private var _viewModel: HistoryViewModel? = null
) : Fragment() {

    private var _binding: FragmentHistoryBinding? = null
    private val binding get() = _binding!!
    private val viewModel get() = _viewModel!!
    private val adapter by lazy { SessionsAdapter(requireContext(), true) }

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
        binding.sessionsRecyclerView.layoutManager = LinearLayoutManager(requireContext())
        binding.sessionsRecyclerView.adapter = adapter
    }

    private fun collectState() {
        lifecycleScope.launchWhenCreated {
            viewModel.pastSessions.collect { pastSessions ->
                binding.doneTextView.text =
                    pastSessions.count { it.status == SessionStatus.DONE }.toString()
                binding.skippedTextView.text =
                    pastSessions.count { it.status == SessionStatus.SKIPPED }.toString()
                binding.noPastSessionsTextView.visibility =
                    if (pastSessions.isEmpty()) View.VISIBLE else View.INVISIBLE
                adapter.setSessionList(pastSessions)
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}