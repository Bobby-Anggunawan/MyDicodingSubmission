package id.chainlizard.saltiesmovie

import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import dagger.hilt.android.testing.HiltTestApplication
import id.chainlizard.saltiesmovie.data.MyRepository
import id.chainlizard.saltiesmovie.data.model.MovieDetailMod
import id.chainlizard.saltiesmovie.data.model.TVDetailMod
import id.chainlizard.saltiesmovie.functions.MyObj
import org.junit.Assert
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
@Config(application = HiltTestApplication::class, manifest = Config.NONE)
class ModelTest {
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
    fun testMovieDetailMod() {
        val idMovie = 550
        val dataDownload = repo.getMovieDetail(idMovie)
        Assert.assertEquals("Harus online", MyObj.isOnline(activity), true)
        Assert.assertEquals(dataDownload.id, idMovie)
        Assert.assertEquals(dataDownload.original_title, "Fight Club")
        Assert.assertTrue(dataDownload.poster_path.substring(dataDownload.poster_path.length - 4) == ".jpg")
    }

    @Test
    fun testMovieDiscoverMod() {
        val page = 1
        val dataDownload = repo.getMovieDiscover(page)
        Assert.assertEquals("Harus online", MyObj.isOnline(activity), true)
        Assert.assertEquals(dataDownload.page, page)
        Assert.assertEquals(dataDownload.results.count(), 20)
        Assert.assertEquals(dataDownload.total_results, 10000)
    }

    @Test
    fun testTVDetailMod() {
        val idTV = 550
        val dataDownload = repo.getTVDetail(idTV)
        Assert.assertEquals("Harus online", MyObj.isOnline(activity), true)
        Assert.assertEquals(dataDownload.id, idTV)
        Assert.assertEquals(dataDownload.original_name, "Till Death Us Do Part")
        Assert.assertTrue(dataDownload.poster_path.substring(dataDownload.poster_path.length - 4) == ".jpg")
    }

    @Test
    fun testTVDiscoverMod() {
        val page = 1
        val dataDownload = repo.getTVDiscover(page)
        Assert.assertEquals("Harus online", MyObj.isOnline(activity), true)
        Assert.assertEquals(dataDownload.page, page)
        Assert.assertEquals(dataDownload.results.count(), 20)
        Assert.assertEquals(dataDownload.total_results, 10000)
    }

    @Test
    fun testDBMovie() {
        val movie1 = MovieDetailMod.MovieFavoriteDetail(
            6385,
            "hauhi",
            "abc",
            "def",
            "ghi",
            1.0,
            "afa",
            "in",
            "finish",
            10000,
            500
        )
        val movie2 = MovieDetailMod.MovieFavoriteDetail(
            8241,
            "ausi",
            "bduis",
            "sduiue",
            "sdbuui",
            1.0,
            "sdbiu",
            "in",
            "finish",
            10000,
            500
        )
        repo.addMovie(movie1)
        repo.addMovie(movie2)
        Assert.assertTrue(repo.movieExist(movie1.id))
        Assert.assertTrue(repo.movieExist(movie2.id))
        Assert.assertEquals(repo.getMovieWLAsc(2)[0], movie2)
        repo.removeMovie(movie1)
        Assert.assertEquals(repo.countMovieWL(), 1)
    }

    @Test
    fun testDBTV() {
        val tv1 = TVDetailMod.TVFavoriteDetail(
            2123,
            "jbidi",
            "uauish",
            "huahsui",
            "buaui",
            1.0,
            3,
            5,
            "asuh",
            "hsuhiu",
            "iuahsu",
            "ugais"
        )
        val tv2 = TVDetailMod.TVFavoriteDetail(
            8193,
            "guigs",
            "asvu",
            "awguuv",
            "wgauy",
            1.0,
            3,
            5,
            "gewigi",
            "adbi",
            "advuy",
            "eafaefdda"
        )
        repo.addTV(tv1)
        repo.addTV(tv2)
        Assert.assertTrue(repo.tvExist(tv1.id))
        Assert.assertTrue(repo.tvExist(tv2.id))
        Assert.assertEquals(repo.getTVWLAsc(2)[0], tv2)
        repo.removeTV(tv1)
        Assert.assertEquals(repo.countTVWL(), 1)
    }
}