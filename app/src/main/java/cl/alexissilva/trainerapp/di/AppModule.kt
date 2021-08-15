package cl.alexissilva.trainerapp.di

import android.content.Context
import androidx.room.Room
import cl.alexissilva.trainerapp.data.LocalWorkoutSource
import cl.alexissilva.trainerapp.data.RemoteWorkoutSource
import cl.alexissilva.trainerapp.data.WorkoutRepository
import cl.alexissilva.trainerapp.data.WorkoutRepositoryImpl
import cl.alexissilva.trainerapp.domain.WorkoutStatus
import cl.alexissilva.trainerapp.framework.FakeRemoteWorkoutSource
import cl.alexissilva.trainerapp.framework.database.AppDatabase
import cl.alexissilva.trainerapp.framework.database.DatabaseWorkoutSource
import cl.alexissilva.trainerapp.framework.database.WorkoutMap
import cl.alexissilva.trainerapp.framework.network.NetworkWorkoutSource
import cl.alexissilva.trainerapp.framework.network.WorkoutApi
import cl.alexissilva.trainerapp.utils.Constants.BASE_URL
import cl.alexissilva.trainerapp.utils.Constants.DATABASE_NAME
import cl.alexissilva.trainerapp.utils.Constants.USE_FAKE_REMOTE
import cl.alexissilva.trainerapp.utils.LocalDateGsonAdapter
import cl.alexissilva.trainerapp.utils.WorkoutStatusGsonAdapter
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
    fun provideWorkoutRepository(
        localSource: LocalWorkoutSource,
        remoteSource: RemoteWorkoutSource,
    ): WorkoutRepository = WorkoutRepositoryImpl(localSource, remoteSource)

    @Singleton
    @Provides
    fun provideLocalWorkoutSource(
        @ApplicationContext context: Context
    ): LocalWorkoutSource {
        val database = Room.databaseBuilder(
            context,
            AppDatabase::class.java,
            DATABASE_NAME,
        ).build()
        return DatabaseWorkoutSource(database.workoutDao(), WorkoutMap())
    }

    @Singleton
    @Provides
    fun provideRemoteWorkoutSource(
        networkSource: NetworkWorkoutSource,
        fakeSource: FakeRemoteWorkoutSource,
    ): RemoteWorkoutSource {
        return if (USE_FAKE_REMOTE) {
            fakeSource
        } else {
            networkSource
        }
    }

    @Singleton
    @Provides
    fun provideWorkoutApi(gson: Gson): WorkoutApi {
        val retrofit = Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create(gson))
            .build()
        return retrofit.create(WorkoutApi::class.java)
    }

    @Singleton
    @Provides
    fun provideGson(): Gson {
        return GsonBuilder()
            .registerTypeAdapter(LocalDate::class.java, LocalDateGsonAdapter())
            .registerTypeAdapter(WorkoutStatus::class.java, WorkoutStatusGsonAdapter())
            .create()
    }


    @Singleton
    @Provides
    fun provideClock(): Clock {
        return Clock.systemDefaultZone()
    }

}