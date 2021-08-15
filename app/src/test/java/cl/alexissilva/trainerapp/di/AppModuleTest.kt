package cl.alexissilva.trainerapp.di

import android.content.Context
import androidx.test.filters.SmallTest
import cl.alexissilva.trainerapp.data.LocalWorkoutSource
import cl.alexissilva.trainerapp.data.RemoteWorkoutSource
import cl.alexissilva.trainerapp.data.WorkoutRepositoryImpl
import cl.alexissilva.trainerapp.framework.FakeRemoteWorkoutSource
import cl.alexissilva.trainerapp.framework.database.DatabaseWorkoutSource
import cl.alexissilva.trainerapp.framework.network.NetworkWorkoutSource
import com.google.common.truth.Truth.assertThat
import org.junit.Before
import org.junit.Ignore
import org.junit.Test
import org.mockito.kotlin.mock

@SmallTest
class AppModuleTest {

    private lateinit var appModule: AppModule

    @Before
    fun setUp() {
        appModule = AppModule()
    }

    @Test
    fun provideLocalWorkoutSource() {
        val context = mock<Context>()
        val source = appModule.provideLocalWorkoutSource(context)
        assertThat(source).isInstanceOf(DatabaseWorkoutSource::class.java)
    }

    @Test
    fun provideRemoteWorkoutSource() {
        val networkSource = mock<NetworkWorkoutSource>()
        val fakeSource = mock<FakeRemoteWorkoutSource>()
        val source = appModule.provideRemoteWorkoutSource(networkSource, fakeSource)
        assertThat(source).isInstanceOf(NetworkWorkoutSource::class.java)
    }

    @Test
    fun providesWorkoutRepository() {
        val localSource = mock<LocalWorkoutSource>()
        val remoteSource = mock<RemoteWorkoutSource>()
        val source = appModule.provideWorkoutRepository(localSource, remoteSource)
        assertThat(source).isInstanceOf(WorkoutRepositoryImpl::class.java)
    }
}