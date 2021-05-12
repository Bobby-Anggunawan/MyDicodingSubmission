package id.chainlizard.saltiesmovie

import androidx.test.core.app.ActivityScenario
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.IdlingRegistry
import androidx.test.espresso.action.ViewActions
import androidx.test.espresso.assertion.ViewAssertions
import androidx.test.espresso.matcher.ViewMatchers
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
class OfflineTest {
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
    fun testOffline() {
        assertEquals(MyObj.isOnline(myActivityRule.activity), false)
        onView(ViewMatchers.withText("Network Error"))
            .check(ViewAssertions.matches(ViewMatchers.isDisplayed()))
        onView(ViewMatchers.withText("OK")).perform(ViewActions.click())
        onView(ViewMatchers.withId(R.id.mySpin))
            .check(ViewAssertions.matches(ViewMatchers.withEffectiveVisibility(ViewMatchers.Visibility.GONE)))
        onView(ViewMatchers.withId(R.id.view_pager)).perform(ViewActions.swipeLeft())
        onView(ViewMatchers.withText("Network Error"))
            .check(ViewAssertions.matches(ViewMatchers.isDisplayed()))
        onView(ViewMatchers.withText("OK")).perform(ViewActions.click())
        onView(ViewMatchers.withId(R.id.mySpin))
            .check(ViewAssertions.matches(ViewMatchers.withEffectiveVisibility(ViewMatchers.Visibility.GONE)))
    }
}