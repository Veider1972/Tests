package com.geekbrains.tests

import android.os.Build
import android.widget.Button
import androidx.lifecycle.Lifecycle
import androidx.test.core.app.ActivityScenario
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.geekbrains.tests.view.details.DetailsActivity
import junit.framework.TestCase
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.annotation.Config

@RunWith(AndroidJUnit4::class)
@Config(sdk = [Build.VERSION_CODES.O])
class MyMainActivityTest {

    private lateinit var scenario: ActivityScenario<DetailsActivity>

    @Before
    fun setup() {
        scenario = ActivityScenario.launch(DetailsActivity::class.java)
    }

    @After
    fun close() {
        scenario.close()
    }

    @Test
    fun activity_AssertNotNull() {
        scenario.onActivity {
            TestCase.assertNotNull(it)
        }
    }

    @Test
    fun activity_IsResumed() {
        TestCase.assertEquals(Lifecycle.State.RESUMED, scenario.state)
    }

    @Test
    fun activityTextView_Displayed() {
        scenario.onActivity {
           onView(withId(R.id.decrementButton)).check(matches(isDisplayed()))
           onView(withId(R.id.incrementButton)).check(matches(isDisplayed()))
           onView(withId(R.id.totalCountTextView)).check(matches(isDisplayed()))
        }
    }

    @Test
    fun activityTextView_HasText() {
        scenario.onActivity {
            onView(withId(R.id.decrementButton)).check(matches(withText(R.string.decrement_text)))
            onView(withId(R.id.incrementButton)).check(matches(withText(R.string.increment_text)))
            onView(withId(R.id.totalCountTextView)).check(matches(withText(String.format("Number of results: %d",0))))
        }
    }

    @Test
    fun checkClickTest() {
        scenario.onActivity {
            it.findViewById<Button>(R.id.incrementButton).performClick()
            onView(withId(R.id.totalCountTextView)).check(matches(withText(String.format("Number of results: %d",1))))
        }
    }
}