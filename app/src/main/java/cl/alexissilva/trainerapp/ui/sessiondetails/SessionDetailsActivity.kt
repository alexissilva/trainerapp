package cl.alexissilva.trainerapp.ui.sessiondetails

import android.os.Bundle
import android.util.Log
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import cl.alexissilva.trainerapp.databinding.ActivitySessionDetailsBinding
import cl.alexissilva.trainerapp.ui.adapters.ExercisesAdapter
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect
import java.time.format.DateTimeFormatter

@AndroidEntryPoint
class SessionDetailsActivity : AppCompatActivity() {

    private var viewModel: SessionDetailsViewModel? = null
    private var _binding: ActivitySessionDetailsBinding? = null
    private val binding get() = _binding!!
    private val exercisesAdapter by lazy { ExercisesAdapter(this) }

    companion object {
        const val DATE_FORMAT = "EEEE, d MMMM"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivitySessionDetailsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setSupportActionBar(binding.toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        setupViewModel()
        setupRecyclerView()
    }

    private fun setupRecyclerView() {
        binding.exercisesRecyclerView.adapter = exercisesAdapter
        binding.exercisesRecyclerView.layoutManager = LinearLayoutManager(this)
    }

    //FIXME It was the only option that I found test the activity and set the viewModel
    internal fun setupViewModel(testingViewModel: SessionDetailsViewModel? = null) {
        try {
            viewModel =
                testingViewModel ?: ViewModelProvider(this).get(SessionDetailsViewModel::class.java)
        } catch (e: RuntimeException) {
            Log.d("SessionDetailsActivity", "Error setting up viewModel", e)
        }

        collectState()
    }

    private fun collectState() {
        lifecycleScope.launchWhenCreated {
            viewModel?.session?.collect {
                if (it != null) {
                    supportActionBar?.title = it.name
                    val formattedDate = it.date.format(
                        DateTimeFormatter.ofPattern(DATE_FORMAT)
                    );
                    binding.dateTextView.text = formattedDate
                    exercisesAdapter.setExerciseList(it.exercises)
                }
            }
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            android.R.id.home -> {
                finish()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}