package id.chainlizard.saltiesmovie.functions

import androidx.test.espresso.IdlingResource
import androidx.test.espresso.idling.CountingIdlingResource

object MyIdlingResource {
    private val countingIdlingResource = CountingIdlingResource("salties")
    val idlingresource: IdlingResource
        get() = countingIdlingResource

    fun increment() {
        countingIdlingResource.increment()
    }

    fun decrement() {
        countingIdlingResource.decrement()
    }
}