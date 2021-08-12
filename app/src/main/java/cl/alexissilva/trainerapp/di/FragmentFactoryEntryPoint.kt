package cl.alexissilva.trainerapp.di

import cl.alexissilva.trainerapp.ui.MyFragmentFactory
import dagger.hilt.EntryPoint
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityComponent

//TODO use or delete
@EntryPoint
@InstallIn(ActivityComponent::class)
interface FragmentFactoryEntryPoint {
    fun getFragmentFactory(): MyFragmentFactory
}