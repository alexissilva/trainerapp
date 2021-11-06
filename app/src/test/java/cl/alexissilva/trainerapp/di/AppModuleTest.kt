package cl.alexissilva.trainerapp.di

import androidx.test.filters.SmallTest
import cl.alexissilva.trainerapp.data.LocalWorkoutSource
import cl.alexissilva.trainerapp.data.RemoteWorkoutSource
import cl.alexissilva.trainerapp.data.WorkoutLogSource
import cl.alexissilva.trainerapp.data.WorkoutRepositoryImpl
import cl.alexissilva.trainerapp.framework.FakeRemoteWorkoutSource
import cl.alexissilva.trainerapp.framework.database.AppDatabase
import cl.alexissilva.trainerapp.framework.database.workout.DatabaseWorkoutSource
import cl.alexissilva.trainerapp.framework.database.workoutlog.DatabaseWorkoutLogSource
import cl.alexissilva.trainerapp.framework.network.NetworkWorkoutSource
import com.google.common.truth.Truth.assertThat
import org.junit.Before
import org.junit.Test
import org.mockito.kotlin.doReturn
import org.mockito.kotlin.mock

@SmallTest
class AppModuleTest {

    private lateinit var appModule: AppModule
    private lateinit var database: AppDatabase

    @Before
    fun setUp() {
        database = mock {
            on { workoutDao() } doReturn mock()
            on { workoutLogDao() } doReturn mock()
        }
        appModule = AppModule()
    }

    @Test
    fun providesDatabaseSource_asLocalWorkoutSource() {
        val source = appModule.provideLocalWorkoutSource(database)
        assertThat(source).isInstanceOf(DatabaseWorkoutSource::class.java)
    }

    @Test
    fun providesDatabaseSource_asWorkoutLogSource() {
        val source = appModule.provideWorkoutLogSource(database)
        assertThat(source).isInstanceOf(DatabaseWorkoutLogSource::class.java)
    }

    @Test
    fun providesNetworkSource_asRemoteWorkoutSource() {
        val networkSource = mock<NetworkWorkoutSource>()
        val fakeSource = mock<FakeRemoteWorkoutSource>()
        val source = appModule.provideRemoteWorkoutSource(networkSource, fakeSource)
        assertThat(source).isInstanceOf(NetworkWorkoutSource::class.java)
    }

    @Test
    fun providesRepositoryImpl_asWorkoutRepository() {
        val localSource = mock<LocalWorkoutSource>()
        val remoteSource = mock<RemoteWorkoutSource>()
        val logSource = mock<WorkoutLogSource>()
        val source = appModule.provideWorkoutRepository(localSource, remoteSource, logSource)
        assertThat(source).isInstanceOf(WorkoutRepositoryImpl::class.java)
    }
}