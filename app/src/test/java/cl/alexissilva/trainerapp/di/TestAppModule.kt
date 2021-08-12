package cl.alexissilva.trainerapp.di

import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import cl.alexissilva.trainerapp.framework.database.AppDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Named

@Module
@InstallIn(SingletonComponent::class)
object TestAppModule {

    @Provides
    @Named("TestDb")
    fun provideInMemoryDb() = Room.inMemoryDatabaseBuilder(
        ApplicationProvider.getApplicationContext(),
        AppDatabase::class.java
    ).allowMainThreadQueries().build()
}

