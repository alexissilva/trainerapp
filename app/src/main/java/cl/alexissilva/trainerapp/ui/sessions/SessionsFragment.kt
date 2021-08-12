package cl.alexissilva.trainerapp.ui.sessions

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import cl.alexissilva.trainerapp.databinding.FragmentSessionsBinding
import cl.alexissilva.trainerapp.domain.Session
import cl.alexissilva.trainerapp.ui.adapters.SessionsAdapter
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect

@AndroidEntryPoint
class SessionsFragment(
    private var _viewModel: SessionsViewModel? = null
) : Fragment() {
    private var _binding: FragmentSessionsBinding? = null
    private val binding get() = _binding!!
    private val viewModel get() = _viewModel!!
    private val adapter by lazy {
        SessionsAdapter(requireContext(), onItemClick = navigateToDetails)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentSessionsBinding.inflate(inflater, container, false)
        _viewModel = _viewModel ?: ViewModelProvider(this).get(SessionsViewModel::class.java)
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
            viewModel.sessions.collect {
                adapter.setSessionList(it)
                binding.noSessionsTextView.visibility =
                    if (it.isEmpty()) View.VISIBLE else View.INVISIBLE
            }
        }
    }

    private val navigateToDetails = { session: Session ->
        val action =
            SessionsFragmentDirections.actionSessionsFragmentToSessionDetailsActivity(session.id)
        findNavController().navigate(action)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}