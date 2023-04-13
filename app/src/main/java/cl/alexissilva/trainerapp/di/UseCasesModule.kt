package cl.alexissilva.trainerapp.di

import cl.alexissilva.trainerapp.core.data.WorkoutRepository
import cl.alexissilva.trainerapp.core.usecases.*
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import java.time.Clock
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class UseCasesModule {

    @Singleton
    @Provides
    fun deleteWorkoutLogs(workoutRepository: WorkoutRepository) =
        DeleteWorkoutLogs(workoutRepository)


    @Singleton
    @Provides
    fun downloadWorkouts(workoutRepository: WorkoutRepository) =
        DownloadWorkouts(workoutRepository)

    @Singleton
    @Provides
    fun getLocalWorkout(workoutRepository: WorkoutRepository) =
        GetLocalWorkout(workoutRepository)

    @Singleton
    @Provides
    fun getLocalWorkouts(workoutRepository: WorkoutRepository) =
        GetLocalWorkouts(workoutRepository)

    @Singleton
    @Provides
    fun getNextWorkout(getUpcomingWorkouts: GetUpcomingWorkouts) =
        GetNextWorkout(getUpcomingWorkouts)


    @Singleton
    @Provides
    fun getUpcomingWorkouts(workoutRepository: WorkoutRepository) =
        GetUpcomingWorkouts(workoutRepository)

    @Singleton
    @Provides
    fun getWorkoutLogsWithWorkout(workoutRepository: WorkoutRepository) =
        GetWorkoutLogsWithWorkout(workoutRepository)


    @Singleton
    @Provides
    fun skipWorkout(workoutRepository: WorkoutRepository, clock: Clock) =
        SkipWorkout(workoutRepository, clock)


    @Singleton
    @Provides
    fun createDraftWorkoutLog(getLocalWorkout: GetLocalWorkout) =
        CreateDraftWorkoutLog(getLocalWorkout)


    @Singleton
    @Provides
    fun getWorkoutLog(workoutRepository: WorkoutRepository) =
        GetWorkoutLog(workoutRepository)

    @Singleton
    @Provides
    fun saveWorkoutLog(workoutRepository: WorkoutRepository, clock: Clock) =
        SaveWorkoutLog(workoutRepository, clock)

    @Singleton
    @Provides
    fun getPastWorkouts(workoutRepository: WorkoutRepository) =
        GetPastWorkouts(workoutRepository)


}