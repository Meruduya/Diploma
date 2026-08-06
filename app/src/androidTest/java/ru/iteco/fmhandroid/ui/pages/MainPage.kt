package ru.iteco.fmhandroid.ui.pages

import android.view.View
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.isDisplayed
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.espresso.matcher.ViewMatchers.withText
import org.hamcrest.Matcher
import org.hamcrest.Matchers.allOf
import ru.iteco.fmhandroid.R
import ru.iteco.fmhandroid.ui.data.Waiters

class MainPage {

    private val mainMenuButton: Matcher<View> = withId(R.id.main_menu_image_button)
    private val authorizationButton: Matcher<View> = withId(R.id.authorization_image_button)
    private val ourMissionButton: Matcher<View> = withId(R.id.our_mission_image_button)
    private val allNewsLink: Matcher<View> = allOf(withId(R.id.all_news_text_view), isDisplayed())

    fun waitForMainScreen(): MainPage {
        Waiters.waitForDisplayedView(mainMenuButton, 20000)
        return this
    }
    fun checkMainScreenIsDisplayed(): MainPage {
        onView(mainMenuButton).check(matches(isDisplayed()))
        return this
    }

    fun openAllNews(): MainPage {
        onView(allNewsLink).perform(click())
        return this
    }

    fun openMainMenu(): MainPage {
        onView(mainMenuButton).perform(click())
        return this
    }

    fun selectMenuItem(itemTitle: String): MainPage {
        onView(withText(itemTitle)).perform(click())
        return this
    }

    fun openOurMission(): MainPage {
        onView(ourMissionButton).perform(click())
        return this
    }

    fun logOut(): MainPage {
        onView(authorizationButton).perform(click())
        onView(withText("Log out")).perform(click())
        return this
    }
}