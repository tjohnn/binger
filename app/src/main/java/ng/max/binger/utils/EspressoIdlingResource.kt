package ng.max.binger.utils

import android.support.test.espresso.IdlingResource

object EspressoIdlingResource {

    private const val RESOURCE = "GLOBAL"

    private val mCountingIdlingResource = CountingIdlingResource(RESOURCE)

    val idlingResource: IdlingResource
        get() = mCountingIdlingResource

    fun increment() = mCountingIdlingResource.increment()


    fun decrement() = mCountingIdlingResource.decrement()


}
