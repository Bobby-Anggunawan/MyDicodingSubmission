package id.chainlizard.saltiesmovie

import androidx.recyclerview.widget.RecyclerView
import androidx.room.Room
import androidx.test.core.app.ActivityScenario
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.IdlingRegistry
import androidx.test.espresso.action.ViewActions
import androidx.test.espresso.assertion.ViewAssertions
import androidx.test.espresso.contrib.RecyclerViewActions
import androidx.test.espresso.matcher.ViewMatchers
import androidx.test.internal.runner.junit4.AndroidJUnit4ClassRunner
import androidx.test.platform.app.InstrumentationRegistry
import androidx.test.rule.ActivityTestRule
import id.chainlizard.saltiesmovie.data.MyRepository
import id.chainlizard.saltiesmovie.functions.MyDatabase
import id.chainlizard.saltiesmovie.functions.MyIdlingResource
import id.chainlizard.saltiesmovie.functions.Networking
import org.junit.*
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4ClassRunner::class)
class FavoritTest {

    var repo = MyRepository(
        Networking.MyNetwork(), Room.databaseBuilder(
            InstrumentationRegistry.getInstrumentation().getTargetContext(),
            MyDatabase.AppDatabase::class.java, "SaltiesMovieDB"
        ).build()
    )

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
    fun testFavorit() {
        if (repo.countMovieWL() < 1) {
            onView(ViewMatchers.withId(R.id.movieList)).perform(
                RecyclerViewActions.actionOnItemAtPosition<RecyclerView.ViewHolder>(
                    1,
                    ViewActions.click()
                )
            )
            onView(ViewMatchers.withId(R.id.favorit)).perform(ViewActions.click())
            onView(ViewMatchers.isRoot()).perform(ViewActions.pressBack())
        }
        if (repo.countTVWL() < 1) {
            onView(ViewMatchers.withId(R.id.view_pager)).perform(ViewActions.swipeLeft())
            onView(ViewMatchers.withId(R.id.tvList)).perform(
                RecyclerViewActions.actionOnItemAtPosition<RecyclerView.ViewHolder>(
                    1,
                    ViewActions.click()
                )
            )
            onView(ViewMatchers.withId(R.id.favorit)).perform(ViewActions.click())
            onView(ViewMatchers.isRoot()).perform(ViewActions.pressBack())
        }
        Assert.assertTrue(repo.countMovieWL() > 0)
        Assert.assertTrue(repo.countTVWL() > 0)

        val movie1 = repo.getMovieWLAsc(1)[0]
        val tv1 = repo.getTVWLAsc(1)[0]
        onView(ViewMatchers.withId(R.id.extended_fab)).perform(ViewActions.click())
        onView(ViewMatchers.withText(movie1.title)).perform(ViewActions.click())
        onView(ViewMatchers.withId(R.id.favorit)).perform(ViewActions.click())
        onView(ViewMatchers.isRoot()).perform(ViewActions.pressBack())

        onView(ViewMatchers.withId(R.id.view_pager)).perform(ViewActions.swipeLeft())
        onView(ViewMatchers.withText(tv1.name)).perform(ViewActions.click())
        onView(ViewMatchers.withId(R.id.favorit)).perform(ViewActions.click())
        onView(ViewMatchers.isRoot()).perform(ViewActions.pressBack())
        onView(ViewMatchers.isRoot()).perform(ViewActions.pressBack())

        onView(ViewMatchers.withId(R.id.extended_fab)).perform(ViewActions.click())
        onView(ViewMatchers.withText(movie1.title)).check(ViewAssertions.doesNotExist())
        onView(ViewMatchers.withId(R.id.view_pager)).perform(ViewActions.swipeLeft())
        onView(ViewMatchers.withText(tv1.name)).check(ViewAssertions.doesNotExist())
    }
}