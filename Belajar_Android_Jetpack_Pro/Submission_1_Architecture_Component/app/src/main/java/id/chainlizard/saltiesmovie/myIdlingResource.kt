package id.chainlizard.saltiesmovie

import androidx.test.espresso.IdlingResource
import androidx.test.espresso.idling.CountingIdlingResource

object myIdlingResource {
    private val RESOURCE = "salties"
    private val countingIdlingResource = CountingIdlingResource(RESOURCE)

    val idlingresource: IdlingResource
        get() = countingIdlingResource

    fun increment() {
        countingIdlingResource.increment()
    }

    fun decrement() {
        countingIdlingResource.decrement()
    }
}