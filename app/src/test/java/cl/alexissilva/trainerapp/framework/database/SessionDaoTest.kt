package cl.alexissilva.trainerapp.framework.database

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.MediumTest
import cl.alexissilva.trainerapp.domain.SessionStatus
import com.google.common.truth.Truth.assertThat
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import dagger.hilt.android.testing.HiltTestApplication
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.runBlockingTest
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.annotation.Config
import java.time.LocalDate
import javax.inject.Inject
import javax.inject.Named

@MediumTest
@ExperimentalCoroutinesApi
@HiltAndroidTest
@Config(application = HiltTestApplication::class)
@RunWith(AndroidJUnit4::class)
class SessionDaoTest {
    private val dummyEntity = SessionEntity("session", "session", LocalDate.MIN, emptyList(), SessionStatus.PENDING)

    @get:Rule
    var hiltRule = HiltAndroidRule(this)

    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    @Inject
    @Named("TestDb")
    lateinit var database: AppDatabase
    private lateinit var sessionDao: SessionDao

    @Before
    fun setUp() {
        hiltRule.inject()
        sessionDao = database.sessionDao()
    }

    @After
    fun teardown() {
        database.close()
    }

    @Test
    fun insertsSessionEntity() = runBlockingTest {
        sessionDao.insert(dummyEntity)

        val sessions = sessionDao.getAll().first()
        assertThat(sessions).contains(dummyEntity)
    }

    @Test
    fun updateSessionEntity() = runBlockingTest {
        val dummyEntity2 = SessionEntity(dummyEntity.id, "dummy2", LocalDate.MIN, emptyList(), SessionStatus.DONE)
        sessionDao.insert(dummyEntity)
        sessionDao.insert(dummyEntity2)

        val sessions = sessionDao.getAll().first()
        assertThat(sessions).containsExactly(dummyEntity2)
    }


    @Test
    fun insertManySessionEntities() = runBlockingTest {
        val dummyEntity2 = SessionEntity("dummy2", "dummy2", LocalDate.MIN, emptyList(), SessionStatus.PENDING)
        sessionDao.insert(listOf(dummyEntity, dummyEntity2))

        val sessions = sessionDao.getAll().first()
        assertThat(sessions).containsExactly(dummyEntity, dummyEntity2)
    }

    @Test
    fun deletesSessionEntity() = runBlockingTest {
        sessionDao.insert(dummyEntity)
        sessionDao.delete(dummyEntity)

        val sessions = sessionDao.getAll().first()
        assertThat(sessions).doesNotContain(dummyEntity)
    }

    @Test
    fun getsSessionById() = runBlockingTest {
        sessionDao.insert(dummyEntity)

        val session = sessionDao.getSessionById(dummyEntity.id).first()
        assertThat(session).isEqualTo(dummyEntity)
    }

    @Test
    fun getsNullWhenNotFoundSessionById() = runBlockingTest {
        val session = sessionDao.getSessionById(dummyEntity.id).first()
        assertThat(session).isNull()
    }
}