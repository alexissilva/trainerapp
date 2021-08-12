package cl.alexissilva.trainerapp.di

import android.content.Context
import androidx.test.filters.SmallTest
import cl.alexissilva.trainerapp.data.LocalSessionSource
import cl.alexissilva.trainerapp.data.RemoteSessionSource
import cl.alexissilva.trainerapp.data.SessionRepositoryImpl
import cl.alexissilva.trainerapp.framework.FakeRemoteSessionSource
import cl.alexissilva.trainerapp.framework.database.DatabaseSessionSource
import cl.alexissilva.trainerapp.framework.network.NetworkSessionSource
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
    fun provideLocalSessionSource() {
        val context = mock<Context>()
        val source = appModule.provideLocalSessionSource(context)
        assertThat(source).isInstanceOf(DatabaseSessionSource::class.java)
    }

    @Ignore("It also can returns a fakeSessionSource. Test pending")
    @Test
    fun provideRemoteSessionSource() {
        val networkSource = mock<NetworkSessionSource>()
        val fakeSource = mock<FakeRemoteSessionSource>()
        val source = appModule.provideRemoteSessionSource(networkSource, fakeSource)
        assertThat(source).isInstanceOf(NetworkSessionSource::class.java)
    }

    @Test
    fun providesSessionRepository() {
        val localSource = mock<LocalSessionSource>()
        val remoteSource = mock<RemoteSessionSource>()
        val source = appModule.provideSessionRepository(localSource, remoteSource)
        assertThat(source).isInstanceOf(SessionRepositoryImpl::class.java)
    }
}