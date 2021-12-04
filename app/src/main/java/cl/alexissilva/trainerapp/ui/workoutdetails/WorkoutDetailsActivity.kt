package cl.alexissilva.trainerapp.ui.workoutdetails

import android.os.Bundle
import android.view.LayoutInflater
import android.view.MenuItem
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import cl.alexissilva.trainerapp.R
import cl.alexissilva.trainerapp.databinding.ActivityWorkoutDetailsBinding
import cl.alexissilva.trainerapp.ui.adapters.ExercisesAdapter
import cl.alexissilva.trainerapp.ui.base.BindingActivity
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect

@AndroidEntryPoint
class WorkoutDetailsActivity : BindingActivity<ActivityWorkoutDetailsBinding>() {

    private var viewModel: WorkoutDetailsViewModel? = null
    private val exercisesAdapter by lazy { ExercisesAdapter() }

    override val inflateBinding: (LayoutInflater) -> ActivityWorkoutDetailsBinding
        get() = ActivityWorkoutDetailsBinding::inflate

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setSupportActionBar(binding.toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        val isBeingTested = intent.getBooleanExtra("isBeingTested", false)
        if (!isBeingTested) {
            setupViewModel()
        }
        setupRecyclerView()
    }

    private fun setupRecyclerView() {
        binding.exercisesRecyclerView.adapter = exercisesAdapter
        binding.exercisesRecyclerView.layoutManager = LinearLayoutManager(this)
    }

    //FIXME It was the best option I found for setup the viewModel in tests
    internal fun setupViewModel(testingViewModel: WorkoutDetailsViewModel? = null) {
        viewModel =
            testingViewModel ?: ViewModelProvider(this).get(WorkoutDetailsViewModel::class.java)
        collectState()
    }

    private fun collectState() {
        lifecycleScope.launchWhenCreated {
            viewModel?.workout?.collect {
                if (it != null) {
                    supportActionBar?.title = it.name
                    binding.dayTextView.text = getString(R.string.workout_day, it.day)
                    exercisesAdapter.submitList(it.exercises)
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

}