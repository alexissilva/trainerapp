package cl.alexissilva.trainerapp.ui.showworkoutlog

import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Card
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.navArgs
import cl.alexissilva.trainerapp.ui.theme.MyApplicationTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ShowWorkoutLogActivity : AppCompatActivity() {

    private val args: ShowWorkoutLogActivityArgs by navArgs()
    private val _viewModel: ShowWorkoutLogViewModel by viewModels()
    private val viewModel get() = _viewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //_viewModel = ViewModelProvider(this)[HistoricalLogViewModel::class.java]
        viewModel.loadWorkoutLog(args.workoutLogId)
        setContent {
            MyApplicationTheme {
                ActivityScreen()
            }
        }
    }

    @Composable
    fun ActivityScreen() {
        val workoutLogs = viewModel.workoutLogAsStringPairs.collectAsState().value ?: emptyList()
        Surface(modifier = Modifier.fillMaxSize()) {
            Column {
                Workouts(workoutLogs)
            }
        }
    }

    @Composable
    fun Workouts(workoutLogAsStringPairs: List<Pair<String, List<String>>>) {
        LazyColumn(modifier = Modifier.padding(8.dp)) {
            items(workoutLogAsStringPairs) { log ->
                WorkoutCard(log.first, log.second)
            }
        }
    }


    @Composable
    fun WorkoutCard(exercise: String, setLogs: List<String>) {
        Card(
            modifier = Modifier
                .padding(4.dp)
                .fillMaxWidth()
        ) {
            Column(modifier = Modifier.padding(8.dp)) {
                Text(
                    text = exercise,
                    style = MaterialTheme.typography.h6,
                    modifier = Modifier.padding(top = 8.dp, bottom = 4.dp)
                )
                //FIXME if setLogs is too big it will cause performance issues
                Column {
                    for (set in setLogs) {
                        Text(text = set)
                    }
                }
            }
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