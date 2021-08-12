package cl.alexissilva.trainerapp.ui

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentFactory
import cl.alexissilva.trainerapp.ui.nextsession.NextSessionFragment
import javax.inject.Inject

//TODO use or delete
class MyFragmentFactory @Inject constructor() : FragmentFactory() {

    override fun instantiate(classLoader: ClassLoader, className: String): Fragment {
        return when (className) {
            NextSessionFragment::class.java.name -> NextSessionFragment()
            else -> super.instantiate(classLoader, className)
        }
    }
}