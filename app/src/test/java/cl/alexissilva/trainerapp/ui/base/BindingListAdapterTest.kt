package cl.alexissilva.trainerapp.ui.base

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.FrameLayout
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import cl.alexissilva.trainerapp.testutils.TestViewBinding
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.kotlin.mock
import org.mockito.kotlin.spy
import org.mockito.kotlin.verify


private class TestListAdapter : BindingListAdapter<String, TestViewBinding>() {
    var mockViewBindingOnBind: ((String) -> Unit)? = null

    override val inflateBinding: (LayoutInflater, ViewGroup, Boolean) -> TestViewBinding
        get() = TestViewBinding.inflate

    override fun TestViewBinding.onBind(item: String, position: Int) {
        mockViewBindingOnBind?.invoke(item)
    }
}

@RunWith(AndroidJUnit4::class)
class BindingListAdapterTest {

    private lateinit var adapter: TestListAdapter
    private val context = ApplicationProvider.getApplicationContext<Context>()

    @Before
    fun setUp() {
        adapter = spy(TestListAdapter())
        adapter.submitList(listOf("item1", "item2"))
    }


    @Test
    fun inflateBinding_onCreateViewHolder() {
        adapter.onCreateViewHolder(FrameLayout(context), 0)
        verify(adapter).inflateBinding
    }

    @Test
    fun callsOnBindWithItem_onBindViewHolder() {
        val holder = ViewHolderBinding(TestViewBinding(context))

        adapter.mockViewBindingOnBind = mock()
        adapter.onBindViewHolder(holder, 0)

        verify(adapter.mockViewBindingOnBind)!!.invoke("item1")
    }

}
