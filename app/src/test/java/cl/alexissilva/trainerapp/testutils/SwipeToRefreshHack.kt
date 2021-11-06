package cl.alexissilva.trainerapp.testutils

import android.view.animation.Animation
import androidx.annotation.IdRes
import androidx.fragment.app.Fragment
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import org.mockito.kotlin.mock

/**
 * Swipe-To-Refresh is not trigger performing swipeDown with robolectric
 * https://github.com/robolectric/robolectric/issues/5375
 */
fun Fragment.simulateSwipeToRefresh(@IdRes swipeToRefreshId: Int) =
    requireView().findViewById<SwipeRefreshLayout>(swipeToRefreshId).run {
        isRefreshing = true
        val listener = this.javaClass.getDeclaredField("mRefreshListener")
        val notify = this.javaClass.getDeclaredField("mNotify")
        notify.isAccessible = true
        listener.isAccessible = true
        notify.set(this, true)
        val animatorListener = listener.get(this) as Animation.AnimationListener
        animatorListener.onAnimationEnd(mock())
    }