package id.chainlizard.saltiesmovie

import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import dagger.hilt.android.testing.HiltTestApplication
import id.chainlizard.saltiesmovie.data.MyRepository
import id.chainlizard.saltiesmovie.functions.MyObj
import id.chainlizard.saltiesmovie.viewmodel.MovieDetailVM
import id.chainlizard.saltiesmovie.viewmodel.MovieDiscoverVM
import id.chainlizard.saltiesmovie.viewmodel.TVDetailVM
import id.chainlizard.saltiesmovie.viewmodel.TVDiscoverVM
import junit.framework.Assert.assertEquals
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.Robolectric
import org.robolectric.RobolectricTestRunner
import org.robolectric.annotation.Config
import javax.inject.Inject


@RunWith(RobolectricTestRunner::class)
@HiltAndroidTest
@Config(manifest = Config.NONE, application = HiltTestApplication::class)
class ViewModelTest {

    lateinit var activity: MainActivity

    //inject hilt
    @get:Rule
    var hiltRule = HiltAndroidRule(this)

    @Inject
    lateinit var repo: MyRepository

    @Before
    fun init() {
        hiltRule.inject()

        activity = Robolectric.buildActivity(MainActivity::class.java)
            .create()
            .resume()
            .get()
    }

    @Test
    fun testMovieDetailVM() {
        assertEquals(MyObj.isOnline(activity), true)
        val myMovieDetailVM = MovieDetailVM(repo)
        myMovieDetailVM.getMovie(550).observe(activity, {
            assertEquals(it, repo.getMovieDetail(550))
        })
    }

    @Test
    fun testMovieDiscoverVM() {
        assertEquals(MyObj.isOnline(activity), true)
        val myMovieDiscoverVM = MovieDiscoverVM(repo)
        myMovieDiscoverVM.getMovie().observe(activity, {
            assertEquals(it, repo.getMovieDiscover(1).results)
        })
    }

    @Test
    fun testTVDetailVM() {
        assertEquals(MyObj.isOnline(activity), true)
        val myTVDetailVM = TVDetailVM(repo)
        myTVDetailVM.getTV(550).observe(activity, {
            assertEquals(it, repo.getTVDetail(550))
        })
    }

    @Test
    fun testTVDiscoverVM() {
        assertEquals(MyObj.isOnline(activity), true)
        val myTVDiscoverVM = TVDiscoverVM(repo)
        myTVDiscoverVM.getTV().observe(activity, {
            assertEquals(it, repo.getTVDiscover(1).results)
        })
    }
}