package cl.alexissilva.trainerapp.ui.workoutdetails

import android.os.Bundle
import android.view.LayoutInflater
import android.view.MenuItem
import androidx.lifecycle.Lifecycle
import androidx.recyclerview.widget.LinearLayoutManager
import cl.alexissilva.trainerapp.R
import cl.alexissilva.trainerapp.databinding.ActivityWorkoutDetailsBinding
import cl.alexissilva.trainerapp.ui.adapters.ExercisesAdapter
import cl.alexissilva.trainerapp.ui.base.ActivityWithViewModelTesting
import cl.alexissilva.trainerapp.utils.extensions.launchOnLifecycle
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class WorkoutDetailsActivity :
    ActivityWithViewModelTesting<WorkoutDetailsViewModel,ActivityWorkoutDetailsBinding>() {

    private var viewModel: WorkoutDetailsViewModel? = null
    private val exercisesAdapter by lazy { ExercisesAdapter() }

    override val inflateBinding: (LayoutInflater) -> ActivityWorkoutDetailsBinding
        get() = ActivityWorkoutDetailsBinding::inflate

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setSupportActionBar(binding.toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        setupRecyclerView()
    }

    override val viewModelClass: Class<WorkoutDetailsViewModel>
        get() = WorkoutDetailsViewModel::class.java

    override fun onViewModelCreated(viewModel: WorkoutDetailsViewModel) {
        this.viewModel = viewModel
        collectState()
    }

    private fun setupRecyclerView() {
        binding.exercisesRecyclerView.adapter = exercisesAdapter
        binding.exercisesRecyclerView.layoutManager = LinearLayoutManager(this)
    }

    private fun collectState() {
        launchOnLifecycle(Lifecycle.State.CREATED) {
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