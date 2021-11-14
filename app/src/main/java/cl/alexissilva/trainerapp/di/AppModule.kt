package cl.alexissilva.trainerapp.di

import android.content.Context
import androidx.room.Room
import cl.alexissilva.trainerapp.core.data.*
import cl.alexissilva.trainerapp.core.domain.WorkoutStatus
import cl.alexissilva.trainerapp.framework.FakeRemoteWorkoutSource
import cl.alexissilva.trainerapp.framework.database.AppDatabase
import cl.alexissilva.trainerapp.framework.database.workout.DatabaseWorkoutSource
import cl.alexissilva.trainerapp.framework.database.workout.WorkoutMap
import cl.alexissilva.trainerapp.framework.database.workoutlog.DatabaseWorkoutLogSource
import cl.alexissilva.trainerapp.framework.database.workoutlog.WorkoutLogMap
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
    fun provideClock(): Clock {
        return Clock.systemDefaultZone()
    }
}