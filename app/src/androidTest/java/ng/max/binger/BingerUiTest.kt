package ng.max.binger

import android.support.annotation.NonNull
import android.support.test.InstrumentationRegistry.getInstrumentation
import android.support.test.espresso.*
import android.support.test.espresso.Espresso.onView
import android.support.test.espresso.Espresso.openActionBarOverflowOrOptionsMenu
import android.support.test.filters.LargeTest
import android.support.test.runner.AndroidJUnit4
import ng.max.binger.utils.EspressoIdlingResource
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import android.support.test.espresso.action.ViewActions.click
import android.support.test.espresso.assertion.ViewAssertions.matches
import android.support.test.espresso.contrib.RecyclerViewActions
import android.support.v7.widget.RecyclerView
import android.view.View
import org.hamcrest.Matcher
import android.support.test.espresso.matcher.BoundedMatcher
import android.support.test.espresso.matcher.ViewMatchers.*
import android.support.test.rule.ActivityTestRule
import ng.max.binger.activities.MainActivity
import ng.max.binger.utils.DrawableMatcher
import org.hamcrest.CoreMatchers.`is`
import org.hamcrest.Description
import org.junit.Rule


@RunWith(AndroidJUnit4::class)
@LargeTest
class BingerUiTest {


    @Rule
    @JvmField
    val activityTestRule: ActivityTestRule<MainActivity> = ActivityTestRule(MainActivity::class.java)

    @Before
    fun registerIdlingResource() {
        IdlingRegistry.getInstance().register(EspressoIdlingResource.idlingResource)
    }

    @Test
    fun likeShowItem_ChangesLikeIcon(){
        // like first item
        onView(withId(R.id.tvShowsToday)).perform(
                RecyclerViewActions.actionOnItemAtPosition<RecyclerView.ViewHolder>(0, clickChildViewWithId(R.id.likeButton)))

        // like second item
        onView(withId(R.id.tvShowsToday)).perform(
                RecyclerViewActions.actionOnItemAtPosition<RecyclerView.ViewHolder>(1, clickChildViewWithId(R.id.likeButton)))

        // like third item
        onView(withId(R.id.tvShowsToday)).perform(
                RecyclerViewActions.actionOnItemAtPosition<RecyclerView.ViewHolder>(2, clickChildViewWithId(R.id.likeButton)))

        // confirm that the liked shows has imageview with id with ic_favorite
        onView(withId(R.id.tvShowsToday)).check(matches(atPosition(0, hasDescendant(withDrawable(R.drawable.ic_favorite)))))
        onView(withId(R.id.tvShowsToday)).check(matches(atPosition(1, hasDescendant(withDrawable(R.drawable.ic_favorite)))))
        onView(withId(R.id.tvShowsToday)).check(matches(atPosition(2, hasDescendant(withDrawable(R.drawable.ic_favorite)))))


        // unlike the items
        onView(withId(R.id.tvShowsToday)).perform(
                RecyclerViewActions.actionOnItemAtPosition<RecyclerView.ViewHolder>(0, clickChildViewWithId(R.id.likeButton)))
        onView(withId(R.id.tvShowsToday)).perform(
                RecyclerViewActions.actionOnItemAtPosition<RecyclerView.ViewHolder>(1, clickChildViewWithId(R.id.likeButton)))
        onView(withId(R.id.tvShowsToday)).perform(
                RecyclerViewActions.actionOnItemAtPosition<RecyclerView.ViewHolder>(2, clickChildViewWithId(R.id.likeButton)))

        // confirm that the unliked show has imageview with id with ic_favorite_holo
        onView(withId(R.id.tvShowsToday)).check(matches(atPosition(0, hasDescendant(withDrawable(R.drawable.ic_favorite_holo)))))
        onView(withId(R.id.tvShowsToday)).check(matches(atPosition(1, hasDescendant(withDrawable(R.drawable.ic_favorite_holo)))))
        onView(withId(R.id.tvShowsToday)).check(matches(atPosition(2, hasDescendant(withDrawable(R.drawable.ic_favorite_holo)))))

    }



    @Test
    fun likeShowItems_AppearOnFavoritesPage_AndUnlikedItemsGetRemoved() {
        // like 3 items
        onView(withId(R.id.tvShowsToday)).perform(
                RecyclerViewActions.actionOnItemAtPosition<RecyclerView.ViewHolder>(0, clickChildViewWithId(R.id.likeButton)))
        onView(withId(R.id.tvShowsToday)).perform(
                RecyclerViewActions.actionOnItemAtPosition<RecyclerView.ViewHolder>(1, clickChildViewWithId(R.id.likeButton)))
        onView(withId(R.id.tvShowsToday)).perform(
                RecyclerViewActions.actionOnItemAtPosition<RecyclerView.ViewHolder>(2, clickChildViewWithId(R.id.likeButton)))

        // open favorites activity
        openActionBarOverflowOrOptionsMenu(getInstrumentation().targetContext)
        onView(withText(R.string.title_favorites)).perform(click())

        // confirm that favoriteTvShows recyclerview has 3 items
        onView(withId(R.id.favoriteTvShows)).check(hasTotalItems(3))

        // unlike the items
        onView(withId(R.id.favoriteTvShows)).perform(
                RecyclerViewActions.actionOnItemAtPosition<RecyclerView.ViewHolder>(2, clickChildViewWithId(R.id.likeButton)))
        onView(withId(R.id.favoriteTvShows)).perform(
                RecyclerViewActions.actionOnItemAtPosition<RecyclerView.ViewHolder>(1, clickChildViewWithId(R.id.likeButton)))
        onView(withId(R.id.favoriteTvShows)).perform(
                RecyclerViewActions.actionOnItemAtPosition<RecyclerView.ViewHolder>(0, clickChildViewWithId(R.id.likeButton)))

        // confirm that favoriteTvShows recyclerview has 0 items now
        onView(withId(R.id.favoriteTvShows)).check(hasTotalItems(0))
    }

    @Test
    fun clickOnShowItem_OpensShowDetailActivity() {
        // click on the first item in the list
        onView(withId(R.id.tvShowsToday))
                .perform(RecyclerViewActions.actionOnItemAtPosition<RecyclerView.ViewHolder>(0, click()))

        onView(withId(R.id.moviePoster)).check(matches(isDisplayed()))


    }

    /**
     * Asserts that recyclerview has the number of items given as [numOfItems]
     */
    private fun hasTotalItems(numOfItems: Int): ViewAssertion{
        return ViewAssertion { view, noViewFoundException ->
            if (noViewFoundException != null) {
                throw noViewFoundException
            }

            val recyclerView = view as RecyclerView
            val adapter = recyclerView.adapter
            assertThat(adapter!!.itemCount, `is`(numOfItems))
        }
    }

    /**
     * Checks if the drawable in an imageview matches the drawable whose id is given
     */
    private fun withDrawable(resourceId: Int): Matcher<View> {
        return DrawableMatcher(resourceId)
    }

    /**
     * View assertion to check if a recyclerview item has a descendant with that matches the matcher given
     */
    private fun atPosition(position: Int, @NonNull itemMatcher: Matcher<View>): Matcher<View> {
        checkNotNull(itemMatcher)
        return object : BoundedMatcher<View, RecyclerView>(RecyclerView::class.java) {
            override fun describeTo(description: Description) {
                description.appendText("has item at position $position: ")
                itemMatcher.describeTo(description)
            }

            override fun matchesSafely(view: RecyclerView): Boolean {
                val viewHolder = view.findViewHolderForAdapterPosition(position)
                        ?: // has no item on such position
                        return false
                return itemMatcher.matches(viewHolder.itemView)
            }
        }
    }

    /**
     * Performs a click on a child view whose #id is supplied
     */
    private fun clickChildViewWithId(id: Int): ViewAction {
        return object : ViewAction {
            override fun getConstraints(): Matcher<View>? {
                return null
            }

            override fun getDescription(): String {
                return "Click on a child view with specified id."
            }

            override fun perform(uiController: UiController, view: View) {
                val v: View = view.findViewById(id)
                v.performClick()
            }
        }
    }

    @After
    fun unregisterIdlingResource() {
        IdlingRegistry.getInstance().unregister(EspressoIdlingResource.idlingResource)
    }
}