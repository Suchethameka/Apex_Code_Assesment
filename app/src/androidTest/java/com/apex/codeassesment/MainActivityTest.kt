package com.apex.codeassesment

import com.apex.codeassesment.ui.main.MainActivity
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.intent.Intents
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.LargeTest
import org.junit.Test
import org.junit.runner.RunWith
import androidx.test.ext.junit.rules.ActivityScenarioRule
import org.junit.After
import org.junit.Before
import org.junit.Rule

@RunWith(AndroidJUnit4::class)
@LargeTest
class MainActivityTest {

    @get:Rule
    val activityRule = ActivityScenarioRule(MainActivity::class.java)

    @Before
    fun setUp(){
        Intents.init()
    }

    @Test
    fun testUserListButton() {
        onView(withId(R.id.main_user_list_button)).perform(click())
        onView(withId(R.id.main_user_list)).check(matches(isDisplayed()))
    }

    @After
    fun tearDown() {
        Intents.release()
    }

}
