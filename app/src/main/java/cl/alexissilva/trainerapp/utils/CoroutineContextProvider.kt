package cl.alexissilva.trainerapp.utils

import kotlinx.coroutines.Dispatchers
import javax.inject.Inject
import kotlin.coroutines.CoroutineContext

//TODO change to an interface(?)
open class CoroutineContextProvider @Inject constructor() {
    open val IO: CoroutineContext by lazy { Dispatchers.IO }
}