package com.apex.codeassesment

import com.apex.codeassesment.data.model.Dob
import com.apex.codeassesment.data.model.Location
import com.apex.codeassesment.data.model.Picture
import com.apex.codeassesment.ui.details.DetailsActivity
import com.apex.codeassesment.ui.main.MainActivity
import android.content.Intent
import androidx.test.core.app.ActivityScenario
import androidx.test.core.app.ApplicationProvider
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.action.ViewActions.scrollTo
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.ext.junit.rules.ActivityScenarioRule
import com.apex.codeassesment.data.model.Coordinates
import com.apex.codeassesment.data.model.Name
import com.apex.codeassesment.data.model.User
import com.apex.codeassesment.ui.location.LocationActivity
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test

class DetailsActivityTest {

    private lateinit var user: User

    @Before
    fun setUp() {
        user = User(
            picture = Picture("", "", ""),
            name = Name(title = "John", first = "John", last = "Doe"),
            gender = "Male",
            dob = Dob("1990-01-01", 30),
            location = Location(coordinates = Coordinates("12.34", "56.78"))
        )
    }

    @Test
    fun testUserDetailsDisplayed() {
        val activityScenario = ActivityScenario.launch<DetailsActivity>(
            Intent(ApplicationProvider.getApplicationContext(), DetailsActivity::class.java)
                .putExtra(MainActivity.SAVED_USER_KEY, user)
        )
        onView(withId(R.id.details_image)).check(matches(isDisplayed()))
        onView(withId(R.id.details_name)).check(matches(withText("Name: John Doe")))
        onView(withId(R.id.details_email)).check(matches(withText("Email: Male")))
        onView(withId(R.id.details_age)).check(matches(withText("Age: 30")))
        onView(withId(R.id.details_location)).check(matches(withText("Location: (12.34, 56.78)")))
        activityScenario.close()
    }

    @Test
    fun testLocationButtonClick() {
        val activityScenario = ActivityScenario.launch<DetailsActivity>(
            Intent(ApplicationProvider.getApplicationContext(), DetailsActivity::class.java)
                .putExtra(MainActivity.SAVED_USER_KEY, user)
        )
        onView(withId(R.id.details_location)).perform(scrollTo(), click())

        val expectedIntent = Intent(
            ApplicationProvider.getApplicationContext(),
            LocationActivity::class.java
        ).putExtra(DetailsActivity.LAT_KEY, "12.34")
            .putExtra(DetailsActivity.LONG_KEY, "56.78")
        val locationActivityScenario =
            ActivityScenario.launch<LocationActivity>(expectedIntent)
        locationActivityScenario.onActivity { activity ->
            val actualIntent = activity.intent
            assert(actualIntent.filterEquals(expectedIntent))
        }
        activityScenario.close()
    }
}
