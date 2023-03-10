package com.geekbrains.tests

import android.os.Build
import androidx.test.core.app.ActivityScenario
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.geekbrains.tests.presenter.search.SearchPresenter
import com.geekbrains.tests.repository.GitHubRepository
import com.geekbrains.tests.view.search.MainActivity
import com.geekbrains.tests.view.search.ViewSearchContract
import org.junit.After
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.MockitoAnnotations
import org.robolectric.annotation.Config

@RunWith(AndroidJUnit4::class)
@Config(sdk = [Build.VERSION_CODES.O])
class MyActivityTest {

    lateinit var scenario: ActivityScenario<MainActivity>

    @Mock
    private lateinit var viewContract: ViewSearchContract

    @Mock
    private lateinit var repository: GitHubRepository

    private lateinit var presenter: SearchPresenter

    @Before
    fun setup() {
        scenario = ActivityScenario.launch(MainActivity::class.java)
        MockitoAnnotations.initMocks(this)
        presenter = SearchPresenter(viewContract, repository)
    }

    @After
    fun close() {
        scenario.close()
    }

    @Test
    fun checkActivityExists() {
        scenario.onActivity {
            Assert.assertNotNull(it)
        }
    }

    @Test
    fun checkPresenterExists() {
        scenario.onActivity {
            Assert.assertNotNull(presenter)
        }
    }

    @Test
    fun checkPresenterOnAttach() {
        scenario.onActivity {
            presenter.onAttach(it)
            Assert.assertTrue(presenter.isLive())
        }
    }

    @Test
    fun checkPresenterOnDetach() {
        scenario.onActivity {
            presenter.onDetach()
            Assert.assertFalse(presenter.isLive())
        }
    }

    @Test
    fun checkPresenterOnAttachDetach() {
        scenario.onActivity {
            presenter.onAttach(it)
            presenter.onDetach()
            Assert.assertFalse(presenter.isLive())
        }
    }
}