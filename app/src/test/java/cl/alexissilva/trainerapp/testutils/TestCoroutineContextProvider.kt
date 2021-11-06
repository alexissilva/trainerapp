package cl.alexissilva.trainerapp.testutils

import cl.alexissilva.trainerapp.utils.CoroutineContextProvider
import kotlinx.coroutines.Dispatchers

class TestCoroutineContextProvider : CoroutineContextProvider() {
    override val IO = Dispatchers.Unconfined
}