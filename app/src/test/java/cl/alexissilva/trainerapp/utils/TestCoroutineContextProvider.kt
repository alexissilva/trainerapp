package cl.alexissilva.trainerapp.utils

import kotlinx.coroutines.Dispatchers

class TestCoroutineContextProvider : CoroutineContextProvider() {
    override val IO = Dispatchers.Unconfined
}