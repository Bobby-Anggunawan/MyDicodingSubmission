package id.chainlizard.saltiesmovie

import android.app.Activity
import androidx.recyclerview.widget.RecyclerView
import androidx.test.core.app.ActivityScenario
import androidx.test.espresso.Espresso
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.IdlingRegistry
import androidx.test.espresso.action.ViewActions
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.action.ViewActions.swipeLeft
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.idling.CountingIdlingResource
import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.internal.runner.junit4.AndroidJUnit4ClassRunner
import androidx.test.rule.ActivityTestRule
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import androidx.test.espresso.contrib.RecyclerViewActions;
import androidx.test.espresso.matcher.ViewMatchers.*


@RunWith(AndroidJUnit4ClassRunner::class)
class MainTest {
    @Before
    fun setup(){
        ActivityScenario.launch(MainActivity::class.java)
        IdlingRegistry.getInstance().register(myIdlingResource.idlingresource)
    }

    @After
    fun tearDown() {
        IdlingRegistry.getInstance().unregister(myIdlingResource.idlingresource)
    }

    @Test
    fun testSemua(){
        onView(withId(R.id.movieList)).check(matches(isDisplayed()))
        onView(withId(R.id.movieList)).perform(RecyclerViewActions.actionOnItemAtPosition<RecyclerView.ViewHolder>(19, click()))
        onView(withId(R.id.poster)).check(matches(isDisplayed()))
        onView(isRoot()).perform(ViewActions.pressBack())
        onView(withId(R.id.view_pager)).perform(swipeLeft())
        onView(withId(R.id.tvList)).check(matches(isDisplayed()))
        onView(withId(R.id.tvList)).perform(RecyclerViewActions.actionOnItemAtPosition<RecyclerView.ViewHolder>(19, click()))
        onView(withId(R.id.poster)).check(matches(isDisplayed()))
    }
}