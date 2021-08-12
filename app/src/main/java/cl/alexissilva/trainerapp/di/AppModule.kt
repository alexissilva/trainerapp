package cl.alexissilva.trainerapp.di

import android.content.Context
import androidx.room.Room
import cl.alexissilva.trainerapp.data.LocalSessionSource
import cl.alexissilva.trainerapp.data.RemoteSessionSource
import cl.alexissilva.trainerapp.data.SessionRepository
import cl.alexissilva.trainerapp.data.SessionRepositoryImpl
import cl.alexissilva.trainerapp.domain.SessionStatus
import cl.alexissilva.trainerapp.framework.FakeRemoteSessionSource
import cl.alexissilva.trainerapp.framework.database.AppDatabase
import cl.alexissilva.trainerapp.framework.database.DatabaseSessionSource
import cl.alexissilva.trainerapp.framework.database.SessionMap
import cl.alexissilva.trainerapp.framework.network.NetworkSessionSource
import cl.alexissilva.trainerapp.framework.network.SessionsApi
import cl.alexissilva.trainerapp.utils.Constants.BASE_URL
import cl.alexissilva.trainerapp.utils.Constants.DATABASE_NAME
import cl.alexissilva.trainerapp.utils.Constants.USE_FAKE_REMOTE
import cl.alexissilva.trainerapp.utils.LocalDateGsonAdapter
import cl.alexissilva.trainerapp.utils.SessionStatusGsonAdapter
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.time.Clock
import java.time.LocalDate
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class AppModule {

    @Singleton
    @Provides
    fun provideSessionRepository(
        localSource: LocalSessionSource,
        remoteSource: RemoteSessionSource,
    ): SessionRepository = SessionRepositoryImpl(localSource, remoteSource)

    @Singleton
    @Provides
    fun provideLocalSessionSource(
        @ApplicationContext context: Context
    ): LocalSessionSource {
        val database = Room.databaseBuilder(
            context,
            AppDatabase::class.java,
            DATABASE_NAME,
        ).build()
        return DatabaseSessionSource(database.sessionDao(), SessionMap())
    }

    @Singleton
    @Provides
    fun provideRemoteSessionSource(
        networkSource: NetworkSessionSource,
        fakeSource: FakeRemoteSessionSource,
    ): RemoteSessionSource {
        return if (USE_FAKE_REMOTE) {
            fakeSource
        } else {
            networkSource
        }
    }

    @Singleton
    @Provides
    fun provideSessionApi(gson: Gson): SessionsApi {
        val retrofit = Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create(gson))
            .build()
        return retrofit.create(SessionsApi::class.java)
    }

    @Singleton
    @Provides
    fun provideGson(): Gson {
        return GsonBuilder()
            .registerTypeAdapter(LocalDate::class.java, LocalDateGsonAdapter())
            .registerTypeAdapter(SessionStatus::class.java, SessionStatusGsonAdapter())
            .create()
    }


    @Singleton
    @Provides
    fun provideClock(): Clock {
        return Clock.systemDefaultZone()
    }

}