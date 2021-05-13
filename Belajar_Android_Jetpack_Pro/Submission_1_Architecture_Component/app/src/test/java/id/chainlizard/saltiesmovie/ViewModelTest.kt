package id.chainlizard.saltiesmovie

import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import dagger.hilt.android.testing.HiltTestApplication
import id.chainlizard.saltiesmovie.data.MyRepository
import id.chainlizard.saltiesmovie.data.model.MovieDetailMod
import id.chainlizard.saltiesmovie.data.model.TVDetailMod
import id.chainlizard.saltiesmovie.functions.MyObj
import id.chainlizard.saltiesmovie.viewmodel.MovieDetailVM
import id.chainlizard.saltiesmovie.viewmodel.TVDetailVM
import org.junit.Assert
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.Robolectric
import org.robolectric.RobolectricTestRunner
import org.robolectric.annotation.Config
import javax.inject.Inject
import kotlin.random.Random

@RunWith(RobolectricTestRunner::class)
@HiltAndroidTest
@Config(manifest = Config.NONE, application = HiltTestApplication::class)
class ViewModelTest {

    //todo: PLEASE READ
    // Dari semua viewmodel saya, memang hanya 2 viewmodel ini yang punya fungsi di dalamnya.
    // Viewmodel lain hanya menampung sebuah variable PagingData. Saya menggunakan ini karena kode dari tutorial paging di dicoding sudah deprecated.
    // Karena ini saya tidak bisa menemukan cara melakukan testing. Saat saya mencari ke stackoverflow bahkan tidak ada satupun jawaban yang mendapat upvote(semua vote 0)
    // Walau begitu saya tetap coba melakukan seperti yang di stackoverflow tapi semuanya error(kodenya ada merah-merah bukan error pas dijalanin)
    // Lalu saya menemukan link berikut yang menyatakan test memang sulit dilakukan di paging3:
    // https://medium.com/movile-tech/using-jetpack-paging-v3-b52729c9fd76
    // https://github.com/android/architecture-components-samples/issues/942

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
        Assert.assertEquals(MyObj.isOnline(activity), true)
        val myMovieDetailVM = MovieDetailVM(repo)
        myMovieDetailVM.getMovie(550).observe(activity, {
            Assert.assertEquals(it, repo.getMovieDetail(550))
        })


        val movie1 = repo.getMovieDetail(550)
        repo.addMovie(MovieDetailMod.convertToFav(movie1))


        while (true) {
            val randomID = Random.nextInt()
            if (randomID != 550) {
                myMovieDetailVM.getFavorit(randomID).observe(activity, {
                    Assert.assertEquals(repo.movieExist(randomID), it)
                })
                break
            }
        }
        val myMovieDetailVM2 = MovieDetailVM(repo)
        myMovieDetailVM2.getFavorit(550).observe(activity, {
            Assert.assertEquals(repo.movieExist(550), it)
        })
    }


    @Test
    fun testTVDetailVM() {
        Assert.assertEquals(MyObj.isOnline(activity), true)
        val myTVDetailVM = TVDetailVM(repo)
        myTVDetailVM.getTV(550).observe(activity, {
            Assert.assertEquals(it, repo.getTVDetail(550))
        })

        val tv1 = repo.getTVDetail(550)
        repo.addTV(TVDetailMod.convertToFav(tv1))


        while (true) {
            val randomID = Random.nextInt()
            if (randomID != 550) {
                myTVDetailVM.getFavorit(randomID).observe(activity, {
                    Assert.assertEquals(repo.tvExist(randomID), it)
                })
                break
            }
        }
        val myTVDetailVM2 = TVDetailVM(repo)
        myTVDetailVM2.getFavorit(550).observe(activity, {
            Assert.assertEquals(repo.tvExist(550), it)
        })
    }
}