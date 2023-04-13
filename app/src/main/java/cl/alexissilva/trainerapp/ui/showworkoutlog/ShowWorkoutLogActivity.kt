package cl.alexissilva.trainerapp.ui.showworkoutlog

import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.background
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Card
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.navArgs
import cl.alexissilva.trainerapp.core.domain.WorkoutLog
import cl.alexissilva.trainerapp.core.domain.WorkoutStatus
import cl.alexissilva.trainerapp.ui.showworkoutlog.WorkoutLogMapper.mapWorkoutLogToExerciseStrings
import cl.alexissilva.trainerapp.ui.theme.AppColors
import cl.alexissilva.trainerapp.ui.theme.MyApplicationTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ShowWorkoutLogActivity : AppCompatActivity() {

    private val args: ShowWorkoutLogActivityArgs by navArgs()
    private val _viewModel: ShowWorkoutLogViewModel by viewModels()
    private val viewModel get() = _viewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel.loadWorkoutLog(args.workoutLogId)
        setContent {
            MyApplicationTheme {
                ActivityScreen()
            }
        }
    }

    private fun getBackgroundColor(darkTheme: Boolean) =
        if (darkTheme) AppColors.GreyDark else AppColors.GreyLight

    @Composable
    fun ActivityScreen() {
        val workoutLog = viewModel.workoutLog.collectAsState().value ?: return
        Surface(modifier = Modifier.fillMaxSize()) {
            Column(
                modifier = Modifier.background(color = getBackgroundColor(isSystemInDarkTheme()))
            ) {
                if (workoutLog.status == WorkoutStatus.SKIPPED) {
                    WorkoutSkippedText()
                } else {
                    ExerciseLogList(workoutLog)
                }
            }
        }
    }

    @Composable
    fun ExerciseLogList(workoutLog: WorkoutLog) {
        val exerciseStrings = mapWorkoutLogToExerciseStrings(workoutLog)
        LazyColumn(modifier = Modifier.padding(8.dp)) {
            items(exerciseStrings) { exercisePair ->
                ExerciseLogCard(exercisePair.first, exercisePair.second)
            }
        }
    }


    @Composable
    fun ExerciseLogCard(exerciseName: String, setLogs: List<String>) {
        Card(
            modifier = Modifier
                .padding(4.dp)
                .fillMaxWidth()
        ) {
            Column(modifier = Modifier.padding(8.dp)) {
                Text(
                    text = exerciseName,
                    style = MaterialTheme.typography.h6,
                    modifier = Modifier.padding(top = 8.dp, bottom = 4.dp)
                )
                //FIXME setLogs is expected to have few items, otherwise it could cause performance issues
                Column {
                    for (set in setLogs) {
                        Text(text = set)
                    }
                }
            }
        }
    }

    @Composable
    fun WorkoutSkippedText() {
        Column(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(text = stringResource(id = cl.alexissilva.trainerapp.R.string.workout_skipped))
        }
    }

    @Preview
    @Composable
    fun PreviewMessageCard() {
        MyApplicationTheme {
            ActivityScreen()
        }
    }

}