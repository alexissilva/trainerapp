package cl.alexissilva.trainerapp.di

import cl.alexissilva.trainerapp.core.data.*
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import java.time.Clock
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