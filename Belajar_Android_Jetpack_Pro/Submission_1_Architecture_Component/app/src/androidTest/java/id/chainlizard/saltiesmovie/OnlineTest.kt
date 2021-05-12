package id.chainlizard.saltiesmovie

import androidx.recyclerview.widget.RecyclerView
import androidx.test.core.app.ActivityScenario
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.IdlingRegistry
import androidx.test.espresso.action.ViewActions
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.action.ViewActions.swipeLeft
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.contrib.RecyclerViewActions
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.internal.runner.junit4.AndroidJUnit4ClassRunner
import androidx.test.rule.ActivityTestRule
import id.chainlizard.saltiesmovie.functions.MyIdlingResource
import id.chainlizard.saltiesmovie.functions.MyObj
import junit.framework.Assert.assertEquals
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith


@RunWith(AndroidJUnit4ClassRunner::class)
class OnlineTest {

    //todo kadang suskes kadang enggak.
    //todo Kalo gak sukses penyebabnya karena error idling timeout
    //todo biasanya gak sukses saat pertamakali dijalankan. Setelahnya selalu berhasil

    @Before
    fun setup() {
        ActivityScenario.launch(MainActivity::class.java)
        IdlingRegistry.getInstance().register(MyIdlingResource.idlingresource)
    }

    @Rule
    @JvmField
    val myActivityRule = ActivityTestRule(MainActivity::class.java)

    @After
    fun tearDown() {
        IdlingRegistry.getInstance().unregister(MyIdlingResource.idlingresource)
    }

    @Test
    fun testSemua() {
        assertEquals(MyObj.isOnline(myActivityRule.activity), true)
        onView(withId(R.id.movieList)).check(matches(isDisplayed()))
        onView(withId(R.id.mySpin)).check(matches(withEffectiveVisibility(Visibility.GONE)))
        onView(withId(R.id.movieList)).perform(
            RecyclerViewActions.actionOnItemAtPosition<RecyclerView.ViewHolder>(
                19,
                click()
            )
        )
        onView(withId(R.id.poster)).check(matches(isDisplayed()))
        onView(withId(R.id.mySpin)).check(matches(withEffectiveVisibility(Visibility.GONE)))
        onView(isRoot()).perform(ViewActions.pressBack())
        onView(withId(R.id.view_pager)).perform(swipeLeft())
        onView(withId(R.id.tvList)).check(matches(isDisplayed()))
        onView(withId(R.id.mySpin)).check(matches(withEffectiveVisibility(Visibility.GONE)))
        onView(withId(R.id.tvList)).perform(
            RecyclerViewActions.actionOnItemAtPosition<RecyclerView.ViewHolder>(
                19,
                click()
            )
        )
        onView(withId(R.id.poster)).check(matches(isDisplayed()))
        onView(withId(R.id.mySpin)).check(matches(withEffectiveVisibility(Visibility.GONE)))
    }

}